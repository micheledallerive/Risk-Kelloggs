package gui.views;

import gui.components.NameDialog;
import model.Game;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class JPanel to set up after main menu, the player's name before the start of
 * the game.
 * 
 * @author dallem@usi.ch
 */
public class JSetup extends JPanel {
    // region FIELDS
    private Game game;
    private JFrame parent;
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

        setLayout(new BorderLayout());

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent componentEvent) {
                super.componentShown(componentEvent);
                askForName();
            }
        });

        final MapPanel map = new MapPanel(game);
        this.add(map, BorderLayout.CENTER);
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
                chooseStartingPlayer();
            }
        });
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(null);
        nameDialog.setVisible(true);
    }

    /**
     * Procedure - handle starting player random choice.
     */
    private void chooseStartingPlayer() {
        final RollingDiceDialog rollDice = new RollingDiceDialog(parent, "", true, game);
        rollDice.pack();
        rollDice.setLocationRelativeTo(null);
        rollDice.setVisible(true);
        rollDice.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                startFillingMap();
            }
        });
    }

    private void startFillingMap() {
        System.out.println("Filling");
    }
    // endregion
}
