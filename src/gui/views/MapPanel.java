package gui.views;

import gui.EventCallback;
import gui.MapUtils;
import gui.components.ImageBackgroundPanel;
import model.Game;
import model.Territory;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private final ArrayList<EventCallback> callbacks;
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
        this.callbacks = new ArrayList<>();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                int pointX = mouseEvent.getX();
                int pointY = mouseEvent.getY();
                pointX = pointX * MapUtils.WIDTH / getWidth();
                pointY = pointY * MapUtils.HEIGHT / getHeight();
                int territoryIndex = getClickedTerritory(pointX, pointY);
                System.out.println("Clicked territory: " + game.getBoard().getTerritories().get(territoryIndex).getName());
                triggerCallbacks(territoryIndex);
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent mouseEvent) {
                super.mouseMoved(mouseEvent);
                int pointX = mouseEvent.getX();
                int pointY = mouseEvent.getY();
                pointX = pointX * MapUtils.WIDTH / getWidth();
                pointY = pointY * MapUtils.HEIGHT / getHeight();
                if (getClickedTerritory(pointX, pointY) != -1) {
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
     * @return the index of the territory.
     */
    public int getClickedTerritory(final int pointX, final int pointY) {
        for (final String name : MapUtils.POLYGONS.keySet()) {
            if (MapUtils.POLYGONS.get(name).contains(pointX, pointY)) {
                return Territory.TerritoryName.valueOf(name).ordinal();
            }
        }
        return -1;
    }

    public void addCallback(EventCallback callback) {
        this.callbacks.add(callback);
    }

    public void triggerCallbacks(int val) {
        for (EventCallback callback : callbacks) {
            callback.onEvent(val);
        }
    }
    // endregion
}
