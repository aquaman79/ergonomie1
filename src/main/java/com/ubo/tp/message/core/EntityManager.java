package main.java.com.ubo.tp.message.core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import main.java.com.ubo.tp.message.common.Constants;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.directory.IWatchableDirectoryObserver;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.datamodel.converter.XmlbeanDatamodelConverter;
import main.java.com.ubo.tp.message.datamodel.jaxb.JaxbReader;
import main.java.com.ubo.tp.message.datamodel.jaxb.JaxbWriter;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.message.MessageXml;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.user.UserXml;

/**
 * Classe de gestion de la mise à jour de la base de données et de génération
 * des fichiers
 *
 * @author S.Lucas
 */
public class EntityManager implements IWatchableDirectoryObserver {

	/**
	 * Base de donnée de l'application.
	 */
	protected final IDatabase mDatabase;

	/**
	 * Chemin d'accès au répertoire d'échange.
	 */
	protected String mDirectoryPath;

	/**
	 * Map reliant les UUID aux utilisateurs associés.
	 */
	protected final Map<UUID, User> mUserMap;

	/**
	 * Map reliant les noms de fichiers aux messages associés.
	 */
	protected final Map<String, Message> mMessageFileMap;

	/**
	 * Map reliant les noms de fichiers aux utilisateurs associés.
	 */
	protected final Map<String, User> mUserFileMap;

