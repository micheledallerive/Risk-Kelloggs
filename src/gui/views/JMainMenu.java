package gui.views;

import gui.ClickCallback;
import gui.components.ImageBackgroundPanel;
import gui.components.TransparentPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Class JMainMenu extended from JPanel to create main menu.
 * @author dallem@usi.ch
 */
public class JMainMenu extends ImageBackgroundPanel {
    /**
     * Constructor.
     * @param callback Event for clickable buttons
     */
    public JMainMenu(ClickCallback callback) {
        super("src/gui/images/main_menu.png");

        GridLayout layout = new GridLayout(3, 1);

        JPanel labelPanel = new TransparentPanel();
        labelPanel.setLayout(new GridBagLayout());
        JLabel label = new JLabel("Risk Kellogg's");
        label.setFont(new Font("Arial", Font.BOLD, 80));
        labelPanel.add(label);

        JPanel buttonPlayPanel = new TransparentPanel();
        buttonPlayPanel.setLayout(new GridBagLayout());
        JButton playButton = new JButton("PLAY!");
        playButton.setFont(new Font("Arial", Font.BOLD, 32));
        playButton.setPreferredSize(new Dimension(275, 100));
        playButton.addActionListener(e -> callback.onClick(0));
        buttonPlayPanel.add(playButton);

        JPanel buttonExitPanel = new TransparentPanel();
        buttonExitPanel.setLayout(new GridBagLayout());
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 32));
        exitButton.setPreferredSize(new Dimension(275, 100));
        exitButton.addActionListener(e -> callback.onClick(1));
        buttonExitPanel.add(exitButton);

        add(labelPanel);
        add(buttonPlayPanel);
        add(buttonExitPanel);
        setLayout(layout);
    }
}
