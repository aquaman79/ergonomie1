package main.java.com.ubo.tp.message.ihm.abonneComponent;

import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class AbonneListView {
    private JPanel contentPane;

    private Box vBox;

    private IAbonneObserver abonneObserver;

    public AbonneListView(IAbonneObserver abonneObserver) {
        this.abonneObserver = abonneObserver;
    }

    public void initGUI() {
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();

        contentPane.add(vBox);
    }

    public void addAbonnes(Set<User> users, User currentUser) {
        AbonneView abonneView = new AbonneView(abonneObserver);
        for(User user: users) {
            abonneView.initGUI(user.getName(), user.getUserTag(), currentUser.isFollowing(user));
            vBox.add(abonneView.getContentPane());
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
