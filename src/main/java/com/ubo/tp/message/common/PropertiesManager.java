package main.java.com.ubo.tp.message.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe utilitaire de gestion du chargement et de la sauvegarde des
 * configuration.
 *
 * @author S.Lucas
 */
public class PropertiesManager {
	/**
	 * Chargement des propriétés de configuration de l'application (depuis un
	 * éventuel fichier de configuration).
	 */
	public static Properties loadProperties(String configurationFilePath) {
		Properties properties = new Properties();

		// Si le fichier de configuration existe
		if (new File(configurationFilePath).exists()) {
			FileInputStream in = null;
			try {
				in = new FileInputStream(configurationFilePath);
				properties.load(in);
			} catch (Throwable t) {
				System.out.println("Impossible de charger les configurations");
				t.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						System.err.println("Erreur lors de la fermeture du flux sur le fichier de configuration");
					}
				}
			}
		}

		return properties;
	}

	/**
	 * Ecriture du fichier de configuration.
	 *
	 * @param properties            , Configurations enregistrées.
	 * @param configurationFilePath , Chemin du fichier de configuration à écrire.
	 */
	public static void writeProperties(Properties properties, String configurationFilePath) {
		if (properties != null) {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(configurationFilePath);
				properties.store(out, "Configuration de l'application Message");
			} catch (Throwable t) {
				System.err.println("Impossible d'enregistrer les configurations");
				t.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (Throwable e) {
						System.err.println("Erreur lors de la fermeture du flux sur le fichier de configuration");
					}
				}
			}
		}
	}
}
