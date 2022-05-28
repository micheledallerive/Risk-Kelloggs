package gui.components;

import gui.ImageUtils;
import model.Game;
import model.Player;
import model.TurnListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.TimerTask;

public class PlayersDisplayer extends TransparentPanel {
    private Game game;
    private JLabel[] pointers;
    private JDie[] dice;

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
            constraints.insets = new Insets(0,0,0,20);
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

    public void chooseStartingPlayer() {
        for (JDie die : dice) {
            die.setVisible(true);
        }
        for (int i = 1; i < dice.length; i++) {
            dice[i].roll();
        }
        dice[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dice[0].roll();
            }
        });
    }
}
