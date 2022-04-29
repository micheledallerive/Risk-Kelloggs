package model;

import model.enums.ArmyColor;

/**
 * Class AI to play versus the computer.
 * @author dallem@usi.ch
 */
public class AI extends Player {

    /**
     * Constructor.
     */
    public AI() {
        this(ArmyColor.BLACK);
    }

    /**
     * Contructor with army color choice option.
     * @param color color of the army
     */
    public AI(ArmyColor color) {
        super(color);
        super.ai = true;
    }

    /**
     * Procedure - state what's the next best move for the AI.
     */
    public void calculateNextMove() {
        assert true;
        // TODO AI method
    }
}
