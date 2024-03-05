package main.java.com.ubo.tp.message.ihm;

import java.io.File;

import main.java.com.ubo.tp.message.IMessageAppObserver;
import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.core.directory.IWatchableDirectory;
import main.java.com.ubo.tp.message.core.directory.WatchableDirectory;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.loginComponent.SigninControlleur;
import main.java.com.ubo.tp.message.ihm.loginComponent.SigninVue;
import main.java.com.ubo.tp.message.ihm.session.ISession;
import main.java.com.ubo.tp.message.ihm.session.ISessionObserver;
import main.java.com.ubo.tp.message.ihm.signupComponent.SignupControlleur;
import main.java.com.ubo.tp.message.ihm.signupComponent.SignupView;

import javax.swing.*;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageApp implements IDatabaseObserver,ISessionObserver {
	/**
	 * Base de données.
	 */
	protected IDatabase mDatabase;


	private SigninControlleur signinControlleur ;

	private SignupControlleur signupControlleur;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	protected MessageAppMainView mMainView;

	/**
	 * Classe de surveillance de répertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange de l'application.
	 */
	protected String mExchangeDirectoryPath;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;


	public SigninControlleur getLoginControlleur() {
		return signinControlleur;
	}

	public void setLoginControlleur(SigninControlleur signinControlleur) {
		this.signinControlleur = signinControlleur;
	}

	/**
	 * Constructeur.
	 *
	 * @param entityManager
	 * @param database
	 */
	public MessageApp(IDatabase database, EntityManager entityManager) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
		signinControlleur = new SigninControlleur(database,entityManager);
		signupControlleur = new SignupControlleur(database,entityManager);

	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation du répertoire d'échange
		this.initDirectory();

		// Initialisation de l'IHM
		this.initGui();
	//	this.loginControlleur.init();
	//	this.signupControlleur.setSession(this);
	//	this.signinControlleur.setSession(this);
		//this.signupControlleur.init();
	//	this.signinControlleur.init();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			// Utiliser le Look and Feel système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// Pour utiliser un Look and Feel spécifique, remplacez getSystemLookAndFeelClassName() par le nom de classe du Look and Feel souhaité
			// Exemple pour utiliser le Look and Feel Nimbus :
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		mMainView = new MessageAppMainView(mDatabase, mEntityManager);
		mMainView.initGUI();
		//mMainView.initGUISignup(signupControlleur);

		SignupView signupView = new SignupView(signupControlleur);
		signupView.initGUI();
		mMainView.changeCotent(signupView.getContentPane());
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
	}

	/**
	 * Indique si le fichier donné est valide pour servir de répertoire d'échange
	 *
	 * @param directory , Répertoire à tester.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du répertoire d'échange.
	 *
	 * @param directoryPath
	 */
	protected void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}

	public void show() {
		if(mMainView == null) {
			this.initGui();
		}
		mMainView.showGUI();
		//loginControlleur.show();

	//	signupControlleur.show();
	//	signinControlleur.show();
	}
//database interfce
	@Override
	public void notifyMessageAdded(Message addedMessage) {
		System.out.println("Message added");
	}

	@Override
	public void notifyMessageDeleted(Message deletedMessage) {
		System.out.println("Message deleted");
	}

	@Override
	public void notifyMessageModified(Message modifiedMessage) {
		System.out.println("Message modified");
	}

	@Override
	public void notifyUserAdded(User addedUser) {
		if(mMainView == null) {
			this.initGui();
		}

		SigninVue signinVue = new SigninVue(signinControlleur);
		signinVue.initGUI();
		mMainView.changeCotent(signinVue.getContentPane());
		System.out.println("User added");
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		System.out.println("User deleted");
	}

	@Override
	public void notifyUserModified(User modifiedUser) {
		System.out.println("User modified");
	}

	@Override
	public void notifyUserSignin(String name, String password) {

	}
//IssessionObserver
	@Override
	public void notifyLogin(User connectedUser) {
		System.out.println("login done");
	}

	@Override
	public void notifyLogout() {

	}
}
