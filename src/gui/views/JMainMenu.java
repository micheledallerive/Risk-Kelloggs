package gui.views;

import gui.EventCallback;
import gui.FontManager;
import gui.components.ImageBackgroundPanel;
import gui.components.TransparentPanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Class JMainMenu extended from JPanel to create main menu.
 * @author dallem@usi.ch
 */
public class JMainMenu extends ImageBackgroundPanel {

    /*private void updateText(final JLabel label) {
        final String DEFAULT = "Press any key to start";
        final String[] dots = new String[]{"", ".", "..", "..."};
        final String[] spaces = new String[]{"   ", "  ", " " , ""};
        final int[] dotsCounter = {0};
        Runnable runnable = () -> {
            int laps = dotsCounter[0]/dots.length;
            int indexToShow;
            if(laps % 2 == 0) {
                indexToShow = dotsCounter[0]%dots.length;
            } else {
                indexToShow = dots.length - dotsCounter[0]%dots.length -1;
            }
            label.setText(DEFAULT + dots[indexToShow] + spaces[indexToShow]);
            dotsCounter[0]++;
        };
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 750, TimeUnit.MILLISECONDS);
    }*/

    /**
     * Constructor.
     * @param callback Event for clickable buttons
     */
    public JMainMenu(EventCallback callback) {
        super("src/gui/assets/images/main_menu.png");

        GridLayout layout = new GridLayout(4, 1);

        JPanel titlePanel = new TransparentPanel();
        titlePanel.setBorder(new EmptyBorder(50, 0, 0, 0));
        titlePanel.setLayout(new GridBagLayout());
        JLabel title = new JLabel("Risk Kellogg's");
        title.setFont(FontManager.getFont().deriveFont(Font.BOLD, 100));

        titlePanel.add(title);

        JPanel labelPanel = new TransparentPanel();
        labelPanel.setLayout(new GridBagLayout());
        JLabel label = new JLabel("Press a key or click anywhere to start...");
        label.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 40));
        labelPanel.add(label);


        add(titlePanel);
        add(new TransparentPanel());
        add(labelPanel);
        setLayout(layout);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                callback.onEvent(0);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                callback.onEvent(0);
            }
        });

    }
}
