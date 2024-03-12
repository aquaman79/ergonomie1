package main.java.com.ubo.tp.message.ihm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.core.directory.IWatchableDirectory;
import main.java.com.ubo.tp.message.core.directory.WatchableDirectory;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.abonneComponent.AbonneControleur;

import main.java.com.ubo.tp.message.ihm.abonneComponent.AbonneListView;
import main.java.com.ubo.tp.message.ihm.abonneComponent.FollowerView;
import main.java.com.ubo.tp.message.ihm.loginComponent.SigninControlleur;
import main.java.com.ubo.tp.message.ihm.loginComponent.SigninVue;
import main.java.com.ubo.tp.message.ihm.messageComponent.MessageControleur;
import main.java.com.ubo.tp.message.ihm.messageComponent.MessageMainView;
import main.java.com.ubo.tp.message.ihm.profilComponent.ProfilControlleur;
import main.java.com.ubo.tp.message.ihm.profilComponent.ProfilView;
import main.java.com.ubo.tp.message.ihm.rechercheComponent.RechercheControlleur;
import main.java.com.ubo.tp.message.ihm.rechercheComponent.RechercheView;
import main.java.com.ubo.tp.message.ihm.session.ISession;
import main.java.com.ubo.tp.message.ihm.session.ISessionObserver;
import main.java.com.ubo.tp.message.ihm.session.Session;
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


	private SigninControlleur signinControlleur;

	private SignupControlleur signupControlleur;

	private MessageControleur messageControleur;

	private RechercheControlleur rechercheControlleur ;

	private ProfilControlleur profilControlleur;


	private AbonneControleur abonneControleur;


	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected EntityManager mEntityManager;

	protected User user;

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

	protected MessageMainView messageMainView;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;

	private ISession session;
	private Message message;

	private RechercheView rechercheView;

	private ProfilView profilView;

	private AbonneListView abonneListView;

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
		this.mDatabase.addObserver(this);
		this.mEntityManager = entityManager;
		signupControlleur = new SignupControlleur(database, entityManager);
		this.session = new Session();
		this.session.addObserver(this);
		signinControlleur = new SigninControlleur(database, entityManager, this.session);
		rechercheControlleur = new RechercheControlleur(this.mDatabase);
		profilControlleur = new ProfilControlleur();
		user = null;

		//	this.mDatabase.addObserver(this);
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
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException e) {
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
		List<JPanel> panels = new ArrayList<>();
		panels.add(signupView.getContentPane());
		mMainView.changeCotent(panels);
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
		if (mMainView == null) {
			this.initGui();
		}
		//this.initGui();
		mMainView.showGUI();
		//loginControlleur.show();

		//	signupControlleur.show();
		//	signinControlleur.show();
	}

	public void showAccueil() {
		if (mMainView == null) {
			this.initGui();
		}
		//this.initGui();
		mMainView.showAccueil();
	}

	//database interfce
	@Override
	public void notifyMessageAdded(Message addedMessage) {
		if(mMainView == null) {
			this.initGui();
		}
		this.message = addedMessage;
		if(messageMainView == null) {
			messageMainView = new MessageMainView(messageControleur, this.user, this.message);
			messageMainView.initGUI();
		}
		messageMainView.addMessage(message);
		List<JPanel> panels = new ArrayList<>();
		if(rechercheView != null) {
			panels.add(rechercheView.getContentPane());
			panels.add(messageMainView.getContentPane());
			mMainView.changeCotent(panels);
		}
		//RechercheView rechercheView = new RechercheView(rechercheControlleur);
		//rechercheView.initGUI();
	}

	@Override
	public void notifyMessageDeleted(Message deletedMessage) {
		System.out.println("Message deleted");
	}

	@Override
	public void notifyMessageModified(Message modifiedMessage) {

	}

	@Override
	public void notifyUserAdded(User addedUser) {
		if(user == null) {
			if (mMainView == null) {
				this.initGui();
			}
			//mes mDatabase.getMessages().stream().filter(m -> this.user.getFollows().contains(m.getSender().getUserTag())).collect(Collectors.toSet());
			this.user = addedUser;
			SigninVue signinVue = new SigninVue(signinControlleur);
			signinVue.initGUI();
			List<JPanel> panels = new ArrayList<>();
			panels.add(signinVue.getContentPane());
			mMainView.changeCotent(panels);
			System.out.println("User added");
			messageControleur = new MessageControleur(this.mDatabase, this.mEntityManager, addedUser);
			abonneControleur = new AbonneControleur(this.mDatabase,this.user);
			abonneListView = new AbonneListView(abonneControleur);
		} else {
			if(profilView != null) {
				abonneControleur = new AbonneControleur(this.mDatabase,this.user);
				abonneListView = new AbonneListView(abonneControleur);
				abonneListView.initGUI();
				Set<User> utilisateurs = mDatabase.getUsers().stream().filter(u -> u.getUserTag() != this.user.getUserTag()).collect(Collectors.toSet());
				abonneListView.addAbonnes(utilisateurs, user);
				List<JPanel> panelsProfil = new ArrayList<>();
				panelsProfil.add(profilView.getContentPane());
				panelsProfil.add(abonneListView.getContentPane());
				mMainView.addProfilBlock(panelsProfil);
				mMainView.refresh();

			}
		}
	}

	@Override
	public void notifyUserDeleted(User deletedUser) {
		System.out.println("User deleted");
	}

	@Override
	public void notifyUserModified(User modifiedUser) {

		profilView = new ProfilView(profilControlleur);
		profilView.initGUI(this.user);

//		abonneControleur = new AbonneControleur();
		abonneListView = new AbonneListView(abonneControleur);
		abonneListView.initGUI();
		Set<User> utilisateurs = mDatabase.getUsers().stream().filter(u -> u.getUserTag() != this.user.getUserTag()).collect(Collectors.toSet());
		abonneListView.addAbonnes(utilisateurs, user);

		messageMainView.viewMessageFiltre(mDatabase.getMessages());

		List<JPanel> panelsProfil = new ArrayList<>();
		panelsProfil.add(profilView.getContentPane());
		panelsProfil.add(abonneListView.getContentPane());

		List<JPanel> panelsAbonnes = new ArrayList<>();

		for(User follower: mDatabase.getFollowers(this.user)) {
			FollowerView followerView = new FollowerView();
			followerView.initGUI(follower.getName(), follower.getUserTag());
			panelsAbonnes.add(followerView.getContentPane());
		}

		List<JPanel> panels = new ArrayList<>();
		panels.add(rechercheView.getContentPane());
		panels.add(messageMainView.getContentPane());

		mMainView.addProfilBlock(panelsProfil);
		mMainView.addAbonnesBlock(panelsAbonnes);
		mMainView.changeCotent(panels);
		//mMainView.refresh();
	}


	@Override
	public void notifyMessageFiltred(Set<Message> message) {
		messageMainView.viewMessageFiltre(message);
		List<JPanel> panels = new ArrayList<>();
		panels.add(rechercheView.getContentPane());
		panels.add(messageMainView.getContentPane());
		mMainView.changeCotent(panels);
	}

	//IssessionObserver
	@Override
	public void notifyLogin(User connectedUser) {
		if(mMainView == null) {
			this.initGui();
		}
		this.user = connectedUser;

		profilView = new ProfilView(profilControlleur);
		profilView.initGUI(connectedUser);

//		abonneControleur = new AbonneControleur();
		abonneListView = new AbonneListView(abonneControleur);
		abonneListView.initGUI();
		Set<User> utilisateurs = mDatabase.getUsers().stream().filter(u -> u.getUserTag() != this.user.getUserTag()).collect(Collectors.toSet());
		abonneListView.addAbonnes(utilisateurs, user);


		rechercheView = new RechercheView(rechercheControlleur);
		rechercheView.initGUI();

		messageMainView = new MessageMainView(messageControleur, this.user, this.message);
		messageMainView.initGUI();

		List<JPanel> panels = new ArrayList<>();
		panels.add(rechercheView.getContentPane());
		panels.add(messageMainView.getContentPane());

		List<JPanel> panelsProfil = new ArrayList<>();
		panelsProfil.add(profilView.getContentPane());
		panelsProfil.add(abonneListView.getContentPane());

		List<JPanel> panelsAbonnes = new ArrayList<>();

		for(User follower: mDatabase.getFollowers(this.user)) {
			FollowerView followerView = new FollowerView();
			followerView.initGUI(follower.getName(), follower.getUserTag());
			panelsAbonnes.add(followerView.getContentPane());
		}

		mMainView.addProfilBlock(panelsProfil);
		mMainView.addAbonnesBlock(panelsAbonnes);
		mMainView.changeCotent(panels);
	}

	@Override
	public void notifyLogout() {
	}



}