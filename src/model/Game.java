package model;

import model.Territory.TerritoryName;
import model.callback.GameCallback;
import model.enums.ArmyColor;
import model.enums.ArmyType;
import model.enums.CardType;
import model.enums.GameStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents the game itself.
 * @author dallem@usi.ch
 */
public class Game {
    //region FIELDS
    // events: Observer, Listeners design pattern
    private final ArrayList<StatusListener> statusListeners;

    private final Board board;
    private final ArrayList<Player> players;
    private int turn;
    private GameStatus status;
    private ArrayList<Card> cardsDeck;
    private HashMap<ArmyColor, ArrayList<Army>> allArmies;
    private int turnsPlayed;
    private Player playerStarting;
    //endregion

    //region CONSTRUCTORS
    /**
     * Create a new empty game.
     */
    public Game() {
        this(new ArrayList<Player>());
    }


    /**
     * Create a new game.
     * @param players The players of the game.
     */
    public Game(final ArrayList<Player> players) {
        Territory.init();
        Continent.init();
        RandomUtil.init();
        Die.init();

        this.statusListeners = new ArrayList<>();

        this.board = new Board();
        this.players = players;
        this.turn = 0;
        this.status = GameStatus.MENU;
        this.cardsDeck = new ArrayList<>();
        this.allArmies = new HashMap<>();
        this.turnsPlayed = 0;
        this.playerStarting = null;

        this.initCards();
    }
    //endregion

    //region METHODS
    /**
     * Initializes the deck of card creating all the cards that it contains.
     */
    private void initCards() {
        int territoriesCount = TerritoryName.values().length;
        for (int i = 0; i < territoriesCount; i++) {
            TerritoryName territoryName = TerritoryName.values()[i];
            CardType type = CardType.values()[i % 3];
            this.cardsDeck.add(new Card(type, territoryName));
        }
        this.cardsDeck.add(new Card(CardType.WILD, TerritoryName.NONE));
        this.cardsDeck.add(new Card(CardType.WILD, TerritoryName.NONE));
        Collections.shuffle(this.cardsDeck);
    }

    /**
     * Procedure - Initializes the armies for each player at the start of the game.
     */
    public void initArmies() {
        int numPlayers = this.players.size();
        if (numPlayers >= 3 && numPlayers <= 6) {
            int numInfantry = 35 - (numPlayers - 3) * 5;
            for (Player player : this.players) {
                ArmyColor color = player.getColor();
                ArrayList<Army> colorArmies = new ArrayList<>();

                // Since we are only using INFANTRY instead of using also CAVALRY and ARTILLERY,
                // the total amount of INFANTRY is 40 (the normal value) + 12 (CAVALRY) * 5 (the value)
                // + 8 (ARTILLERY) * 10 (the value) = 40 + 140.
                for (int i = 0; i < 140 + 40; i++) {
                    colorArmies.add(new Army(ArmyType.INFANTRY, color, null));
                }

                this.allArmies.put(color, colorArmies);

                giveArmiesToPlayer(player, numInfantry);

            }
        }
    }

    /**
     * Add back an army to the all armies map.
     * @param color The color of the army.
     * @param army The army to add.
     */
    public void addArmy(ArmyColor color, Army army) {
        ArrayList<Army> armies = this.allArmies.get(color);
        armies.add(army);
    }

    /**
     * Moves the chosen amount of armies from the total armies to the armies owned by the player.
     * @param player the player to give armies to
     * @param num the amount of armies
     */
    public void giveArmiesToPlayer(Player player, int num) {
        player.getArmies().addAll(this.allArmies.get(player.getColor()).subList(0, num));
        this.allArmies.get(player.getColor()).removeAll(this.allArmies.get(player.getColor()).subList(0, num));
    }

    /**
     * Get a random card from the deck and remove it.
     * @return the card that was randomly picked.
     */
    public Card getRandomCard() {
        if (this.cardsDeck.size() < 1) {
            return null;
        }
        int randomIndex = RandomUtil.random.nextInt(this.cardsDeck.size());
        Card card = this.cardsDeck.get(randomIndex);
        this.cardsDeck.remove(randomIndex);
        return card;
    }

