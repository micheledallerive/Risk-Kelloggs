package model.enums;

/**
 * Describes the type of the army piece.
 * @author dallem@usi.ch
 * {@link #INFANTRY}
 * {@link #CAVALRY}
 * {@link #ARTILLERY}
 */
public enum ArmyType {
    /**
     * Infantry army.
     */
    INFANTRY,
    /**
     * Cavalry army (1 cavalry = 5 infantry).
     */
    CAVALRY,
    /**
     * Artillery army (1 artillery = 2 cavalry = 10 infantry).
     */
    ARTILLERY
}
