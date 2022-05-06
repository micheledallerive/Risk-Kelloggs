package model;

import model.enums.ArmyColor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class AI to play versus the computer.
 * @author dallem@usi.ch
 */
public class AI extends Player {
    //region FIELDS
    private static ArrayList<AI> listAI = new ArrayList<>();
    //endregion

    //region CONSTRUCTORS
    /**
     * Constructor.
     */
    public AI() {
        this(ArmyColor.BLACK);
    }

    /**
     * Constructor with army color choice option.
     * @param color The color of the army
     */
    public AI(final ArmyColor color) {
        super(color, "AI" + (AI.listAI.size() + 1));
    }
    //endregion

    //region METHODS
    /**
     * Procedure - state what's the next best move for the AI.
     */
    public void calculateNextMove() {
        assert true;
        // TODO AI method
    }

    /**
     * Procedure - a pseudo-random way to place an army, level: easy. TODO do random field for throwing away the random.
     * @param game The game object.
     */
    public void placeArmy(final Game game) {
        ArrayList<Territory> freeTerritories = new ArrayList<>();
        for (Territory territory : game.getBoard().getTerritories()) {
            if (territory.getOwner() == null) {
                freeTerritories.add(territory);
            }
        }
        Territory chosen = freeTerritories.get(StaticRandom.random.nextInt(freeTerritories.size())); // do random field
        int freeArmies = this.getFreeArmies().size();
        int amount = StaticRandom.random.nextInt(Math.min(10, freeArmies))+1;
        this.placeArmies(chosen, amount);
    }
    //endregion
}
