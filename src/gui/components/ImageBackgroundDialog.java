package gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ImageBackgroundDialog extends JDialog {
    public ImageBackgroundDialog(JFrame parent, String title, boolean modal,
                                 String path, float brightness) {
        this(parent,title,modal,path,brightness,0);
    }
    public ImageBackgroundDialog(JFrame parent, String title, boolean modal,
                                 String path, float brightness, int radius) {
        super(parent,title,modal);
        setUndecorated(true);
        setBackground (new Color (0, 0, 0, 0));
        Image image = new ImageIcon(path).getImage();
        ImageBackgroundPanel panel = new ImageBackgroundPanel(image,brightness);
        panel.setRoundedCorners(radius);
        panel.setBorder(new EmptyBorder(30,20,30,20));
        setContentPane(panel);
    }

}
