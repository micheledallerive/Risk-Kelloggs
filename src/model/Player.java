package model;

import model.enums.ArmyColor;
import model.enums.CardType;

import java.util.ArrayList;
import java.util.List;

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
     * Adds a card to the player cards.
     * @param card the card to add.
     */
    public void addCard(Card card) {
        this.cards.add(card);
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

    /**
     * Returns all the card combinations that the player could play.
     * @return a list of all the card combinations.
     */
    public ArrayList<ArrayList<Card>> getCardCombinations() {
        ArrayList<ArrayList<Card>> validCombinations = new ArrayList<>();
        for(List<CardType> combination : Card.combinations) {
            for(CardType combination_card : combination) {
                // TODO complete
            }
        }
        return validCombinations;
    }

    /**
     * Picks a random card from the game deck.
     * @param game the game the player is playing in.
     */
    public void pickCard(Game game) {
        Card card = game.getRandomCard();
        this.cards.add(card);
    }
}
