package main.java.com.ubo.tp.message.ihm.messageComponent;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MessageMainView {
    private Message message;
    private User user;
    private JPanel contentPane;

    private Box vBox;

    private Box vBoxMessages;

    MessageObserver messageObserver;

    MessageControleur messageControleur;

    public MessageMainView(MessageObserver messageObserver, MessageControleur messageControleur, User user, Message message) {
        this.messageObserver = messageObserver;
        this.messageControleur = messageControleur;
        this.user = user;
        this.message = message;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void initGUI() {
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();
        vBoxMessages = Box.createVerticalBox();

        MessageView messageView = new MessageView(messageControleur);
        String msgText = this.message== null ? "" : this.message.getText();
        messageView.initGUI(user.getName(), msgText, LocalDate.now().toString());
        MessageInputView messageInputView= new MessageInputView(messageObserver);
        messageInputView.initGUI();
        vBox.add(vBoxMessages);
        vBox.add(messageInputView.getContentPane());

        contentPane.add(vBox);

        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void addMessage(Message message) {
        MessageView messageView = new MessageView(messageControleur);
        messageView.initGUI(user.getName(), message.getText(), LocalDate.now().toString());

        vBoxMessages.add(messageView.getContentPane());
    }
}
