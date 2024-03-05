package main.java.com.ubo.tp.message.ihm.messageComponent;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MessageMainView {
    private Message message;
    private User user;
    private JPanel contentPane;

    private Box vBox;

    private Box vBoxMessages;

    MessageObserver messageObserver;

    public MessageMainView(MessageObserver messageObserver, User user, Message message) {
        this.messageObserver = messageObserver;
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

        MessageInputView messageInputView= new MessageInputView(messageObserver);
        messageInputView.initGUI();
        vBox.add(vBoxMessages);
        vBox.add(messageInputView.getContentPane());

        contentPane.add(vBox);

        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void addMessage(Message message) {
        MessageView messageView = new MessageView();
        messageView.initGUI(user.getName(), message.getText(), LocalTime.now().toString());

        vBoxMessages.add(messageView.getContentPane());
    }
}
