package gui;

import javax.swing.*;
import java.awt.Polygon;
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

    public static int transformX(int x, int width) {
        return (int) ((x * (MapUtils.WIDTH * 1.125) / width) );
    }

    public static int transformY(int y, int height) {
        return (y * MapUtils.HEIGHT / height);
    }
    //endregion
}
