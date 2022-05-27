package gui.components;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Class.
 * @author dallem@usi.ch
 */
public class ImageBackgroundDialog extends JDialog {
    /**
     * Constructor.
     * @param parent
     * @param title
     * @param modal
     * @param path
     * @param brightness
     */
    public ImageBackgroundDialog(JFrame parent, String title, boolean modal, String path, float brightness) {
        this(parent, title, modal, path, brightness,0);
    }

    /**
     * Constructor.
     * @param parent
     * @param title
     * @param modal
     * @param path
     * @param brightness
     * @param radius
     */
    public ImageBackgroundDialog(JFrame parent, String title, boolean modal,
                                 String path, float brightness, int radius) {
        super(parent, title, modal);
        this.setUndecorated(true);
        this.setBackground (new Color (0, 0, 0, 0));
        final Image image = new ImageIcon(path).getImage();
        final ImageBackgroundPanel panel = new ImageBackgroundPanel(image, brightness);
        panel.setRoundedCorners(radius);
        panel.setBorder(new EmptyBorder(30,20,30,20));
        this.setContentPane(panel);
        this.setUndecorated(true);
    }

}
