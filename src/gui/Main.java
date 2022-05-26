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
import javax.swing.*;
import javax.swing.plaf.FontUIResource;


/**
 * GUI class.
 * 
 * @author moralj@usi.ch, dallem@usi.ch
 */
public class Main {
    JPanel cards; //a panel that uses CardLayout
    String current = ""; //current card
    Game game;

    private void setDefaultFont(Font font) {
        FontUIResource myFont = new FontUIResource(font);
        UIManager.put("CheckBoxMenuItem.acceleratorFont", myFont);
        UIManager.put("Button.font", myFont);
        UIManager.put("ToggleButton.font", myFont);
        UIManager.put("RadioButton.font", myFont);
        UIManager.put("CheckBox.font", myFont);
        UIManager.put("ColorChooser.font", myFont);
        UIManager.put("ComboBox.font", myFont);
        UIManager.put("Label.font", myFont);
        UIManager.put("List.font", myFont);
        UIManager.put("MenuBar.font", myFont);
        UIManager.put("Menu.acceleratorFont", myFont);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", myFont);
        UIManager.put("MenuItem.acceleratorFont", myFont);
        UIManager.put("MenuItem.font", myFont);
        UIManager.put("RadioButtonMenuItem.font", myFont);
        UIManager.put("CheckBoxMenuItem.font", myFont);
        UIManager.put("OptionPane.buttonFont", myFont);
        UIManager.put("OptionPane.messageFont", myFont);
        UIManager.put("Menu.font", myFont);
        UIManager.put("PopupMenu.font", myFont);
        UIManager.put("OptionPane.font", myFont);
        UIManager.put("Panel.font", myFont);
        UIManager.put("ProgressBar.font", myFont);
        UIManager.put("ScrollPane.font", myFont);
        UIManager.put("Viewport.font", myFont);
        UIManager.put("TabbedPane.font", myFont);
        UIManager.put("Slider.font", myFont);
        UIManager.put("Table.font", myFont);
        UIManager.put("TableHeader.font", myFont);
        UIManager.put("TextField.font", myFont);
        UIManager.put("Spinner.font", myFont);
        UIManager.put("PasswordField.font", myFont);
        UIManager.put("TextArea.font", myFont);
        UIManager.put("TextPane.font", myFont);
        UIManager.put("EditorPane.font", myFont);
        UIManager.put("TabbedPane.smallFont", myFont);
        UIManager.put("TitledBorder.font", myFont);
        UIManager.put("ToolBar.font", myFont);
        UIManager.put("ToolTip.font", myFont);
        UIManager.put("Tree.font", myFont);
        UIManager.put("FormattedTextField.font", myFont);
        UIManager.put("IconButton.font", myFont);
        UIManager.put("InternalFrame.optionDialogTitleFont", myFont);
        UIManager.put("InternalFrame.paletteTitleFont", myFont);
        UIManager.put("InternalFrame.titleFont", myFont);
    }

    private void inits() {

        FontManager.init();
        setDefaultFont(FontManager.getFont());

        JDie.init();

        Map.init();
    }

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
        if (currentStatus.toString().equals(current)) { return; }

        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, currentStatus.toString());
        cards.getComponents()[0].requestFocus();
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