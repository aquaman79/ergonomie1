package main.java.com.ubo.tp.message.ihm.messageComponent;

import main.java.com.ubo.tp.message.ihm.loginComponent.ISigninObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageInputView {
    private JPanel contentPane;
    private JTextField senderField;
    private JTextArea messageTextArea;
    private JTextField timeField;
    private JButton sendButton;

    private MessageObserver messageObserver;


    public MessageInputView(MessageObserver messageObserver) {
       // this.messageControleur = messageControleur;
        this.messageObserver = messageObserver;
        initGUI();
    }

    public  void initGUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        // Créer et configurer les champs de saisie
        senderField = new JTextField();
        senderField.setFont(new Font("Tahoma", Font.PLAIN, 50));
        messageTextArea = new JTextArea(5, 20);
        messageTextArea.setLineWrap(true);
        timeField = new JTextField();

        // Configurer le bouton d'envoi
        sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitMessage();
            }
        });

        // Panneau pour les champs de saisie
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 1));

        inputPanel.add(new JLabel("Message:"));
        inputPanel.add(new JScrollPane(messageTextArea));

        // Ajouter les composants au contentPane
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(sendButton, BorderLayout.SOUTH);
    }

    private void submitMessage() {
        // Ici, récupérez les informations saisies et utilisez votre contrôleur pour les traiter
       // String sender = senderField.getText();
        String message = messageTextArea.getText();
        System.out.println("ici");

        //String time = timeField.getText(); // Peut être optionnel selon votre implémentation
        if (messageObserver != null) {
            messageObserver.sendMessage(message);
        }

        // Vous pourriez vouloir appeler une méthode de votre contrôleur ici
        // messageControleur.sendMessage(sender, message, time);

        // Réinitialiser les champs après l'envoi
        senderField.setText("");
        //messageTextArea.setText("");
        //timeField.setText("");
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
