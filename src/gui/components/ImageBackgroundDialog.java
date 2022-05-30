package gui.components;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

/**
 * Class to create a modal dialog with an image as background.
 *
 * @author dallem @usi.ch
 */
public class ImageBackgroundDialog extends JDialog {
    /**
     * Constructor.
     *
     * @param parent     JFrame parent component.
     * @param title      String of current component.
     * @param modal      Boolean stating if modal.
     * @param path       String path of Image.
     * @param brightness Double number about brightness value.
     */
    public ImageBackgroundDialog(JFrame parent, String title, boolean modal, String path, float brightness) {
        this(parent, title, modal, path, brightness, 0);
    }

    /**
     * Constructor.
     *
     * @param parent     JFrame parent component.
     * @param title      String of current component.
     * @param modal      Boolean stating if modal.
     * @param path       String path of Image.
     * @param brightness Double number about brightness value.
     * @param radius     Integer number of radius of corner roundness.
     */
    public ImageBackgroundDialog(JFrame parent, String title, boolean modal,
                                 String path, float brightness, int radius) {
        super(parent, title, modal);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        final Image image = new ImageIcon(path).getImage();
        final ImageBackgroundPanel panel = new ImageBackgroundPanel(image, brightness);
        panel.setRoundedCorners(radius);
        panel.setBorder(new EmptyBorder(30, 20, 30, 20));
        this.setContentPane(panel);
        this.setUndecorated(true);
    }

}
