package gui;

import model.Territory;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map {
    public static final HashMap<String, Polygon> POLYGONS = new HashMap<>();
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1280;

    public static void init() {
        if(POLYGONS.isEmpty()) {
            try {
                final File file = new File("src/gui/bounds.dat");
                final Scanner scanner = new Scanner(file);
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
            } catch (final FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }
}
