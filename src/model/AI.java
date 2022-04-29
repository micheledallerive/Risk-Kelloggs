package model;

import model.enums.ArmyColor;

public class AI extends Player{

    public AI() {
        this(ArmyColor.BLACK);
    }

    public AI(ArmyColor color) {
        super(color);
        super.ai = true;
    }

    public void calculateNextMove() {
        assert true;
        // TODO AI method
    }
}
