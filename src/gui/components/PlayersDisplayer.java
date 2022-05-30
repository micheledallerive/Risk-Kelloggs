package gui.components;

import gui.EventCallback;
import model.Game;
import model.TurnListener;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * The type Players displayer.
 */
public class PlayersDisplayer extends TransparentPanel {
    private final Game game;
    private final JLabel[] pointers;
    private final JDie[] dice;

    /**
     * Instantiates a new Players displayer.
     *
     * @param game the game
     */
    public PlayersDisplayer(Game game) {
        super();
        this.game = game;
        this.pointers = new JLabel[game.getPlayers().size()];
        this.dice = new JDie[game.getPlayers().size()];
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        for (int i = 0; i < game.getPlayers().size(); i++) {
            constraints.gridy = i;
            constraints.gridx = 0;
            constraints.anchor = GridBagConstraints.EAST;
            constraints.insets = new Insets(20, 0, 20, 20);
            JDie die = new JDie();
            die.setPreferredSize(new Dimension(50, 50));
            dice[i] = die;
            add(die, constraints);

            constraints.gridx++;
            constraints.insets = new Insets(20, 0, 20, 0);
            PlayerIconComponent playerIconComponent = new PlayerIconComponent(game.getPlayers().get(i));
            add(playerIconComponent, constraints);

            constraints.gridx++;
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(0, 0, 0, 20);
            JLabel pointerIcon = new JLabel(new ImageIcon("src/gui/assets/images/pointer.png"));
            pointerIcon.setPreferredSize(new Dimension(30, 30));
            pointerIcon.setVisible(false);
            pointers[i] = pointerIcon;
            add(pointerIcon, constraints);
        }

        game.addListener((TurnListener) newTurn -> {
            for (JLabel pointer : pointers) {
                pointer.setVisible(false);
            }
            pointers[newTurn].setVisible(true);
//            repaint();
        });
    }

    /**
     * Choose starting player.
     *
     * @param eventCallback the event callback
     */
    public void chooseStartingPlayer(EventCallback eventCallback) {
        for (JDie die : dice) {
            die.setVisible(true);
        }
        for (int i = 1; i < dice.length; i++) {
            dice[i].roll();
        }

        dice[0].setCursor(new Cursor(Cursor.HAND_CURSOR));
        dice[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dice[0].roll();
            }
        });
        dice[0].addCallback((id, args) -> {
            int maxIndex = 0;
            for (int i = 0; i < dice.length; i++) {
                if (dice[i].getValue() > dice[maxIndex].getValue()) {
                    maxIndex = i;
                }
            }

            game.setPlayerStarting(game.getPlayers().get(maxIndex));
            game.setTurn(maxIndex);

            eventCallback.onEvent(0);
        });
    }

    /**
     * Hide dice.
     */
    public void hideDice() {
        for (JDie die : dice) {
            die.setVisible(false);
        }
    }
}
