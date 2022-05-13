package gui;

import gui.views.JMainMenu;
import model.Game;
import model.enums.GameStatus;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * GUI class.
 * 
 * @author moralj@usi.ch, dallem@usi.ch
 */
public class Main {
    JPanel cards; //a panel that uses CardLayout
    String current = ""; //current card

    Game game;

    /**
     * Procedure - show different game panel.
     * @param currentStatus status to display
     */
    public void show(GameStatus currentStatus) {
        if (currentStatus.toString().equals(current)) {
            return;
        }
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, currentStatus.toString());
        current = currentStatus.toString();
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
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Risk");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

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
