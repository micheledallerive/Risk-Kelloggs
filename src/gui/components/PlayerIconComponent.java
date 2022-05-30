package gui.components;

import gui.utils.ImageUtils;
import model.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Class display player icon JComponent.
 *
 * @author dallem@usi.ch
 */
public class PlayerIconComponent extends TransparentPanel {

    //region CONSTRUCTORS

    private final Player player;

    /**
     * Constructor.
     *
     * @param player Player representing this component.
     */
    public PlayerIconComponent(Player player) {
        super();
        this.player = player;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 15;

        JLabel playerName = new JLabel(player.getName());
        playerName.setFont(playerName.getFont().deriveFont(Font.BOLD, 24));
        playerName.setForeground(ImageUtils.chooseForegroundColor(player.getColor()));
        add(playerName, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.ipadx = 0;

        JLabel icon = new JLabel(new ImageIcon(ImageUtils.getPlayerIcon(player)));

        JPanel iconCircle = new TransparentPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        iconCircle.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        iconCircle.setLayout(new GridBagLayout());
        iconCircle.add(icon, constraints);

        add(iconCircle, constraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ImageUtils.armyColorToColor(player.getColor()));
        int width = getComponents()[1].getX() + getComponents()[1].getWidth();
        g.fillRoundRect(0, 0, width, getHeight(), getHeight(), getHeight());
    }

    //endregion
}
