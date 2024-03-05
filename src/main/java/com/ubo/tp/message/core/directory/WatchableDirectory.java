package main.java.com.ubo.tp.message.core.directory;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe responsable de la surveillance d'un répertoire (avec notification des
 * {@link IWatchableDirectoryObserver} lors des modifications)
 *
 * @author S.Lucas
 */
public class WatchableDirectory implements IWatchableDirectory {

	/**
	 * Temps (en ms) entre deux vérification du répertoire.
	 */
	protected static final int POLLING_TIME = 1000;

	/**
	 * Chemin d'accès au repertoire à surveiller.
	 */
	protected String mDirectoryPath;

	/**
	 * Répertoire surveillé.
	 */
	protected File mDirectory;

	/**
	 * Liste des fichiers présents.
	 */
	protected Set<File> mPresentFiles;

	/**
	 * Map permettant de stocker les dates de modifications des fichiers.
	 */
	protected Map<String, Long> mFileModificationMap;

	/**
	 * Thread de surveillance du répertoire.
	 */
	protected Thread mWatchingThread;

	/**
	 * Liste des observeurs sur le contenu du répertoire.
	 */
	protected final Set<IWatchableDirectoryObserver> mObservers;

	/**
	 * Constructeur.
	 *
	 * @param directoryPath , Chemin d'accès au repertoire à surveiller.
	 */
	public WatchableDirectory(String directoryPath) {
		this.mDirectoryPath = directoryPath;
		this.mPresentFiles = new HashSet<>();
		this.mFileModificationMap = new HashMap<>();
		this.mObservers = new HashSet<>();
	}

	@Override
	public void changeDirectory(String directoryPath) {
		// Clonage de la liste pour notification
		HashSet<File> presentFiles = new HashSet<>(this.mPresentFiles);

		// Arret de la surveillance en cours
		this.stopWatching();

		// Notification de la suppression des fichiers
		if (!presentFiles.isEmpty()) {
			this.notifyDeletedFiles(presentFiles);
		}

		// Réinit du répertoire de surveillance.
		this.mDirectoryPath = directoryPath;
	}

	@Override
	public void initWatching() {
		// Chargement du répertoire
		mDirectory = new File(mDirectoryPath);

		// Si le répertoire est valide
		if (mDirectory.exists() && mDirectory.isDirectory()) {
			// Initialisation des fichiers présents
			this.initPresentFiles();

			// Démarrage de la surveillance
			this.startPolling();
		} else {
			mDirectory = null;
			System.err.println("Erreur lors du démarrage de la surveillance du répertoire : " + mDirectoryPath);
		}
	}

	/**
	 * Ajoute un fichier à la liste des ficheirs présents et stock sa date de
	 * modification.
	 */
	protected void addPresentFile(File fileToAdd) {
		// Ajout du fichier
		this.mPresentFiles.add(fileToAdd);

		// Stockage de la date de modification
		this.mFileModificationMap.put(fileToAdd.getName(), fileToAdd.lastModified());
	}

	/**
	 * Initialisation de la liste des fichiers présents (et notification aux
	 * intéressés)
	 */
	protected void initPresentFiles() {
		if (mDirectory != null) {
			// Parcours de la liste des fichiers présent
			for (File presentFile : mDirectory.listFiles()) {
				// Ajout du fichier courant
				this.addPresentFile(presentFile);
			}

			// Notification de la liste des fichiers présents
			if (!this.mPresentFiles.isEmpty()) {
				this.notifyPresentFiles(this.mPresentFiles);
			}
		}
	}

