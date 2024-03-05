package main.java.com.ubo.tp.message.core.database;

import java.util.Set;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

/**
 * Interface de la base de données de l'application.
 *
 * @author S.Lucas
 */
public interface IDatabase {

	/**
	 * Ajoute un observateur sur les modifications de la base de données.
	 *
	 * @param observer
	 */
	void addObserver(IDatabaseObserver observer);

	/**
	 * Supprime un observateur sur les modifications de la base de données.
	 *
	 * @param observer
	 */
	void deleteObserver(IDatabaseObserver observer);

	/**
	 * Retourne la liste des utilisateurs
	 */
	Set<User> getUsers();

	/**
	 * Retourne la liste des messages
	 */
	Set<Message> getMessages();

	/**
	 * Ajoute un message à la base de données.
	 *
	 * @param messageToAdd
	 */
	void addMessage(Message messageToAdd);

	/**
	 * Supprime un message de la base de données.
	 *
	 * @param messageToRemove
	 */
	void removeMessage(Message messageToRemove);

	/**
	 * Modification d'un message de la base de données. <br/>
	 * <i>Normalement peu probable qu'un message soit modifié...</i>
	 *
	 * @param messageToModify
	 */
	void modifiyMessage(Message messageToModify);

	/**
	 * Ajoute un utilisateur à la base de données.
	 *
	 * @param userToAdd
	 */
	void addUser(User userToAdd);

	/**
	 * Supprime un utilisateur de la base de données.
	 *
	 * @param userToRemove
	 */
	void removeUser(User userToRemove);

	/**
	 * Modification d'un utilisateur de la base de données.
	 *
	 * @param userToModify
	 */
	void modifiyUser(User userToModify);

	/**
	 * Supprime l'intégralité des messages enregistrés.
	 */
	void clearMessages();

	/**
	 * Supprime l'intégralité des utilisateurs enregistrés.
	 */
	void clearUsers();

	/**
	 * Supprime l'intégralité des données.
	 */
	void clear();

	/**
	 * Retourne tous les Messages présents en base ayant le tag donné
	 *
	 * @param tag , tag à rechercher.
	 */
	Set<Message> getMessagesWithTag(String tag);

	/**
	 * Retourne tous les Messages présents en base ayant le tag utilisateur donné
	 *
	 * @param userTag , tag utilisateur à rechercher.
	 */
	Set<Message> getMessagesWithUserTag(String userTag);

	/**
	 * Retourne tous les Messages d'un utilisateur.
	 *
	 * @param user , utilisateur dont les messages sont à rechercher.
	 */
	Set<Message> getUserMessages(User user);

	/**
	 * Retourne tous les utilisateurs suivant l'utilisateur donnée
	 *
	 * @param user , utilisateur dont les followers sont à rechercher.
	 */
	Set<User> getFollowers(User user);

	/**
	 * Retourne le nombre de followers pour l'utilisateur donné.
	 *
	 * @param user , utilisateur dont le nombre de followers est à rechercher.
	 */
	int getFollowersCount(User user);

	/**
	 * Retourne l'utilisateur inconnu du système.
	 */
	public User getUnknowUser();

}
