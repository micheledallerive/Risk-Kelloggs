package gui.views;

import gui.EventCallback;
import gui.utils.FontUtils;
import gui.utils.ImageUtils;
import gui.utils.MapUtils;
import gui.components.ImageBackgroundPanel;
import model.Game;
import model.Territory;

import java.awt.*;
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
        this.game = game;
        this.callbacks = new ArrayList<>();
        this.game = game;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getButton() != MouseEvent.BUTTON1 || !isEnabled()) {
                    return;
                }

                int pointX = MapUtils.viewToMapX(mouseEvent.getX(), getWidth());
                int pointY = MapUtils.viewToMapY(mouseEvent.getY(), getHeight());
                int territoryIndex = getClickedTerritory(pointX, pointY);
                triggerCallbacks(territoryIndex, mouseEvent.getX(), mouseEvent.getY());
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent mouseEvent) {
                super.mouseMoved(mouseEvent);
                int pointX = MapUtils.viewToMapX(mouseEvent.getX(), getWidth());
                int pointY = MapUtils.viewToMapY(mouseEvent.getY(), getHeight());
                if (getClickedTerritory(pointX, pointY) != -1) {
                    if (getCursor().getType() != Cursor.HAND_CURSOR) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                } else {
                    if (getCursor().getType() != Cursor.DEFAULT_CURSOR) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
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
     *
     * @param val Value to pass to the callbacks.
     */
    public void triggerCallbacks(int val, Object... args) {
        for (EventCallback callback : callbacks) {
            callback.onEvent(val, args);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setBrightness(enabled ? 1f : .5f);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final int RADIUS = 30;
        for (Territory territory : game.getBoard().getTerritories()) {
            if (territory.getOwner() == null || territory.getArmiesCount() == 0) {
                continue;
            }
            Polygon polygon = MapUtils.POLYGONS.get(territory.getName().toString());
            Point centroid = MapUtils.getCentroid(polygon);
            centroid.x = MapUtils.mapToViewX(centroid.x, getWidth());
            centroid.y = MapUtils.mapToViewY(centroid.y, getHeight());
            graphics.setColor(ImageUtils.armyColorToColor(territory.getOwner().getColor()));
            graphics.fillOval(centroid.x - RADIUS, centroid.y - RADIUS, RADIUS * 2, RADIUS * 2);

            // write the number of armies inside the oval
            graphics.setColor(ImageUtils.chooseForegroundColor(territory.getOwner().getColor()));
            graphics.setFont(FontUtils.getFont().deriveFont(Font.BOLD, 20));

            String number = String.valueOf(territory.getArmiesCount());
            FontMetrics fm = graphics.getFontMetrics();
            int x = (int) (centroid.x - (fm.stringWidth(number) * .5) + 1);
            int y = (fm.getAscent() + (centroid.y - (fm.getAscent() + fm.getDescent()) / 2));
            graphics.drawString(number, x, y);
        }
    }

    // endregion
}
