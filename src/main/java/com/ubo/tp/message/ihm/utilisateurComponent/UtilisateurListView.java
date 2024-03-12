package main.java.com.ubo.tp.message.ihm.utilisateurComponent;

import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class UtilisateurListView {
    private JPanel contentPane;

    private Box vBox;

    private IUtilisateurObserver abonneObserver;

    public UtilisateurListView(IUtilisateurObserver abonneObserver) {
        this.abonneObserver = abonneObserver;
    }

    public void initGUI() {
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();

        contentPane.add(vBox);
    }

    public void addAbonnes(Set<User> users, User currentUser) {
        UtilisateurView utilisateurView = new UtilisateurView(abonneObserver);
        for(User user: users) {
            utilisateurView.initGUI(user.getName(), user.getUserTag(), currentUser.isFollowing(user));
            vBox.add(utilisateurView.getContentPane());
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
