package main.java.com.ubo.tp.message.datamodel.jaxb;

import java.io.File;
import java.io.FileWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import main.java.com.ubo.tp.message.common.FilesUtils;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.message.MessageXml;
import main.java.com.ubo.tp.message.datamodel.jaxb.bean.user.UserXml;

/**
 * Classe de génération des fichiers XML.
 *
 * @author S.Lucas
 */
public class JaxbWriter {
	/**
	 * Génération d'un fichier pour un message ({@link MessageXml}).
	 *
	 * @param message      , MessageXml à générer.
	 * @param destFileName , Fichier de destination.
	 * @return un booléen indiquant si la génération s'est déroulée avec succès.
	 */
	public static boolean writeMessageFile(MessageXml message, String destFileName) {
		return writeFile(MessageXml.class, message, destFileName);
	}

	/**
	 * Génération d'un fichier pour un utilisateur ({@link UserXml}).
	 *
	 * @param user         , UserXml à générer.
	 * @param destFileName , Fichier de destination.
	 * @return un booléen indiquant si la génération s'est déroulée avec succès.
	 */
	public static boolean writeUserFile(UserXml user, String destFileName) {
		return writeFile(UserXml.class, user, destFileName);
	}

	/**
	 * Génération d'un fichier XML correspondant à un objet.
	 *
	 * @param jaxbContext     , Contexte JAXB pour le marshalling
	 * @param objectToMarshal , Objet à convertir en XML.
	 * @param destFileName    , Chemin du fichier de destination finale.
	 * @return un booléen indiquant si l'opération s'est déroulée avec succès.
	 */
	protected static boolean writeFile(Class<?> clazz, Object objectToMarshal, String destFileName) {
		boolean isOk = false;

		try {
			// Conf du marshaller
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Cr�ation du fichier XML temporaire
			File tmpFile = File.createTempFile("message", ".tmp");
			marshaller.marshal(objectToMarshal, new FileWriter(tmpFile.getAbsolutePath()));

			// Si la g�n�ration s'est bien pass�e, d�placement du
			// fichier
			if (tmpFile.exists()) {
				isOk = FilesUtils.moveFile(tmpFile, destFileName);
			}
		} catch (Throwable t) {
			System.err.println("Erreur lors de la génération du fichier pour l'objet : '" + objectToMarshal + "'");
			t.printStackTrace();
		}

		return isOk;
	}

}
