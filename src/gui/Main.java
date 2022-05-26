package gui;

import gui.views.JMainMenu;
import model.Game;
import model.enums.GameStatus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import javax.swing.*;


/**
 * GUI class.
 * 
 * @author moralj@usi.ch, dallem@usi.ch
 */
public class Main {
    JPanel cards; //a panel that uses CardLayout
    GameStatus current; //current card
    Game game;

    /**
     * Procedure - show different game panel.
     * @param currentStatus status to display
     */
    public void show(final GameStatus currentStatus) {
        if (this.current == currentStatus) { return; }
        final CardLayout cl = (CardLayout) (this.cards.getLayout());
        cl.show(cards, currentStatus.toString());
        this.current = currentStatus;
    }

    /**
     * Procedure - setup the gui elements.
     * @param pane Container object to add cards to.
     */
    public void setup(Container pane) {
        // Create the game.
        this.game = new Game();

        //Create the "cards".
        JPanel mainMenuCard = new JMainMenu(new ClickCallback() {
            @Override
            public void onClick(int id) {
                //how(TEXTPANEL);
            }
        });

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(mainMenuCard, GameStatus.MENU.toString());
        //cards.add(card2, TEXTPANEL);
        pane.add(cards, BorderLayout.CENTER);
        show(GameStatus.MENU);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem item1 = new JMenuItem("Save");
        JMenuItem item2 = new JMenuItem("Load");
        JMenuItem item3 = new JMenuItem("Quit");
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

        //Create and set up the content pane.
        Main main = new Main();
        main.setup(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Procedure - Main method.
     * @param args Optional arguments.
     */
    public static void main(String[] args) {
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

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}
