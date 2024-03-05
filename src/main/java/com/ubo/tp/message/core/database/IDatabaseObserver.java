package main.java.com.ubo.tp.message.core.database;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

/**
 * Interface des observateurs des modifications de la base de données.
 *
 * @author S.Lucas
 */
public interface IDatabaseObserver {
	/**
	 * Notification lorsqu'un Message est ajouté en base de données.
	 *
	 * @param addedMessage
	 */
	void notifyMessageAdded(Message addedMessage);

	/**
	 * Notification lorsqu'un Message est supprimé de la base de données.
	 *
	 * @param deletedMessage
	 */
	void notifyMessageDeleted(Message deletedMessage);

	/**
	 * Notification lorsqu'un Message est modifié en base de données.
	 *
	 * @param modifiedMessage
	 */
	void notifyMessageModified(Message modifiedMessage);

	/**
	 * Notification lorsqu'un utilisateur est ajouté en base de données.
	 *
	 * @param addedUser
	 */
	void notifyUserAdded(User addedUser);

	/**
	 * Notification lorsqu'un utilisateur est supprimé de la base de données.
	 *
	 * @param deletedUser
	 */
	void notifyUserDeleted(User deletedUser);

	/**
	 * Notification lorsqu'un utilisateur est modifié en base de données.
	 *
	 * @param modifiedUser
	 */
	void notifyUserModified(User modifiedUser);

	void notifyUserSignin(String name, String password);
}
