package gui.components;

import gui.utils.ImageUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Class for panel with background image.
 *
 * @author dallem @usi.ch
 */
public class ImageBackgroundPanel extends TransparentPanel {

    //region CONSTANTS
    private static final float BRIGHTNESS = 0.33f;
    //endregion

    //region FIELDS
    private Image image;
    private Image roundedImage;
    private int radius;
    private float brightness;
    private Dimension dimension;
    //endregion

    //region CONSTRUCTORS

    /**
     * Constructor.
     *
     * @param image Image object of panel background.
     */
    public ImageBackgroundPanel(final Image image) {
        this(image, BRIGHTNESS);
    }

    /**
     * Constructor.
     *
     * @param path String of image path.
     */
    public ImageBackgroundPanel(final String path) {
        this(path, BRIGHTNESS);
    }

    /**
     * Constructor.
     *
     * @param path       String of image path.
     * @param brightness Double number of brightness value.
     */
    public ImageBackgroundPanel(final String path, final float brightness) {
        this(new ImageIcon(path).getImage(), brightness);
    }

    /**
     * Main constructor.
     *
     * @param img        Image object of panel background.
     * @param brightness Double number of brightness value.
     */
    public ImageBackgroundPanel(final Image img, final float brightness) {
        super();
        this.image = img;
        this.roundedImage = null;
        this.brightness = brightness;
        this.dimension = null;
    }
    //endregion

    //region METHODS

    /**
     * setter of brightness field.
     *
     * @param brightness New float value for brightness.
     */
    public void setBrightness(final float brightness) {
        this.brightness = brightness;
    }

    /**
     * setter of rounded image field.
     *
     * @param radius New radius to round each image corner.
     */
    public void setRoundedCorners(final int radius) {
        if (dimension != null && dimension.width > 0 && dimension.height > 0) {
            this.roundedImage = ImageUtils.scale(
                ImageUtils.imageToBufferedImage(this.image),
                BufferedImage.TYPE_INT_ARGB,
                dimension.width, dimension.height);
        } else {
            this.roundedImage = image;
        }
        this.roundedImage = ImageUtils.makeRoundedCorner(ImageUtils.imageToBufferedImage(roundedImage), radius);
        this.radius = radius;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        this.dimension = preferredSize;
    }

    /**
     * Procedure - reset rounded corners of image.
     */
    public void resetRoundedCorners() {
        this.roundedImage = null;
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        super.setOpaque(false);

        Graphics2D graphics = (Graphics2D) gr;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Image toDraw = roundedImage != null ? roundedImage : image;
        graphics.drawImage(toDraw, 0, 0, getWidth(), getHeight(), this);

        final int brightness = (int) (256 - 256 * this.brightness);
        graphics.setColor(new Color(0, 0, 0, brightness));
        if (roundedImage != null) {
            graphics.fillRoundRect(0, 0, getWidth() - 1, getWidth() - 1, radius, radius);
        } else {
            graphics.fillRect(0, 0, getWidth(), getWidth());
        }
    }
    //endregion
}