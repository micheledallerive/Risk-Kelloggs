package gui.components;

import model.Die;
import model.enums.DieColor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

import gui.EventCallback;

/**
 * Class Die graphical representation.
 *
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class JDie extends JComponent {
    //region CONSTANTS
    private static final String PATH = "src/gui/assets/images/die/";
    private static final String EXT = ".png";
    private static final byte SIZE = 64;
    private static final byte FACES = 6;
    //endregion

    //region FIELDS
    private static final HashMap<DieColor, ImageIcon[]> images = new HashMap<>();
    private Timer timer;
    private int animation = 0;
    private int value;
    private ArrayList<EventCallback> callbacks;
    private boolean rolling;
    private DieColor color;
    //endregion

    //region CONSTRUCTORS

    /**
     * Constructor - default empty.
     */
    public JDie() {
        this(DieColor.WHITE);
    }

    /**
     * Constructor - just the color of the die.
     */
    public JDie(DieColor color) {
        this(color, 250, 4000);
    }

    /**
     * Constructor - full optional.
     *
     * @param frameDuration Duration in milliseconds of Timer.
     * @param totalDuration Total amount of milliseconds to roll.
     */
    public JDie(final DieColor color, final int frameDuration, final int totalDuration) {
        this.setPreferredSize(new Dimension(SIZE, SIZE));
        this.rolling = false;
        this.value = 1;
        this.color = color;
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
        for (DieColor color : DieColor.values()) {
            images.put(color, new ImageIcon[FACES]);
            for (int i = 0; i < FACES; i++) {
                images.get(color)[i] = new ImageIcon(
                    PATH + color.toString() + "/" + (i + 1) + EXT);
            }
        }
    }

    /**
     * getter of int value of face represented.
     *
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
     *
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

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // for some reason the paintComponent is called even in dice that are not rolling
        // to avoid this i check if it is rolling, only in that case I show a random number.
        int imageIndex = this.rolling ? Die.casualRoll() - 1 : this.value - 1;
        imageIndex = Math.max(imageIndex, 0);
        graphics.drawImage(images.get(color)[imageIndex].getImage(), 0, 0, getWidth(), getHeight(), this);
    }
    //endregion
}
