package main.java.com.ubo.tp.message.ihm.messageComponent;


import javax.swing.*;
import java.awt.*;

public class MessageView {
    private JPanel contentPane;

    private JLabel senderLabel;
    private JLabel messageLabel;
    private JLabel timeLabel;

    private MessageControleur messageControleur;

    public MessageView(MessageControleur messageControleur) {
        this.messageControleur = messageControleur;
    }

    public void initGUI(String sender, String message, String time) {
        contentPane = new JPanel(new BorderLayout());

        // Créer les labels pour le sender, le message et l'heure
        senderLabel = new JLabel(sender);
        messageLabel = new JLabel("<html><body style='width: 200px;'>" + message + "</body></html>");
        timeLabel = new JLabel(time);

        // Aligner le contenu à gauche
        senderLabel.setHorizontalAlignment(SwingConstants.LEFT);
        messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timeLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Ajouter les labels au panel
        contentPane.add(senderLabel, BorderLayout.NORTH);
        contentPane.add(messageLabel, BorderLayout.CENTER);
        contentPane.add(timeLabel, BorderLayout.SOUTH);

        // Ajouter une marge pour l'apparence
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
