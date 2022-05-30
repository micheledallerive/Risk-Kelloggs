package gui.components;

import javax.swing.JPanel;

/**
 * Transparent panel to paint all swing objects.
 *
 * @author dallem@usi.ch
 */
public class TransparentPanel extends JPanel {
    /**
     * Constructor - spread the graphic update to all its components.
     */
    public TransparentPanel() {
        super();
        this.setOpaque(false);
    }
}