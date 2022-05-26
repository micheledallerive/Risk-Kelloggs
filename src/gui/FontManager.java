package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    public static Font font;

    public static void init() {
        try {
            File file = new File("src/gui/assets/fonts/CenturyGothic.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.PLAIN, 14f);
        } catch (FontFormatException | IOException ex) {
            System.out.println("Error loading font");
            font = new Font("Arial", Font.PLAIN, 14);
        }
    }

    public static Font getFont() {
        return font;
    }
    public static Font getBoldFont() {
        return font.deriveFont(Font.BOLD);
    }
}
