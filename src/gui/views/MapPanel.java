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
    private final ArrayList<EventCallback> callbacks;
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
        this.callbacks = new ArrayList<>();
        this.game = game;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getButton() != MouseEvent.BUTTON1 || !isEnabled()) {
                    return;
                }

                int pointX = MapUtils.transformX(mouseEvent.getX(), getWidth());
                int pointY = MapUtils.transformY(mouseEvent.getY(), getHeight());
                System.out.println("Clicked on " + pointX + "," + pointY);
                int territoryIndex = getClickedTerritory(pointX, pointY);
                System.out.println("Territory index: " + game.getBoard().getTerritories().get(territoryIndex).getName());
                triggerCallbacks(territoryIndex);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                System.out.println("Mouse moved");
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent mouseEvent) {
                super.mouseMoved(mouseEvent);
                int pointX = MapUtils.transformX(mouseEvent.getX(), getWidth());
                int pointY = MapUtils.transformY(mouseEvent.getY(), getHeight());
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
                return game.getBoard().getTerritoryIdx(name);
            }
        }
        return -1;
    }

    /**
     * Function - add a callback to the list of callbacks.
     * @param callback Callback to add.
     */
    public void addCallback(EventCallback callback) {
        this.callbacks.add(callback);
    }

    /**
     * Function - trigger all the callbacks.
     * @param val Value to pass to the callbacks.
     */
    public void triggerCallbacks(int val) {
        for (EventCallback callback : callbacks) {
            callback.onEvent(val);
        }
    }
    // endregion
}
