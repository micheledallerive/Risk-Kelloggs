package gui.utils;

import model.Player;
import model.enums.ArmyColor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Class of Utils methods.
 *
 * @author dallem @usi.ch
 */
public class ImageUtils {
    /**
     * Constructor - non istantiable class.
     */
    private ImageUtils() {

    }

    /**
     * Gets image.
     *
     * @param path the path
     * @return the image
     */
    public static Image getImage(final String path) {
        return new ImageIcon(path).getImage();
    }

    /**
     * Convert an Image to a buffered one.
     *
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
     * scale image
     *
     * @param sbi       image to scale
     * @param imageType type of image
     * @param dWidth    width of destination image
     * @param dHeight   height of destination image
     * @return scaled image
     */
    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight) {
        BufferedImage dbi = null;
        if (sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(((float) dWidth) / sbi.getWidth(),
                ((float) dHeight) / sbi.getHeight());
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

    /**
     * Apply roundness to corners image.
     *
     * @param image        Image to round corners.
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
     *
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

    /**
     * Choose foreground color color.
     *
     * @param color the color
     * @return the color
     */
    public static Color chooseForegroundColor(ArmyColor color) {
        Color rgb = armyColorToColor(color);
        int red = rgb.getRed();
        int green = rgb.getGreen();
        int blue = rgb.getBlue();
        return (red * 0.299 + green * 0.587 + blue * 0.114) > 100 ? Color.BLACK : Color.WHITE;
    }

    /**
     * Gets player icon.
     *
     * @param player the player
     * @return the player icon
     */
    public static Image getPlayerIcon(Player player) {
        String path = "src/gui/assets/images/icon/";
        path += player.isAI() ? "ai" : "player";
        path += ".png";

        return new ImageIcon(path).getImage();
    }
}
