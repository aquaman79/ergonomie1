package main.java.com.ubo.tp.message.ihm.rechercheComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RechercheView {
    private JPanel contentPane;
    private JTextField champSaisie;
    private JButton boutonRecherche;

    IRechercheObserver rechercheObserver;

    public RechercheView(IRechercheObserver rechercheObserver) {
        this.rechercheObserver = rechercheObserver;
    }

    public void initGUI() {
        contentPane = new JPanel(new FlowLayout());

        champSaisie = new JTextField(20);
        boutonRecherche = new JButton("Search");

        boutonRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texteRecherche = champSaisie.getText();
                rechercheObserver.filterMessages(texteRecherche);
            }
        });

        contentPane.add(champSaisie);
        contentPane.add(boutonRecherche);
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}