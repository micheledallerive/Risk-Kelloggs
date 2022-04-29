package test;

import model.Game;
import model.Player;
import model.enums.ArmyColor;
import model.enums.GameStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the class Game
 */
public class GameTest {

    @Test
    public void testConstructorGetterSetter() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(ArmyColor.BLACK);
        Player p2 = new Player(ArmyColor.RED);
        players.add(p1);
        players.add(p2);
        Game game = new Game(players);
        assertEquals(game.getPlayers(), players);
        game.addPlayer(new Player(ArmyColor.GREEN));
        assertEquals(3, game.getPlayers().size());
        assertEquals(0, game.getTurn());
        assertFalse(game.isWorldConquered());
        assertEquals(GameStatus.MENU, game.getStatus());
    }

    @Test
    public void testCards() {
        Game game = new Game();
        assertNotNull(game.getRandomCard());
        for (int i = 0; i < 42; i++) {
            assertNotNull(game.getRandomCard());
        }
        assertNull(game.getRandomCard());
    }

}
