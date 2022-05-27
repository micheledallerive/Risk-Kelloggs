package gui.components;

import gui.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Class for panel with background image.
 * @author dallem@usi.ch
 */
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
     * @param image Image object of panel background.
     */
    public ImageBackgroundPanel(final Image image) {
        this(image, BRIGHTNESS);
    }

    /**
     * Constructor.
     * @param path String of image path.
     */
    public ImageBackgroundPanel(final String path) {
        this(path, BRIGHTNESS);
    }

    /**
     * Constructor.
     * @param path String of image path.
     * @param brightness Double number of brightness value.
     */
    public ImageBackgroundPanel(final String path, final float brightness) {
        this(new ImageIcon(path).getImage(), brightness);
    }

    /**
     * Main constructor.
     * @param image Image object of panel background.
     * @param brightness Double number of brightness value.
     */
    public ImageBackgroundPanel(final Image image, final float brightness) {
        this.image = image;
        this.roundedImage = null;
        this.brightness = brightness;
    }
    //endregion

    //region METHODS

    /**
     * setter of brightness field.
     * @param brightness New float value for brightness.
     */
    public void setBrightness(final float brightness) {
        this.brightness = brightness;
    }

    /**
     * setter of rounded image field.
     * @param radius New radius to round each image corner.
     */
    public void setRoundedCorners(final int radius) {
        this.roundedImage = Utils.makeRoundedCorner(Utils.imageToBufferedImage(image), radius);
    }

    /**
     * Procedure - reset rounded corners of image.
     */
    public void resetRoundedCorners() {
        this.roundedImage = null;
    }

    @Override public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        super.setOpaque(false);

        Image toDraw = roundedImage != null ? roundedImage : image;
        graphics.drawImage(toDraw, 0, 0, getWidth(), getHeight(), this);

        final int brightness = (int)(256 - 256 * this.brightness);
        graphics.setColor(new Color(0, 0, 0, brightness));
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }
    //endregion
}