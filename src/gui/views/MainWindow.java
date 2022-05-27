package gui.views;

import gui.EventCallback;
import model.Game;
import model.StatusListener;
import model.enums.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    //region CONSTANTS
    private static final String TITLE = "Risk";
    private static final String PATH_ICON = "./img/icon.png";
    //endregion

    //region FIELDS
    private final Game game;
    //private GameStatus current;
    private JPanel cards; //a panel that uses CardLayout
    //endregion

    //region CONSTRUCTORS
    /**
     * Constructor.
     * @param game Game object.
     */
    public MainWindow(final Game game) {
        // set up window title.
        super(TITLE);
        this.game = game;

        // add MenuBar to window
        this.setJMenuBar(this.menuBarConfiguration());

        // generic settings
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setIconImage(new ImageIcon(PATH_ICON).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // content in window panels, which are card layout to substitute when a game status changes
        this.initCards();

        // deployment
        this.pack();
        this.setVisible(true);
    }
    //endregion

    //region METHODS
    /**
     * Function - configure the menu bar.
     * @return JMenuBar swing object
     */
    private final JMenuBar menuBarConfiguration() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem item1 = new JMenuItem("Save");
        JMenuItem item2 = new JMenuItem("Load");
        JMenuItem item3 = new JMenuItem("Quit");
        item3.addActionListener(e -> this.dispose());
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * Procedure - Initialize all the card panels.
     */
    private void initCards() {
        // show different game panel based on game status changing
        game.addListener(new StatusListener() {
            @Override public void changed(final GameStatus status) {
                CardLayout cl = (CardLayout) (cards.getLayout());
                cl.show(cards, status.toString());
                cards.getComponents()[0].requestFocus();
            }
        });

        //Create the "cards" (panels for each game status)
        final JPanel mainMenuCard = new JMainMenu(new EventCallback() {
            @Override public void onEvent(int id) {
                game.nextStatus();
            }
        });
        final JPanel playCard = new JSetup(game, this);

        //Create the panel that contains the "cards".
        this.cards = new JPanel(new CardLayout());                      // create card layout for panels
        this.cards.add(mainMenuCard, GameStatus.MENU.toString());        // add menu panel
        this.cards.add(playCard, GameStatus.SETUP.toString());           // add setup panel
        this.getContentPane().add(this.cards, BorderLayout.CENTER);     // position panels at the center whole screen
        game.setStatus(GameStatus.MENU);                                // set first game status at menu
    }
    //endregion
}
