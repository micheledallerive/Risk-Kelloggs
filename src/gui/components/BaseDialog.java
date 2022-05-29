package gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Class for message modal dialogs.
 *
 * @author dallem@usi.ch
 */
public class BaseDialog extends ImageBackgroundDialog {

    /**
     * Constructor.
     *
     * @param parent Invoking JFrame parent.
     * @param title  String title of Dialog.
     * @param modal  Boolean stating if modal.
     */
    public BaseDialog(JFrame parent, String title, boolean modal) {
        this(parent, title, modal, 0);
    }

    /**
     * Constructor - full options.
     *
     * @param parent Invoking JFrame parent.
     * @param title  String title of Dialog.
     * @param modal  Boolean stating if modal.
     * @param radius Integer number of radius for round corners.
     */
    public BaseDialog(JFrame parent, String title, boolean modal, int radius) {
        super(parent, title, modal, "src/gui/assets/images/dialog_texture.png", 1f, radius);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        parent.setGlassPane(new JComponent() {
            public void paintComponent(Graphics graphics) {
                graphics.setColor(new Color(0, 0, 0, 200));
                graphics.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(graphics);
            }
        });
        parent.getGlassPane().setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);
                parent.getGlassPane().setVisible(false);
            }
        });
    }
}
