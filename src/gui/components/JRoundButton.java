package gui.components;

import javax.swing.*;
import java.awt.*;

public class JRoundButton extends ImageBackgroundPanel {

    public JRoundButton() {
        super("src/gui/assets/images/dialog_texture.png", 0.3f);
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public JRoundButton(String text) {
        this();
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);
    }

    public JRoundButton(ImageIcon icon) {
        this();
        setIcon(icon);
    }

    public void setIcon(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setPreferredSize(getPreferredSize());
        add(label, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        setRoundedCorners(Math.min(preferredSize.width, preferredSize.height) / 2);
        revalidate();
        repaint();
    }
}
