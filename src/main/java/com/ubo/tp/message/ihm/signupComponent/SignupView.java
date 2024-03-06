package main.java.com.ubo.tp.message.ihm.signupComponent;

import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupView {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;

    private IDatabaseObserver databaseObserver;


    private ISignupObserver signupObserver;
    private String avatarPath;

    public void setSignupObserver(ISignupObserver observer) {
        this.signupObserver = observer;
    }

    public SignupView(ISignupObserver signupObserver) {
        this.signupObserver = signupObserver;
    }

    /**
     * Create the frame.
     */
    public void initGUI() {
        // Configuration initiale du cadre et du panneau
        frame = new JFrame("Inscription");
      /*  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1014, 597); // Taille initiale
        frame.setLocationRelativeTo(null); // Centrer
        frame.setResizable(false);
       */

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


        JLabel avatarPreview = new JLabel();
        int avatarSize = 200;
        avatarPreview.setBounds(width - avatarSize - 50, height / 4, avatarSize, avatarSize);
        avatarPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPane.add(avatarPreview);

        JButton btnChooseAvatar = new JButton("Choisir Avatar");
        btnChooseAvatar.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnChooseAvatar.setBounds(width - avatarSize - 50, height / 4 + avatarSize + 10, avatarSize, 50);
        contentPane.add(btnChooseAvatar);

        JButton btnSignup = new JButton("S'inscrire");
        btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnSignup.setBounds((width - textFieldWidth) / 2, (int) (height / 4 + 120), textFieldWidth, 50);
        contentPane.add(btnSignup);



        JButton btnSignin = new JButton("Se connecter");
        btnSignin.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnSignin.setBounds((width - textFieldWidth) / 2, (int) (height / 4 + 180), textFieldWidth, 50); // Notez le changement de 120 à 180 dans la coordonnée y
        contentPane.add(btnSignin);

        // Ajouter les écouteurs d'événements
        btnChooseAvatar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Sélectionnez votre avatar");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif"));
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    ImageIcon icon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
                    Image image = icon.getImage().getScaledInstance(avatarPreview.getWidth(), avatarPreview.getHeight(), Image.SCALE_SMOOTH);
                    avatarPreview.setIcon(new ImageIcon(image));
                }
            }
        });

        btnSignin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implémentation de la logique d'inscription
                String userName = textField.getText();
                String tag = new String(tagField.getText());
                if (signupObserver != null) {
                    signupObserver.onSignupAttempt(userName, tag, avatarPath);
                }
            }
        });

        btnSignin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implémentation de la logique d'inscription
                System.out.println("boutton se connecter");
            }
        });


        //frame.setVisible(true);
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
                JFrame frame = SignupView.this.frame;
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - frame.getWidth()) / 6,
                        (screenSize.height - frame.getHeight()) / 4);

                // Affichage
                SignupView.this.frame.setVisible(true);
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }


}