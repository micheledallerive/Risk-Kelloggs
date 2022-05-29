package test;

import model.Continent;
import model.Game;
import model.Player;
import model.Territory;
import model.enums.ArmyColor;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the functionality of the class Player
 */
public class ContinentTest {
    /*@Test
    public void testInit() {
        Continent.init();
        assertEquals(6, Continent.TERRITORIES.size());
        assertEquals(6, Continent.BONUS_VALUES.length);
    }*/

    @Test
    public void testConstructorGetterSetter() {
        new Game();
        Continent europe = new Continent("EUROPE", new ArrayList<Territory>(), 0);
        assertEquals("EUROPE", europe.getName());
        assertNull(europe.getOwner());
        assertEquals(0, europe.getTerritories().size());
        assertEquals(0, europe.getValue());
        assertFalse(europe.isOccupied());
    }

    @Test
    public void testIsOccupied() {
        Game g = new Game();
        ArrayList<Territory> asiaTerritories = g.getBoard().getContinents().get(3).getTerritories();
        Continent asia = new Continent("ASIA", asiaTerritories, 5);
        assertFalse(asia.isOccupied());
        assertEquals(5, asia.getValue());

        Player p1 = new Player(ArmyColor.BLUE, "bob");
        for(Territory t : asiaTerritories) {
            t.setOwner(p1);
        }
        asiaTerritories.get(0).setOwner(null);
        assertFalse(asia.isOccupied());
        asiaTerritories.get(0).setOwner(p1);
        assertTrue(asia.isOccupied());
        assertEquals(p1, asia.getOwner());
    }

    @Test
    public void testString() {
        Game game = new Game();

        Continent europe = game.getBoard().getContinents().get(2);
        assertEquals("Europe", europe.toString());
        Continent africa = game.getBoard().getContinents().get(4);
        assertEquals("Africa", africa.toString());
        Continent asia = game.getBoard().getContinents().get(3);
        assertEquals("Asia", asia.toString());
        Continent northAmerica = game.getBoard().getContinents().get(0);
        assertEquals("North America", northAmerica.toString());
        Continent southAmerica = game.getBoard().getContinents().get(1);
        assertEquals("South America", southAmerica.toString());
        Continent australia = game.getBoard().getContinents().get(5);
        assertEquals("Australia", australia.toString());
    }

}
