package main.java.com.ubo.tp.message.ihm.signupComponent;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.session.ISession;
import main.java.com.ubo.tp.message.ihm.session.ISessionObserver;

import javax.swing.*;
import java.util.HashSet;
import java.util.UUID;

public class SignupControlleur implements IDatabaseObserver, ISignupObserver  {
    private User user ;

    /**
     * Base de données.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    private SignupView loginView ;

    private ISession session ;

    public ISession getSession() {
        return session;
    }

    public void setSession(ISession session) {
        this.session = session;
    }

    public SignupControlleur(IDatabase database, EntityManager entityManager) {
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
        //mMainView = new MessageAppMainView();
        loginView.setSignupObserver(this);
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

    }

    @Override
    public void onSignupAttempt(String username, String tag, String avatarPath) {
        for(User user : this.mDatabase.getUsers()){
            if(user.getUserTag().equals(tag)){
                onSignupFailure("Le tag est déjà utilisé.");
                return ;
            }
        }
        User newUser = new User(UUID.randomUUID(), tag, "--", username, new HashSet<>(), avatarPath);
        this.mDatabase.addUser(newUser);
        mEntityManager.writeUserFile(newUser); // Génère le fichier utilisateur
        onSignupSuccess(newUser);
    }

    @Override
    public void onSignupFailure(String errorMessage) {
        System.err.println(errorMessage);

    }

    @Override
    public void onSignupSuccess(User newUser) {
        System.out.println("Inscription réussie coté controlleur  signup");

    }
}
