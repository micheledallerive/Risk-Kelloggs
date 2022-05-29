package gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MessageDialog extends BaseDialog {

    public MessageDialog(JFrame parent, String message) {
        this(parent, new String[]{message}, "", 20f, "OK");
    }

    public MessageDialog(JFrame parent, String[] messages) {
        this(parent, messages, "", 20f, "OK");
    }

    public MessageDialog(JFrame parent, String[] messages,
                         String title, float fontSize,
                         String buttonMessage) {
        super(parent, title, true, 100);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 20, 20);
        gbc.gridy = 0;

        for (String message : messages) {
            JLabel label = new JLabel(message);
            label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
            add(label, gbc);

            gbc.gridy++;
        }

        if (buttonMessage != null) {
            gbc.insets = new Insets(0, 20, 0, 20);
            JButton button = new JButton(buttonMessage);
            button.addActionListener(e -> dispose());
            add(button, gbc);
        }

    }

}
