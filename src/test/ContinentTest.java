package test;

import model.Continent;
import model.Game;
import model.Player;
import model.Territory;
import model.enums.ArmyColor;
import model.enums.ContinentName;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the functionality of the class Player
 */
public class ContinentTest {

    @Test
    public void testInit() {
        Continent.init();
        assertEquals(6, Continent.territories.size());
        assertEquals(6, Continent.values.length);
    }

    @Test
    public void testConstructorGetterSetter() {
        Game g = new Game();
        Continent europe = new Continent(ContinentName.EUROPE, new ArrayList<Territory>(), 0);
        assertEquals(ContinentName.EUROPE, europe.getName());
        assertNull(europe.getOwner());
        assertEquals(0, europe.getCountries().size());
        assertEquals(0, europe.getValue());
        assertFalse(europe.isOccupied());
    }

    @Test
    public void testIsOccupied() {
        Game g = new Game();
        ArrayList<Territory> asiaTerritories = g.getBoard().getContinents().get(3).getTerritories();
        Continent asia = new Continent(ContinentName.ASIA, asiaTerritories, 5);
        assertFalse(asia.isOccupied());
        assertEquals(5, asia.getValue());

        Player p1 = new Player(ArmyColor.BLUE, "bob");
        for(Territory t : asiaTerritories) t.setOwner(p1);
        asiaTerritories.get(0).setOwner(null);
        assertFalse(asia.isOccupied());
        asiaTerritories.get(0).setOwner(p1);
        assertTrue(asia.isOccupied());
        assertEquals(p1, asia.getOwner());
    }

}
