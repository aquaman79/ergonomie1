package main.java.com.ubo.tp.message.ihm.utilisateurComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtilisateurView {
    private JPanel contentPane;

    private IUtilisateurObserver abonneObserver;

    private Box vBox;

    private Box hBox;

    private JButton btAbonner;

    private  JButton btDesabonner;

    public UtilisateurView(IUtilisateurObserver abonneObserver) {
        this.abonneObserver = abonneObserver;
    }

    public void initGUI(String username, String tag, boolean estSuivi) {
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();
        hBox = Box.createHorizontalBox();

        JLabel nomLabel = new JLabel(username);
        JLabel tagLabel = new JLabel("@"+ tag);
        btAbonner = new JButton("S'abonner");
        btDesabonner = new JButton("Se désabonner");

        vBox.add(nomLabel);
        vBox.add(tagLabel);
        hBox.add(vBox);
        hBox.add(estSuivi ? btDesabonner : btAbonner);

        btAbonner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abonneObserver.saveFollewer(username,tag);
                System.out.println("s'abonner");
            }
        });

        btDesabonner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abonneObserver.removeFollewer(username,tag);
                System.out.println("Desabonner");
            }
        });

        contentPane.add(hBox);
        //hBox.add();
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
