package gui.views;

import gui.Map;
import gui.components.ImageBackgroundPanel;
import model.Game;
import model.Territory;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class to display the map of risk game.
 * 
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class MapPanel extends ImageBackgroundPanel {
    // region CONSTANTS
    private static final String MAP_PATH = "src/gui/assets/images/map.jpg";
    private static final float BRIGHTNESS_DEFAULT = 1f;
    // endregion

    // region FIELDS
    private final Game game;
    // endregion

    // region CONSTRUCTORS
    /**
     * Constructor.
     * 
     * @param game Game object.
     */
    public MapPanel(final Game game) {
        super(MAP_PATH, BRIGHTNESS_DEFAULT);
        this.game = game;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                int pointX = mouseEvent.getX();
                int pointY = mouseEvent.getY();
                pointX = pointX * Map.WIDTH / getWidth();
                pointY = pointY * Map.HEIGHT / getHeight();
                Territory territory = getClickedTerritory(pointX, pointY);
                if (territory != null) {
                    System.out.println(territory.getName());
                }
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent mouseEvent) {
                super.mouseMoved(mouseEvent);
                int pointX = mouseEvent.getX();
                int pointY = mouseEvent.getY();
                pointX = pointX * Map.WIDTH / getWidth();
                pointY = pointY * Map.HEIGHT / getHeight();
                if (getClickedTerritory(pointX, pointY) != null) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }
    // endregion

    // region METHODS
    /**
     * Function - give the territory clicked on the map.
     * 
     * @param pointX Abscissa of the clicked point.
     * @param pointY Ordinate of the clicked point.
     * @return Territory object.
     */
    public Territory getClickedTerritory(final int pointX, final int pointY) {
        for (final String name : Map.POLYGONS.keySet()) {
            if (Map.POLYGONS.get(name).contains(pointX, pointY)) {
                return this.game.getBoard().getTerritories().get(Territory.TerritoryName.valueOf(name).ordinal());
            }
        }
        return null;
    }

    @Override public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

    }
    // endregion
}
