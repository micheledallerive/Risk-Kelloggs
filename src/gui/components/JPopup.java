package gui.components;

import gui.utils.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/**
 * A JPopupMenu that displays a message and a close button.
 *
 * @author dallem@usi.ch
 */
public class JPopup extends JPopupMenu {

    private static final int AUTOHIDE_DELAY = 4000;

    private final boolean autohide;

    /**
     * Instantiates a new J popup.
     */
    public JPopup() {
        this("", false);
    }

    /**
     * Instantiates a new J popup.
     *
     * @param message  the message
     * @param autohide the autohide
     */
    public JPopup(String message, boolean autohide) {
        super(message);
        this.autohide = autohide;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel label = new JLabel(message);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void show(Component invoker, int pointX, int pointY) {
        super.show(invoker, pointX, (int) (pointY + 2 * getPreferredSize().getHeight()));
        if (autohide) {
            Timer timer = new Timer(AUTOHIDE_DELAY, e -> setVisible(false));
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Image image = ImageUtils.getImage("src/gui/assets/images/dialog_texture.png");
        image = ImageUtils.makeRoundedCorner(ImageUtils.imageToBufferedImage(image), 50);
        graphics.drawImage(image, 0, 0, null);
    }
}
