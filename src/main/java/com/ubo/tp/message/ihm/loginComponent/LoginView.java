package main.java.com.ubo.tp.message.ihm.loginComponent;

import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;
import main.java.com.ubo.tp.message.ihm.MessageAppMainView;
import main.java.com.ubo.tp.message.ihm.session.ISessionObserver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginView  {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;

    private  IDatabaseObserver databaseObserver;

    public IDatabaseObserver getDatabaseObserver() {
        return databaseObserver;
    }

    public void setDatabaseObserver(IDatabaseObserver databaseObserver) {
        this.databaseObserver = databaseObserver;
    }

    private ISessionObserver sSessionObserver;

    /**
     * Launch the application.
     */
    public LoginView() {
    }

    /**
     * Create the frame.
     */
    public void initGUI() {
        this.frame = new JFrame("Login");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Marges ajustées pour une apparence épurée
        this.frame.setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nom d'utilisateur
        gbc.anchor = GridBagConstraints.CENTER; // Centre les éléments pour une apparence plus équilibrée
        JLabel lblUsername = new JLabel("Nom d'utilisateur:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(lblUsername, gbc);

        textField = new JTextField(10);
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(textField, gbc);

        // Mot de passe
        JLabel lblPassword = new JLabel("Mot de passe:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(lblPassword, gbc);

        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPane.add(passwordField, gbc);

        // Bouton de connexion
        btnNewButton = new JButton("Se connecter");
        btnNewButton.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.fill = GridBagConstraints.NONE; // Pas de remplissage pour le bouton pour qu'il ne s'étende pas trop horizontalement
        contentPane.add(btnNewButton, gbc);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = new String(passwordField.getPassword());
                databaseObserver.notifyUserSignin(userName,password);
            }
        });

        this.frame.pack();
        this.frame.setLocationRelativeTo(null); // Centrer la fenêtre
        this.frame.setVisible(true);
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
                JFrame frame = LoginView.this.frame;
                Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - frame.getWidth()) / 6,
                        (screenSize.height - frame.getHeight()) / 4);

                // Affichage
                LoginView.this.frame.setVisible(true);
            }
        });
    }
}