	/**
	 * Constructeur.
	 */
	public EntityManager(IDatabase database) {
		this.mDatabase = database;
		this.mUserMap = new HashMap<>();
		this.mMessageFileMap = new HashMap<>();
		this.mUserFileMap = new HashMap<>();

		// Ajout de l'utilisateur inconnu
		User unknowUser = database.getUnknowUser();
		this.mUserMap.put(unknowUser.getUuid(), unknowUser);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyPresentFiles(Set<File> presentFiles) {
		// L'initialisation est une phase d'ajout massive
		this.notifyNewFiles(presentFiles);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyNewFiles(Set<File> newFiles) {
		//
		// Récupération des fichiers utilisateurs en premier
		// (nécessaires pour gérer les messages)
		Set<File> userFiles = this.getUserFiles(newFiles);

		// Parcours de la liste des fichiers utilisateurs
		for (File userFile : userFiles) {

			// Extraction du nouvel utilisateur
			User newUser = this.extractUser(userFile);

			if (newUser != null) {
				// Ajout de l'utilisateur
				this.mDatabase.addUser(newUser);

				// Stockage dans les maps
				mUserMap.put(newUser.getUuid(), newUser);
				mUserFileMap.put(userFile.getName(), newUser);
			}
		}

		//
		// Récupération des fichiers de Messages.
		Set<File> messageFiles = this.getMessageFiles(newFiles);

		// Parcours de la liste des nouveaux messages
		for (File messageFile : messageFiles) {

			// Extraction du nouveau message
			Message newMessage = this.extractMessage(messageFile);

			if (newMessage != null) {
				// Ajout du message
				this.mDatabase.addMessage(newMessage);

				// MAJ de la map
				this.mMessageFileMap.put(messageFile.getName(), newMessage);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyDeletedFiles(Set<File> deletedFiles) {
		//
		// Récupération des fichiers d'utilisateurs
		Set<File> userFiles = this.getUserFiles(deletedFiles);

		// Parcours de la liste des fichiers utilisateurs supprimés
		for (File deletedUserFile : userFiles) {

			// Récupération de l'utilisateur correspondant
			User deletedUser = this.mUserFileMap.get(deletedUserFile.getName());

			if (deletedUser != null) {
				// Suppression de l'utilisateur
				this.mDatabase.removeUser(deletedUser);

				// MAJ des maps
				mUserMap.remove(deletedUser.getUuid());
				mUserFileMap.remove(deletedUserFile.getName());
			}
		}

		//
		// Récupération des fichiers message supprimés
		Set<File> deletedMessageFiles = this.getMessageFiles(deletedFiles);

		// Parcours de la liste des fichiers message supprimés
		for (File deletedMessageFile : deletedMessageFiles) {

			// Récupération du message correspondant
			Message deletedMessage = this.mMessageFileMap.get(deletedMessageFile.getName());

			if (deletedMessage != null) {
				// Suppression du message
				this.mDatabase.removeMessage(deletedMessage);

				// MAJ de la map
				mMessageFileMap.remove(deletedMessageFile.getName());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyModifiedFiles(Set<File> modifiedFiles) {
		//
		// Récupération des utilisateurs en premier (nécessaires pour gérer
		// les
		// message)
		Set<File> userFiles = this.getUserFiles(modifiedFiles);

		// Récupération et parcours de la liste des utilisateurs modifiés
		for (User modifiedUser : this.extractAllUsers(userFiles)) {
			// Modification de l'utilisateur
			this.mDatabase.modifiyUser(modifiedUser);

			// Stockage dans la map
			mUserMap.put(modifiedUser.getUuid(), modifiedUser);
		}

		//
		// Récupération des Message.
		Set<File> messageFiles = this.getMessageFiles(modifiedFiles);

		// Récupération et parcours de la liste des messages modifiés
		for (Message modifiedMessage : this.extractAllMessages(messageFiles)) {
			// Ajout du message
			this.mDatabase.modifiyMessage(modifiedMessage);
		}
	}

	/**
	 * Extraction de tous les message d'une liste de fichier.
	 *
	 * @param allMessageFiles , Liste des fichiers de message.
	 */
	protected Set<Message> extractAllMessages(Set<File> allMessageFiles) {
		Set<Message> allMessages = new HashSet<>();

		// Parcours de tous les fichiers de message
		for (File messageFile : allMessageFiles) {
			// Extraction du message pour le fichier courant
			Message message = this.extractMessage(messageFile);

			// Si le message a bien été récupéré
			if (message != null) {
				// Sauvegarde de l'objet
				allMessages.add(message);
			}
		}

		return allMessages;
	}

	/**
	 * Extraction du fichier pour récupérer le Message correspondant. <br/>
	 * <i>Retourne <code>null</code> si un problème est rencontré</i>.
	 *
	 * @param messageFile , Fichier XML du message à ectraire.
	 */
	protected Message extractMessage(File messageFile) {
		Message newMessage = null;

		// Si le fichier est valide
		if (messageFile != null && messageFile.exists()
				&& messageFile.getName().endsWith(Constants.MESSAGE_FILE_EXTENSION)) {
			// Lecture du fichier pour récupérer le bean xml
			MessageXml xmlMessage = JaxbReader.readMessage(messageFile.getAbsolutePath());

			// Si le fichier a bien été lu
			if (xmlMessage != null) {
				// Conversion vers l'objet du modèle
				newMessage = XmlbeanDatamodelConverter.convertAsModelMessage(xmlMessage, mUserMap);
			}
		}

		return newMessage;
	}

	/**
	 * Extraction de tous les utilisateur d'une liste de fichier.
	 *
	 * @param allUserFiles , Liste des fichiers d'utilisateur.
	 */
	protected Set<User> extractAllUsers(Set<File> allUserFiles) {
		Set<User> allUsers = new HashSet<>();

		// Parcours de tous les fichiers de l'utilsiateur
		for (File userFile : allUserFiles) {
			// Extraction de l'utilisateur pour le fichier courant
			User user = this.extractUser(userFile);

			// Si l'utilisateur a bien été récupéré
			if (user != null) {
				// Sauvegarde de l'objet
				allUsers.add(user);
			}
		}

		return allUsers;
	}

	/**
	 * Extraction du fichier pour récupérer l'utilisateur correspondant. <br/>
	 * <i>Retourne <code>null</code> si un problème est rencontré</i>.
	 *
	 * @param userFile , Fichier XML de l'utilisateur à ectraire.
	 */
	protected User extractUser(File userFile) {
		User newUser = null;

		// Si le fichier est valide
		if (userFile != null && userFile.exists() && userFile.getName().endsWith(Constants.USER_FILE_EXTENSION)) {
			// Lecture du fichier pour récupérer le bean xml
			UserXml xmlUser = JaxbReader.readUser(userFile.getAbsolutePath());

			// Si le fichier a bien été lu
			if (xmlUser != null) {
				// Conversion vers l'objet du modèle
				newUser = XmlbeanDatamodelConverter.convertAsModelUser(xmlUser);
			}
		}

		return newUser;
	}

	/**
	 * Retourne la liste des fichiers de type 'Utilisateur' parmis la liste des
	 * fichiers donnés.
	 *
	 * @param allFiles , Liste complète des fichiers.
	 */
	protected Set<File> getUserFiles(Set<File> allFiles) {
		return this.getSpecificFiles(allFiles, Constants.USER_FILE_EXTENSION);
	}

	/**
	 * Retourne la liste des fichiers de type 'Message' parmis la liste des fichiers
	 * donnés.
	 *
	 * @param allFiles , Liste complète des fichiers.
	 */
	protected Set<File> getMessageFiles(Set<File> allFiles) {
		return this.getSpecificFiles(allFiles, Constants.MESSAGE_FILE_EXTENSION);
	}

	/**
	 * Retourne la liste des fichiers ayant une extension particulière parmis la
	 * liste des fichiers donnés.
	 *
	 * @param allFiles  , Liste complète des fichiers.
	 * @param extension , Extension des fichiers à récupérer.
	 */
	protected Set<File> getSpecificFiles(Set<File> allFiles, String extension) {
		Set<File> specificFiles = new HashSet<>();

		// Parcours de tous les fichiers donnés
		for (File file : allFiles) {
			// Si le fichier est un fichier ayant l'extension voulue
			if (file.getName().endsWith(extension)) {
				specificFiles.add(file);
			}
		}

		return specificFiles;
	}

	/**
	 * Configure le chemin d'accès au répertoire d'échange.
	 *
	 * @param directoryPath
	 */
	public void setExchangeDirectory(String directoryPath) {
		this.mDirectoryPath = directoryPath;
	}

	/**
	 * Génération du fichier correspondant au message.
	 *
	 * @param message
	 */
	public void writeMessageFile(Message message) {
		if (mDirectoryPath != null) {
			// Récupération du chemin pour le fichier à générer
			String messageFileName = this.getFileName(message.getUuid(), Constants.MESSAGE_FILE_EXTENSION);

			// Conversion en structure XML
			MessageXml xmlMessage = XmlbeanDatamodelConverter.convertAsXmlMessage(message);

			// Génération du fichier XML
			JaxbWriter.writeMessageFile(xmlMessage, messageFileName);
		} else {
			throw new RuntimeException("Le répertoire d'échange n'est pas configuré !");
		}
	}

	/**
	 * Génération du fichier correspondant à l'utilisateur.
	 *
	 * @param user
	 */
	public void writeUserFile(User user) {
		if (mDirectoryPath != null) {
			// Récupération du chemin pour le fichier à générer
			String userFileName = this.getFileName(user.getUuid(), Constants.USER_FILE_EXTENSION);

			// Conversion en structure XML
			UserXml xmlUser = XmlbeanDatamodelConverter.convertAsXmlUser(user);

			// Génération du fichier XML
			JaxbWriter.writeUserFile(xmlUser, userFileName);
		}
	}

	/**
	 * Retourne un chemin d'accès au fichier pour l'uuid et l'extension donnés.
	 *
	 * @param objectUuid
	 * @param fileExtension
	 */
	protected String getFileName(UUID objectUuid, String fileExtension) {
		return mDirectoryPath + Constants.SYSTEM_FILE_SEPARATOR + objectUuid + "." + fileExtension;
	}
}
