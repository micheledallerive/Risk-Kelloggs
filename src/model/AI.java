package model;

import model.enums.ArmyColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * Attacks a player.
     * @param callback the callback to be colled in the Main function
     *                 (either TUI or GUI) in order to let the player do
     *                 something when they are attacked by an AI.
     */
    public void attack(Callback callback) {
        List<Territory> available = this.getTerritories()
                .stream()
                .filter((territory) -> territory.getArmiesCount() > 1)
                .collect(Collectors.toList());
        if (available.size() == 0) {
            return;
        }
        Territory from = available.get(RandomUtil.random.nextInt(available.size()));
        Territory attackedTerritory = from.getAdjacent().get(
                RandomUtil.random.nextInt(from.getAdjacent().size())
        );
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
     * @param game the game the AI is playing in
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
        if (freeTerritories.size() > 0) {
            chosen = freeTerritories.get(RandomUtil.random.nextInt(freeTerritories.size()));
        } else {
            ArrayList<Territory> territories = this.getTerritories();
            System.out.println(territories.size());
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
    //endregion
}
