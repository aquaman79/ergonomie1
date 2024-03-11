package main.java.com.ubo.tp.message.ihm.profilComponent;

import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;

public class ProfilView {
    private JPanel contentPane;

    private Box vBox;

    private IProfilObserver profilObserver;

    public ProfilView(IProfilObserver profilObserver) {
        this.profilObserver = profilObserver;
    }

    public void initGUI(User user) {
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();

        JLabel titre = new JLabel("PROFIL : ");

        JLabel nom = new JLabel(user.getName());
        JLabel tag = new JLabel("@" + user.getUserTag());

        vBox.add(titre);
        vBox.add(nom);
        vBox.add(tag);

        contentPane.add(vBox);
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
