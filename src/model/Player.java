package model;

import model.enums.ArmyColor;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Describes each player of the game.
 */
public class Player {

    private ArrayList<Card> cards;
    private ArrayList<Army> armies;
    private ArmyColor color;
    private ArrayList<Territory> territories;

    /**
     * Creates a new player.
     */
    public Player() {
        this(ArmyColor.BLACK);
    }

    /**
     * Creates a new player.
     * @param color the color of the army of the player
     */
    public Player(ArmyColor color) {
        this.cards = new ArrayList<>();
        this.armies = new ArrayList<>();
        this.color = color;
        this.territories = new ArrayList<>();
    }

    /**
     * Returns the cards owned by the players.
     * @return the player's cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Returns the list of the armies owned by the player.
     * @return the player's armies.
     */
    public ArrayList<Army> getArmies() {
        return armies;
    }

    /**
     * Returns the color of the player.
     * @return the color of the player.
     */
    public ArmyColor getColor() {
        return color;
    }

    /**
     * Returns the list of territories owned by the player.
     * @return the territories of the player.
     */
    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    /**
     * Attack a territory.
     * @param territory the territory to attack
     * @param armies the number of armies to use for the attack
     */
    public void attack(Territory territory, int armies) {
        assert this.armies.size() > armies;

    }
}
