package main.java.com.ubo.tp.message.core.directory;

import java.io.File;
import java.util.Set;

/**
 * Interface des observeurs de répertoires
 *
 * @author S.Lucas
 */
public interface IWatchableDirectoryObserver {

	/**
	 * Notification de la liste des fichiers présents initialement dans le
	 * répertoire.
	 *
	 * @param presentFiles , Liste des fichiers présents initialement.
	 */
	void notifyPresentFiles(Set<File> presentFiles);

	/**
	 * Notification de la liste des nouveaux fichiers dans le répertoire.
	 *
	 * @param newFiles , Liste des nouveaux fichiers.
	 */
	void notifyNewFiles(Set<File> newFiles);

	/**
	 * Notification de la liste des fichiers supprimés dans le répertoire.
	 *
	 * @param deletedFiles , Liste des fichiers supprimés.
	 */
	void notifyDeletedFiles(Set<File> deletedFiles);

	/**
	 * Notification de la liste des fichiers modifiés dans le répertoire.
	 *
	 * @param modifiedFiles , Liste des fichiers modifiés.
	 */
	void notifyModifiedFiles(Set<File> modifiedFiles);
}
