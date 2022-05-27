package gui;

import gui.components.JDie;
import gui.views.JSetup;
import gui.views.MapPanel;
import gui.views.JMainMenu;
import gui.views.MainWindow;
import model.Game;
import model.enums.GameStatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

/**
 * GUI class.
 * 
 * @author moralj@usi.ch, dallem@usi.ch
 */
public class Main {
    Game game;

    public static void setUIFont(final FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

    private void inits() {
        FontManager.init();
        setUIFont(new FontUIResource(FontManager.getFont()));
        JDie.init();
        Map.init();
    }

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
        this.game = new Game();

        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        // EventQueue.invokeLater((Runnable) new MainWindow(this.game));
        // javax.swing.SwingUtilities.invokeLater();
        new MainWindow(this.game);
    }


    /**
     * Procedure - Main method.
     * @param args Optional arguments.
     */
    public static void main(String[] args) {
        new Main();
    }
}