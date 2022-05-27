package gui.views;

import gui.EventCallback;
import gui.components.JDie;
import gui.components.MessageDialog;
import gui.components.PlayerIconComponent;
import gui.components.TransparentPanel;
import model.Game;
import model.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class model dialog to roll dice at the beginning.
 * @author dallem@usi.ch
 */
public class RollingDiceDialog extends MessageDialog {

    private Game game;

    /**
     * Constructor.
     * @param parent JFrame parent.
     * @param windowTitle String about the title of the Component.
     * @param modal Boolean signaling if the Component is modal.
     * @param game Game object.
     */
    public RollingDiceDialog(JFrame parent, String windowTitle, boolean modal, Game game) {
        super(parent, windowTitle, modal, 100);

        this.game = game;

        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());

        JPanel titlePanel = new TransparentPanel();
        titlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gridbagcons = new GridBagConstraints();

        JLabel title = new JLabel("Click your die to roll it.");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        JLabel subtitle = new JLabel("The player with the highest number starts.");
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 18f));

        gridbagcons.insets = new Insets(10, 0, 0, 0);
        gridbagcons.gridy = 0;
        titlePanel.add(title, gridbagcons);
        gridbagcons.gridy = 1;
        titlePanel.add(subtitle, gridbagcons);

        GridLayout diceLayout = new GridLayout(3, 5);
        diceLayout.setHgap(20);
        diceLayout.setVgap(20);

        JPanel dicePanel = new TransparentPanel();
        dicePanel.setLayout(diceLayout);

        JDie[] dice = new JDie[6];
        for (int i = 0; i < 6; i++) {
            dice[i] = new JDie();
        }

        for (int i = 0; i < 3; i++) {
            dicePanel.add(new PlayerIconComponent(game.getPlayers().get(2 * i), false));
            dicePanel.add(dice[2 * i]);
            dicePanel.add(new TransparentPanel());
            dicePanel.add(dice[2 * i + 1]);
            dicePanel.add(new PlayerIconComponent(game.getPlayers().get(2 * i + 1), true));
        }

        final int[] maxValue = {0};
        final int[] maxPlayerIndex = {0};

        for (int i = 0; i < 6; i++) {
            int finalI = i;
            dice[i].addCallback((id) -> {
                maxValue[0] = Math.max(maxValue[0], dice[finalI].getValue());
                maxPlayerIndex[0] = maxValue[0] == dice[finalI].getValue() ? finalI : maxPlayerIndex[0];
            });
            if (i > 0) {
                // only roll the AIs dice.
                dice[i].roll();
            }
        }

        final boolean[] playerRolled = {false};
        // roll player die only on click.
        dice[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event);
                if (!playerRolled[0]) {
                    dice[0].roll();
                }
            }
        });


        JPanel winnerPanel = new TransparentPanel();
        winnerPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        JLabel label = new JLabel("Player: ");
        label.setVisible(false);
        winnerPanel.add(label, constraints);

        constraints.gridx = 1;
        PlayerIconComponent playerIcon = new PlayerIconComponent(game.getPlayers().get(0), false);
        playerIcon.setVisible(false);
        winnerPanel.add(playerIcon, constraints);

        dice[0].addCallback((id) -> {
            playerRolled[0] = true;
            game.setPlayerStarting(game.getPlayers().get(maxPlayerIndex[0]));
            //showPlayerStarting(game.getPlayerStarting(), winnerPanel, true);
            dispose();
        });


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        add(titlePanel, gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.gridy = 1;
        add(dicePanel, gbc);
    }

    private void showPlayerStarting(Player player, JPanel panel, boolean visible) {

        for (Component component : panel.getComponents()) {
            component.setVisible(visible);
        }

        panel.repaint();
    }
}
