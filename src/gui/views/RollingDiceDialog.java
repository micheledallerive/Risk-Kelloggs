package gui.views;

import gui.EventCallback;
import gui.components.JDie;
import gui.components.MessageDialog;
import gui.components.PlayerIconComponent;
import gui.components.TransparentPanel;
import model.Game;
import model.enums.ArmyColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RollingDiceDialog extends MessageDialog {

    public RollingDiceDialog(JFrame parent, String windowTitle, boolean modal, Game game) {
        super(parent,windowTitle,modal,100);

        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel titlePanel = new TransparentPanel();
        titlePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel title = new JLabel("Click your die to roll it.");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        JLabel subtitle = new JLabel("The player with the highest number starts.");
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 18f));

        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 0;
        titlePanel.add(title,c);
        c.gridy = 1;
        titlePanel.add(subtitle,c);


        GridLayout diceLayout = new GridLayout(3, 5);
        diceLayout.setHgap(20);
        diceLayout.setVgap(20);

        JPanel dicePanel = new TransparentPanel();
        dicePanel.setLayout(diceLayout);

        JDie[] dice = new JDie[6];
        for(int i=0;i<6;i++) {
            dice[i] = new JDie();
        }

        for(int i = 0; i < 3; i++) {
            dicePanel.add(new PlayerIconComponent(game.getPlayers().get(2*i), false));
            dicePanel.add(dice[2*i]);
            dicePanel.add(new TransparentPanel());
            dicePanel.add(dice[2*i+1]);
            dicePanel.add(new PlayerIconComponent(game.getPlayers().get(2*i + 1), true));
        }

        final int[] maxValue = {0};
        final int[] maxIndex = {0};
        for(int i=1;i<6;i++) {
            maxValue[0] = Math.max(maxValue[0], dice[i].getValue());
            maxIndex[0] = maxValue[0] > dice[i].getValue() ? i : maxIndex[0];
        }


        final boolean[] rolled = {false};
        dice[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(rolled[0]) return;
                rolled[0] = true;
                dice[0].roll();
                dice[0].addCallback(new EventCallback() {
                    @Override
                    public void onEvent(int id) {
                        maxValue[0] = Math.max(maxValue[0], dice[0].getValue());
                        maxIndex[0] = maxValue[0] > dice[0].getValue() ? 0 : maxIndex[0];
                        game.setPlayerStarting(game.getPlayers().get(maxIndex[0]));
                    }
                });
            }
        });

        gbc.gridy = 0;
        add(titlePanel,gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.gridy = 1;
        add(dicePanel,gbc);
    }
}
