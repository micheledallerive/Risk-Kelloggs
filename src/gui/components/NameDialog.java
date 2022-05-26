package gui.components;

import javax.swing.*;

public class NameDialog extends JDialog {
    private JTextField nameField;
    private JButton okButton;

    public NameDialog(JFrame parent) {
        super(parent, "Enter your name", true);
        setSize(300, 100);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter your name:");
        add(label);
        nameField = new JTextField(20);
        add(nameField);
        okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dispose();
        });
        add(okButton);
    }

    public String getName() {
        return nameField.getText();
    }
}
