package gui.views;

import gui.components.JDie;
import gui.components.MessageDialog;
import gui.components.PlayerIconComponent;
import gui.components.TransparentPanel;
import model.Game;
import model.enums.ArmyColor;

import javax.swing.*;
import java.awt.*;

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

        for(int i = 0; i < 3; i++) {
            dicePanel.add(new PlayerIconComponent(game.getPlayers().get(2*i), false));
            dicePanel.add(new JDie());
            dicePanel.add(new TransparentPanel());
            dicePanel.add(new JDie());
            dicePanel.add(new PlayerIconComponent(game.getPlayers().get(2*i + 1), true));
        }

        gbc.gridy = 0;
        add(titlePanel,gbc);
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.gridy = 1;
        add(dicePanel,gbc);
    }
}
