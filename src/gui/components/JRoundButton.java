package gui.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * The type J round button.
 *
 * @author dallem@usi.ch
 */
public class JRoundButton extends ImageBackgroundPanel {

    private static final int MARGIN = 7;

    /**
     * Instantiates a new J round button.
     */
    public JRoundButton() {
        super("src/gui/assets/images/dialog_texture.png", 1f);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
    }

    /**
     * Instantiates a new J round button.
     *
     * @param text the text
     */
    public JRoundButton(String text) {
        this();
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);
    }

    /**
     * Instantiates a new J round button.
     *
     * @param icon the icon
     */
    public JRoundButton(ImageIcon icon) {
        this();
        setIcon(icon);
    }

    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(ImageIcon icon) {
        JLabel label = new JLabel(icon);
        label.setPreferredSize(
            new Dimension(getPreferredSize().width - 2 * MARGIN, getPreferredSize().height - 2 * MARGIN));
        add(label, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        setRoundedCorners(100);
        System.out.println("Preferred size");
        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
