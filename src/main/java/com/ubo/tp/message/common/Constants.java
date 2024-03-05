package main.java.com.ubo.tp.message.common;

import java.util.UUID;

/**
 * Classe de contantes de l'appli.
 *
 * @author S.Lucas
 */
public interface Constants {
	/**
	 * Extension des fichiers XML des User
	 */
	public static final String USER_FILE_EXTENSION = "usr";

	/**
	 * Extension des fichiers XML des Message
	 */
	public static final String MESSAGE_FILE_EXTENSION = "msg";

	/**
	 * Répertoire des fichiers temporaires du système.
	 */
	public static final String SYSTEM_TMP_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * Séparateur de fichier du système.
	 */
	public static final String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * Caractère pour délimiter les tags référencant des utilisateurs.
	 */
	public static final String USER_TAG_DELIMITER = "@";

	/**
	 * Caractère pour délimiter les tags référencant des mots-clés.
	 */
	public static final String WORD_TAG_DELIMITER = "#";

	/**
	 * Identifiant de l'utilisateur inconnu.
	 */
	public static final UUID UNKNONWN_USER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

	/**
	 * Fichier de configuration de l'application.
	 */
	public static final String CONFIGURATION_FILE = "src/resources/configuration.properties";

	/**
	 * Clé de configuration pour la sauvegarde du répertoire d'échange.
	 */
	public static final String CONFIGURATION_KEY_EXCHANGE_DIRECTORY = "EXCHANGE_DIRECTORY";

	/**
	 * Clé de configuration pour l'UI
	 */
	public static final String CONFIGURATION_KEY_UI_CLASS_NAME = "UI_CLASS_NAME";

	/**
	 * Clé de configuration pour le mode bouchoné
	 */
	public static final String CONFIGURATION_KEY_MOCK_ENABLED = "MOCK_ENABLED";
}
