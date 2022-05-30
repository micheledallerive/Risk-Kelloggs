package gui;

import gui.components.JDie;
import gui.utils.FontUtils;
import gui.utils.MapUtils;
import gui.views.MainWindow;
import model.Game;

import java.util.Enumeration;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

/**
 * Main class starting the GUI game.
 *
 * @author moralj @usi.ch, dallem@usi.ch
 */
public class Main {
    /**
     * Procedure - set the global font of the game.
     *
     * @param fontUIResource FontUIResource object.
     */
    public static void setUIFont(final FontUIResource fontUIResource) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontUIResource);
            }
        }
    }

    /**
     * Procedure - initializations.
     */
    private void inits() {
        FontUtils.init();
        setUIFont(new FontUIResource(FontUtils.getFont()));
        JDie.init();
        MapUtils.init();
    }

    /**
     * Constructor.
     */
    public Main() {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final UnsupportedLookAndFeelException
                       | IllegalAccessException
                       | InstantiationException
                       | ClassNotFoundException
                       | ClassCastException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        inits();

        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        // EventQueue.invokeLater((Runnable) new MainWindow(this.game));
        // javax.swing.SwingUtilities.invokeLater();
        SwingUtilities.invokeLater(() -> new MainWindow(new Game()));
    }


    /**
     * Procedure - Main method.
     *
     * @param args Optional arguments.
     */
    public static void main(String[] args) {
        new Main();
    }
}