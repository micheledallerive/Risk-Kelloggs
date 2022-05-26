package gui.views;

import gui.EventCallback;
import gui.FontManager;
import gui.components.ImageBackgroundPanel;
import gui.components.TransparentPanel;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;


/**
 * Class JMainMenu extended from JPanel to create main menu.
 * @author dallem@usi.ch
 */
public class JMainMenu extends ImageBackgroundPanel {

    private void updateText(final JLabel label) {
        final String DEFAULT = "Press any key to start";
        final String[] dots = new String[]{"", ".", "..", "..."};
        final int[] dotsCounter = {0};
        Runnable runnable = () -> {
            int carry = dotsCounter[0]%dots.length;
            int indexToShow;
            if(carry % 2 == 0) {
                indexToShow = dotsCounter[0];
            } else {
                indexToShow = dots.length - dotsCounter[0];
            }
            label.setText(DEFAULT + dots[indexToShow]);
            dotsCounter[0]++;
        };
        ScheduledExecutorService executor =
                Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, 750, TimeUnit.MILLISECONDS);
    }

    /**
     * Constructor.
     * @param callback Event for clickable buttons
     */
    public JMainMenu(EventCallback callback) {
        super("src/gui/assets/images/main_menu.png");

        GridLayout layout = new GridLayout(4, 1);

        JPanel titlePanel = new TransparentPanel();
        titlePanel.setLayout(new GridBagLayout());
        JLabel title = new JLabel("Risk Kellogg's");
        title.setFont(FontManager.getFont().deriveFont(Font.BOLD, 80));
        titlePanel.add(title);

        JPanel labelPanel = new TransparentPanel();
        labelPanel.setLayout(new GridBagLayout());
        JLabel label = new JLabel("Press any key to start");
        label.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 40));
        updateText(label);
        labelPanel.add(label);


        add(titlePanel);
        add(new TransparentPanel());
        add(labelPanel);
        setLayout(layout);
    }
}
