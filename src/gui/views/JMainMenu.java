package gui.views;

import gui.ClickCallback;

import javax.swing.*;
import java.awt.*;

public class JMainMenu extends JPanel {
    public JMainMenu(ClickCallback callback) {
        SpringLayout layout = new SpringLayout();

        JLabel label = new JLabel("Risk Kellogg's");
        JButton exitButton = new JButton("EXIT");
        JButton playButton = new JButton("PLAY!");

        label.setFont(new Font("Arial", Font.BOLD, 80));

        layout.putConstraint(SpringLayout.NORTH, label, 48, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label, 0, SpringLayout.HORIZONTAL_CENTER, this);


        playButton.setFont(new Font("Arial", Font.BOLD, 32));
        playButton.setPreferredSize(new Dimension(200, 75));
        playButton.addActionListener(e -> callback.onClick(0));

        exitButton.setFont(new Font("Arial", Font.BOLD, 32));
        exitButton.setPreferredSize(new Dimension(200, 75));
        exitButton.addActionListener(e -> callback.onClick(0));


        JPanel buttonPanel = new JPanel();

        SpringLayout buttonLayout = new SpringLayout();
        buttonLayout.putConstraint(SpringLayout.NORTH, playButton, 0, SpringLayout.NORTH, buttonPanel);
        buttonLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, playButton, 0, SpringLayout.HORIZONTAL_CENTER, buttonPanel);
        buttonLayout.putConstraint(SpringLayout.NORTH, exitButton, 48, SpringLayout.SOUTH, playButton);
        buttonLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, exitButton, 0, SpringLayout.HORIZONTAL_CENTER, buttonPanel);

        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);

        buttonPanel.setLayout(buttonLayout);

        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 48, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, buttonPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        add(label);
        add(buttonPanel);
        setLayout(layout);
    }
}
