package model.callback;

/**
 * Callback to handle game status.
 * @author dallem@usi.ch
 */
public interface GameCallback {
    /**
     * Function - handles main menu.
     * @return True if game is in the main menu
     */
    boolean onMainMenu();

    /**
     * Event - Handles setup game phase.
     * @return True if setup finished and go to next state
     */
    boolean onGameSetup();

    /**
     * Event - Manage play in-game behavior.
     * @return True if have to stay in this state
     */
    boolean onGamePlay();

    /**
     * Event - Handles game pause state behavior.
     * @return True if pass to another game state
     */
    boolean onGamePause();

    /**
     * Event - Handles game final state behavior.
     * @return True if have to stay in this state
     */
    boolean onGameEnd();

    /**
     * Event - handles game exit final behavior.
     */
    void onGameExit();
}
