package main.java.com.ubo.tp.message.ihm.abonneComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AbonneView {
    private JPanel contentPane;

    private IAbonneObserver abonneObserver;

    private Box vBox;

    private Box hBox;

    private JButton btAbonner;

    private  JButton btDesabonner;

    public AbonneView(IAbonneObserver abonneObserver) {
        this.abonneObserver = abonneObserver;
    }

    public void initGUI(String username, String tag, boolean estSuiviInit) {
        final boolean[] estSuivi = {estSuiviInit};
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();
        hBox = Box.createHorizontalBox();

        JLabel nomLabel = new JLabel(username);
        JLabel tagLabel = new JLabel(tag);
        btAbonner = new JButton("S'abonner");
        btDesabonner = new JButton("Se désabonner");

        vBox.add(nomLabel);
        vBox.add(tagLabel);
        hBox.add(vBox);
        hBox.add(estSuivi[0] ? btDesabonner : btAbonner);

        btAbonner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estSuivi[0] = !estSuivi[0]; // Inverse l'état d'abonnement
                btAbonner.setText(estSuivi[0] ? "S'abonner" : "Se désabonner");
                if(btAbonner.getText().contains("S'abonner")){
                    abonneObserver.saveFollewer(username,tag);
                    System.out.println("s'abonner");
                } else{
                    if(btAbonner.getText().contains("Se désabonner")){
                        abonneObserver.removeFollewer(username,tag);
                        System.out.println("Desabonner");
                    }
                }
            }

        });

        btDesabonner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("okdoki");
                abonneObserver.saveFollewer(username,tag);


            }
        });

        contentPane.add(hBox);
        //hBox.add();
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