    /**
     * Gives the turn bonus to the player and returns the value of armies gained
     * divided by reason (for UI purposes).
     * @param player the player to give the bonus to
     * @param indexCardsCombination cards combination the player wants to play (-1 to not play)
     * @return Integer array representing bonus
     */
    public int[] giveBonus(Player player, int indexCardsCombination) {
        int[] bonus = new int[3];

        /* bonus = 1 every 3 territories
         *       + continent bonus
         *       + card (soon) */

        // territories bonus
        bonus[0] = player.getTerritories().size() / 3;

        // continent bonus
        for (Continent continent : this.getBoard().getContinents()) {
            //System.out.println(continent.getName().toString());
            if (continent.getOwner() != null
                    && continent.getOwner().getColor() == player.getColor()) {
                bonus[1] += continent.getValue();
            }
        }

        if (indexCardsCombination != -1) {
            // cards bonus
            int combinationArmies = player.playCardsCombination(
                    player.getCardCombinations().get(indexCardsCombination)
            );
            bonus[2] = combinationArmies;
        }

        // add the bonus to the player
        giveArmiesToPlayer(player, bonus[0] + bonus[1] + bonus[2]);

        return bonus;
    }

    /**
     * Returns the board of the current game.
     * @return the board of the game.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Returns the list of players of the game.
     * @return the list of players of the game.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Adds a player to the list of players.
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Initializes randomly created players.
     * @param total the total number of players
     * @param users the number of actual players (not AIs)
     * @param names the names of the players
     */
    public void initializePlayers(int total, int users, String[] names) {
        ArrayList<Player> toAddPlayers = Player.generatePlayersRandomly((byte)total, (byte)users, names);
        this.players.addAll(toAddPlayers);
    }

    /**
     * Returns the current state of the game.
     * @return the GameStatus
     */
    public GameStatus getStatus() {
        return this.status;
    }

    /**
     * Sets the current state of the game.
     * @param status the new status of the game
     */
    public void setStatus(GameStatus status) {
        this.status = status;
        this.fireStatusChanged();
    }

    /**
     * Returns the current turn.
     * @return the current turn
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Sets the current turn.
     * @param turn the turn to set as the current one.
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Returns the current number of turns each player played.
     * @return the current number of turns each player played.
     */
    public int getTurnsPlayed() {
        return this.turnsPlayed;
    }

    /**
     * Sets the player that starts the game.
     * @param player the player that starts the game.
     */
    public void setPlayerStarting(Player player) {
        this.playerStarting = player;
    }

    /**
     * Sets the current number of turns each player played.
     * @param turnsPlayed the number of turns each player played.
     */
    public void setTurnsPlayed(int turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
    }

    /**
     * Updates the current turn to the next player.
     */
    public void nextTurn() {
        this.turn = (this.turn + 1) % this.players.size();
        if (this.players.get(this.turn) == this.playerStarting) {
            this.turnsPlayed++;
        }
    }

    /**
     * Goes to the next status of the game.
     */
    public void nextStatus() {
        this.setStatus(GameStatus.values()[this.status.ordinal() + 1]);
    }

    /**
     * Checks if the whole world has been conquered by only one person.
     * @return true if the whole world is owned by a single person.
     */
    public boolean isWorldConquered() {
        for (int i = 1; i < this.board.getContinents().size(); i++) {
            if (this.board.getContinents().get(i).getOwner() == null
                || this.board.getContinents().get(i).getOwner() != this.board.getContinents().get(i - 1).getOwner()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Starts the game handling the different states.
     * @param callback The event to call with respect to game status.
     */
    public void play(GameCallback callback) {
        while (this.status != GameStatus.EXIT) {
            boolean result = false;
            switch (this.status) {
                case MENU:
                    result = callback.onMainMenu();
                    break;
                case SETUP:
                    result = callback.onGameSetup();
                    break;
                case PLAYING:
                    result = callback.onGamePlay();
                    break;
                case PAUSE:
                    result = callback.onGamePause();
                    break;
                default:
                    result = callback.onGameEnd();
                    break;
            }
            if (result) {
                this.nextStatus();
            }
        }
        callback.onGameExit();
    }
    //endregion

    //region EVENTS
    public void addListener(final StatusListener sl) {
        this.statusListeners.add(sl);
    }

    public void removeListener(final StatusListener sl) {
        this.statusListeners.remove(sl);
    }

    public void fireStatusChanged() {
        for (final StatusListener sc : this.statusListeners) {
            sc.changed(this.status);
        }
    }
    //endregion
}
