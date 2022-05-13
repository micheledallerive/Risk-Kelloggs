package test;

import model.Player;
import model.Territory;
import model.enums.ArmyColor;
import model.Territory.TerritoryName;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * This class tests the functionality of the class Territory
 */
public class TerritoryTest {

    @Test
    public void testOccupied() {
        Player player = new Player(ArmyColor.RED, "bob");
        Territory territory = new Territory(TerritoryName.ALASKA);
        assertFalse(territory.isOccupied());
        territory.setOwner(player);
        assertTrue(territory.isOccupied());
    }

    @Test
    public void testOwner() {
        Player player = new Player(ArmyColor.RED, "bob");
        Territory territory = new Territory(TerritoryName.ALASKA);
        assertFalse(territory.isOccupied());
        territory.setOwner(player);
        assertEquals(territory.getOwner(), player);
        assertTrue(territory.isOccupied());
    }

    @Test
    public void testInit() {
        Territory.init();
        assertEquals(42, Territory.adjacency.size());
    }

    @Test
    public void testGetters() {
        Territory territory = new Territory(TerritoryName.ALASKA);
        assertEquals(0, territory.getArmies().size());
        assertEquals(territory.getArmies().size(), territory.getArmiesCount());
        assertEquals(0, territory.getAdjacent().size());
    }

}
