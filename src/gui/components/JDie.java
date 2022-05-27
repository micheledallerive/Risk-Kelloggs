package gui.components;

import gui.EventCallback;
import model.Die;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * Class Die graphical representation.
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class JDie extends JComponent {
    //region CONSTANTS
    private static final String PATH = "src/gui/assets/images/die/white/";
    private static final String EXT = ".png";
    private static final byte SIZE = 64;
    private static final byte FACES = 6;
    //endregion

    //region FIELDS
    private static final ImageIcon[] images = new ImageIcon[6];
    private Timer timer;
    private int animation = 0;
    private int value;
    private EventCallback callback;
    //endregion

    //region CONSTRUCTORS

    /**
     * Constructor - default empty.
     */
    public JDie() {
        this(250, 5000);
    }

    /**
     * Constructor - full optional.
     * @param frameDuration Duration in milliseconds of Timer.
     * @param totalDuration Total amount of milliseconds to roll.
     */
    public JDie(final int frameDuration, final int totalDuration) {
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.timer = new Timer(frameDuration, e -> {
            this.value = (int) (Math.random() * FACES);
            if (this.animation == totalDuration / frameDuration) {
                this.animation = this.value - 1;
                this.timer.stop();
            } else {
                this.animation++;
            }
            this.repaint();
        });
    }
    //endregion

    //region METHODS

    /**
     * Procedure - initialization of images.
     */
    public static final void init() {
        for (int i = 0; i < FACES; i++) {
            images[i] = new ImageIcon(PATH + (i + 1) + EXT);
        }
    }

    /**
     * getter of int value of face represented.
     * @return integer value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Procedure - rolls the dice.
     */
    public void roll() {
        this.value = Die.casualRoll();
        this.timer.start();
    }

    /**
     * Procedure - add callback.
     * @param callback Interface implemented.
     */
    public void addCallback(EventCallback callback) {
        this.callback = callback;
    }

    /**
     * Procedure - stop the rolling dice.
     */
    public void stop() {
        this.timer.stop();
        if (callback != null) {
            this.callback.onEvent(0);
        }
    }

    @Override public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(images[Die.casualRoll() - 1].getImage(), 0, 0, this);
    }
    //endregion
}
