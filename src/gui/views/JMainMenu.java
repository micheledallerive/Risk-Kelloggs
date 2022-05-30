package gui.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.EventCallback;
import gui.components.ImageBackgroundPanel;
import gui.components.TransparentPanel;
import gui.utils.FontUtils;

//TODO: Rename JMainMenu

/**
 * Class JMainMenu extended from JPanel to create main menu.
 *
 * @author dallem@usi.ch
 */
public class JMainMenu extends ImageBackgroundPanel {

    // region CONSTANTS
    private static final String TITLE = "Risk Kellogg's";
    private static final int SIZE_TITLE = 100;
    private static final String SUBTITLE = "Press a key or click anywhere to start...";
    private static final int SIZE_SUBTITLE = 40;
    private static final String BACKGROUND = "src/gui/assets/images/main_menu.jpg";
    // endregion

    // region CONSTRUCTOR

    /**
     * Constructor.
     *
     * @param callback Event for clickable buttons
     */
    public JMainMenu(final EventCallback callback) {
        super(BACKGROUND);

        // set layout: title 1st row, 2nd space, 3rd subtitle, 4th space
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // create title panel, and title text, font, color
        // create subtitle panel, and subtitle text, font, color
        final JPanel titlePanel = new TransparentPanel();
        final JPanel labelPanel = new TransparentPanel();
        titlePanel.setBorder(new EmptyBorder(50, 0, 0, 0)); // add top margin
        final JLabel title = new JLabel(TITLE);
        final JLabel label = new JLabel(SUBTITLE);
        title.setFont(FontUtils.getFont().deriveFont(Font.BOLD, SIZE_TITLE));
        label.setFont(FontUtils.getFont().deriveFont(Font.PLAIN, SIZE_SUBTITLE));
        title.setForeground(Color.WHITE);
        label.setForeground(Color.WHITE);
        titlePanel.add(title);
        labelPanel.add(label);

        // add panels to JFrame
        this.add(titlePanel);
        this.add(new TransparentPanel());
        this.add(labelPanel);

        // add events to click or push any key to start the game
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                callback.onEvent(0);
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                callback.onEvent(0);
            }
        });
    }
    // endregion
}
