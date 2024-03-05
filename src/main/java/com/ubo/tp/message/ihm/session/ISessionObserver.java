package main.java.com.ubo.tp.message.ihm.session;

import main.java.com.ubo.tp.message.datamodel.User;

/**
 * Interface d'observation de la session.
 *
 * @author S.Lucas
 */
public interface ISessionObserver {

	/**
	 * Notification de connection d'un utilisateur.
	 *
	 * @param connectedUser, utilisateur nouvellement connecté.
	 */
	void notifyLogin(User connectedUser);

	/**
	 * Notification de déconnection.
	 */
	void notifyLogout();
}
