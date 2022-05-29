package gui.components;

import gui.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

public class JPopup extends JPopupMenu {

    private static final int AUTOHIDE_DELAY = 4000;

    private final boolean autohide;

    public JPopup() {
        this("", false);
    }

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
    public void show(Component invoker, int x, int y) {
        super.show(invoker, x, (int) (y + 2 * getPreferredSize().getHeight()));
        if (autohide) {
            Timer timer = new Timer(AUTOHIDE_DELAY, e -> setVisible(false));
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = ImageUtils.getImage("src/gui/assets/images/dialog_texture.png");
        image = ImageUtils.makeRoundedCorner(ImageUtils.imageToBufferedImage(image), 50);
        g.drawImage(image, 0, 0, null);
    }
}
