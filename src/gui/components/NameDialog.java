package gui.components;

import gui.FontManager;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Class to get name of player.
 * @author dallem@usi.ch
 */
public class NameDialog extends MessageDialog {
    private final JTextField nameField;

    /**
     * Constructor.
     * @param parent invoking JFrame parent.
     */
    public NameDialog(JFrame parent) {
        super(parent, "", true, 100);
        setLocationRelativeTo(null);

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
        nameField.addActionListener(this::actionPerformed);
        namePanel.add(nameField);

        JPanel okPanel = new TransparentPanel();
        okPanel.setBorder(defaultBorder);
        okPanel.setLayout(new GridBagLayout());

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setFont(okButton.getFont().deriveFont(18f));
        okButton.addActionListener(this::actionPerformed);
        okPanel.add(okButton);

        setLayout(new GridLayout(3, 1));
        add(labelPanel);
        add(namePanel);
        add(okPanel);
    }

    /**
     * Procedure - event on action performed.
     * @param actionEvent Event to handle.
     */
    private void actionPerformed(ActionEvent actionEvent) {
        dispose();
    }

    /**
     * Function - string name from JTextField holding new user names.
     * @return String name.
     */
    public String getName() {
        return nameField.getText();
    }
}
