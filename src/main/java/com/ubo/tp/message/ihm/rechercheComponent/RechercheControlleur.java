package main.java.com.ubo.tp.message.ihm.rechercheComponent;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;

public class RechercheControlleur implements IRechercheObserver {
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    protected User user;

    private RechercheView rechercherView ;

    public RechercheControlleur(IDatabase database) {
        this.mDatabase = database;

    }

    protected void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filterMessages(String message) {
        this.mDatabase.getMessagesContainingText(message);
    }
}
