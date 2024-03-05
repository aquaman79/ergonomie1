package main.java.com.ubo.tp.message.ihm.loginComponent;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.MessageAppMainView;
import main.java.com.ubo.tp.message.ihm.session.Session;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LoginControlleur implements IDatabaseObserver {
    private User user ;

    /**
     * Base de données.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    private LoginView loginView ;

    private Session msession ;


    public LoginControlleur(IDatabase database, EntityManager entityManager) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
    }

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
    public void init() {
        // Init du look and feel de l'application
        this.initLookAndFeel();

        // Initialisation de l'IHM
        this.initGui();
    }

    protected void initGui() {
        loginView = new LoginView();
        //mMainView = new MessageAppMainView();
        loginView.setDatabaseObserver(this);
        loginView.initGUI();
    }

    public void show() {
        if(loginView == null) {
            this.initGui();
        }
        loginView.showGUI();
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {

    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {

    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {

    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

    }

    @Override
    public void notifyUserModified(User modifiedUser) {

    }

    @Override
    public void notifyUserSignin(String name, String password) {
        System.out.println("succes");
        Set<User> users = this.mDatabase.getUsers();
        User newUser = new User(UUID.randomUUID(), name, password, name, new HashSet<>(), "");
        for(User user : users){
            if(user.getName().equals(name)){
                if(user.getUserPassword().equals(password)){
                    System.out.println("succes");
                    msession.connect(user);
                }
            }
        }

    }


}
