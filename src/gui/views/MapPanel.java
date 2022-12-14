package gui.views;

import gui.EventCallback;
import gui.components.ImageBackgroundPanel;
import gui.utils.FontUtils;
import gui.utils.ImageUtils;
import gui.utils.MapUtils;
import model.Game;
import model.Territory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to display the map of risk game.
 *
 * @author dallem @usi.ch, moralj@usi.ch
 */
public class MapPanel extends ImageBackgroundPanel {
    // region CONSTANTS
    private static final String MAP_PATH = "src/gui/assets/images/map.jpg";
    private static final float BRIGHTNESS_DEFAULT = 1f;
    private static final int RADIUS = 30;
    private static final int LOSS_CIRCLE_RADIUS = 20;
    // endregion

    // region FIELDS
    private final ArrayList<EventCallback> callbacks;
    private final Game game;
    private Point lastMovedPoint = null;

    private Territory attackingFrom = null;

    private Territory attackingTo = null;

    private int[] attackResult = null;
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
                if (getAttackingFrom() != null) {
                    if (getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                    lastMovedPoint = mouseEvent.getPoint();
                    repaint();
                } else if (getClickedTerritory(pointX, pointY) != -1) {
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
     *
     * @param callback Callback to add.
     */
    public void addCallback(EventCallback callback) {
        this.callbacks.add(callback);
    }

    /**
     * Function - removes a callback from the list of callbacks.
     *
     * @param callback Callback to remove.
     */
    public void removeCallback(EventCallback callback) {
        this.callbacks.remove(callback);
    }

    /**
     * Function - returns the callbacks list.
     *
     * @return the callbacks list.
     */
    public ArrayList<EventCallback> getCallbacks() {
        return this.callbacks;
    }

    /**
     * Resets the callbacks.
     */
    public void resetCallbacks() {
        this.callbacks.clear();
    }

    /**
     * Function - trigger all the callbacks.
     *
     * @param val  Value to pass to the callbacks.
     * @param args the args
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

    /**
     * Sets attacking from.
     *
     * @param territory the territory
     */
    public void setAttackingFrom(Territory territory) {
        this.attackingFrom = territory;
    }

    /**
     * Gets attacking from.
     *
     * @return the attacking from
     */
    public Territory getAttackingFrom() {
        return this.attackingFrom;
    }

    /**
     * Sets attacking to.
     *
     * @param territory the territory
     */
    public void setAttackingTo(Territory territory) {
        this.attackingTo = territory;
    }

    /**
     * Gets attacking to.
     *
     * @return the attacking to
     */
    public Territory getAttackingTo() {
        return this.attackingTo;
    }

    /**
     * Sets attack result.
     *
     * @param attackResult the attack result
     */
    public void setAttackResult(final int[] attackResult) {
        this.attackResult = attackResult.clone();
    }

    /**
     * Clear attacking.
     */
    public void clearAttacking() {
        this.attackingFrom = null;
        this.attackingTo = null;
        this.attackResult = null;
    }

    @Override
    public void paintComponent(Graphics paramGraphics) {
        super.paintComponent(paramGraphics);

        Graphics2D graphics = (Graphics2D) paramGraphics;
        for (Territory territory : game.getBoard().getTerritories()) {
            if (territory.getOwner() == null || territory.getArmiesCount() == 0) {
                continue;
            }

            Polygon polygon = MapUtils.POLYGONS.get(territory.getName());
            Point centroid = MapUtils.getCentroid(polygon);
            centroid = MapUtils.mapToView(centroid, getWidth(), getHeight());
            graphics.setColor(ImageUtils.armyColorToColor(territory.getOwner().getColor()));
            graphics.fillOval(centroid.x - RADIUS, centroid.y - RADIUS, RADIUS * 2, RADIUS * 2);

            // write the number of armies inside the oval
            graphics.setColor(ImageUtils.chooseForegroundColor(territory.getOwner().getColor()));
            graphics.setFont(FontUtils.getFont().deriveFont(Font.BOLD, 20));

            graphics.setStroke(new BasicStroke(2));
            graphics.drawOval(centroid.x - RADIUS, centroid.y - RADIUS, RADIUS * 2 - 1, RADIUS * 2 - 1);

            String number = String.valueOf(territory.getArmiesCount());
            FontMetrics fm = graphics.getFontMetrics();
            int xxx = (int) (centroid.x - (fm.stringWidth(number) * .5) + 1);
            int yyy = fm.getAscent() + (centroid.y - (fm.getAscent() + fm.getDescent()) / 2);
            graphics.drawString(number, xxx, yyy);
        }

        if (getAttackingFrom() != null && getAttackingTo() == null) {
            Point centroid = MapUtils.getCentroid(MapUtils.POLYGONS.get(getAttackingFrom().getName().toString()));
            centroid = MapUtils.mapToView(centroid, getWidth(), getHeight());
            //graphics.drawLine(centroid.x, centroid.y, mouse.x, mouse.y);
            Point mouse = lastMovedPoint;
            mouse = MapUtils.mapToView(mouse, getWidth(), getHeight());
            graphics.setColor(Color.BLACK);
            drawArrow(graphics, centroid.x, centroid.y, MapUtils.viewToMapX(mouse.x, getWidth()),
                MapUtils.viewToMapY(mouse.y, getHeight()), 20, 50);
        }

        // fire condition - attacker
        if (attackResult == null) {
            return;
        }

        Point fromCentroid = MapUtils.getCentroid(MapUtils.POLYGONS.get(getAttackingFrom().getName().toString()));
        Point toCentroid = MapUtils.getCentroid(MapUtils.POLYGONS.get(getAttackingTo().getName().toString()));
        fromCentroid = MapUtils.mapToView(fromCentroid, getWidth(), getHeight());
        toCentroid = MapUtils.mapToView(toCentroid, getWidth(), getHeight());
        // draw on top of the centroid how many armies were lost
        graphics.setColor(Color.WHITE);
        float ylabelIncrease = 1.75f;
        if (attackResult[0] > 0) {
            drawString(graphics, Color.RED, "-" + attackResult[0], fromCentroid.x,
                (int) (fromCentroid.y - ylabelIncrease * RADIUS), LOSS_CIRCLE_RADIUS);
        }
        if (attackResult[1] > 0) {
            drawString(graphics, Color.RED, "-" + attackResult[1], toCentroid.x,
                (int) (toCentroid.y - ylabelIncrease * RADIUS), LOSS_CIRCLE_RADIUS);

        }
    }

    private void drawString(Graphics2D graphics, Color color, String text, int xxx, int yyy, final int radius) {
        graphics.setColor(Color.WHITE);
        graphics.fillOval(xxx - radius, yyy - radius, radius * 2 - 1, radius * 2 - 1);
        graphics.setColor(color);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = graphics.getFontMetrics();
        int x1 = (int) (xxx - (fm.stringWidth(text) * .5) + 1);
        int y1 = fm.getAscent() + (yyy - (fm.getAscent() + fm.getDescent()) / 2);
        graphics.drawString(text, x1, y1);
    }

    private void drawArrow(Graphics2D graphics, int x0, int y0, int x1,
                           int y1, int headLength, int headAngle) {
        graphics.setStroke(new BasicStroke(10));
        double offs = headAngle * Math.PI / 180.0;
        double angle = Math.atan2(y0 - y1, x0 - x1);
        int[] xs = {x1 + (int) (headLength * Math.cos(angle + offs)), x1,
            x1 + (int) (headLength * Math.cos(angle - offs))};
        int[] ys = {y1 + (int) (headLength * Math.sin(angle + offs)), y1,
            y1 + (int) (headLength * Math.sin(angle - offs))};
        graphics.drawLine(x0, y0, x1, y1);
        graphics.drawPolyline(xs, ys, 3);
    }

    // endregion
}
