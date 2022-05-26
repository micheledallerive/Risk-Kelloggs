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

public class JSetup extends JPanel {

    private Game game;
    private JFrame parent;

    public JSetup(Game game, JFrame parent) {
        super();
        this.game = game;
        this.parent = parent;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                NameDialog nameDialog = new NameDialog(parent);
                nameDialog.addDisposeListener(id -> {
                    String name = nameDialog.getName();
                    if (name == null || name.isEmpty()) {
                        name = "Player";
                    }
                    game.initializePlayers(6, 1, new String[]{name});
                });
                nameDialog.pack();
                nameDialog.setLocationRelativeTo(null);
                nameDialog.setVisible(true);
            }
        });

        setLayout(new BorderLayout());

        MapPanel map = new MapPanel(game);
        add(map, BorderLayout.CENTER);
        map.requestFocus();
    }


}
