package gui;

import gui.components.JDie;
import gui.views.JSetup;
import gui.views.MapPanel;
import gui.views.JMainMenu;
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
    JPanel cards; //a panel that uses CardLayout
    GameStatus current; //current card
    Game game;

    public static void setUIFont (final FontUIResource f){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
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

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    /**
     * Procedure - show different game panel.
     * @param currentStatus status to display
     */
    public void show(final GameStatus currentStatus) {
        if (currentStatus == current) { return; }

        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, currentStatus.toString());
        cards.getComponents()[0].requestFocus();
        current = currentStatus;
    }

    /**
     * Initialize all the card panels.
     * @param frame the frame of the game.
     */
    public void initCards(JFrame frame) {
        Container pane = frame.getContentPane();

        //Create the "cards".
        JPanel mainMenuCard = new JMainMenu(new EventCallback() {
            @Override
            public void onEvent(int id) {
                System.out.println("Changing status");
                game.nextStatus();
                show(game.getStatus());
            }
        });

        JPanel playCard = new JSetup(game, frame);

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(mainMenuCard, GameStatus.MENU.toString());
        cards.add(playCard, GameStatus.SETUP.toString());
        pane.add(cards, BorderLayout.CENTER);

        show(GameStatus.MENU);
    }

    /**
     * Procedure - setup the gui elements.
     * @param frame The frame of the game.
     */
    public void setup(JFrame frame) {
        initCards(frame);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem item1 = new JMenuItem("Save");
        JMenuItem item2 = new JMenuItem("Load");
        JMenuItem item3 = new JMenuItem("Quit");
        item1.setEnabled(this.game.getStatus().ordinal() > GameStatus.MENU.ordinal());
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menubar.add(menu);
        frame.setJMenuBar(menubar);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("./img/icon.png").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setup(frame);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Procedure - Main method.
     * @param args Optional arguments.
     */
    public static void main(String[] args) {
        new Main();
    }
}