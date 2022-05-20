package model;

import model.enums.ArmyColor;
import model.enums.DieColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private final String playerName;
    private final ArmyColor color;
    private final ArrayList<Card> cards;
    private final ArrayList<Army> armies;
    //endregion

    //region CONSTRUCTORS
    /**
     * Creates a new player.
     * @param color The color of the army of the player.
     * @param playerName The name of the player.
     */
    public Player(final ArmyColor color, final String playerName) {
        this.playerName = playerName;
        this.color = color;
        this.cards = new ArrayList<>();
        this.armies = new ArrayList<>();
    }
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Give string representation of player's name.
     * @return Player name as String object
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * Give player's color.
     * @return player's color.
     */
    public ArmyColor getColor() {
        return this.color;
    }

    /**
     * Give player's cards.
     * @return player's cards.
     */
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    /**
     * Give player's army list.
     * @return player's armies.
     */
    public ArrayList<Army> getArmies() {
        return this.armies;
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
     * Creates a list of players.
     * @param tot The number of total players: users + ias.
     * @param users The number of user players.
     * @param names The name of the users to be created.
     * @return Return the list of players
     */
    public static ArrayList<Player> generatePlayersRandomly(final byte tot, final byte users, final String[] names) {
        final ArrayList<Player> players = new ArrayList<>(tot);
        final List<ArmyColor> colors = Arrays.asList(ArmyColor.values());
        Collections.shuffle(colors);

        ArmyColor color = null;
        byte index = 0;
        for (;index < users; index++) {
            color = colors.get(index);
            players.add(new Player(color, names[index]));
        }

        for (; index < tot; index++) {
            color = colors.get(index);
            players.add(new AI(color));
        }
        return players;
    }

    /**
     * Give player's territories list.
     * @return player's territories.
     */
    public ArrayList<Territory> getTerritories() {
        final HashSet<Territory> set = new HashSet<>();
        for (final Army army : armies) {
            if (army.getTerritory() != null) {
                set.add(army.getTerritory());
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * Return the list of continents owned by the player.
     * @param game The game object
     * @return The continents of the player.
     */
    public ArrayList<Continent> getContinents(final Game game) {
        final ArrayList<Continent> continents = new ArrayList<>();
        for (final Continent continent : game.getBoard().getContinents()) {
            if (continent.getOwner() == this) {
                continents.add(continent);
            }
        }
        return continents;
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
        return false;
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
        final int defenderMaxArmies = Math.min(territory.getArmiesCount(), 3);
        final int defenderArmies = defArmies.length == 1 ? defArmies[0] : defenderMaxArmies;
        for (int i = 0; i < defenderArmies; i++) {
            Die.getBlueDice().get(i).roll();
        }

        ArrayList<DieColor> rollResult = Die.winner();

        final int defenderLost = (int) rollResult.stream()
                .filter(dieColor -> dieColor == DieColor.RED).count();
        final int attackerLost = rollResult.size() - defenderLost;

        this.removeArmies(fromTerritory, attackerLost);
        defender.removeArmies(territory, defenderLost);

        if (territory.getArmiesCount() == 0) {
            moveArmies((byte) (armies - attackerLost), fromTerritory, territory);
            territory.setOwner(fromTerritory.getOwner());
        }

        return new Integer[]{attackerLost, defenderLost};
    }

    /**
     * Return all the card combinations that the player could play.
     * @return A list of all the valid trio card combinations.
     */
    public ArrayList<Card[]> getCardCombinations() {
        final ArrayList<Card[]> validCombinations = new ArrayList<>();
        for (int i = 0; i < this.cards.size() - 2; i++) {
            for (int j = i + 1; j < this.cards.size() - 1; j++) {
                for (int k = j + 1; k < this.cards.size(); k++) {
                    final Card c1 = this.cards.get(i);
                    final Card c2 = this.cards.get(j);
                    final Card c3 = this.cards.get(k);
                    if (Card.validTrio(c1,c2,c3)) {
                        validCombinations.add(new Card[] {c1, c2, c3});
                    }
                }
            }
        }
        return validCombinations;
    }

    /**
     * Plays the wanted trio of cards in order to get armies.
     * @param cards the trio of cards to play
     * @return the number of armies gained
     */
    public int playCardsCombination(final Card[] cards) {
        final ArrayList<Territory> playerTerritories = getTerritories();
        int armiesCount = 0;

        for (final Card card : cards) {
            this.cards.remove(card);
        }

        for (final Territory territory : playerTerritories) {
            for (final Card card : cards) {
                if (card.getTerritory() == territory.getName() // if the player owns the territory
                    && armiesCount == 0) { // but you can only get the bonus once
                    armiesCount += 2;
                }
            }
        }

        armiesCount += Card.getCombinationValue(cards);
        return armiesCount;
    }

    /**
     * Pick a random card from the game deck.
     * @param game The game the player is playing in.
     * @return The card picked.
     */
    public Card pickCard(final Game game) {
        final Card card = game.getRandomCard();
        this.cards.add(card);
        return card;
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
        final Iterator<Army> it = this.getFreeArmies().iterator();
        for (byte i = 0; i < amount && it.hasNext(); i++) {
            Army next = it.next();
            next.setTerritory(territory);
            it.remove();
        }
    }

    /**
     * Removes the given amount of armies from the given territory.
     * @param territory The territory to remove the armies from
     * @param armies The number of armies to remove
     */
    public void removeArmies(final Territory territory, int armies) {
        final ArrayList<Army> toRemove = new ArrayList<>();
        final Iterator<Army> iterator = this.armies.iterator();
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
    public void moveArmies(final byte num, final Territory from, final Territory to) {
        final ArrayList<Army> armies = from.getArmies();
        if (armies.size() <= num) { return; }
        final Iterator<Army> iterator = armies.iterator();
        for (byte i = 0; i < num; i++) {
            final Army next = iterator.next();
            next.setTerritory(to);
            iterator.remove();
        }
    }
    //endregion
}
