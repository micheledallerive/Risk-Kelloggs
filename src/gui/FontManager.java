package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FontManager {
    //region CONSTANTS
    private static final String PATH = "src/gui/assets/fonts/CenturyGothic.ttf";
    private static final String DEFAULT = "Arial";
    private static final int SIZE = 14;
    //endregion

    //region FIELDS
    private static Font font;
    //endregion

    //region METHODS
    public static void init() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(PATH)).deriveFont(Font.PLAIN, SIZE);
        } catch (final IllegalArgumentException | NullPointerException | IOException
                       | FontFormatException | SecurityException ex) {
            System.out.println("Error loading font - default applied (Arial, 14pt).");
            ex.printStackTrace();
            font = new Font(DEFAULT, Font.PLAIN, SIZE);
        }
    }

    public static Font getFont() {
        return font;
    }

    public static Font addLetterSpacing(final Font font, final float spacing) {
        final Map attributes = font.getAttributes();

        try {
            attributes.put(TextAttribute.TRACKING, spacing);
        } catch (final UnsupportedOperationException | ClassCastException
                       | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        return font.deriveFont(attributes);
    }
    //endregion
}
