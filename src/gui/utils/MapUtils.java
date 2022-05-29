package gui.utils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
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
    //endregion
}
