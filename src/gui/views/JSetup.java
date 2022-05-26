package gui.views;

import gui.EventCallback;
import gui.components.ImageBackgroundPanel;
import gui.components.NameDialog;
import model.Game;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JSetup extends ImageBackgroundPanel {

    private Game game;
    private JFrame parent;

    public JSetup(Game game, JFrame parent) {
        super("src/gui/assets/images/map.jpg", .33f);
        this.game = game;
        this.parent = parent;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                NameDialog nameDialog = new NameDialog(parent);
                nameDialog.addDisposeListener(id -> {
                    setBrightness(1f);
                });
                nameDialog.pack();
                nameDialog.setLocationRelativeTo(null);
                nameDialog.setVisible(true);

            }
        });
    }


}
