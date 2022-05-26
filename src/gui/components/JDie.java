package gui.components;

import model.Die;

import javax.swing.*;
import java.awt.*;

public class JDie extends JComponent {
    private static final ImageIcon[] images = new ImageIcon[6];
    private int animation = 0;

    public static void init() {
        for (int i = 0; i < 6; i++) {
            images[i] = new ImageIcon("src/gui/assets/images/die/white/" + (i+1) + ".png");
        }
    }

    private Timer timer;
    private int value;

    public JDie() {
        this(250, 5000);
    }

    public JDie(int frameDuration, int totalDuration) {
        setPreferredSize(new Dimension(64,64));
        this.timer = new Timer(frameDuration, e -> {
            this.value = (int) (Math.random() * 6);
            if(animation == totalDuration/frameDuration) {
                animation = value-1;
                stop();
            } else {
                animation++;
            }
            this.repaint();
        });
    }

    public void start() {
        this.timer.start();
        Die.casualRoll();
    }

    public void stop() {
        this.timer.stop();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(images[Die.casualRoll() - 1].getImage(), 0, 0, this);
    }

}
