package main.java.com.ubo.tp.message.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Classe utilitaire pour la gestion des fichiers.
 *
 * @author S.Lucas
 */
public class FilesUtils {
	/**
	 * Déplacement du fichier source vers le fichier destination.
	 *
	 * @param sourceFileName , Chemin du fichier source
	 * @param destFileName   , Chemin du fichier de destination
	 * @return un booléen indiquant si le déplacement s'est déroulé avec succès.
	 */
	public static boolean moveFile(File sourceFile, String destFileName) {
		boolean isOk = false;

		// Copie du fichier
		isOk = copyFile(sourceFile.getAbsolutePath(), destFileName);

		// Si c'est bon, suppression de la source
		if (isOk) {
			sourceFile.delete();
		}

		return isOk;
	}

	/**
	 * Déplacement du fichier source vers le fichier destination.
	 *
	 * @param sourceFileName , Chemin du fichier source
	 * @param destFileName   , Chemin du fichier de destination
	 * @return un booléen indiquant si la copie s'est déroulée avec succès.
	 */
	public static boolean copyFile(String sourceFileName, String destFileName) {
		boolean isOk = false;
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			// Init
			fis = new FileInputStream(sourceFileName);
			in = fis.getChannel();
			fos = new FileOutputStream(destFileName);
			out = fos.getChannel();

			// Transfert des contenus
			in.transferTo(0, in.size(), out);

			isOk = true;
		} catch (Throwable t) {
			System.err.println("Erreur lors de la copie du fichier '" + sourceFileName + "'");
			t.printStackTrace();
		} finally {
			// Fermeture des flux
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// NA
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// NA
				}
			}
			// Fermeture des flux
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// NA
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// NA
				}
			}
		}

		return isOk;
	}
}
