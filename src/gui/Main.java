package gui;

import gui.components.MapPanel;
import gui.views.JMainMenu;
import model.Game;
import model.enums.GameStatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;


/**
 * GUI class.
 * 
 * @author moralj@usi.ch, dallem@usi.ch
 */
public class Main {
    JPanel cards; //a panel that uses CardLayout
    String current = ""; //current card
    Game game;

    public Main() {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException
                 | IllegalAccessException
                 | InstantiationException
                 | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        FontManager.init();

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
        if (currentStatus.toString().equals(current)) { return; }
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, currentStatus.toString());
        current = currentStatus.toString();
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

            }
        });

        JPanel mapCard = new MapPanel(game);

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(mainMenuCard, GameStatus.MENU.toString());
        cards.add(mapCard, GameStatus.PLAYING.toString());
        //cards.add(card2, TEXTPANEL);
        pane.add(cards, BorderLayout.CENTER);
    }

    /**
     * Procedure - setup the gui elements.
     * @param frame The frame of the game.
     */
    public void setup(JFrame frame) {
        initCards(frame);
        show(GameStatus.MENU);


        // game.play(new GameCallback() {
        //    @Override
        //    public boolean onMainMenu() {
        //        //show(GameStatus.MENU);
        //        return false;
        //    }
        //
        //    @Override
        //    public boolean onGameSetup() {
        //
        //        return true;
        //    }
        //
        //    @Override
        //    public boolean onGamePlay() {
        //        show(GameStatus.PLAYING);
        //        return false;
        //    }
        //
        //    @Override
        //    public boolean onGamePause() {
        //        show(GameStatus.PAUSE);
        //        return false;
        //    }
        //
        //    @Override
        //    public boolean onGameEnd() {
        //        show(GameStatus.END);
        //        return false;
        //    }
        //
        //    @Override
        //    public void onGameExit() {
        //        System.exit(0);
        //    }
        //});
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        frame.setSize(800,600);

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
        frame.setVisible(true);
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