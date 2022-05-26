package gui;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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

    public static Font addLetterSpacing(Font font, float spacing) {
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
        attributes.put(TextAttribute.TRACKING, spacing);
        return font.deriveFont(attributes);
    }

}
