package gui.views;

import gui.EventCallback;
import gui.components.JRoundButton;
import gui.components.MessageDialog;
import gui.components.NameDialog;
import gui.components.PlayersDisplayer;
import gui.utils.PopupUtils;
import model.AI;
import model.Game;
import model.Player;
import model.Territory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class JPanel to set up after main menu, the player's name before the start of
 * the game.
 *
 * @author dallem@usi.ch
 */
public class JGame extends JLayeredPane {
    // region FIELDS
    private final Game game;
    private final JFrame parent;
    private PlayersDisplayer playersDisplayer;
    private final MapPanel map;
    // endregion

    // region CONSTRUCTORS

    /**
     * Constructor.
     *
     * @param game   Game object.
     * @param parent JFrame parent component.
     */
    public JGame(final Game game, final JFrame parent) {
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
                game.initializePlayers(6, 1, new String[]{name});
                game.initArmies();

                createUI();

                MessageDialog rollDie = new MessageDialog(parent,
                        "Roll your die to determine who starts.");
                rollDie.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        super.windowClosed(windowEvent);
                        chooseStartingPlayer();
                    }
                });
                rollDie.pack();
                rollDie.setLocationRelativeTo(null);
                rollDie.setVisible(true);
            }
        });
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(null);
        nameDialog.setVisible(true);
    }

    private void createUI() {
        final JLayeredPane uiPane = new JLayeredPane();
        uiPane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 1f;
        constraints.weighty = 1f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 10, 0, 0);
        final JRoundButton pauseButton = new JRoundButton();
        pauseButton.setIcon(new ImageIcon("src/gui/assets/images/pause.png"));
        pauseButton.setPreferredSize(new Dimension(50, 50));
        pauseButton.setSize(pauseButton.getPreferredSize());
        uiPane.add(pauseButton, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 0);
        final PlayersDisplayer playersDisplayer = new PlayersDisplayer(game);
        this.playersDisplayer = playersDisplayer;
        uiPane.add(playersDisplayer, constraints);

        uiPane.setEnabled(false);
        add(uiPane, JLayeredPane.PALETTE_LAYER);
    }

    private void chooseStartingPlayer() {
        map.setEnabled(false);
        playersDisplayer.chooseStartingPlayer(new EventCallback() {
            @Override
            public void onEvent(int id, Object... args) {
                map.setEnabled(true);

                MessageDialog message = new MessageDialog(parent,
                        game.getPlayerStarting() + " starts the game!");
                message.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        playersDisplayer.hideDice();
                        gameSetup();
                    }
                });
                message.pack();
                message.setLocationRelativeTo(null);
                message.setVisible(true);
            }
        });
    }

    private void gameSetup() {
        final boolean DEBUG = true;
        Timer timer = new Timer(DEBUG ? 500 : 1000, null);
        timer.addActionListener(e -> {
            boolean someoneHasFreeArmies = game.getPlayers().stream().anyMatch(p -> !p.getFreeArmies().isEmpty());
            if (!someoneHasFreeArmies) {
                timer.stop();
                game.setTurn(game.getPlayerStarting());
            }
            boolean everythingOccupied = game.getBoard().getTerritories().stream().noneMatch(t -> t.getOwner() == null);
            Player currentPlayer = game.getPlayers().get(game.getTurn());
            if (currentPlayer.isAI()) {
                ((AI) currentPlayer).placeArmy(game, !everythingOccupied);
                game.nextTurn();
            } else {
                timer.stop();
            }
            map.repaint();
        });
        timer.start();

        EventCallback setupCallback = (id, args) -> {
            int clickX = (int) args[0];
            int clickY = (int) args[1];

            if (id == -1) return;

            if (game.getPlayers().get(game.getTurn()).isAI()) return;

            Territory territory = game.getBoard().getTerritories().get(id);
            Player player = game.getPlayers().get(game.getTurn());

            if (player.getFreeArmies().isEmpty()) return;

            boolean everythingOccupied = game.getBoard().getTerritories().stream().noneMatch(t -> t.getOwner() == null);
            if (territory.getOwner() != null) {
                if (territory.getOwner() == player && !everythingOccupied) {
                    PopupUtils.showPopup(parent, "You have to place armies on free territories!", clickX, clickY);
                    return;
                }
                if (territory.getOwner() != player) {
                    PopupUtils.showPopup(parent, "You can't place armies on enemy territories!", clickX, clickY);
                    return;
                }
            }
            player.placeArmies(territory, 1);
            game.nextTurn();
            timer.start();
            map.repaint();
        };
        map.addCallback(setupCallback);

    }
    // endregion
}
