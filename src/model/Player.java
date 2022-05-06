package model;

import model.enums.ArmyColor;
import model.enums.DieColor;

import java.util.*;
import java.util.stream.Collectors;

import static tui.Utils.print;

/**
 * Describes each player of the game.
 * @author dallem@usi.ch
 */
public class Player {

    protected boolean ai;
    private ArrayList<Card> cards;
    private ArrayList<Army> armies;
    private ArmyColor color;

    /**
     * Creates a random list of players: one actual players and num-1 AIs.
     * @param num the number of players to create: 1 player, num-1 AIs.
     * @return returns the list of players
     */
    public static ArrayList<Player> generatePlayersRandomly(int num) {
        ArrayList<Player> players = new ArrayList<>(num);

        List<ArmyColor> colors = Arrays.asList(ArmyColor.values());
        Collections.shuffle(colors);
        int playersToRemove = colors.size() - num;
        while (playersToRemove > 0) {
            colors.remove(colors.size()-1);
            playersToRemove--;
        }

        // I use the first color to create the real player
        Player realPlayer = new Player(colors.get(0));
        players.add(realPlayer);

        // I use all the other colors to create the AIs.
        for (int i = 1; i < colors.size(); i++) {
            players.add(new AI(colors.get(i)));
        }

        return players;
    }

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
        this.ai = false;
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
     * Returns the list of the free armies of the player.
     * @return the player's free armies.
     */
    public List<Army> getFreeArmies() {
        return this.armies.stream().filter((a)->a.getTerritory()==null).collect(Collectors.toList());
    }

    /**
     * Place an amount of armies in a specific territory.
     * This function is used during the setup of the game and
     * when the player gets new armies every turn.
     * @param territory the territory where to place the armies
     * @param amount the amount of armies to place.
     */
    public void placeArmies(Territory territory, int amount) {
        territory.setOwner(this);
        Iterator<Army> iterator = this.getFreeArmies().iterator();
        while (amount > 0 && iterator.hasNext()) {
            Army next = iterator.next();
            next.setTerritory(territory);
            territory.addArmy(next);
            iterator.remove();
            amount--;
        }
    }

    /**
     * Sets the armies owned by the player.
     * @param armies the armies owned by the player.
     */

    public void setArmies(ArrayList<Army> armies) {
        this.armies = armies;
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
        HashSet<Territory> set = new HashSet<>();
        for (Army army : armies) {
            if (army.getTerritory() != null) {
                set.add(army.getTerritory());
            }
        }
        return new ArrayList<>(set);
    }

    /**
     * Checks if the Player is an AI.
     * @return if the player is an AI
     */
    public boolean isAI() {
        return this.ai;
    }

    /**
     * Attack a territory.
     * @param fromTerritory the territory the player is attacking from
     * @param territory the territory to attack
     * @param armies the number of armies to use for the attack
     * @return an array containing how many armies the attacker has lost
     *          and how many armies the defender has lost.
     */
    public Integer[] attack(Territory fromTerritory, Territory territory,
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
                .filter(dieColor -> dieColor==DieColor.RED).count();
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
     * Returns all the card combinations that the player could play.
     * @return a list of all the card combinations.
     */
    public ArrayList<ArrayList<Card>> getCardCombinations() {
        ArrayList<ArrayList<Card>> validCombinations = new ArrayList<>();
        /* for (List<CardType> combination : Card.combinations) {
            for (CardType combinationCard : combination) {
                // TODO complete
            }
        } */
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

    /**
     * Removes the given amount of armies from the given territory.
     * @param armies the number of armies to remove
     * @param territory the territory to remove the armies from
     */
    public void removeArmies(int armies, Territory territory) {
        ArrayList<Army> toRemove = new ArrayList<>();
        Iterator<Army> iterator = this.armies.iterator();
        while(armies > 0 && iterator.hasNext()) {
            Army army = iterator.next();
            if (army.getTerritory().getName() == territory.getName()) {
                toRemove.add(army);
                iterator.remove();
                armies--;
            }
        }
        for (Army army : toRemove) {
            territory.getArmies().remove(army);
        }
    }

    /**
     * Moves the given amount of armies from a territory to another
     * @param num the number of armies to move.
     * @param from the territory where to remove the armies.
     * @param to the territory where to move the armies.
     */
    public void moveArmies(int num, Territory from, Territory to) {
        ArrayList<Army> armies = from.getArmies();
        if (armies.size() <= num) return;
        Iterator<Army> iterator = armies.iterator();
        while(num > 0) {
            Army next = iterator.next();
            next.setTerritory(to);
            to.addArmy(next);
            iterator.remove();
            num--;
        }
    }
}
