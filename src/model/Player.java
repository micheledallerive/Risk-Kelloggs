package model;

import model.enums.ArmyColor;

import static java.util.Comparator.comparing;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Describes each player of the game.
 * @author dallem@usi.ch
 */
public class Player {
    //region CONSTANTS
    final static byte MIN_PLAYERS = 3;
    final static byte MAX_PLAYERS = 6;
    //endregion

    //region FIELDS
    private final String name;
    private final ArmyColor color;
    private final ArrayList<Card> cards;
    private final ArrayList<Army> armies;
    private final ArrayList<Territory> territories;
    //endregion

    //region CONSTRUCTORS
    /**
     * Creates a new player.
     * @param color The color of the army of the player.
     * @param name The name of the player.
     */
    public Player(final ArmyColor color, final String name) {
        this.name = name;
        this.color = color;
        this.cards = new ArrayList<>();
        this.armies = new ArrayList<>();
        this.territories = new ArrayList<>();
    }
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Return the color of the player.
     * @return The color of the player.
     */
    public ArmyColor getColor() {
        return color;
    }

    /**
     * Return the cards owned by the players.
     * @return the player's cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Return the list of the armies owned by the player.
     * @return The player's armies.
     */
    public ArrayList<Army> getArmies() { return armies; }

    /**
     * Return the list of territories owned by the player.
     * @return The territories of the player.
     */
    public ArrayList<Territory> getTerritories() {
        return territories;
    }
    //endregion

    //region METHODS
    /**
     * Creates a random list of players: one actual players and num-1 AIs.
     * @param num the number of players to create: 1 player, num-1 AIs.
     * @return returns the list of players
     */
    public static ArrayList<Player> generatePlayersRandomly(final byte num) {
        return Player.generatePlayers(MAX_PLAYERS, (byte) 1, new String[] { "player" });
    }

    /**
     * Creates a list of players.
     * @param tot The number of total players: users + ias.
     * @param users The number of user players.
     * @param names The name of the users to be created.
     * @return Return the list of players
     */
    public static ArrayList<Player> generatePlayers(final byte tot, final byte users, final String[] names) {
        ArrayList<Player> players = new ArrayList<>(tot);
        List<ArmyColor> colors = Arrays.asList(ArmyColor.values());
        Collections.shuffle(colors);

        //create real players
        for (byte i = users; i >= 0; i--) {
            ArmyColor color = colors.remove(i);
            players.add(new Player(color, names[i]));
            colors.remove(i);
        }

        //create AI
        for (byte i = (byte) (tot - users); i >= 0; i--) {
            ArmyColor color = colors.remove(i);
            players.add(new AI(color));
            colors.remove(color);
        }
        return players;
    }

    /**
     * Return the list of the free armies of the player.
     * @return The player's free armies.
     */
    public List<Army> getFreeArmies() {
        return this.armies.stream().filter((a)->a.getTerritory()==null).collect(Collectors.toList());
    }

    /**
     * Check if the Player is an AI.
     * @return If the player is an AI
     */
    public boolean isAI() {
        return this instanceof AI;
    }

    /**
     * Attack a territory.
     * @param territory The territory to attack
     * @param armies The number of armies to use for the attack
     * @return Return true if the player can actually attack that territory, false otherwise.
     */
    public boolean attack(final Territory territory, final int armies) {
        return this.armies.size() > armies;
        // TODO complete
    }

    /**
     * Return all the card combinations that the player could play.
     * @return A list of all the card combinations.
     */
    public ArrayList<ArrayList<Card>> getCardCombinations() {
        ArrayList<ArrayList<Card>> validCombinations = new ArrayList<>();
        cards.sort(Comparator.comparing(Card::getType));
        Card c1, c2, c3;
        for (byte i = 0; i < cards.size() - 3; i++) {
            c1 = cards.get(i);
            c2 = cards.get(i + 1);
            c3 = cards.get(i + 2);
            if (Card.validTrio(c1, c2, c3)) {
                ArrayList<Card> combination = new ArrayList<>();
                combination.add(c1);
                combination.add(c2);
                combination.add(c3);
                validCombinations.add(combination);
            }
        }
        return validCombinations;
    }

    /**
     * Pick a random card from the game deck.
     * @param game The game the player is playing in.
     */
    public void pickCard(final Game game) {
        Card card = game.getRandomCard();
        this.cards.add(card);
    }

    /**
     * Place an amount of armies in a specific territory.
     * This function is used during the setup of the game and
     * when the player gets new armies every turn.
     * @param territory the territory where to place the armies
     * @param amount the amount of armies to place.
     */
    public void placeArmies(final Territory territory, final int amount) {
        territory.setOwner(this);
        Iterator<Army> it = this.getFreeArmies().iterator();
        for (byte i = 0; i < amount && it.hasNext(); i++) {
            Army next = it.next();
            territory.addArmy(next);
            this.armies.remove(next);
            it.remove();
        }
    }

    /**
     * Add an army to the player's armies.
     * @param army The army to be added to the arraylist collections.
     */
    public void addArmy(final Army army) {
        this.armies.add(army);
    }

    /**
     * Remove an army from player's armies.
     * @param army The army to be removed from the arraylist collection.
     */
    public void removeArmy(final Army army) {
        this.armies.remove(army);
    }

    /**
     * Add a card to the player cards.
     * @param card the card to add.
     */
    public void addCard(final Card card) {
        this.cards.add(card);
    }
    //endregion
}
