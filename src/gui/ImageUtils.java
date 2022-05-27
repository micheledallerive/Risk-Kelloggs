package gui;

import model.enums.ArmyColor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Class of Utils methods.
 * @author dallem@usi.ch
 */
public class ImageUtils {
    /**
     * Constructor - non istantiable class.
     */
    private ImageUtils() {

    }

    /**
     * Convert an Image to a buffered one.
     * @param image Image to convert.
     * @return Buffered image object.
     */
    public static BufferedImage imageToBufferedImage(final Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    /**
     * Apply roundness to corners image.
     * @param image Image to round corners.
     * @param cornerRadius Integer number of corner radius.
     * @return Buffered image object.
     */
    public static BufferedImage makeRoundedCorner(final BufferedImage image, final int cornerRadius) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    /**
     * Create a correspondence between armycolor enum and color object.
     * @param armyColor Enum of armycolor to correspond.
     * @return Color object.
     */
    public static Color armyColorToColor(final ArmyColor armyColor) {
        switch (armyColor) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            case YELLOW:
                return Color.YELLOW;
            case PINK:
                return Color.PINK;
            case BLACK:
                return Color.BLACK;
            default:
                return Color.WHITE;
        }
    }
}
