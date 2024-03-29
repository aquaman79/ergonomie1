package main.java.com.ubo.tp.message.ihm.messageComponent;

import main.java.com.ubo.tp.message.datamodel.Message;
import main.java.com.ubo.tp.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class MessageMainView {
    private Message message;
    private User user;
    private JPanel contentPane;

    private Box vBox;

    private Box vBoxMessages;
    MessageObserver messageObserver;

    Set<Message> messageSet ;

    public MessageMainView(MessageObserver messageObserver, User user, Message message) {
        this.messageObserver = messageObserver;
        this.user = user;
        this.message = message;
        this.messageSet = new HashSet<>() ;
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

    public void addMessage( Message message) {
        MessageView messageView = new MessageView();
        this.messageSet.add(message);

        if (user != null)
        {

        for(String tags : user.getFollows()){
                if(tags.equals(message.getSender().getUserTag()) ){
                    messageView.initGUI(message.getSender().getName(), message.getText(), LocalTime.now().toString());
                    vBoxMessages.add(messageView.getContentPane());
                }
        }
        if( user.getUserTag().equals(message.getSender().getUserTag())) {
            messageView.initGUI(message.getSender().getName(), message.getText(), LocalTime.now().toString());
            if(vBoxMessages == null)
                this.initGUI();
            if(messageView!=null && vBoxMessages != null)
                vBoxMessages.add(messageView.getContentPane());
        }
        }
    }

    public void viewMessageFiltre(  Set<Message> messages){
        if(vBoxMessages == null)
            this.initGUI();
        if(this.vBoxMessages != null)
            this.vBoxMessages.removeAll();
       for(Message message1 : messages){
            this.addMessage(  message1);
        }
    }

    public void rechargeMessage( Set<Message> messages) {
        viewMessageFiltre( messages);

    }
}
