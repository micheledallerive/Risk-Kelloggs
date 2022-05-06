package test;

import model.*;
import model.enums.ArmyColor;
import model.enums.GameStatus;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the class Game
 */
public class GameTest {

    @Test
    public void testConstructorGetterSetter() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(ArmyColor.BLACK, "bob");
        Player p2 = new Player(ArmyColor.RED, "bob");
        players.add(p1);
        players.add(p2);
        Game game = new Game(players);
        assertEquals(game.getPlayers(), players);
        game.addPlayer(new Player(ArmyColor.GREEN, "bob"));
        assertEquals(3, game.getPlayers().size());
        assertEquals(0, game.getTurn());
        assertFalse(game.isWorldConquered());
        assertEquals(GameStatus.MENU, game.getStatus());
    }

    @Test
    public void testCards() {
        Game game = new Game();
        assertNotNull(game.getRandomCard());
        for (int i = 0; i < 44; i++) { // 42 territory card + 2 wild cards
            assertNotNull(game.getRandomCard());
        }
        assertNull(game.getRandomCard());
    }

    @Test
    public void testWorldConquered() {
        Game game = new Game(new Board(), new ArrayList<Player>());
        ArrayList<Continent> continents = game.getBoard().getContinents();
        Player p1 = new Player(ArmyColor.RED, "bob");
        Player p2 = new Player(ArmyColor.BLUE, "chiara");
        for (Continent continent : continents) {
            for (Territory territory : continent.getTerritories()) {
                territory.setOwner(p1);
            }
        }
        continents.get(0).getTerritories().get(0).setOwner(p2);
        assertFalse(game.isWorldConquered());
        continents.get(0).getTerritories().get(0).setOwner(p1);
        assertTrue(game.isWorldConquered());
    }

}
