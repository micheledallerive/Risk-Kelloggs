package gui.components;

import gui.EventCallback;
import model.Die;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
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
    private ArrayList<EventCallback> callbacks;
    private boolean rolling;
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
        this.rolling = false;
        this.value = 1;
        this.callbacks = new ArrayList<>();
        this.timer = new Timer(frameDuration, e -> {
            if (this.animation == totalDuration / frameDuration) {
                this.animation = this.value - 1;
                stop();
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
        this.rolling = true;
        this.value = Die.casualRoll();
        this.timer.start();
    }

    /**
     * Procedure - add callback.
     * @param callback Interface implemented.
     */
    public void addCallback(EventCallback callback) {
        callbacks.add(callback);
    }

    /**
     * Procedure - stop the rolling dice.
     */
    public void stop() {
        this.rolling = false;
        this.timer.stop();
        for (EventCallback callback : callbacks) {
            callback.onEvent(0);
        }
    }

    @Override public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // for some reason the paintComponent is called even in dice that are not rolling
        // to avoid this i check if it is rolling, only in that case I show a random number.
        int imageIndex = this.rolling ? Die.casualRoll() - 1 : this.value - 1;
        imageIndex = Math.max(imageIndex, 0);
        graphics.drawImage(images[imageIndex].getImage(), 0, 0, this);
    }
    //endregion
}
