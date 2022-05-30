package model.enums;

/**
 * The GameStatus describes the current state of the game.
 *
 * @author dallem@usi.ch
 */
public enum GameStatus {
    /**
     * the game is currently on the menu, waiting for the player to start the game.
     */
    MENU,
    /**
     * the game is being setted up (i.e. the game state between the menu and the actual playing state).
     */
    SETUP,
    /**
     * the players are playing and they are alternating their turns (it is the main state).
     */
    PLAYING,
    /**
     * the player is in pause mode, waiting for the player to resume the game.
     */
    PAUSE,
    /**
     * the game has ended and the player can either exit the game or go back to the menu.
     */
    END,
    /**
     * the game has ended and the players wants to exit the game.
     */
    EXIT
}