package gui.utils;

import gui.EventCallback;
import gui.components.QuantityDialog;
import gui.views.MapPanel;
import model.Game;
import model.Player;
import model.Territory;
import model.enums.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.PatternSyntaxException;

/**
 * Class map handling initialization of methods to have a direct representation and
 * correspondence between map image and clickable map territory integrated with the game model.
 * @author dallem@usi.ch
 */
public class MapUtils {
    //region CONSTANTS
    private static final String FILE_BOUNDS = "src/gui/bounds.dat";
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1280;
    //endregion

    //region FIELDS
    public static final HashMap<String, Polygon> POLYGONS = new HashMap<>();
    //endregion

    private MapUtils() {

    }

    //region METHODS
    /**
     * Procedure - get the polygons of the corresponding image map,
     *  into a Hashmap for easy check and usage.
     */
    public static void init() {
        if (!POLYGONS.isEmpty()) {
            return;
        }

        try {

            final Scanner scanner = new Scanner(new File(FILE_BOUNDS));
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final String[] split = line.split(":");
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

    public static int viewToMapX(int x, int width) {
        return (int) ((x * (MapUtils.WIDTH * 1.125) / width));
    }

    public static int viewToMapY(int y, int height) {
        return (y * MapUtils.HEIGHT / height);
    }

    public static Point mapToView(Point point, int width, int height) {
        return new Point(mapToViewX(point.x, width), mapToViewY(point.y, height));
    }

    public static int mapToViewX(int x, int width) {
        return (int) ((x * (width * 0.8888) / MapUtils.WIDTH));
    }

    public static int mapToViewY(int y, int height) {
        return (y * height / MapUtils.HEIGHT);
    }

    /**
     * Calculates the signed area of the polygon
     *
     * @param polygon the polygon
     */
    public static double signedArea(Polygon polygon) {
        int[] x = polygon.xpoints;
        int[] y = polygon.ypoints;
        int n = polygon.npoints;
        double area = 0;
        for (int i = 0; i < n; i++) {
            area += (x[i] * y[(i + 1) % n] - x[(i + 1) % n] * y[i]);
        }
        return area / 2;
    }

    /**
     * Calculates the centroid of the given polygon.
     *
     * @param polygon the polygon to calculate the centroid of.
     */
    public static Point getCentroid(final Polygon polygon) {
        // calculate the area of the polygon
        final double area = signedArea(polygon);
        // calculate the centroid
        int[] x = polygon.xpoints;
        int[] y = polygon.ypoints;
        int n = polygon.npoints;
        double cx = 0;
        double cy = 0;
        for (int i = 0; i < n; i++) {
            final double tmp = x[i] * y[(i + 1) % n] - x[(i + 1) % n] * y[i];
            cx += (x[i] + x[(i + 1) % n]) * tmp;
            cy += (y[i] + y[(i + 1) % n]) * tmp;
        }
        cx /= (6 * area);
        cy /= (6 * area);
        return new Point((int) cx, (int) cy);
    }

    public static EventCallback playingCallback(Game game,
                                                MapPanel map,
                                                Function<Void, Void> nextTurn,
                                                JFrame parent) {
        return (id, args) -> {

            int clickX = (int) args[0];
            int clickY = (int) args[1];
            Territory territory = game.getBoard().getTerritories().get(id);
            Player player = game.getPlayers().get(game.getTurn());

            if (game.getStatus() == GameStatus.SETUP) {


                if (id == -1
                        || game.getPlayers().get(game.getTurn()).isAI()
                        || player.getFreeArmies().isEmpty()) {
                    nextTurn.apply(null);
                    return;
                }

                boolean everythingOccupied = game.getBoard().getTerritories().stream().noneMatch(t -> t.getOwner() == null);
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
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            int quantity = quantityDialog.getSelectedQuantity();
                            if (quantity > 0) {
                                player.placeArmies(territory, quantity);
                                nextTurn.apply(null);
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
            } else {
                System.out.println("Playing");
                if (map.getAttackingFrom() == null) {
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
                    System.out.println("Set where to attack from");
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
            }
        };
    }
    //endregion
}
