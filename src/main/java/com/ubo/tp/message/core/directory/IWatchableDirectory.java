package main.java.com.ubo.tp.message.core.directory;

/**
 * Interface de l'entité responsable de la surveillance d'un répertoire.
 *
 * @author S.Lucas
 */
public interface IWatchableDirectory {

	/**
	 * Initialisation de la surveillance du répertoire. <br/>
	 * <i> Les observeurs sont premièrement avertis du contenu initial du
	 * répertoire, puis avertis des modifications (ajout/suppression)</i>
	 */
	public void initWatching();

	/**
	 * Arret de la surveillance du répertoire.
	 */
	public void stopWatching();

	/**
	 * Changement du répertoire de surveillance. <br/>
	 * Les fichiers présents seront considérés comme 'supprimés' donc les
	 * observateurs seront notifiés comme tel.<br/>
	 * Un appel à la méthode {@link #initWatching()} est nécessaire pour relancer la
	 * surveillance.
	 *
	 * @param directoryPath , nouveau répertoire à surveiller.
	 */
	public void changeDirectory(String directoryPath);

	/**
	 * Ajout un observateur qui sera notifié des changements dans le répertoire
	 * surveillé.
	 *
	 * @param observer
	 */
	public void addObserver(IWatchableDirectoryObserver observer);

	/**
	 * Supprime un observateur de la liste (il ne sera plus notifiés des
	 * changements).
	 *
	 * @param observer
	 */
	public void removeObserver(IWatchableDirectoryObserver observer);

}
