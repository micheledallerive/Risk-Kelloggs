package model;

import model.enums.ArmyColor;
import model.enums.DieColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Describes each player of the game.
 * @author dallem@usi.ch
 */
public class Player {
    //region CONSTANTS
    static final byte MIN_PLAYERS = 3;
    static final byte MAX_PLAYERS = 6;
    //endregion

    //region FIELDS
    private final String name;
    private final ArmyColor color;
    private final ArrayList<Card> cards;
    private final ArrayList<Army> armies;
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
    }
    //endregion

    //region GETTERS AND SETTERS
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Return the name of the player.
     * @return Player name as String object
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the color of the player.
     * @return The color of the player.
     */
    public ArmyColor getColor() {
        return this.color;
    }

    /**
     * Return the cards owned by the players.
     * @return the player's cards.
     */
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    /**
     * Return the list of the armies owned by the player.
     * @return The player's armies.
     */
    public ArrayList<Army> getArmies() {
        return this.armies;
    }

    /**
     * Return the list of territories owned by the player.
     * @return The territories of the player.
     */
    public ArrayList<Territory> getTerritories() {
        HashSet<Territory> set = new HashSet<>();
        for (final Army army : armies) {
            if (army.getTerritory() != null) {
                set.add(army.getTerritory());
            }
        }
        return new ArrayList<>(set);
    }
    //endregion

    //region METHODS
    /**
     * Creates a random list of players: one actual players and num-1 AIs.
     * @param num the number of players to create: 1 player, num-1 AIs.
     * @return returns the list of players
     */
    public static ArrayList<Player> generatePlayersRandomly(final byte num) {
        return Player.generatePlayersRandomly(MAX_PLAYERS, (byte) 1, new String[] { "player" });
    }

    /**
     * Creates a random list of players: one actual players and num-1 AIs.
     * @param num the number of players to create: 1 player, num-1 AIs.
     * @param name The name of the real player
     * @return returns the list of players
     */
    public static ArrayList<Player> generatePlayersRandomly(final int num, final String name) {
        ArrayList<Player> players = new ArrayList<>(num);

        List<ArmyColor> colors = Arrays.asList(ArmyColor.values());
        Collections.shuffle(colors);
        int playersToRemove = colors.size() - num;
        while (playersToRemove > 0) {
            colors.remove(colors.size() - 1);
            playersToRemove--;
        }

        // I use the first color to create the real player
        Player realPlayer = new Player(colors.get(0), name);
        players.add(realPlayer);

        // I use all the other colors to create the AIs.
        for (int i = 1; i < colors.size(); i++) {
            players.add(new AI(colors.get(i)));
        }

        return players;
    }

    /**
     * Creates a list of players.
     * @param tot The number of total players: users + ias.
     * @param users The number of user players.
     * @param names The name of the users to be created.
     * @return Return the list of players
     */
    public static ArrayList<Player> generatePlayersRandomly(final byte tot, final byte users, final String[] names) {
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
        return this.armies.stream().filter((a) -> a.getTerritory() == null).collect(Collectors.toList());
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
     * @param fromTerritory the territory the player is attacking from
     * @param territory the territory to attack
     * @param armies the number of armies to use for the attack
     * @param defArmies The optional parameter array states number of defender armies.
     * @return an array containing how many armies the attacker has lost
     *          and how many armies the defender has lost.
     */
    public Integer[] attack(final Territory fromTerritory, final Territory territory,
                          int armies, int ...defArmies) {
        for (int i = 0; i < armies; i++) {
            Die.getRedDice().get(i).roll();
        }

        Player defender = territory.getOwner();
        int defenderMaxArmies = Math.min(territory.getArmiesCount(), 3);
        int defenderArmies = defArmies.length == 1 ? defArmies[0] : defenderMaxArmies;
        for (int i = 0; i < defenderArmies; i++) {
            Die.getBlueDice().get(i).roll();
        }

        ArrayList<DieColor> rollResult = Die.winner();

        int defenderLost = (int) rollResult.stream()
                .filter(dieColor -> dieColor == DieColor.RED).count();
        int attackerLost = rollResult.size() - defenderLost;

        this.removeArmies(attackerLost, fromTerritory);
        defender.removeArmies(defenderLost, territory);

        if (territory.getArmiesCount() == 0) {
            moveArmies(armies - attackerLost, fromTerritory, territory);
            territory.setOwner(fromTerritory.getOwner());
        }

        return new Integer[]{attackerLost, defenderLost};
    }

    /**
     * Return all the card combinations that the player could play.
     * @return A list of all the card combinations.
     */
    public ArrayList<ArrayList<Card>> getCardCombinations() {
        ArrayList<ArrayList<Card>> validCombinations = new ArrayList<>();
        cards.sort(Comparator.comparing(Card::getType));
        for (byte i = 0; i < cards.size() - 3; i++) {
            Card c1 = cards.get(i);
            Card c2 = cards.get(i + 1);
            Card c3 = cards.get(i + 2);
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
            next.setTerritory(territory);
            it.remove();
        }
    }

    /**
     * Removes the given amount of armies from the given territory.
     * @param armies The number of armies to remove
     * @param territory The territory to remove the armies from
     */
    public void removeArmies(int armies, final Territory territory) {
        ArrayList<Army> toRemove = new ArrayList<>();
        Iterator<Army> iterator = this.armies.iterator();
        while (armies > 0 && iterator.hasNext()) {
            Army army = iterator.next();
            if (army.getTerritory().getName() == territory.getName()) {
                toRemove.add(army);
                iterator.remove();
                armies--;
            }
        }
        for (final Army army : toRemove) {
            territory.getArmies().remove(army);
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

    /**
     * Moves the given amount of armies from a territory to another.
     * @param num the number of armies to move.
     * @param from the territory where to remove the armies.
     * @param to the territory where to move the armies.
     */
    public void moveArmies(int num, Territory from, Territory to) {
        ArrayList<Army> armies = from.getArmies();
        if (armies.size() <= num) { return; }
        Iterator<Army> iterator = armies.iterator();
        while (num > 0) {
            Army next = iterator.next();
            next.setTerritory(to);
            to.addArmy(next);
            iterator.remove();
            num--;
        }
    }
    //endregion
}
