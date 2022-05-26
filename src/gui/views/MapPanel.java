package gui.views;

import gui.Map;
import gui.components.ImageBackgroundPanel;
import model.Game;
import model.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends ImageBackgroundPanel {

    private Game game;

    public MapPanel(Game game) {
        super("src/gui/assets/images/map.jpg", 1f);
        this.game = game;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = e.getX();
                    int y = e.getY();
                    x = x * Map.WIDTH / getWidth();
                    y = y * Map.HEIGHT / getHeight();
                    Territory territory = getClickedTerritory(x, y);
                    if (territory!=null)
                        System.out.println(territory.getName());
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int x = e.getX();
                int y = e.getY();
                x = x * Map.WIDTH / getWidth();
                y = y * Map.HEIGHT / getHeight();
                if (getClickedTerritory(x, y) != null) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public Territory getClickedTerritory(int x, int y) {
        for(String name : Map.POLYGONS.keySet()) {
            if (Map.POLYGONS.get(name).contains(x,y)) {
                return game.getBoard().getTerritories().get(Territory.TerritoryName.valueOf(name).ordinal());
            }
        }
        return null;
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

    }
}

/*

 */