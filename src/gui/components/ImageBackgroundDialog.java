package gui.components;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ImageBackgroundDialog extends JDialog {

    public ImageBackgroundDialog(JFrame parent, String title, boolean modal, String path, float brightness) {
        super(parent,title,modal);
        Image image = new ImageIcon(path).getImage();
        JPanel panel = new ImageBackgroundPanel(image,brightness);
        panel.setBorder(new EmptyBorder(20,20,20,20));
        setContentPane(panel);
        setUndecorated(true);
    }
}
