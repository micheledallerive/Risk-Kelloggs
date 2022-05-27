package gui.components;

import gui.Utils;
import model.Player;
import model.enums.ArmyColor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Class display player icon JComponent.
 * @author dallem@usi.ch
 */
public class PlayerIconComponent extends TransparentPanel {

    //region FIELDS
    private ArmyColor color;
    private String label;
    //endregion

    //region CONSTRUCTORS

    /**
     * Constructor.
     * @param player Player representing this component.
     * @param reversed Boolean stating if this component is facing down.
     */
    public PlayerIconComponent(Player player, boolean reversed) {
        super();
        this.color = player.getColor();
        this.label = player.isAI() ? "AI" : "You";

        GridLayout layout = new GridLayout(1, 2);
        layout.setHgap(5);
        setLayout(layout);

        JLabel jlabel = new JLabel(label);
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD, 16f));

        JPanel jlabelPanel = new TransparentPanel();
        jlabelPanel.setLayout(new GridBagLayout());
        jlabelPanel.add(jlabel);

        int circleHeight = 64 - jlabel.getFont().getSize() - 10;
        JPanel circle = new TransparentPanel() {
            @Override
            protected void paintComponent(final Graphics graphics) {
                super.paintComponent(graphics);
                graphics.setColor(Utils.armyColorToColor(color));
                graphics.fillOval(0,0,getPreferredSize().width, getPreferredSize().height);
            }
        };
        circle.setPreferredSize(new Dimension(circleHeight,circleHeight));

        JPanel circlePanel = new TransparentPanel();
        circlePanel.setLayout(new GridBagLayout());
        circlePanel.add(circle);

        if (!reversed) {
            add(circlePanel);
        }
        add(jlabelPanel);
        if (reversed) {
            add(circlePanel);
        }
    }
    //endregion

    //region METHODS
    @Override public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
    }
    //endregion
}
