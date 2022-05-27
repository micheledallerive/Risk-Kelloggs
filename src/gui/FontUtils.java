package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Class specialized to provide the game font.
 * 
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class FontUtils {
    // region CONSTANTS
    private static final String PATH = "src/gui/assets/fonts/CenturyGothic.ttf";
    private static final String DEFAULT = "Arial";
    private static final int SIZE = 14;
    // endregion

    // region FIELDS
    private static Font font;
    // endregion

    private FontUtils() {
        
    }

    // region METHODS
    /**
     * Procedure - Initialization of Font.
     */
    public static void init() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(PATH)).deriveFont(Font.PLAIN, SIZE);
        } catch (final IllegalArgumentException | IOException
                | FontFormatException | SecurityException ex) {
            System.out.println("Error loading font - default applied (Arial, 14pt).");
            ex.printStackTrace();
            font = new Font(DEFAULT, Font.PLAIN, SIZE);
        }
    }

    /**
     * getter of font field.
     * 
     * @return font field object.
     */
    public static Font getFont() {
        return font;
    }

    /**
     * Function - add the spacing in the font.
     * 
     * @param font    object Font to add spacing to.
     * @param spacing float number specifying the spacing.
     * @return Font object with spacing.
     */
    public static Font addLetterSpacing(final Font font, final float spacing) {
        final Map attributes = font.getAttributes();

        try {
            attributes.put(TextAttribute.TRACKING, spacing);
        } catch (final UnsupportedOperationException | ClassCastException | IllegalArgumentException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        return font.deriveFont(attributes);
    }
    // endregion
}
