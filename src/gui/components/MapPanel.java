package gui.components;

import gui.Map;
import model.Game;
import model.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends ImageBackgroundPanel {

    private Game game;

    public MapPanel(Game game) {
        super("src/gui/images/map.jpg", 1f);
        this.game = game;
        Map.init();
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
                    System.out.println(territory.getName());
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