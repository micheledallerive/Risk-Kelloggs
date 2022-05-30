package gui.views;

import gui.EventCallback;
import gui.components.JRoundButton;
import gui.components.JTurnPicker;
import gui.components.MessageDialog;
import gui.components.NameDialog;
import gui.components.PlayersDisplayer;
import gui.components.QuantityDialog;
import gui.components.TransparentPanel;
import gui.utils.MapUtils;
import model.AI;
import model.Game;
import model.Player;
import model.Territory;
import model.TurnListener;
import model.callback.Callback;
import model.enums.GameStatus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Function;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;

/**
 * Class JPanel to set up after main menu, the player's name before the start of
 * the game.
 *
 * @author dallem @usi.ch
 */
public class JGame extends JLayeredPane {
    // region FIELDS
    private final Game game;
    private final JFrame parent;
    private JLayeredPane uiPanel;
    private PlayersDisplayer playersDisplayer;
    private JTurnPicker turnPicker;
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
                map.setEnabled(false);

                String name = nameDialog.getName();
                if (name == null || name.trim().isEmpty()) {
                    name = "Player";
                }
                game.initializePlayers(6, 1, new String[] {name});
                game.initArmies();
                final boolean[] askedToRoll = {false};
                createUI((id, args) -> {
                    if (askedToRoll[0]) {
                        return;
                    }
                    askedToRoll[0] = true;
                    askToRollDie();
                });

            }
        });
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(null);
        nameDialog.setVisible(true);
    }

    private void askToRollDie() {
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
        Component playerDie = playersDisplayer;
        rollDie.setLocation(playerDie.getX() - rollDie.getWidth(), playerDie.getY() + rollDie.getHeight() / 2);
        rollDie.setVisible(true);
    }

    private void createUI(EventCallback callback) {
        final JLayeredPane uiPane = new JLayeredPane();
        uiPane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 1f;
        constraints.weighty = 1f;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(20, 20, 0, 0);
        final JRoundButton pauseButton = new JRoundButton();
        pauseButton.setIcon(new ImageIcon("src/gui/assets/images/icon/pause.png"));
        pauseButton.setPreferredSize(new Dimension(50, 50));
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                // TODO PAUSE SCREEN we dont have enough time lmao
            }
        });
        uiPane.add(pauseButton, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JPanel turnContainer = new TransparentPanel();
        turnContainer.setLayout(new BorderLayout());
        final JTurnPicker playerTurn = new JTurnPicker();
        playerTurn.setVisible(false);
        this.turnPicker = playerTurn;
        turnContainer.add(playerTurn, BorderLayout.CENTER);
        uiPane.add(turnContainer, constraints);

        constraints.fill = GridBagConstraints.NONE;

        game.addListener((TurnListener) newTurn -> {
            playerTurn.setVisible(!game.getPlayers().get(newTurn).isAI()
                && game.getStatus() == GameStatus.PLAYING);
            System.out.println("Change turn " + (!game.getPlayers().get(newTurn).isAI()
                && game.getStatus() == GameStatus.PLAYING));
            revalidate();
            repaint();
            map.clearAttacking();
        });


        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(0, 0, 0, 0);
        final PlayersDisplayer playersDisplayer = new PlayersDisplayer(game);
        this.playersDisplayer = playersDisplayer;
        uiPane.add(playersDisplayer, constraints);

        uiPane.addMouseMotionListener(map.getMouseMotionListeners()[0]);
        uiPane.addMouseListener(map.getMouseListeners()[0]);

        uiPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                super.componentResized(event);
                if (callback != null) {
                    callback.onEvent(-1);
                }
            }
        });

        /*
        This piece of code is really important: it allows the overlay items to be focusable by the user.
        Since we are using overlays, the overlay layer covers the map, in a way that the mouse cursor does not
        change as intended when going over a country in the map, since the overlay is "capturing" the mouse motion.
        Therefore, to make the cursor toggle on the map, we need to make the overlay disabled (uiPane.setEnabled
        (false)),
        execute in a future method (since when we roll dice we want it to be focusable to click on the die).
        In order to make the cursor change when over a overlay component, we need to make the overlay enabled again
        when going on the component, and disable it back when going of the component.

        The problem is just about the cursor: the click events are handled correctly, the only problem is just that
        the cursor doesn't change when going over, with a negative impact on the user experience.
         */
        for (Component component : uiPane.getComponents()) {
            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent event) {
                    super.mouseEntered(event);
                    uiPanel.setEnabled(true);
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    super.mouseExited(event);
                    uiPanel.setEnabled(false);
                }
            });
        }

        this.uiPanel = uiPane;
        add(uiPane, JLayeredPane.PALETTE_LAYER);

        invalidate();
        validate();
        repaint();

    }

    private void chooseStartingPlayer() {
        playersDisplayer.chooseStartingPlayer(new EventCallback() {
            @Override
            public void onEvent(int id, Object... args) {
                map.setEnabled(true);

                MessageDialog message = new MessageDialog(parent,
                    game.getPlayerStarting() + " starts the game!");
                message.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent event) {
                        super.windowClosed(event);
                        playersDisplayer.hideDice();
                        uiPanel.setEnabled(false);
                        MessageDialog setupMessage = new MessageDialog(parent,
                            new String[] {
                                "Game setup!",
                                "Choose your countries",
                                "Once they are all chosen, finish placing your armies",
                            });
                        setupMessage.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent event) {
                                super.windowClosed(event);
                                startGame();
                            }
                        });
                        setupMessage.pack();
                        setupMessage.setLocationRelativeTo(null);
                        setupMessage.setVisible(true);
                    }
                });
                message.pack();
                message.setLocationRelativeTo(null);
                message.setVisible(true);
            }
        });
    }

    private void startGame() {
        Timer timer = new Timer(500, null);
        // ai interaction
        final Function<Void, Void> nextTurn = aVoid -> {
            game.nextTurn();
            timer.start();
            map.repaint();
            return null;
        };

        final EventCallback setupCallback = MapUtils.setupCallback(game, map, nextTurn, parent);

        // handles user
        map.addCallback(setupCallback);


        // handles ai
        timer.addActionListener(e -> {
            if (game.getStatus() == GameStatus.SETUP) {
                setup(timer);
            } else if (game.getStatus() == GameStatus.PLAYING) {
                playing(timer);
            }
        });

        timer.start();

        final EventCallback playingCallback = MapUtils.playingCallback(game, map, nextTurn, parent);
        final EventCallback moveCallback = MapUtils.moveCallback(game, map, nextTurn, parent);
        turnPicker.setPlayAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                map.removeCallback(playingCallback);
                map.removeCallback(moveCallback);
                if (game.getStatus() == GameStatus.PLAYING) {
                    map.addCallback(playingCallback);
                }
            }
        });

        turnPicker.setMoveAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                map.removeCallback(playingCallback);
                map.removeCallback(moveCallback);
                if (game.getStatus() == GameStatus.PLAYING) {
                    map.addCallback(moveCallback);
                }
            }
        });

        turnPicker.setEndAdapter(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                map.removeCallback(playingCallback);
                map.removeCallback(moveCallback);
                nextTurn.apply(null);
            }
        });
    }

    private void setup(Timer timer) {
        boolean someoneHasFreeArmies = game.getPlayers().stream().anyMatch(p -> !p.getFreeArmies().isEmpty());
        if (!someoneHasFreeArmies) {
            game.setTurn(game.getPlayerStarting());
            timer.stop();

            MessageDialog startPlaying = new MessageDialog(
                parent, new String[] {
                "The game starts!",
                "Destroy the other players and conquer the world!",
            });
            startPlaying.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent event) {
                    super.windowClosed(event);
                    game.nextStatus();
                    timer.start();
                }
            });
            startPlaying.pack();
            startPlaying.setLocationRelativeTo(null);
            startPlaying.setVisible(true);
            return;
        }
        boolean everythingOccupied = game.getBoard().getTerritories().stream().noneMatch(t -> t.getOwner() == null);
        Player currentPlayer = game.getPlayers().get(game.getTurn());
        if (currentPlayer.getFreeArmies().isEmpty()) {
            game.nextTurn();
            return;
        }
        if (currentPlayer.isAI()) {
            ((AI) currentPlayer).placeArmy(game, !everythingOccupied);
            game.nextTurn();
        } else {
            timer.stop();
        }
        map.repaint();
    }

    private void playing(final Timer timer) {
        Player currentPlayer = game.getPlayers().get(game.getTurn());
        if (currentPlayer.isAI()) {
            ((AI) currentPlayer).attack(game.getBoard(), new Callback() {
                @Override
                public void onPlayerAttacked(Player attacker, Player attacked, Territory fromTerritory,
                                             Territory attackedTerritory) {
                    System.out.println("Player is getting attacked by " + attacker);
                    timer.stop();
                    QuantityDialog defendQuantity = new QuantityDialog(
                        parent,
                        new String[] {
                            "You are getting attacked by " + attacker + " in " + attackedTerritory.getName(),
                            "How many armies do you want to defend with?",
                        },
                        1,
                        Math.min(attackedTerritory.getArmiesCount(), 3)
                    );
                    defendQuantity.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent event) {
                            super.windowClosed(event);
                            int quantity = defendQuantity.getSelectedQuantity();
                            map.setAttackingTo(attackedTerritory);
                            map.setAttackingFrom(fromTerritory);
                            map.setAttackResult(
                                attacker.getAttackOutcome(fromTerritory, attackedTerritory,
                                    Math.min(fromTerritory.getArmiesCount() - 1, 3),
                                    Math.min(quantity, 3))
                            );
                            Timer t1 = new Timer(1000, e1 -> {
                                map.clearAttacking();
                                game.nextTurn();
                                timer.start();
                            });
                            t1.setRepeats(false);
                            t1.start();
                        }
                    });
                    defendQuantity.pack();
                    defendQuantity.setLocationRelativeTo(null);
                    defendQuantity.setVisible(true);
                }

                @Override
                public void onAIAttacked(Player attacker, Player attacked, Territory fromTerritory,
                                         Territory attackedTerritory) {
                    System.out.println(attacker + " is attacking " + attacked);
                    currentPlayer.getAttackOutcome(fromTerritory, attackedTerritory,
                        Math.min(fromTerritory.getArmiesCount() - 1, 3),
                        Math.min(attackedTerritory.getArmiesCount(), 2));
                    game.nextTurn();
                }
            });
        } else {
            int[] playerBonus = game.giveBonus(currentPlayer, -1); // im sad we didnt do cards ;(
            int totalBonus = playerBonus[0] + playerBonus[1] + playerBonus[2];
            if (totalBonus > 0 && game.getTurnsPlayed() > 0) {
                MessageDialog bonusDialog = new MessageDialog(parent, new String[] {
                    "You gained " + totalBonus + " bonus armies",
                    "Place them!"
                });
                bonusDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        timer.stop();
                    }
                });
                bonusDialog.pack();
                bonusDialog.setLocationRelativeTo(null);
                bonusDialog.setVisible(true);
            } else {
                timer.stop();
            }
        }
    }
    // endregion
}
