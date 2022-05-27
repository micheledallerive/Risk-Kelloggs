package gui.components;

import gui.Utils;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

public class ImageBackgroundPanel extends TransparentPanel {

    //region CONSTANTS
    private static final float BRIGHTNESS = 0.33f;
    //endregion

    //region FIELDS
    private final Image image;
    private Image roundedImage;
    private float brightness;
    //endregion

    //region CONSTRUCTORS

    /**
     * Constructor.
     * @param image
     */
    public ImageBackgroundPanel(final Image image) {
        this(image, BRIGHTNESS);
    }

    /**
     * Constructor.
     * @param path
     */
    public ImageBackgroundPanel(final String path) {
        this(path, BRIGHTNESS);
    }

    /**
     * Constructor.
     * @param path
     * @param brightness
     */
    public ImageBackgroundPanel(final String path, float brightness) {
        this(new ImageIcon(path).getImage(), brightness);
    }

    /**
     * Main constructor.
     * @param image
     * @param brightness
     */
    public ImageBackgroundPanel(final Image image, final float brightness) {
        this.image = image;
        this.roundedImage = null;
        this.brightness = brightness;
    }
    //endregion

    //region METHODS
    public void setBrightness(final float brightness) {
        this.brightness = brightness;
    }

    public void setRoundedCorners(final int radius) {
        this.roundedImage = Utils.makeRoundedCorner(Utils.imageToBufferedImage(image), radius);
    }

    public void resetRoundedCorners() {
        this.roundedImage = null;
    }

    @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setOpaque(false);

        Image toDraw = roundedImage != null ? roundedImage : image;
        g.drawImage(toDraw, 0, 0, getWidth(), getHeight(), this);

        final int brightness = (int)(256 - 256 * this.brightness);
        g.setColor(new Color(0, 0, 0, brightness));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    //endregion
}