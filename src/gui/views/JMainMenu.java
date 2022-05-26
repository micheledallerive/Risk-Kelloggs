package gui.views;

import gui.ClickCallback;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


/**
 * Class JMainMenu extended from JPanel to create main menu.
 * @author dallem@usi.ch
 */
public class JMainMenu extends JPanel {
    private static final Font FONT_BUTTON = new Font("Arial", Font.BOLD, 32);
    private static final Dimension DIMENSION_BUTTON = new Dimension(200, 75);

    /**
     * Constructor.
     * @param callback Event for clickable buttons
     */
    public JMainMenu(ClickCallback callback) {
        SpringLayout layout = new SpringLayout();

        // main title, choose font and place its top to top of panel and center to center of panel
        JLabel label = new JLabel("Risk Kellogg's");
        label.setFont(new Font("Arial", Font.BOLD, 80));
        layout.putConstraint(SpringLayout.NORTH, label, 48, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // play & exit buttons, choose font and dimension, add the listener with param '0' to onclick event
        JButton playButton = new JButton("PLAY!");
        JButton exitButton = new JButton("EXIT");
        playButton.setFont(FONT_BUTTON);
        exitButton.setFont(FONT_BUTTON);
        playButton.setPreferredSize(DIMENSION_BUTTON);
        exitButton.setPreferredSize(DIMENSION_BUTTON);
        playButton.addActionListener(e -> callback.onClick(0));
        exitButton.addActionListener(e -> callback.onClick(0));

        // add a panel for buttons, place them in a layout and set the panel with it, add the panel to the main layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);
        SpringLayout buttonLayout = new SpringLayout();
        buttonLayout.putConstraint(SpringLayout.NORTH, playButton, 0, SpringLayout.NORTH, buttonPanel);
        buttonLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, playButton, 0, SpringLayout.HORIZONTAL_CENTER, buttonPanel);
        buttonLayout.putConstraint(SpringLayout.NORTH, exitButton, 48, SpringLayout.SOUTH, playButton);
        buttonLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, exitButton, 0, SpringLayout.HORIZONTAL_CENTER, buttonPanel);
        buttonPanel.setLayout(buttonLayout);
        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 48, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        // add gui objects to JFrame and set the main layout
        this.add(label);
        this.add(buttonPanel);
        this.setLayout(layout);
    }
}
