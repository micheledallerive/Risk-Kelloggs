package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTurnPicker extends TransparentPanel {

    public JTurnPicker() {
        super();
        init();
    }

    private void init() {
        setBackground(Color.RED);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;

        final MouseAdapter cursorAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };

        constraints.gridx = 0;
        JRoundButton play = new JRoundButton();
        play.setIcon(new ImageIcon("src/gui/assets/images/icon/attack.png"));
        play.setPreferredSize(new Dimension(50, 50));
        play.setToolTipText("Attack a territory");
        add(play, constraints);

        constraints.gridx = 1;
        JRoundButton move = new JRoundButton();
        move.setIcon(new ImageIcon("src/gui/assets/images/icon/move.png"));
        move.setPreferredSize(new Dimension(50, 50));
        move.setToolTipText("Move armies");
        add(move, constraints);

        constraints.gridx = 2;
        JRoundButton next = new JRoundButton();
        next.setIcon(new ImageIcon("src/gui/assets/images/icon/end.png"));
        next.setPreferredSize(new Dimension(50, 50));
        next.setToolTipText("End your turn");
        add(next, constraints);

        for (Component button : getComponents()) {
            button.addMouseListener(cursorAdapter);
        }
        revalidate();
        repaint();

    }

    public void setPlayAdapter(MouseAdapter adapter) {
        getComponents()[0].addMouseListener(adapter);
    }

    public void setMoveAdapter(MouseAdapter adapter) {
        getComponents()[1].addMouseListener(adapter);
    }

    public void setEndAdapter(MouseAdapter adapter) {
        getComponents()[2].addMouseListener(adapter);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        //if (getParent() != null) ((JPanel) getParent()).getRootPane().getGlassPane().setVisible(aFlag);
    }
}
