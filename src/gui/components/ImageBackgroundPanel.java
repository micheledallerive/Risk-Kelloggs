package gui.components;

import javax.swing.*;
import java.awt.*;

public class ImageBackgroundPanel extends JPanel {

    private Image image;
    private float brightness;

    public ImageBackgroundPanel(Image image) {
        this(image, 0.33f);
    }

    public ImageBackgroundPanel(Image image, float brightness) {
        this.image = image;
        this.brightness = brightness;
    }

    public ImageBackgroundPanel(String path) {
        this(path, 0.33f);
    }

    public ImageBackgroundPanel(String path, float brightness) {
        this(new ImageIcon(path).getImage(), brightness);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        int brightness = (int)(256 - 256 * this.brightness);
        g.setColor(new Color(0,0,0,brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}