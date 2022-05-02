package test;

import model.Board;
import model.Game;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the functionality of the class Board
 */
public class BoardTest {

    @Test
    public void testInit() {
        Game game = new Game();
        Board board = game.getBoard();
        assertEquals(42, board.getTerritories().size());
        assertEquals(6, board.getContinents().size());
    }

}
