package model;

import model.enums.GameStatus;

/**
 * Interface - implement listener for game status change.
 *
 * @author moralj @usi.ch
 */
public interface StatusListener {
    /**
     * Procedure - handle change in game status.
     *
     * @param status GameStatus enum.
     */
    void changed(final GameStatus status);
}
