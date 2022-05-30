package model;

import model.callback.Callback;
import model.enums.ArmyColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AI to play versus the computer.
 *
 * @author dallem @usi.ch
 */
public class AI extends Player {
    //region CONSTRUCTORS

    /**
     * Constructor.
     */
    public AI() {
        this(ArmyColor.BLACK);
    }

    /**
     * Constructor with army color choice option.
     *
     * @param color The color of the army
     */
    public AI(final ArmyColor color) {
        super(color, "AI");
    }
    //endregion

    //region METHODS

    /**
     * Attacks a player.
     *
     * @param board    game board.
     * @param callback the callback to be called in the Main function (either TUI or GUI) in order to
     *      let the player do something when they are attacked by an AI.
     */
    public void attack(final Board board, final Callback callback) {
        // from AI territories, get the ones that have more than 1 army
        List<Territory> available = new ArrayList<>();
        for (Territory territory : this.getTerritories()) {
            if (territory.getArmiesCount() > 1) {
                available.add(territory);
            }
        }

        // if all territories haven't more than one army, AI can't attack
        if (available.isEmpty()) { return; }

        // otherwise, choose a random territory from the list,
        // get the nearby territories and randomly choose one to attack
        Territory from = available.get(RandomUtil.random.nextInt(available.size()));    // from where to attack
        int fromIndex = board.getTerritoryIdx(from.getName());
        int indexAttackNearby = RandomUtil.random.nextInt(board.getAdjacency().get(fromIndex).size());
        int attackedTerritoryIndex = board.getAdjacency().get(fromIndex).get(indexAttackNearby);
        Territory attackedTerritory = board.getTerritories().get(attackedTerritoryIndex);
        /*String attackedTerritory = from.getAdjacent().get(
                RandomUtil.random.nextInt(from.getAdjacent().size())
        );*/
        Player attackedPlayer = attackedTerritory.getOwner();
        if (attackedPlayer.isAI()) {
            callback.onAIAttacked(this, attackedPlayer,
                from, attackedTerritory);
        } else {
            callback.onPlayerAttacked(this, attackedPlayer,
                from, attackedTerritory);
        }
    }

    /**
     * Function to let the AI place armies during the game setup.
     *
     * @param game    the game the AI is playing in
     * @param onlyOne true if the AI can place only one army.
     * @return the territory the AI placed the armies in.
     */
    public Territory placeArmy(Game game, boolean onlyOne) {
        Territory chosen;
        ArrayList<Territory> freeTerritories = new ArrayList<>();
        for (Territory territory : game.getBoard().getTerritories()) {
            if (territory.getOwner() == null) {
                freeTerritories.add(territory);
            }
        }
        if (!freeTerritories.isEmpty()) {
            chosen = freeTerritories.get(RandomUtil.random.nextInt(freeTerritories.size()));
        } else {
            ArrayList<Territory> territories = this.getTerritories();
            chosen = territories.get(RandomUtil.random.nextInt(territories.size()));
        }
        int freeArmies = this.getFreeArmies().size();
        if (freeArmies < 1) {
            return null;
        }
        int amount = onlyOne
            ? 1
            : RandomUtil.random.nextInt(Math.min(10, freeArmies)) + 1;
        this.placeArmies(chosen, amount);
        return chosen;
    }

    @Override
    public boolean isAI() {
        return true;
    }
    //endregion
}
