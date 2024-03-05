package main.java.com.ubo.tp.message.datamodel.converter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import main.java.com.ubo.tp.message.common.Constants;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.message.MessageXml;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.user.UserXml;

/**
 * Classe de gestion des conversion des objets entre le datamodle et les bean
 * XML.
 *
 * @author S.Lucas
 */
public class XmlbeanDatamodelConverter {

	/**
	 * Conversion du modèle de donnée vers le bean du fichier XML.
	 *
	 * @param messageToConvert
	 */
	public static MessageXml convertAsXmlMessage(Message messageToConvert) {
		MessageXml messageXml = new MessageXml();
		messageXml.setID(messageToConvert.getUuid().toString());
		messageXml.setSender(messageToConvert.getSender().getUuid().toString());
		messageXml.setEmissionDate(messageToConvert.getEmissionDate());
		messageXml.setText(messageToConvert.getText());

		return messageXml;
	}

	/**
	 * Conversion du fichier XML vers le modèle de donnée.<br/>
	 * <i>NB, La map doit au moins contenir l'utilisateur inconnu</i>
	 *
	 * @param messageToConvert , Message à convertir.
	 * @param userMap          , Map contenant les utilisateurs enregistrés en
	 *                         fonction de leur UUID.
	 */
	public static Message convertAsModelMessage(MessageXml messageToConvert, Map<UUID, User> userMap) {
		UUID messageUuid = UUID.fromString(messageToConvert.getID());

		// Récupération de l'utilisateur source du message
		User messageUser = userMap.get(UUID.fromString(messageToConvert.getSender()));
		if (messageUser == null) {
			messageUser = userMap.get(Constants.UNKNONWN_USER_UUID);
		}

		return new Message(messageUuid, messageUser, messageToConvert.getEmissionDate(), messageToConvert.getText());
	}

	/**
	 * Conversion du modèle de donnée vers le bean du fichier XML.
	 *
	 * @param userToConvert
	 */
	public static UserXml convertAsXmlUser(User userToConvert) {
		UserXml userXml = new UserXml();
		userXml.setID(userToConvert.getUuid().toString());
		userXml.setUserTag(userToConvert.getUserTag());
		userXml.setUserPassword(userToConvert.getUserPassword());
		userXml.setName(userToConvert.getName());
		userXml.setAvatarPath(userToConvert.getAvatarPath());

		for (String followedTag : userToConvert.getFollows()) {
			userXml.getFollows().add(followedTag);
		}

		return userXml;
	}

	/**
	 * Conversion du fichier XML vers le modèle de donnée.
	 *
	 * @param userToConvert
	 */
	public static User convertAsModelUser(UserXml userToConvert) {
		UUID userUuid = UUID.fromString(userToConvert.getID());
		Set<String> follows = new HashSet<>(userToConvert.getFollows());

		return new User(userUuid, userToConvert.getUserTag(), userToConvert.getUserPassword(), userToConvert.getName(),
				follows, userToConvert.getAvatarPath());
	}

}
