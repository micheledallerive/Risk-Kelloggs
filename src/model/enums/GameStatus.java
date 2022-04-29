package model.enums;

/**
 * The GameStatus describes the current state of the game.
 * @author dallem@usi.ch
 * {@link #MENU}
 * {@link #SETUP}
 * {@link #PLAYING}
 * {@link #END}
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
     * the game has ended and the player can either exit the game or go back to the menu.
     */
    END
}