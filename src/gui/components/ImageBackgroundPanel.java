package gui.components;

import gui.Utils;

import javax.swing.*;
import java.awt.*;

public class ImageBackgroundPanel extends TransparentPanel {

    private Image image;
    private Image roundedImage;
    private float brightness;

    public ImageBackgroundPanel(Image image) {
        this(image, 0.33f);
    }

    public ImageBackgroundPanel(Image image, float brightness) {
        this.image = image;
        this.roundedImage = null;
        this.brightness = brightness;
    }

    public ImageBackgroundPanel(String path) {
        this(path, 0.33f);
    }

    public ImageBackgroundPanel(String path, float brightness) {
        this(new ImageIcon(path).getImage(), brightness);
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public void setRoundedCorners(int radius) {
        roundedImage = Utils.makeRoundedCorner(Utils.imageToBufferedImage(image), radius);
    }
    public void resetRoundedCorners() {
        roundedImage = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setOpaque(false);

        Image toDraw = roundedImage != null ? roundedImage : image;
        g.drawImage(toDraw, 0, 0, getWidth(), getHeight(), this);

        int brightness = (int)(256 - 256 * this.brightness);
        g.setColor(new Color(0,0,0,brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}