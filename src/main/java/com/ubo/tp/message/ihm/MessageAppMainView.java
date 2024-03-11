package main.java.com.ubo.tp.message.ihm;


import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.loginComponent.SigninControlleur;
import main.java.com.ubo.tp.message.ihm.loginComponent.SigninVue;
import main.java.com.ubo.tp.message.ihm.signupComponent.SignupControlleur;
import main.java.com.ubo.tp.message.ihm.signupComponent.SignupView;
import mock.MessageAppMock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Classe de la vue principale de l'application.
 */
public class MessageAppMainView {
    /**
     * Fenetre du bouchon
     */
    protected JFrame mFrame;

    protected Box hBox;

    protected Box vBox;


    protected JFrame acceuilFrame;

    /**
     * Base de donénes de l'application.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire de bdd et de fichier.
     */
    protected EntityManager mEntityManager;

    private JPanel contentPaneHome;


    public MessageAppMainView(IDatabase database, EntityManager entityManager) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;

    }

    protected  void initAcceuil(){
        acceuilFrame = new JFrame("Université bretagne occidentale");
        acceuilFrame.setLayout(new GridBagLayout());



        //
        // Gestion de la base de données

        JLabel dbLabel = new JLabel("Database");

        Button addUserButton = new Button("Add User");
        addUserButton.setPreferredSize(new Dimension(100, 50));
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MessageAppMainView.this.addUserInDatabase();
            }
        });

        Button addMessageButton = new Button("Add Message");
        addMessageButton.setPreferredSize(new Dimension(100, 50));
        addMessageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MessageAppMainView.this.addMessageInDatabase();
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
                MessageAppMainView.this.writeUser();
            }
        });

        Button sendMessageButton = new Button("Write Message");
        sendMessageButton.setPreferredSize(new Dimension(100, 50));
        sendMessageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MessageAppMainView.this.writeMessage();
            }
        });

        //
        // Ajout des composants à la fenêtre
        this.acceuilFrame.add(dbLabel, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        this.acceuilFrame.add(addUserButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        this.acceuilFrame.add(addMessageButton, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      /*  this.acceuilFrame.add(fileLabel, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(15, 5, 0, 5), 0, 0));
        this.acceuilFrame.add(sendUserButton, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        this.acceuilFrame.add(sendMessageButton, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));*/


    }

    /**
     * Initialisation de l'IHM
     */
    protected void initGUI() {

        this.mFrame = new JFrame("MessageApp");
        hBox = Box.createHorizontalBox();
        vBox = Box.createVerticalBox();
        hBox.add(new JLabel());
        hBox.add(vBox);

        mFrame.getContentPane().add(hBox);
        ImageIcon icon = new ImageIcon(MessageAppMainView.class.getClassLoader().getResource("logo_20.png"));
        mFrame.setIconImage(icon.getImage());
        mFrame.setLayout(new GridBagLayout());
        JMenuBar menu = new JMenuBar();
        JMenu fichierMenu = new JMenu("Fichier");
        ImageIcon quitterIcon = new ImageIcon(MessageAppMainView.class.getClassLoader().getResource("exitIcon_20.png"));
        JMenuItem quitter = new JMenuItem("Quitter",quitterIcon);
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Quitte l'application
                System.exit(0);
            }
        });
        //ajouter selecteur de fichier
        // Création du JMenuItem "Importer un fichier"
        JMenuItem importer = new JMenuItem("Importer un fichier");

        // Ajout d'un ActionListener à l'option de menu "Importer un fichier"
        importer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Création d'un JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                // Configuration du fileChooser pour sélectionner des fichiers uniquement
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Affichage de la boîte de dialogue d'ouverture de fichier
                int returnValue = fileChooser.showOpenDialog(null);

                // Vérification si l'utilisateur a sélectionné un fichier
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Obtention du fichier sélectionné
                    File selectedFile = fileChooser.getSelectedFile();
                    // Ici, vous pouvez ajouter du code pour manipuler le fichier sélectionné
                    System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());
                }
            }
        });

        // Ajout du JMenuItem "Importer un fichier" au menu fichier
        fichierMenu.add(importer);
        fichierMenu.add(quitter);
        menu.add(fichierMenu);

        //
        JMenu aproposMenu = new JMenu("à propos");
        // Création d'un JMenuItem
        JMenuItem aproposItem = new JMenuItem("à propos");
        // Ajout de l'ActionListener au JMenuItem
        ActionListener afficherMenuListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JDialog aProposDialog = new JDialog();
                aProposDialog.setTitle("A propos");

                // Création du JPanel principal
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // Contenu
                JLabel labelUBO = new JLabel("UBO M2-TIIL");
                JLabel labelDept = new JLabel("Département Information");

                // Création de la VBox pour le texte
                Box vBoxText = Box.createVerticalBox();
                vBoxText.add(labelUBO);
                vBoxText.add(labelDept);

                // Création de la HBox pour l'icône et le texte
                Box hBoxIconAndText = Box.createHorizontalBox();
                ImageIcon imgDialog = new ImageIcon(MessageAppMainView.class.getClassLoader().getResource("logo_50.png"));
                JLabel imageLabel = new JLabel(imgDialog);
                hBoxIconAndText.add(imageLabel);
                hBoxIconAndText.add(Box.createHorizontalStrut(10)); // Espace horizontal
                hBoxIconAndText.add(vBoxText);

                // Ajout des composants à la boîte principale
                panel.add(Box.createVerticalGlue()); // Espace vertical
                panel.add(hBoxIconAndText);
                panel.add(Box.createVerticalStrut(10)); // Espace vertical
                JButton buttonOK = new JButton("OK");
                buttonOK.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aProposDialog.dispose(); // Fermer la boîte de dialogue
                    }
                });

                panel.add(buttonOK);
                panel.add(Box.createVerticalGlue()); // Espace vertical

                // Ajout du JPanel à la boîte de dialogue
                aProposDialog.getContentPane().add(panel);
                aProposDialog.pack();
                aProposDialog.setResizable(false);
                aProposDialog.setLocationRelativeTo(null);
                aProposDialog.setVisible(true);

            }
        };
        aproposItem.addActionListener(afficherMenuListener);
        // Ajout du JMenuItem au JMenu
        menu.add(aproposItem);

        mFrame.setJMenuBar(menu);
     //   mFrame.setVisible(true);
        //
        // Gestion de la base de données

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
                JFrame frame = MessageAppMainView.this.mFrame;
                Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - frame.getWidth()) / 6,
                        (screenSize.height - frame.getHeight()) / 4);

                // Affichage
                MessageAppMainView.this.mFrame.setVisible(true);

                MessageAppMainView.this.mFrame.pack();

                if(acceuilFrame ==null){
                    MessageAppMainView.this.initAcceuil();
                }

                JFrame frame2 = MessageAppMainView.this.acceuilFrame;
                frame2.setLocation((screenSize.width - frame.getWidth()) / 6,
                        (screenSize.height - frame.getHeight()) / 4);
                // Affichage
                MessageAppMainView.this.acceuilFrame.setVisible(true);
            }
        });
    }

    public void showAccueil() {
        if(acceuilFrame ==null){
            this.initAcceuil();
        }

        MessageAppMainView.this.acceuilFrame.pack();
    }

    /**
     * Ajoute un utilisateur fictif à la base de donnée.
     */
    protected void addUserInDatabase() {
        // Création d'un utilisateur fictif
      //  this.initGUI();
        //this.showGUI();
        MessageApp messageApp = new MessageApp(this.mDatabase,this.mEntityManager);
        messageApp.init();
        messageApp.show();
      //  User newUser = this.generateUser();
      //  this.initGUI();
        // Ajout de l'utilisateur à la base
      //  this.mDatabase.addUser(newUser);
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

//    public File getFolder() {
//    }


    protected void initGUIHome() {
        contentPaneHome = new JPanel(null) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1014, 597);
            }
        };

        //
        // Gestion de la base de données

        JLabel dbLabel = new JLabel("Database");

        Button addUserButton = new Button("Add User");
        addUserButton.setPreferredSize(new Dimension(100, 50));
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MessageAppMainView.this.addUserInDatabase();
            }
        });

        Button addMessageButton = new Button("Add Message");
        addMessageButton.setPreferredSize(new Dimension(100, 50));
        addMessageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MessageAppMainView.this.addMessageInDatabase();
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
                MessageAppMainView.this.writeUser();
            }
        });

        Button sendMessageButton = new Button("Write Message");
        sendMessageButton.setPreferredSize(new Dimension(100, 50));
        sendMessageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                MessageAppMainView.this.writeMessage();
            }
        });

        //
        // Ajout des composants à la fenêtre
        this.contentPaneHome.add(dbLabel, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        this.contentPaneHome.add(addUserButton, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        this.contentPaneHome.add(addMessageButton, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        this.contentPaneHome.add(fileLabel, new GridBagConstraints(0, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(15, 5, 0, 5), 0, 0));
        this.contentPaneHome.add(sendUserButton, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        this.contentPaneHome.add(sendMessageButton, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    }

    public void changeCotent(List<JPanel> panels)
    {
        vBox.removeAll();
        for(JPanel panel: panels) {
            vBox.add(panel);
        }
        mFrame.revalidate();
        mFrame.repaint();
    }

    public void addProfilBlock(JPanel panel) {
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setBackground(Color.GRAY);
        Dimension preferredSize = new Dimension(100, 20);
        panel.setPreferredSize(preferredSize);
        hBox.add(panel, 0);
    }

    public void addComponentAsFirst(JPanel panel) {
        mFrame.getContentPane().add(panel, 0);
        mFrame.revalidate();
        mFrame.repaint();
    }


}

