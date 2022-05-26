package gui.components;

import gui.EventCallback;
import gui.FontManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NameDialog extends MessageDialog {
    private final JTextField nameField;

    public NameDialog(JFrame parent) {
        super(parent, "", true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Border defaultBorder = new EmptyBorder(10, 0, 10, 0);

        JPanel labelPanel = new TransparentPanel();
        labelPanel.setBorder(defaultBorder);
        JLabel label = new JLabel("Enter your name:");
        label.setFont(label.getFont().deriveFont(24f));
        labelPanel.setLayout(new GridBagLayout());
        labelPanel.add(label);

        JPanel namePanel = new TransparentPanel();
        namePanel.setBorder(defaultBorder);
        namePanel.setLayout(new GridBagLayout());
        nameField = new JTextField(20);
        nameField.setMargin(new Insets(5, 5, 5, 5));
        nameField.setFont(FontManager.addLetterSpacing(nameField.getFont().deriveFont(18f), .1f));
        namePanel.add(nameField);

        JPanel okPanel = new TransparentPanel();
        okPanel.setBorder(defaultBorder);
        okPanel.setLayout(new GridBagLayout());
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setFont(okButton.getFont().deriveFont(18f));
        okButton.addActionListener(e -> {
            dispose();
            triggerDisposeListener();
        });
        okPanel.add(okButton);

        setLayout(new GridLayout(3, 1));
        add(labelPanel);
        add(namePanel);
        add(okPanel);

    }

    public String getName() {
        return nameField.getText();
    }
}
