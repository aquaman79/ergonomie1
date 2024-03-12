package main.java.com.ubo.tp.message.ihm.abonneComponent;

import javax.swing.*;
import java.awt.*;

public class FollowerView {
    private JPanel contentPane;

    private Box vBox;

    public FollowerView() {
    }

    public void initGUI(String username, String tag) {
        contentPane = new JPanel(new BorderLayout());
        vBox = Box.createVerticalBox();

        JLabel nomLabel = new JLabel(username);
        JLabel tagLabel = new JLabel("@"+tag);

        vBox.add(nomLabel);
        vBox.add(tagLabel);

        contentPane.add(vBox);

    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
