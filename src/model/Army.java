package model;

import model.enums.ArmyColor;
import model.enums.ArmyType;

/**
 * Describes a single piece of army, using it's type (i.e. the shape) and the color.
 */
public class Army {

    private ArmyType type;
    private ArmyColor color;

    /**
     * Creates a new default army piece (black infantry piece).
     */
    public Army() {
        type = ArmyType.INFANTRY;
        color = ArmyColor.BLACK;
    }

    /**
     * Creates a new army piece.
     * @param type the type of the army piece
     * @param color the color of the army piece
     */
    public Army(ArmyType type, ArmyColor color) {
        this.type = type;
        this.color = color;
    }

}
