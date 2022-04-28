package test;

import model.Player;
import model.Territory;
import model.enums.ArmyColor;
import model.enums.TerritoryName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

}
