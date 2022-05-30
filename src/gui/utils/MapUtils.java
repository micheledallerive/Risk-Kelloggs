package gui.utils;

import gui.EventCallback;
import gui.components.QuantityDialog;
import gui.views.MapPanel;
import model.Game;
import model.Player;
import model.Territory;
import model.enums.GameStatus;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Class map handling initialization of methods to have a direct representation and
 * correspondence between map image and clickable map territory integrated with the game model.
 *
 * @author dallem @usi.ch
 */
public class MapUtils {
    //region CONSTANTS
    private static final String FILE_BOUNDS = "src/gui/bounds.dat";
    /**
     * The constant WIDTH.
     */
    public static final int WIDTH = 1920;
    /**
     * The constant HEIGHT.
     */
    public static final int HEIGHT = 1280;
    //endregion

    //region FIELDS
    /**
     * The constant POLYGONS.
     */
    public static final HashMap<String, Polygon> POLYGONS = new HashMap<>();
    //endregion

    //region CONSTRUCTORS

    /**
     * Singleton.
     */
    private MapUtils() {
        // singleton
    }

    //region METHODS

    /**
     * Procedure - get the polygons of the corresponding image map,
     * into a Hashmap for easy check and usage.
     */
    public static void init() {
        if (!POLYGONS.isEmpty()) {
            return;
        }

        try (final Scanner scanner = new Scanner(new File(FILE_BOUNDS))) {

            while (scanner.hasNextLine()) {
                final String[] split = scanner.nextLine().split(":");
                final String name = split[0];
                final String[] points = split[1].split(";");
                final int[] xValues = new int[points.length];
                final int[] yValues = new int[points.length];
                for (int i = 0; i < points.length; i++) {
                    final String[] point = points[i].split(",");
                    xValues[i] = Integer.parseInt(point[0]);
                    yValues[i] = Integer.parseInt(point[1]);
                }
                final Polygon polygon = new Polygon(xValues, yValues, points.length);
                POLYGONS.put(name, polygon);
            }

        } catch (final FileNotFoundException | IllegalStateException | PatternSyntaxException | NumberFormatException
                       | NegativeArraySizeException | IndexOutOfBoundsException exception) {
            exception.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * View to map pointX int.
     *
     * @param pointX the pointX
     * @param width  the width
     * @return the int
     */
    public static int viewToMapX(int pointX, int width) {
        return (int) ((pointX * (MapUtils.WIDTH * 1.125) / width));
    }

    /**
     * View to map y int.
     *
     * @param pointY the y
     * @param height the height
     * @return the int
     */
    public static int viewToMapY(int pointY, int height) {
        return (pointY * MapUtils.HEIGHT / height);
    }

    /**
     * Map to view point.
     *
     * @param point  the point
     * @param width  the width
     * @param height the height
     * @return the point
     */
    public static Point mapToView(Point point, int width, int height) {
        return new Point(mapToViewX(point.x, width), mapToViewY(point.y, height));
    }

    /**
     * Map to view x int.
     *
     * @param pointX the x
     * @param width  the width
     * @return the int
     */
    public static int mapToViewX(int pointX, int width) {
        return (int) ((pointX * (width * 0.8888) / MapUtils.WIDTH));
    }

    /**
     * Map to view y int.
     *
     * @param pointY the y
     * @param height the height
     * @return the view y
     */
    public static int mapToViewY(int pointY, int height) {
        return (pointY * height / MapUtils.HEIGHT);
    }

    /**
     * Calculates the signed area of the polygon.
     *
     * @param polygon the polygon
     * @return the area
     */
    public static double signedArea(Polygon polygon) {
        int[] pointX = polygon.xpoints;
        int[] pointY = polygon.ypoints;
        int points = polygon.npoints;
        double area = 0;
        for (int i = 0; i < points; i++) {
            area += (pointX[i] * pointY[(i + 1) % points] - pointX[(i + 1) % points] * pointY[i]);
        }
        return area / 2;
    }

    /**
     * Calculates the centroid of the given polygon.
     *
     * @param polygon the polygon to calculate the centroid of.
     * @return the centroid
     */
    public static Point getCentroid(final Polygon polygon) {
        // calculate the area of the polygon
        final double area = signedArea(polygon);
        // calculate the centroid
        int[] pointsX = polygon.xpoints;
        int[] pointsY = polygon.ypoints;
        int points = polygon.npoints;
        double cx = 0;
        double cy = 0;
        for (int i = 0; i < points; i++) {
            final double tmp = pointsX[i] * pointsY[(i + 1) % points] - pointsX[(i + 1) % points] * pointsY[i];
            cx += (pointsX[i] + pointsX[(i + 1) % points]) * tmp;
            cy += (pointsY[i] + pointsY[(i + 1) % points]) * tmp;
        }
        cx /= (6 * area);
        cy /= (6 * area);
        return new Point((int) cx, (int) cy);
    }

    /**
     * Playing callback event callback.
     *
     * @param game     the game
     * @param map      the map
     * @param nextTurn the next turn
     * @param parent   the parent
     * @return the event callback
     */
    public static EventCallback setupCallback(Game game,
                                              MapPanel map,
                                              Function<Void, Void> nextTurn,
                                              JFrame parent) {
        return (id, args) -> {

            int clickX = (int) args[0];
            int clickY = (int) args[1];
            if (id == -1) {
                nextTurn.apply(null);
                return;
            }
            Territory territory = game.getBoard().getTerritories().get(id);
            Player player = game.getPlayers().get(game.getTurn());

            if (!player.getFreeArmies().isEmpty()) {

                if (game.getPlayers().get(game.getTurn()).isAI()
                    || player.getFreeArmies().isEmpty()) {
                    nextTurn.apply(null);
                    return;
                }

                boolean everythingOccupied = game.getBoard().getTerritories().stream()
                    .noneMatch(t -> t.getOwner() == null);
                if (territory.getOwner() != null) {
                    if (territory.getOwner() == player && !everythingOccupied) {
                        PopupUtils.showPopup(parent, "You have to place armies on free territories!", clickX, clickY);
                        return;
                    }
                    if (territory.getOwner() != player) {
                        PopupUtils.showPopup(parent, "You can't place armies on enemy territories!", clickX, clickY);
                        return;
                    }
                }
                if (everythingOccupied) {
                    QuantityDialog quantityDialog = new QuantityDialog(
                        parent, "How many armies do you want to place?",
                        1, player.getFreeArmies().size());
                    quantityDialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent windowEvent) {
                            super.windowClosed(windowEvent);
                            int quantity = quantityDialog.getSelectedQuantity();
                            if (quantity > 0) {
                                player.placeArmies(territory, quantity);
                                if (game.getStatus() != GameStatus.PLAYING) {
                                    nextTurn.apply(null);
                                }
                            }
                        }
                    });
                    quantityDialog.pack();
                    quantityDialog.setLocationRelativeTo(null);
                    quantityDialog.setVisible(true);
                } else {
                    player.placeArmies(territory, 1);
                    nextTurn.apply(null);
                }
            }
        };
    }

    /**
     * Playing callback event callback.
     *
     * @param game     the game
     * @param map      the map
     * @param nextTurn the next turn
     * @param parent   the parent
     * @return the event callback
     */
    public static EventCallback playingCallback(Game game,
                                                MapPanel map,
                                                Function<Void, Void> nextTurn,
                                                JFrame parent) {
        return (id, args) -> {

            int clickX = (int) args[0];
            int clickY = (int) args[1];
            if (id == -1) {
                nextTurn.apply(null);
                return;
            }
            Territory territory = game.getBoard().getTerritories().get(id);
            Player player = game.getPlayers().get(game.getTurn());
            if (map.getAttackingFrom() == null || map.getAttackingTo() != null) {
                // i have to choose where to attack from
                if (territory.getOwner() != player) {
                    PopupUtils.showPopup(parent, "You can't attack from enemy territories!", clickX, clickY);
                    return;
                }
                if (game.getBoard().getAdjacent(territory).stream().allMatch(t -> t.getOwner() == player)) {
                    PopupUtils.showPopup(parent, "You can't attack any territory from here!", clickX, clickY);
                    return;
                }
                if (territory.getArmies().size() < 2) {
                    PopupUtils.showPopup(parent, "You don't have enough armies!", clickX, clickY);
                    return;
                }
                map.setAttackingFrom(territory);
            } else {
                // i have to choose where to attack to


                if (territory.getOwner() == player) {
                    PopupUtils.showPopup(parent, "You can't attack your own territories!", clickX, clickY);
                    return;
                }
                if (game.getBoard().getAdjacent(territory).stream().noneMatch(t -> map.getAttackingFrom() == t)) {
                    PopupUtils.showPopup(parent, "You must attack an adjacent territory", clickX, clickY);
                    return;
                }
                System.out.println("Set where to attack to");
                map.setAttackingTo(territory);
                QuantityDialog quantityDialog = new QuantityDialog(
                    parent, "How many armies do you want to attack with?",
                    1, Math.min(map.getAttackingFrom().getArmies().size() - 1, 3));
                quantityDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent event) {
                        if (quantityDialog.getSelectedQuantity() > 0) {
                            int quantity = quantityDialog.getSelectedQuantity();
                            int[] result = player.getAttackOutcome(
                                map.getAttackingFrom(),
                                map.getAttackingTo(),
                                quantity);
                            map.setAttackResult(result);
                            Timer timer = new Timer(1000, e -> {
                                map.clearAttacking();
                                map.repaint();
                            });
                            timer.setRepeats(false);
                            timer.start();
                            map.repaint();
                        }
                    }
                });
                quantityDialog.pack();
                quantityDialog.setLocationRelativeTo(null);
                quantityDialog.setVisible(true);
            }
        };
    }

    /**
     * Move callback event callback.
     *
     * @param game     the game
     * @param map      the map
     * @param nextTurn the next turn
     * @param parent   the parent
     * @return the event callback
     */
    public static EventCallback moveCallback(Game game,
                                             MapPanel map,
                                             Function<Void, Void> nextTurn,
                                             JFrame parent) {
        return (id, args) -> {

            int clickX = (int) args[0];
            int clickY = (int) args[1];
            if (id == -1) {
                // nextTurn.apply(null);
                return;
            }
            Territory territory = game.getBoard().getTerritories().get(id);
            Player player = game.getPlayers().get(game.getTurn());

            if (territory.getOwner() != player) {
                PopupUtils.showPopup(parent, "You must choose a territory you owe.", clickX, clickY);
                return;
            }

            if (map.getAttackingFrom() == null || map.getAttackingTo() != null) {
                // i have to choose where to attack from
                if (territory.getArmies().size() < 2) {
                    PopupUtils.showPopup(parent, "You don't have enough armies!", clickX, clickY);
                    return;
                }
                map.setAttackingFrom(territory);
            } else {
                // i have to choose where to attack to
                if (game.getBoard().getAdjacent(territory).stream().noneMatch(t -> map.getAttackingFrom() == t)) {
                    PopupUtils.showPopup(parent, "You must attack an adjacent territory", clickX, clickY);
                    return;
                }
                map.setAttackingTo(territory);
                QuantityDialog quantityDialog = new QuantityDialog(
                    parent, "How many armies do you want to move?",
                    1, map.getAttackingFrom().getArmies().size() - 1);
                quantityDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent event) {
                        if (quantityDialog.getSelectedQuantity() > 0) {
                            int quantity = quantityDialog.getSelectedQuantity();
                            player.moveArmies((byte) quantity, map.getAttackingFrom(), map.getAttackingTo());
                            Timer timer = new Timer(1000, e -> {
                                map.clearAttacking();
                                map.repaint();
                            });
                            timer.setRepeats(false);
                            timer.start();
                            map.repaint();
                        }
                    }
                });
                quantityDialog.pack();
                quantityDialog.setLocationRelativeTo(null);
                quantityDialog.setVisible(true);
            }
        };
    }
    //endregion
}
