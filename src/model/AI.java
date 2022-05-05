package model;

import model.enums.ArmyColor;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void placeArmy(Game game) {
        ArrayList<Territory> freeTerritories = new ArrayList<>();
        for (Territory territory : game.getBoard().getTerritories()) {
            if (territory.getOwner() == null) {
                freeTerritories.add(territory);
            }
        }
        Territory chosen = freeTerritories.get(StaticRandom.random.nextInt(freeTerritories.size()));
        int amount = StaticRandom.random.nextInt(Math.min(10, this.getFreeArmies().size()-1))+1;
        this.placeArmies(chosen, amount);
    }
}
