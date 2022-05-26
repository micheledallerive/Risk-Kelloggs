package gui.components;

import gui.Utils;
import model.Player;
import model.enums.ArmyColor;

import javax.swing.*;
import java.awt.*;

public class PlayerIconComponent extends TransparentPanel {

    private ArmyColor color;
    private String label;

    public PlayerIconComponent(Player player, boolean reversed) {
        super();
        this.color = player.getColor();
        this.label = player.isAI() ? "AI" : "You";

        GridLayout layout = new GridLayout(1, 2);
        layout.setHgap(5);
        setLayout(layout);

        JPanel circle = new TransparentPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Utils.armyColorToColor(color));
                g.fillOval(0,0,getPreferredSize().width, getPreferredSize().height);
            }
        };
        JLabel jlabel = new JLabel(label);
        jlabel.setFont(jlabel.getFont().deriveFont(Font.BOLD, 16f));

        JPanel jlabelPanel = new TransparentPanel();
        jlabelPanel.setLayout(new GridBagLayout());
        jlabelPanel.add(jlabel);

        int circleHeight = 64 - jlabel.getFont().getSize() -10;
        circle.setPreferredSize(new Dimension(circleHeight,circleHeight));

        JPanel circlePanel = new TransparentPanel();
        circlePanel.setLayout(new GridBagLayout());
        circlePanel.add(circle);

        if(!reversed) add(circlePanel);
        add(jlabelPanel);
        if(reversed) add(circlePanel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
