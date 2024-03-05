package main.java.com.ubo.tp.message.datamodel.jaxb;

import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import main.java.com.ubo.tp.message.common.Constants;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.message.MessageXml;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.user.UserXml;

/**
 * Classe de lecture des fichiers XML.
 *
 * @author S.Lucas
 */
public class JaxbReader {

	protected static final String JAXB_BEAN_ROOT_PACKAGE = "main.java.com.ubo.tp.message.datamodel.jaxb.bean";

	protected static final String JAXB_TWIT_BEAN_PACKAGE = JAXB_BEAN_ROOT_PACKAGE + "." + "message";

	protected static final String JAXB_USER_BEAN_PACKAGE = JAXB_BEAN_ROOT_PACKAGE + "." + "user";

	/**
	 * Lecture du fichier XML pour un {@link MessageXml}
	 *
	 * @param messageFileName
	 */
	public static MessageXml readMessage(String messageFileName) {
		MessageXml message = null;

		if (messageFileName != null && messageFileName.endsWith(Constants.MESSAGE_FILE_EXTENSION)) {
			message = (MessageXml) readFile(messageFileName, JAXB_TWIT_BEAN_PACKAGE);
		}

		return message;
	}

	/**
	 * Lecture du fichier XML pour un {@link UserXml}
	 *
	 * @param messageFileName
	 */
	public static UserXml readUser(String userFileName) {
		UserXml user = null;

		if (userFileName != null && userFileName.endsWith(Constants.USER_FILE_EXTENSION)) {
			user = (UserXml) readFile(userFileName, JAXB_USER_BEAN_PACKAGE);
		}

		return user;
	}

	/**
	 * Unmarshalling du fichier XML
	 *
	 * @param xmlFileName , Fichier XML à lire
	 * @param beanPackage , Package contenant les bean JAXB.
	 */
	protected static Object readFile(String xmlFileName, String beanPackage) {
		Object object = null;
		try {
			JAXBContext context;
			context = JAXBContext.newInstance(beanPackage);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			object = unmarshaller.unmarshal(new FileReader(xmlFileName));
		} catch (Throwable t) {
			System.err.println("Erreur de chargement du fichier : '" + xmlFileName + "'");
			t.printStackTrace();
		}
		return object;
	}

}
