package gui.components;

import gui.EventCallback;
import model.Die;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDie extends JComponent {
    private static final ImageIcon[] images = new ImageIcon[6];

    public static void init() {
        for (int i = 0; i < 6; i++) {
            images[i] = new ImageIcon("src/gui/assets/images/die/white/" + (i+1) + ".png");
        }
    }

    private Timer timer;
    private int value;
    private EventCallback callback;
    private int animation = 0;

    public JDie() {
        this(250, 5000);
    }

    public JDie(int frameDuration, int totalDuration) {
        super();
        setPreferredSize(new Dimension(64,64));
        this.timer = new Timer(frameDuration, e -> {
            if(animation == totalDuration/frameDuration) {
                animation = value-1;
                stop();
            } else {
                animation++;
            }
            this.repaint();
        });
    }

    public int getValue() {
        return this.value;
    }

    public void roll() {
        this.value = Die.casualRoll();
        this.timer.start();
    }

    public void addCallback(EventCallback callback) {
        this.callback = callback;
    }

    public void stop() {
        this.timer.stop();
        if(callback != null) this.callback.onEvent(0);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(images[Die.casualRoll() - 1].getImage(), 0, 0, this);
    }

}
