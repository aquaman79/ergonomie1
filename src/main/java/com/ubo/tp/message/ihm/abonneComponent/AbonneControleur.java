package main.java.com.ubo.tp.message.ihm.abonneComponent;

import main.java.com.ubo.tp.message.core.EntityManager;
import main.java.com.ubo.tp.message.core.database.IDatabase;
import main.java.com.ubo.tp.message.datamodel.User;
import main.java.com.ubo.tp.message.ihm.rechercheComponent.RechercheView;

import javax.swing.*;

public class AbonneControleur implements IAbonneObserver {

    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    protected User user;

    private RechercheView rechercherView ;

    public AbonneControleur(IDatabase database,User user ) {
        this.mDatabase = database;
        this.user  = user ;
    }

    protected void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFollewer(String name, String tag){
        for(User user : this.mDatabase.getUsers()){
            if(user.getUserTag().equals(tag)){
                if(user.getName().equals(name)){
                    if(!this.user.getUserTag().equals(tag))
                        this.user.addFollowing(tag);
                        mDatabase.modifiyUser(this.user);
                }
            }
        }
    }

    @Override
    public void removeFollewer(String name, String tag){
        for(User user : this.mDatabase.getUsers()){
            if(user.getUserTag().equals(tag)){
                if(user.getName().equals(name)){
                    for(String tags : this.user.getFollows()){
                        if(tags.equals(tag)){
                            this.user.removeFollowing(tag);
                            mDatabase.modifiyUser(this.user);
                        }
                    }
                }
            }
        }
    }

}
