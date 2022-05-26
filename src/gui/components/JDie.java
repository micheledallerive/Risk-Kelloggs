package gui.components;

import model.Die;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Graphics;

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
    //endregion

    //region CONSTRUCTORS
    public JDie() {
        this(250, 5000);
    }

    public JDie(final int frameDuration, final int totalDuration) {
        this.setSize(SIZE, SIZE);
        this.timer = new Timer(frameDuration, e -> {
            this.value = (int) (Math.random() * FACES);
            if (this.animation == totalDuration/frameDuration) {
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
    public static final void init() {
        for (int i = 0; i < FACES; i++) {
            images[i] = new ImageIcon(PATH + (i + 1) + EXT);
        }
    }

    public void start() {
        this.timer.start();
        Die.casualRoll();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(images[Die.casualRoll() - 1].getImage(), 0, 0, this);
    }
    //endregion
}
