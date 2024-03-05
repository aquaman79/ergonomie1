package mock;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import javax.swing.*;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

public class MessageAppMock {

	/**
	 * Fenetre du bouchon
	 */
	protected JFrame mFrame;

	/**
	 * Base de donénes de l'application.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire de bdd et de fichier.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Constructeur.
	 *
	 * @param database , Base de données de l'application.
	 */
	public MessageAppMock(IDatabase database, EntityManager entityManager) {
		this.mDatabase = database;
		this.mEntityManager = entityManager;
	}

	/**
	 * Lance l'afficahge de l'IHM.
	 */
	public void showGUI() {
		// Init auto de l'IHM au cas ou ;)
		if (mFrame == null) {
			this.initGUI();
		}

		// Affichage dans l'EDT
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Custom de l'affichage
				JFrame frame = MessageAppMock.this.mFrame;
				Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation((screenSize.width - frame.getWidth()) / 6,
						(screenSize.height - frame.getHeight()) / 4);

				// Affichage
				MessageAppMock.this.mFrame.setVisible(true);

				MessageAppMock.this.mFrame.pack();
			}
		});
	}

	/**
	 * Initialisation de l'IHM
	 */
	protected void initGUI() {
		// Création de la fenetre principale
		mFrame = new JFrame("Université bretagne occidentale");
		mFrame.setLayout(new GridBagLayout());
		JMenuBar menu = new JMenuBar();
		JMenu fichierMenu = new JMenu("Fichier");
		menu.add(fichierMenu);
		JMenu aproposMenu = new JMenu("à propos");
		//aproposMenu.addActionListener();
		menu.add(aproposMenu);

		mFrame.setJMenuBar(menu);

		//
		// Gestion de la base de données

		JLabel dbLabel = new JLabel("Database");

		Button addUserButton = new Button("Add User");
		addUserButton.setPreferredSize(new Dimension(100, 50));
		addUserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageAppMock.this.addUserInDatabase();
			}
		});

		Button addMessageButton = new Button("Add Message");
		addMessageButton.setPreferredSize(new Dimension(100, 50));
		addMessageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageAppMock.this.addMessageInDatabase();
			}
		});

		//
		// Gestion des fichiers

		JLabel fileLabel = new JLabel("Files");

		Button sendUserButton = new Button("Write User");
		sendUserButton.setPreferredSize(new Dimension(100, 50));
		sendUserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageAppMock.this.writeUser();
			}
		});

		Button sendMessageButton = new Button("Write Message");
		sendMessageButton.setPreferredSize(new Dimension(100, 50));
		sendMessageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageAppMock.this.writeMessage();
			}
		});

		//
		// Ajout des composants à la fenêtre
		this.mFrame.add(dbLabel, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		this.mFrame.add(addUserButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.mFrame.add(addMessageButton, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.mFrame.add(fileLabel, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(15, 5, 0, 5), 0, 0));
		this.mFrame.add(sendUserButton, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.mFrame.add(sendMessageButton, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

	}

	/**
	 * Ajoute un utilisateur fictif à la base de donnée.
	 */
	protected void addUserInDatabase() {
		// Création d'un utilisateur fictif
		User newUser = this.generateUser();

		// Ajout de l'utilisateur à la base
		this.mDatabase.addUser(newUser);
	}

	/**
	 * Génération et envoi d'un fichier utilisateur
	 */
	protected void writeUser() {
		// Création d'un utilisateur fictif
		User newUser = this.generateUser();

		// Génération du fichier utilisateur
		this.mEntityManager.writeUserFile(newUser);
	}

	/**
	 * Génération d'un utilisateur fictif.
	 */
	protected User generateUser() {
		int randomInt = new Random().nextInt(99999);
		String userName = "MockUser" + randomInt;
		User newUser = new User(UUID.randomUUID(), userName, "--", userName, new HashSet<>(), "");

		return newUser;
	}

	/**
	 * Ajoute un message fictif à la base de données.
	 */
	protected void addMessageInDatabase() {
		// Création 'un message fictif
		Message newMessage = this.generateMessage();

		// Ajout du message
		this.mDatabase.addMessage(newMessage);
	}

	/**
	 * Génération et envoi d'un fichier message
	 */
	protected void writeMessage() {
		// Création d'un message fictif
		Message newMessage = this.generateMessage();

		// Génération du fichier message
		this.mEntityManager.writeMessageFile(newMessage);
	}

	/**
	 * Génération d'un message fictif.
	 */
	protected Message generateMessage() {
		// Si la base n'a pas d'utilisateur
		if (this.mDatabase.getUsers().size() == 0) {
			// Création d'un utilisateur
			this.addUserInDatabase();
		}

		// Récupération d'un utilisateur au hazard
		int userIndex = new Random().nextInt(this.mDatabase.getUsers().size());
		User randomUser = new ArrayList<>(this.mDatabase.getUsers()).get(Math.max(0, userIndex - 1));

		// Création d'un message fictif
		Message newMessage = new Message(randomUser, "Message fictif!! #Mock #test ;)");

		return newMessage;
	}
}
