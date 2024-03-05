package main.java.com.ubo.tp.message.ihm.session;

import main.java.com.ubo.tp.message.datamodel.User;

/**
 * Interface représentant la session active.
 *
 * @author S.Lucas
 */
public interface ISession {

	/**
	 * Ajoute un observateur à la session.
	 *
	 * @param observer
	 */
	void addObserver(ISessionObserver observer);

	/**
	 * Retire un observateur à la session.
	 *
	 * @param observer
	 */
	void removeObserver(ISessionObserver observer);

	/**
	 * Connecte un utilisateur à la session.
	 *
	 * @param connectedUser
	 */
	void connect(User connectedUser);

	/**
	 * Deconnecte l'utilisateur actif.
	 */
	void disconnect();

	/**
	 * @return l'utilisateur connecté.
	 */
	User getConnectedUser();

}
