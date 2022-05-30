package gui.utils;

import gui.components.JPopup;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/**
 * The type Popup utils.
 */
public class PopupUtils {

    private static final int AUTOHIDE_DELAY = 5000;

    /**
     * Show popup.
     *
     * @param message the message
     * @param x       the x
     * @param y       the y
     */
    public static void showPopup(String message, int x, int y) {
        showPopup(null, message, true, x, y);
    }

    /**
     * Show popup.
     *
     * @param parent  the parent
     * @param message the message
     * @param x       the x
     * @param y       the y
     */
    public static void showPopup(Component parent, String message, int x, int y) {
        JPopup popup = new JPopup(message, true);
        popup.show(parent, x, y);
    }

    /**
     * Show popup.
     *
     * @param parent   the parent
     * @param message  the message
     * @param autohide the autohide
     * @param x        the x
     * @param y        the y
     */
    public static void showPopup(Component parent, String message, boolean autohide, int x, int y) {
        JPopupMenu popup = new JPopupMenu();

        popup.setLayout(new BorderLayout());
        popup.add(new JLabel(message), BorderLayout.CENTER);
        popup.show(parent, x, y);
        if (autohide) {
            new Timer(AUTOHIDE_DELAY, e -> popup.setVisible(false)).start();
        }
    }
}
