package gui.utils;

import gui.components.JPopup;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/**
 * The type Popup utils.
 *
 * @author dallem @usi.ch
 */
public class PopupUtils {

    private static final int AUTOHIDE_DELAY = 5000;

    /**
     * Show popup.
     *
     * @param message the message
     * @param pointX  the x
     * @param pointY  the y
     */
    public static void showPopup(String message, int pointX, int pointY) {
        showPopup(null, message, true, pointX, pointY);
    }

    /**
     * Show popup.
     *
     * @param parent  the parent
     * @param message the message
     * @param pointX  the pointX
     * @param pointY  the pointY
     */
    public static void showPopup(Component parent, String message, int pointX, int pointY) {
        JPopup popup = new JPopup(message, true);
        popup.show(parent, pointX, pointY);
    }

    /**
     * Show popup.
     *
     * @param parent   the parent
     * @param message  the message
     * @param autohide the autohide
     * @param pointX   the x
     * @param pointY   the y
     */
    public static void showPopup(Component parent, String message, boolean autohide, int pointX, int pointY) {
        JPopupMenu popup = new JPopupMenu();

        popup.setLayout(new BorderLayout());
        popup.add(new JLabel(message), BorderLayout.CENTER);
        popup.show(parent, pointX, pointY);
        if (autohide) {
            new Timer(AUTOHIDE_DELAY, e -> popup.setVisible(false)).start();
        }
    }
}
