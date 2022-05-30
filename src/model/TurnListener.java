package model;

/**
 * Event to implement to handle turn changing behavior.
 *
 * @author dallem@usi.ch
 */
public interface TurnListener {
    /**
     * Event for turn change.
     *
     * @param newTurn the player's turn
     */
    void turnChanged(int newTurn);
}
