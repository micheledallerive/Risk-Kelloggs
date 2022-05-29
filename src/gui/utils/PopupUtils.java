package gui.utils;

import gui.components.ImageBackgroundPanel;
import gui.components.JPopup;

import javax.swing.*;
import java.awt.*;

public class PopupUtils {

    private static final int AUTOHIDE_DELAY = 5000;

    public static void showPopup(String message, int x, int y) {
        showPopup(null, message, true, x, y);
    }

    public static void showPopup(Component parent, String message, int x, int y) {
        JPopup popup = new JPopup(message, true);
        popup.show(parent, x, y);
    }

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
