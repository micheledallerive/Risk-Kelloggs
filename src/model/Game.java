package model;

import model.enums.CardType;
import model.enums.GameStatus;
import model.enums.StaticRandom;
import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents the game itself.
 * @author dallem@usi.ch
 */
public class Game {
    private Board board;
    private ArrayList<Player> players;
    private GameStatus status;
    private int turn;
    private ArrayList<Card> cardsDeck;

    /**
     * Create a new empty game.
     */
    public Game() {
        this(null, new ArrayList<Player>());
    }

    /**
     * Creates a new game.
     * @param players the players in the game.
     */
    public Game(ArrayList<Player> players) {
        this(null, players);
    }

    /**
     * Creates a new game.
     * @param board the board of the game.
     * @param players the players of the game.
     */
    public Game(Board board, ArrayList<Player> players) {
        Territory.init();
        Continent.init();

        this.board = board==null ? new Board() : board;
        this.players = players;
        this.status = GameStatus.MENU;
        this.turn = 0;
        this.cardsDeck = new ArrayList<>();

        this.initCards();
    }

    /**
     * Initializes the deck of card creating all the cards that it contains.
     */
    private void initCards() {
        int territoriesCount = TerritoryName.values().length;
        for (int i = 0; i < territoriesCount; i++) {
            TerritoryName territoryName = TerritoryName.values()[i];
            CardType type = CardType.values()[i%3];
            this.cardsDeck.add(new Card(type, territoryName));
        }
        this.cardsDeck.add(new Card(CardType.WILD, TerritoryName.NONE));
        this.cardsDeck.add(new Card(CardType.WILD, TerritoryName.NONE));
        Collections.shuffle(this.cardsDeck);
    }

    /**
     * Get a random card from the deck and remove it.
     * @return the card that was randomly picked.
     */
    public Card getRandomCard() {
        if (this.cardsDeck.size() < 1) {
            return null;
        }
        int randomIndex = StaticRandom.random.nextInt(this.cardsDeck.size());
        Card card = this.cardsDeck.get(randomIndex);
        this.cardsDeck.remove(randomIndex);
        return card;
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
     * Updates the current turn to the next player.
     */
    public void nextTurn() {
        this.turn = (this.turn + 1) % this.players.size();
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
}
