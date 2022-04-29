package test;

import model.Player;
import model.Territory;
import model.enums.ArmyColor;
import model.enums.TerritoryName;

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
        Player player = new Player(ArmyColor.RED);
        Territory territory = new Territory(TerritoryName.ALASKA);
        assertFalse(territory.isOccupied());
        territory.setOwner(player);
        assertTrue(territory.isOccupied());
    }

    @Test
    public void testOwner() {
        Player player = new Player(ArmyColor.RED);
        Territory territory = new Territory(TerritoryName.ALASKA);
        assertFalse(territory.isOccupied());
        territory.setOwner(player);
        assertEquals(territory.getOwner(), player);
        assertTrue(territory.isOccupied());
    }

    /*
    @Test
    public void testLoadFromFile() {
        try{
            //Territory.init();
            assertEquals(42, Territory.adjacency.size());
            assertEquals(TerritoryName.NORTH_WEST_TERRITORY, Territory.adjacency.get(0).get(0));
        } catch(Exception e) {
            assertEquals(Territory.adjacency.size(), 0);
            e.printStackTrace();
        }
    } */

}
