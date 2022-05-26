package gui.views;

import gui.EventCallback;
import gui.components.ImageBackgroundPanel;
import gui.components.JDie;
import gui.components.NameDialog;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JSetup extends JPanel {

    private Game game;
    private JFrame parent;

    public JSetup(Game game, JFrame parent) {
        super();
        this.game = game;
        this.parent = parent;

        setLayout(new BorderLayout());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                askForName();
            }
        });

        MapPanel map = new MapPanel(game);
        add(map, BorderLayout.CENTER);
        map.requestFocus();
    }

    private void askForName() {
        NameDialog nameDialog = new NameDialog(parent);
        nameDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);

                String name = nameDialog.getName();
                if (name == null || name.isEmpty()) {
                    name = "Player";
                }
                game.initializePlayers(6, 1, new String[]{name});

                chooseStartingPlayer();
            }
        });
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(null);
        nameDialog.setVisible(true);
    }

    private void chooseStartingPlayer() {
        RollingDiceDialog rollDice = new RollingDiceDialog(parent, "", true, game);
        rollDice.pack();
        rollDice.setLocationRelativeTo(null);
        rollDice.setVisible(true);
    }


}
