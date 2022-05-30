package gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The type Message dialog.
 *
 * @author dallem@usi.ch
 */
public class MessageDialog extends BaseDialog {

    /**
     * Instantiates a new Message dialog.
     *
     * @param parent  the parent
     * @param message the message
     */
    public MessageDialog(JFrame parent, String message) {
        this(parent, new String[] {message}, "", 20f, "OK");
    }

    /**
     * Instantiates a new Message dialog.
     *
     * @param parent   the parent
     * @param messages the messages
     */
    public MessageDialog(JFrame parent, String[] messages) {
        this(parent, messages, "", 20f, "OK");
    }

    /**
     * Instantiates a new Message dialog.
     *
     * @param parent        the parent
     * @param messages      the messages
     * @param title         the title
     * @param fontSize      the font size
     * @param buttonMessage the button message
     */
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
            label.setForeground(Color.WHITE);
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
