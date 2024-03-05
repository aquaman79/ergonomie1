package main.java.com.ubo.tp.message.ihm.signupComponent;

import main.java.com.ubo.tp.message.core.database.IDatabaseObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView  {

    private JFrame frame;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;

    private IDatabaseObserver databaseObserver;

    /**
     * Launch the application.
     */
    public LoginView() {
    }

    /**
     * Create the frame.
     */
    public void initGUI() {
        this.frame = new JFrame("login");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setBounds(450, 190, 1014, 597);
        this.frame.setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("nom");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lbltag = new JLabel("tag");
        lbltag.setForeground(Color.BLACK);
        lbltag.setBackground(Color.CYAN);
        lbltag.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lbltag.setBounds(250, 286, 193, 52);
        contentPane.add(lbltag);


        JLabel lblavatar = new JLabel("avatar");
        lblavatar.setForeground(Color.BLACK);
        lblavatar.setBackground(Color.CYAN);
        lblavatar.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblavatar.setBounds(250, 286, 193, 52);
        contentPane.add(lblavatar);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(545, 392, 162, 73);
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                databaseObserver.notifyUserSignin(userName,password);

            }
        });

        contentPane.add(btnNewButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        contentPane.add(label);
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
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation((screenSize.width - frame.getWidth()) / 6,
                        (screenSize.height - frame.getHeight()) / 4);

                // Affichage
                LoginView.this.frame.setVisible(true);
            }
        });
    }
}