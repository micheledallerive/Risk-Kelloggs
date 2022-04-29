package model;

import model.enums.ArmyColor;
import model.enums.ArmyType;

/**
 * Describes a single piece of army, using its type (i.e. the shape) and the color.
 * @author dallem@usi.ch
 */
public class Army {

    private ArmyType type;
    private ArmyColor color;
    private Territory territory;

    /**
     * Creates a new default army piece (black infantry piece).
     */
    public Army() {
        this(ArmyType.INFANTRY, ArmyColor.BLACK, null);
    }

    /**
     * Creates a new army piece.
     * @param type the type of the army piece
     * @param color the color of the army piece
     * @param territory the territory of the army
     */
    public Army(ArmyType type, ArmyColor color, Territory territory) {
        this.type = type;
        this.color = color;
        this.territory = territory;
    }

    /**
     * Returns the type of the army.
     * @return the type of the army.
     */
    public ArmyType getType() {
        return type;
    }

    /**
     * Returns the color of the army.
     * @return the color of the army.
     */
    public ArmyColor getColor() {
        return color;
    }

    /**
     * Returns the territory the army is placed on.
     * @return the territory of the army
     */
    public Territory getTerritory() {
        return this.territory;
    }

    /**
     * Moves the army to a new territory.
     * @param newTerritory the new territory of the army
     */
    public void moveTo(Territory newTerritory) {
        this.territory = newTerritory;
    }
}
