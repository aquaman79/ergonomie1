package main.java.com.ubo.tp.message.core.database;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import main.java.com.ubo.tp.message.common.Constants;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

/**
 * Classe représentant les donénes chargées dans l'application.
 *
 * @author S.Lucas
 */
public class Database implements IDatabase {
	/**
	 * Liste des utilisateurs enregistrés.
	 */
	protected final Set<User> mUsers;

	/**
	 * Liste des Message enregistrés.
	 */
	protected final Set<Message> mMessages;

	/**
	 * Liste des observateurs de modifications de la base.
	 */
	protected final Set<IDatabaseObserver> mObservers;

	/**
	 * Constructeur.
	 */
	public Database() {
		mUsers = new HashSet<>();
		mMessages = new HashSet<>();
		mObservers = new HashSet<>();
	}

	@Override
	public Set<User> getUsers() {
		// Clonage pour éviter les modifications extérieures.
		return new HashSet<>(this.mUsers);
	}

	@Override
	public Set<Message> getMessages() {
		// Clonage pour éviter les modifications extérieures.
		return new HashSet<>(this.mMessages);
	}

	@Override
	public void addMessage(Message messageToAdd) {
		// Ajout du message
		this.mMessages.add(messageToAdd);

		// Notification des observateurs
		for (IDatabaseObserver observer : mObservers) {
			observer.notifyMessageAdded(messageToAdd);
		}
	}

	@Override
	public void removeMessage(Message messageToRemove) {
		// Suppression du message
		this.mMessages.remove(messageToRemove);

		// Notification des observateurs
		// Notification des observateurs
		for (IDatabaseObserver observer : mObservers) {
			observer.notifyMessageDeleted(messageToRemove);
		}
	}

	@Override
	public void modifiyMessage(Message messageToModify) {
		// Le ré-ajout va écraser l'ancienne copie.
		this.mMessages.add(messageToModify);

		// Notification des observateurs
		for (IDatabaseObserver observer : mObservers) {
			observer.notifyMessageModified(messageToModify);
		}
	}

	@Override
	public void addUser(User userToAdd) {
		// Ajout de l'utilisateur
		this.mUsers.add(userToAdd);

		// Notification des observateurs
		for (IDatabaseObserver observer : mObservers) {
			observer.notifyUserAdded(userToAdd);
		}
	}

	@Override
	public void removeUser(User userToRemove) {
		// Suppression de l'utilisateur
		this.mUsers.remove(userToRemove);

		// Notification des observateurs
		for (IDatabaseObserver observer : mObservers) {
			observer.notifyUserDeleted(userToRemove);
		}
	}

	@Override
	public void modifiyUser(User userToModify) {
		// Le ré-ajout va écraser l'ancienne copie.
		this.mUsers.add(userToModify);

		// Notification des observateurs
		for (IDatabaseObserver observer : mObservers) {
			observer.notifyUserModified(userToModify);
		}
	}

	public void loginUser(String name, String password ) {
		// Le ré-ajout va écraser l'ancienne copie.
		for(User user : this.mUsers){
			if(user.getUserPassword().equals(password)){
				if(user.getName().equals(name)){
					for (IDatabaseObserver observer : mObservers) {
						//observer.notifyUserSignin();
					}
				}
			}
		}
	}


	@Override
	public void clearMessages() {
		// Parcours de la liste clonnée des messages
		Set<Message> clonedMessages = this.mMessages;
		for (Message message : clonedMessages) {
			// Suppression de chacun des messages
			this.removeMessage(message);
		}
	}

	@Override
	public void clearUsers() {
		// Parcours de la liste clonnée des utilisateurs
		Set<User> clonedUsers = this.mUsers;
		for (User user : clonedUsers) {
			// Suppression de chacun des utlisateurs
			this.removeUser(user);
		}
	}

	@Override
	public void clear() {
		this.clearMessages();
		this.clearUsers();
	}

	@Override
	public Set<Message> getMessagesWithTag(String tag) {
		Set<Message> taggedMessages = new HashSet<>();

		// Parcours de tous les messages de la base
		for (Message message : this.getMessages()) {
			// Si le message contiens le tag demandé
			if (message.containsTag(tag)) {
				taggedMessages.add(message);
			}
		}

		return taggedMessages;
	}

	@Override
	public Set<Message> getMessagesWithUserTag(String userTag) {
		Set<Message> taggedMessages = new HashSet<>();

		// Parcours de tous les messages de la base
		for (Message message : this.getMessages()) {
			// Si le message contiens le tag utilisateur demandé
			if (message.containsUserTag(userTag)) {
				taggedMessages.add(message);
			}
		}

		return taggedMessages;
	}

	@Override
	public Set<Message> getUserMessages(User user) {
		Set<Message> userMessages = new HashSet<>();

		// Parcours de tous les messages de la base
		for (Message message : this.getMessages()) {
			// Si le messageer est celui recherché
			if (message.getSender().equals(user)) {
				userMessages.add(message);
			}
		}

		return userMessages;
	}

	@Override
	public Set<User> getFollowers(User user) {
		Set<User> followers = new HashSet<>();

		// Parcours de tous les utilisateurs de la base
		for (User otherUser : this.getUsers()) {
			// Si le l'utilisateur courant suit l'utilisateur donné
			if (otherUser.isFollowing(user)) {
				followers.add(otherUser);
			}
		}

		return followers;
	}

	public Set<User> getFollowed(User user) {
		Set<User> followers = new HashSet<>();

		// Parcours de tous les utilisateurs de la base
		for (User otherUser : this.getUsers()) {
			// Si le l'utilisateur courant suit l'utilisateur donné
			if (user.getFollows().contains(otherUser.getUserTag())) {
				followers.add(otherUser);
			}
		}

		return followers;
	}

	@Override
	public int getFollowersCount(User user) {
		return this.getFollowers(user).size();
	}

	@Override
	public User getUnknowUser() {
		return new User(Constants.UNKNONWN_USER_UUID, "<Inconnu>", "--", "<Inconnu>", new HashSet<>(), "");
	}

	@Override
	public void addObserver(IDatabaseObserver observer) {
		this.mObservers.add(observer);

		// Notification pour le nouvel observateur
		for (Message message : this.getMessages()) {
			observer.notifyMessageAdded(message);
		}

		// Notification pour le nouvel observateur
		for (User user : this.getUsers()) {
			// Pas de notification pour l'utilisateur inconnu
			if (!user.getUuid().equals(Constants.UNKNONWN_USER_UUID)) {
				observer.notifyUserAdded(user);
			}
		}
	}

	@Override
	public void deleteObserver(IDatabaseObserver observer) {
		this.mObservers.remove(observer);
	}

	/**
	 * Retourne une liste clonées des observateurs de modifications.
	 */
	protected Set<IDatabaseObserver> getObservers() {
		return new HashSet<>(this.mObservers);
	}


	public Set<Message> getMessagesContainingText(String text) {
		Set<Message> filteredMessages = new HashSet<>();
		for (Message message : this.getMessages()) {
			if (message.getText().contains(text)) {
				filteredMessages.add(message);
			}
		}
		for (IDatabaseObserver observer : mObservers) {
				observer.notifyMessageFiltred(filteredMessages);

		}
		return filteredMessages;
	}
}
