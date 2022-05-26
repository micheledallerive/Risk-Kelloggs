package gui.components;

import gui.EventCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MessageDialog extends ImageBackgroundDialog {

    JFrame parent;

    public MessageDialog(JFrame parent, String title, boolean modal) {
        this(parent, title, modal, 0);
    }

    public MessageDialog(JFrame parent, String title, boolean modal, int radius) {
        super(parent, title, modal, "src/gui/assets/images/dialog_texture.png", 1f, radius);
        this.parent = parent;
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        parent.setGlassPane(new JComponent() {
            public void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        });
        parent.getGlassPane().setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                parent.getGlassPane().setVisible(false);
            }
        });
    }
}
