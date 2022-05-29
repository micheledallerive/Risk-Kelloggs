package model;

import model.callback.GameCallback;
import model.enums.ArmyColor;
import model.enums.ArmyType;
import model.enums.CardType;
import model.enums.GameStatus;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Represents the game itself.
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class Game implements Serializable {
    //region CONSTANTS
    public static final String PATH = "src/model/data/save.txt";
    //endregion

    //region FIELDS
    // events: Observer, Listeners design pattern
    private final ArrayList<StatusListener> statusListeners;
    private final ArrayList<TurnListener> turnListeners;

    private HashMap<ArmyColor, ArrayList<Army>> allArmies;
    private final ArrayList<Player> players;
    private final ArrayList<Card> cardsDeck;
    private final Board board;
    private GameStatus status;
    private Player playerStarting;
    private int turnsPlayed;
    private int turn;
    //endregion

    //region CONSTRUCTORS

    /**
     * Create a new empty game.
     */
    public Game() {
        this(new ArrayList<>());
    }


    /**
     * Create a new game.
     * @param players The players of the game.
     */
    public Game(final ArrayList<Player> players) {
        RandomUtil.init();
        Die.init();

        this.statusListeners = new ArrayList<>();
        this.turnListeners = new ArrayList<>();

        this.allArmies = new HashMap<>();
        this.players = players;
        this.cardsDeck = new ArrayList<>();
        this.board = new Board();
        this.status = GameStatus.MENU;
        this.playerStarting = null;
        this.turnsPlayed = 0;
        this.turn = 0;

        this.initCards();
    }
    //endregion

    //region GETTERS AND SETTERS
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
     * Returns the board of the current game.
     * @return the board of the game.
     */
    public Board getBoard() {
        return this.board;
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
     * Returns the player that starts the game.
     * @return the player that starts the game.
     */
    public Player getPlayerStarting() {
        return this.playerStarting;
    }

    /**
     * Sets the player that starts the game.
     * @param player the player that starts the game.
     */
    public void setPlayerStarting(final Player player) {
        this.playerStarting = player;
    }

    /**
     * Returns the current number of turns each player played.
     * @return the current number of turns each player played.
     */
    public int getTurnsPlayed() {
        return this.turnsPlayed;
    }

    /**
     * Sets the current number of turns each player played.
     * @param turnsPlayed the number of turns each player played.
     */
    public void setTurnsPlayed(int turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
    }

    /**
     * Returns the current turn.
     * @return the current turn
     */
    public final int getTurn() {
        return this.turn;
    }

    /**
     * Sets the current turn.
     * @param turn the turn to set as the current one.
     */
    public final void setTurn(int turn) {
        this.turn = turn;
        fireTurnChanged();
    }

    /**
     * Sets the current turn to the player.
     *
     * @param player the player to set as the current one.
     */
    public void setTurn(Player player) {
        int playerIndex = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getColor() == player.getColor()) {
                playerIndex = i;
                break;
            }
        }
        setTurn(playerIndex);
    }
    //endregion

    //region METHODS
    /**
     * Save game object into file.
     */
    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Initializes the deck of card creating all the cards that it contains.
     */
    private void initCards() {
        ArrayList<Territory> territories = this.board.getTerritories();
        for (int i = 0; i < territories.size(); i++) {
            String territoryName = territories.get(i).getName();
            CardType type = CardType.values()[i % 3];
            this.cardsDeck.add(new Card(type, territoryName));
        }
        this.cardsDeck.add(new Card(CardType.WILD, null));
        this.cardsDeck.add(new Card(CardType.WILD, null));
        Collections.shuffle(this.cardsDeck);
    }

    /**
     * Procedure - Initializes the armies for each player at the start of the game.
     */
    public void initArmies() {
        final int numPlayers = this.players.size();
        if (numPlayers >= 3 && numPlayers <= 6) {
            final int numInfantry = 35 - (numPlayers - 3) * 5;
            for (final Player player : this.players) {
                final ArmyColor color = player.getColor();
                final ArrayList<Army> colorArmies = new ArrayList<>();

                // Since we are only using INFANTRY instead of using also CAVALRY and ARTILLERY,
                // the total amount of INFANTRY is 40 (the normal value) + 12 (CAVALRY) * 5 (the value)
                // + 8 (ARTILLERY) * 10 (the value) = 40 + 140.
                for (int i = 0; i < 140 + 40; i++) {
                    colorArmies.add(new Army(ArmyType.INFANTRY, color, null));
                }
                this.allArmies.put(color, colorArmies);
                this.giveArmiesToPlayer(player, numInfantry);
            }
        }
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
     * Updates the current turn to the next player.
     */
    public void nextTurn() {
        this.turn = (this.turn + 1) % this.players.size();
        if (this.players.get(this.turn) == this.playerStarting) {
            this.turnsPlayed++;
        }
        fireTurnChanged();
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
            boolean result;
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
    /**
     * Procedure - add status listener to its listeners arraylist.
     * @param sl StatusListener interface implemented.
     */
    public void addListener(final StatusListener sl) {
        this.statusListeners.add(sl);
    }

    /**
     * Procedure - add turn listener
     * @param tl TurnListener interface implemented.
     */
    public void addListener(final TurnListener tl) {
        this.turnListeners.add(tl);
    }

    /**
     * Procedure - notify all registered listener whenever game status change.
     */
    public void fireStatusChanged() {
        for (final StatusListener sc : this.statusListeners) {
            sc.changed(this.status);
        }
    }

    /**
     * Procedure - notify all registered listener whenever game turn change.
     */
    public void fireTurnChanged() {
        for (final TurnListener sc : this.turnListeners) {
            sc.turnChanged(this.getTurn());
        }
    }
    //endregion
}
