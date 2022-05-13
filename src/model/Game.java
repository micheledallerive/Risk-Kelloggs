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
    private final Board board;
    private final ArrayList<Player> players;
    private int turn;
    private GameStatus status;
    private ArrayList<Card> cardsDeck;
    private HashMap<ArmyColor, ArrayList<Army>> allArmies;
    //endregion

    //region CONSTRUCTORS
    /**
     * Create a new empty game.
     */
    public Game() {
        this(null, new ArrayList<Player>());
    }

    /**
     * Create a new game.
     * @param players The players in the game.
     */
    public Game(ArrayList<Player> players) {
        this(null, players);
    }

    /**
     * Create a new game.
     * @param board The board of the game.
     * @param players The players of the game.
     */
    public Game(Board board, ArrayList<Player> players) {
        Territory.init();
        Continent.init();
        RandomUtil.init();
        Die.init();

        this.board = board == null ? new Board() : board;
        this.players = players;
        this.turn = 0;
        this.status = GameStatus.MENU;
        this.cardsDeck = new ArrayList<>();
        this.allArmies = new HashMap<>();

        this.initCards();
    }
    //endregion

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

                for (int i = 0; i < numInfantry; i++) {
                    player.getArmies().add(new Army(ArmyType.INFANTRY, color, null));
                }

                for (int i = 0; i < 40 - numInfantry; i++) {
                    colorArmies.add(new Army(ArmyType.INFANTRY, color, null));
                }

                for (int i = 0; i < 12; i++) {
                    colorArmies.add(new Army(ArmyType.CAVALRY, color, null));
                }

                for (int i = 0; i < 8; i++) {
                    colorArmies.add(new Army(ArmyType.ARTILLERY, color, null));
                }

                this.allArmies.put(color, colorArmies);

            }
        }
    }

    /**
     * Moves the chosen amount of armies from the total armies to the armies owned by the player.
     * @param player the player to give armies to
     * @param num the amount of armies
     */
    public void giveArmiesToPlayer(Player player, int num) {
        int[] availableByType = new int[3];
        ArrayList<Army> availableArmies = this.allArmies.get(player.getColor());
        for (Army availableArmy : availableArmies){
            availableByType[availableArmy.getType().ordinal()]++;
        }
        int[] givenByType = new int[3];
        int[] armyValues = new int[] {1,5,10};
        // todo finish: find a way to give as many infantry as possible it's not an hard algorithm
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
     * Gives the turn bonus to the player.
     * @param game the game the player is playing in
     * @param player the player to give the bonus to
     */
    public void giveBonus(Game game, Player player) {
        int bonus = 0;

        /* bonus = 1 every 3 territories
         *       + continent bonus
         *       + card (soon) */

        bonus += player.getTerritories().size() / 3;

        for (Continent continent : game.getBoard().getContinents()) {
            if (continent.getOwner().getColor() == player.getColor()) {
                bonus += continent.getValue();
            }
        }

        

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
     * Updates the current turn to the next player.
     */
    public void nextTurn() {
        this.turn = (this.turn + 1) % this.players.size();
    }

    /**
     * Goes to the next status of the game.
     */
    public void nextStatus() {
        this.status = GameStatus.values()[this.status.ordinal() + 1];
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
     */
    public void play(GameCallback callback) {
        while(this.status != GameStatus.EXIT) {
            boolean result = false;
            switch(this.status) {
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
                case END:
                    result = callback.onGameEnd();
                    break;
                default:
                    break;
            }
            if (result) {
                this.nextStatus();
            }
        }
        callback.onGameExit();
    }
}
