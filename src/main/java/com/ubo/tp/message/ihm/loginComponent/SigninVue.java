package main.java.com.ubo.tp.message.ihm.loginComponent;

import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SigninVue {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;

    private  IDatabaseObserver databaseObserver;
    private ISigninObserver siggninObserver;



    public IDatabaseObserver getDatabaseObserver() {
        return databaseObserver;
    }

    public void setDatabaseObserver(IDatabaseObserver databaseObserver) {
        this.databaseObserver = databaseObserver;
    }

    public ISigninObserver getSiggninObserver() {
        return siggninObserver;
    }

    public void setSiggninObserver(ISigninObserver siggninObserver) {
        this.siggninObserver = siggninObserver;
    }

    /**
     * Launch the application.
     */
    public SigninVue() {
    }

    /**
     * Create the frame.
     */
    public void initGUI() {
            // Configuration initiale du cadre et du panneau
            frame = new JFrame("Authentification");
            /*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1014, 597); // Taille initiale
            frame.setLocationRelativeTo(null); // Centrer
            frame.setResizable(false);*/

            contentPane = new JPanel(null) {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(1014, 597);
                }
            };
            frame.setContentPane(contentPane);

            int width = contentPane.getPreferredSize().width;
            int height = contentPane.getPreferredSize().height;

// Calcul de la largeur pour les champs de texte
            int textFieldWidth = width / 3;
            int fieldX = (width - textFieldWidth) / 2; // Position X centrée pour les champs de saisie

// Étiquette pour le nom d'utilisateur
            JLabel lblUsername = new JLabel("Nom d'utilisateur :");
            lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
// Positionner l'étiquette directement à gauche du champ de saisie, en ajustant en fonction de la largeur estimée de l'étiquette
            lblUsername.setBounds(fieldX - 220, height / 4 - 30, 200, 30);
            contentPane.add(lblUsername);

// Champ nom d'utilisateur
            JTextField textField = new JTextField();
            textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
            textField.setBounds(fieldX, height / 4 - 30, textFieldWidth, 50);
            contentPane.add(textField);

// Étiquette pour le tag
            JLabel lblTag = new JLabel("Tag :");
            lblTag.setFont(new Font("Tahoma", Font.PLAIN, 20));
// Positionner l'étiquette directement à gauche du champ de saisie pour le tag, similairement au nom d'utilisateur
            lblTag.setBounds(fieldX - 220, height / 4 + 30, 200, 30);
            contentPane.add(lblTag);

// Champ pour le tag
            JTextField tagField = new JTextField();
            tagField.setFont(new Font("Tahoma", Font.PLAIN, 32));
            tagField.setBounds(fieldX, height / 4 + 30, textFieldWidth, 50);
            contentPane.add(tagField);

            JButton btnSignup = new JButton("S'authentifier");
            btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 26));
            btnSignup.setBounds((width - textFieldWidth) / 2, (int) (height / 4 + 120), textFieldWidth, 50);
            contentPane.add(btnSignup);

            // Ajouter les écouteurs d'événements
            btnSignup.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Implémentation de la logique d'inscription
                    String userName = textField.getText();
                    String tag = new String(tagField.getText());
                    if (siggninObserver != null) {
                        siggninObserver.onSigninAttempt(userName, tag);
                    }
                }
            });

           // frame.setVisible(true);
        }
    public void showGUI() {
        if (this.frame == null) {
            this.initGUI();
        }

        // Affichage dans l'EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Custom de l'affichage
                JFrame frame = SigninVue.this.frame;
                Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - frame.getWidth()) / 6,
                        (screenSize.height - frame.getHeight()) / 4);

                // Affichage
                SigninVue.this.frame.setVisible(true);
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}