	/**
	 * Démarrage de la surveillance du répertoire
	 */
	protected void startPolling() {
		mWatchingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Attente avant la prochaine vérification
					Thread.sleep(POLLING_TIME);

					// Vérification des changements
					watchDirectory();

					// Relancement automatique
					startPolling();
				} catch (InterruptedException e) {
					System.err.println("Surveillance du répertoire interrompue.");
				}
			}
		});

		mWatchingThread.start();
	}

	/**
	 * Lancement d'une étape de surveillance (avec notification des changements aux
	 * observeurs).
	 */
	protected void watchDirectory() {
		if (mDirectory != null) {
			Set<File> presentFiles = new HashSet<>();
			Set<File> newFiles = new HashSet<>();
			Set<File> deletedFiles = new HashSet<>();
			Set<File> modifiedFiles = new HashSet<>();
			Set<File> oldFiles = new HashSet<>(this.mPresentFiles);

			// Récupération de fichiers actuellement présent
			for (File file : mDirectory.listFiles()) {
				presentFiles.add(file);
			}

			// Récupération des nouveaux fichiers
			for (File presentFile : presentFiles) {
				// Si le fichier n'était pas présent avant
				if (!oldFiles.contains(presentFile)) {
					// C'est un nouveau fichier
					newFiles.add(presentFile);
				}
			}

			// Récupération des fichiers supprimés
			for (File oldFile : oldFiles) {
				// Si le fichier n'est plus présent
				if (!presentFiles.contains(oldFile)) {
					// C'est un fichier supprimé
					deletedFiles.add(oldFile);
				}
			}

			// Récupération des fichiers modifiés
			for (File presentFile : presentFiles) {
				// Si le fichiers n'est pas nouveau
				if (!newFiles.contains(presentFile)) {
					// Récupération de la date de modification de la version
					// précédente
					Long savedLastModification = mFileModificationMap.get(presentFile.getName());

					if (savedLastModification != null) {
						// Si le fichier a été modifié depuis
						if (savedLastModification < presentFile.lastModified()) {
							// Stockage du fichier comme ayant été modifié
							modifiedFiles.add(presentFile);
						}
					}
				}
			}

			// Notification des fichiers supprimés
			if (!deletedFiles.isEmpty()) {
				this.notifyDeletedFiles(deletedFiles);
			}

			// Notification des fichiers ajoutés
			if (!newFiles.isEmpty()) {
				this.notifyNewFiles(newFiles);
			}

			// Notification des fichiers modifiés
			if (!modifiedFiles.isEmpty()) {
				this.notifyModifiedFiles(modifiedFiles);
			}

			// Mise à jour de la liste des fichiers présents
			this.mPresentFiles.clear();
			this.mFileModificationMap.clear();
			for (File file : presentFiles) {
				this.addPresentFile(file);
			}
		}
	}

	@Override
	public void stopWatching() {
		if (this.mWatchingThread != null) {
			this.mWatchingThread.interrupt();
		}
		this.mPresentFiles.clear();
	}

	@Override
	public void addObserver(IWatchableDirectoryObserver observer) {
		// Notification initiale du contenu
		if (!this.mPresentFiles.isEmpty()) {
			observer.notifyPresentFiles(this.mPresentFiles);
		}

		// Ajout du nouvel observeur
		this.mObservers.add(observer);
	}

	@Override
	public void removeObserver(IWatchableDirectoryObserver observer) {
		this.mObservers.remove(observer);
	}

	/**
	 * Notification de la liste des fichiers présents initialement dans le
	 * répertoire.
	 *
	 * @param presentFiles
	 */
	protected void notifyPresentFiles(Set<File> presentFiles) {
		// Clonage de la liste pour éviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyPresentFiles(presentFiles);
		}
	}

	/**
	 * Notification de la liste des nouveaux fichiers dans le répertoire.
	 *
	 * @param newFiles , Liste des nouveaux fichiers.
	 */
	protected void notifyNewFiles(Set<File> newFiles) {
		// Clonage de la liste pour éviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyNewFiles(newFiles);
		}
	}

	/**
	 * Notification de la liste des fichiers supprimés dans le répertoire.
	 *
	 * @param deletedFiles , Liste des fichiers supprimés.
	 */
	protected void notifyDeletedFiles(Set<File> deletedFiles) {
		// Clonage de la liste pour éviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyDeletedFiles(deletedFiles);
		}
	}

	/**
	 * Notification de la liste des fichiers modifiés dans le répertoire.
	 *
	 * @param modifiedFiles , Liste des fichiers modifiés.
	 */
	protected void notifyModifiedFiles(Set<File> modifiedFiles) {
		// Clonage de la liste pour éviter les modifications concurantes
		Set<IWatchableDirectoryObserver> clonedList = this.mObservers;

		// Parcours de la liste des observeurs pour notification
		for (IWatchableDirectoryObserver observer : clonedList) {
			observer.notifyModifiedFiles(modifiedFiles);
		}
	}
}