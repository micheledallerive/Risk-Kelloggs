package test;

import static org.junit.Assert.assertEquals;

import model.Board;
import model.Game;

import org.junit.Test;

/**
 * This class tests the functionality of the class Board
 */
public class BoardTest {

    /**
     * Test init.
     */
    @Test
    public void testInit() {
        Game game = new Game();
        Board board = game.getBoard();
        assertEquals(42, board.getTerritories().size());
        assertEquals(6, board.getContinents().size());
    }

}
