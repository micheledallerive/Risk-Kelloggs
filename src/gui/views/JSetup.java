package gui.views;

import gui.components.NameDialog;
import gui.components.PlayersDisplayer;
import gui.components.TransparentPanel;
import model.Game;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * Class JPanel to set up after main menu, the player's name before the start of
 * the game.
 * 
 * @author dallem@usi.ch
 */
public class JSetup extends JLayeredPane {
    // region FIELDS
    private Game game;
    private JFrame parent;
    private PlayersDisplayer playersDisplayer;
    private MapPanel map;
    // endregion

    // region CONSTRUCTORS

    /**
     * Constructor.
     * @param game Game object.
     * @param parent JFrame parent component.
     */
    public JSetup(final Game game, final JFrame parent) {
        super();
        this.game = game;
        this.parent = parent;

        setLayout(new OverlayLayout(this));

        final MapPanel map = new MapPanel(game);
        map.setAlignmentX(CENTER_ALIGNMENT);
        map.setAlignmentY(CENTER_ALIGNMENT);
        map.setEnabled(true);

        this.map = map;

        add(map, JLayeredPane.DEFAULT_LAYER);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent componentEvent) {
                super.componentShown(componentEvent);
                askForName();
            }
        });

        map.requestFocus();
    }
    // endregion

    // region METHODS
    /**
     * Procedure - handle name ask form.
     */
    private void askForName() {
        NameDialog nameDialog = new NameDialog(parent);
        nameDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);

                String name = nameDialog.getName();
                if (name == null || name.isEmpty()) {
                    name = "Player";
                }
                game.initializePlayers(6, 1, new String[] { name });

                createUI();

                chooseStartingPlayer();
            }
        });
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(null);
        nameDialog.setVisible(true);
    }

    private void createUI() {
        final PlayersDisplayer playersDisplayer = new PlayersDisplayer(game);
        this.playersDisplayer = playersDisplayer;
        final JLayeredPane uiPane = new JLayeredPane();
        uiPane.setLayout(new BorderLayout());
        uiPane.add(playersDisplayer, BorderLayout.EAST);
        uiPane.setEnabled(false);
        add(uiPane, JLayeredPane.PALETTE_LAYER);
    }

    private void chooseStartingPlayer() {
        map.setBrightness(.5f);
        playersDisplayer.chooseStartingPlayer();
    }

    private void startFillingMap() {
        System.out.println("Filling");
    }
    // endregion
}
