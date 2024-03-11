package main.java.com.ubo.tp.message;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.Database;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.ihm.MessageApp;
import main.java.com.ubo.tp.message.ihm.session.ISession;
import main.java.com.ubo.tp.message.ihm.session.Session;
import mock.MessageAppMock;

/**
 * Classe de lancement de l'application.
 *
 * @author S.Lucas
 */
public class MessageAppLauncher {

	/**
	 * Indique si le mode bouchoné est activé.
	 */
	protected static boolean IS_MOCK_ENABLED = false;

	/**
	 * Launcher.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		IDatabase database = new Database();
	//	ISession iSession= new Session();

		EntityManager entityManager = new EntityManager(database);

		if (IS_MOCK_ENABLED) {
			MessageAppMock mock = new MessageAppMock(database, entityManager);
			mock.showGUI();
		}

		MessageApp messageApp = new MessageApp(database, entityManager);

		//database.addObserver(messageApp);
		//iSession.addObserver(messageApp);

		messageApp.init();

		messageApp.showAccueil();

		messageApp.show();
		///ISession iSession= new Session();
		//iSession.addObserver(messageApp);
	//	LoginControlleur loginControlleur = new LoginControlleur(database, entityManager);
	//	loginControlleur.init();

	//	loginControlleur.show();

	}
}
