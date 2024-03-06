package main.java.com.ubo.tp.message.ihm.messageComponent;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;

public class MessageControlleur {
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    protected User user;

    private MessageView messageView;

    public MessageControlleur(IDatabase database, EntityManager entityManager,User user) {
        this.mDatabase = database;
        this.mEntityManager = entityManager;
        this.user = user ;
    }

    protected void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    protected void initGui() {
        //mMainView = new MessageAppMainView();
    }

    public void show() {
        if(loginView == null) {
            this.initGui();
        }
        loginView.showGUI();
    }


}
