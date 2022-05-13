package model;

import model.enums.ArmyColor;
import model.enums.ArmyType;

import java.util.ArrayList;

/**
 * Describes a single piece of army, using its type (i.e. the shape) and the color.
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class Army {
    //region FIELDS
    private final ArmyType type;
    private final ArmyColor color;
    private Territory territory;
    //endregion

    //region CONSTRUCTORS
    /**
     * Creates a new default army piece (black infantry piece).
     */
    public Army() {
        this(ArmyType.INFANTRY, ArmyColor.BLACK, null);
    }

    /**
     * Creates a new army piece.
     * @param type The type of the army piece
     * @param color The color of the army piece
     * @param territory The territory of the army
     */
    public Army(final ArmyType type, final ArmyColor color, final Territory territory) {
        this.type = type;
        this.color = color;
        this.territory = territory;
    }
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Returns the type of the army.
     * @return The type of the army.
     */
    public ArmyType getType() {
        return type;
    }

    /**
     * Returns the value of the army in "Infantry units".
     * Type Infantry -> Value 1
     * Type Cavalry -> Value 5
     * Type Artillery -> Value 10
     * @return the value of the type of army
     */
    public int getValue() {
        int factor = ArmyType.values().length - 1;
        int value = 1;
        int ordinal = type.ordinal();
        while (ordinal > 0) {
            value = value << factor;
            value += factor - 1;
            ordinal--;
            factor--;
        }
        return value;
    }

    /**
     * Returns the cumulative value of an array of armies.
     * @param armies the array of armies
     * @return the cumulative value
     */
    public static int getValue(ArrayList<Army> armies) {
        int totalValue = 0;
        for (Army army : armies) {
            totalValue += army.getValue();
        }
        return totalValue;
    }

    /**
     * Returns the color of the army.
     * @return The color of the army.
     */
    public ArmyColor getColor() {
        return color;
    }

    /**
     * Returns the territory the army is placed on.
     * @return The territory of the army
     */
    public Territory getTerritory() {
        return this.territory;
    }

    /**
     * Sets the territory of the army.
     * @param territory The territory of the army
     */
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }
    //endregion
}
