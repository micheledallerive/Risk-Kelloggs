package test;

import model.Army;
import model.Territory;
import model.enums.ArmyColor;
import model.enums.ArmyType;
import model.enums.TerritoryName;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the functionality of the class Army
 */
public class ArmyTest {

    @Test
    public void testConstructor() {
        Army army1 = new Army();
        assertEquals(ArmyType.INFANTRY, army1.getType());
        assertEquals(ArmyColor.BLACK, army1.getColor());
        assertNull(army1.getTerritory());

        Territory territory = new Territory(TerritoryName.ALASKA);
        Army army2 = new Army(ArmyType.ARTILLERY, ArmyColor.BLUE, territory);
        assertEquals(ArmyType.ARTILLERY, army2.getType());
        assertEquals(ArmyColor.BLUE, army2.getColor());
        assertEquals(territory, army2.getTerritory());
        assertEquals(TerritoryName.ALASKA, army2.getTerritory().getName());
    }

    @Test
    public void testMoveTo() {
        Territory t1 = new Territory(TerritoryName.ALASKA);
        Territory t2 = new Territory(TerritoryName.ALBERTA);
        Army army1 = new Army(ArmyType.ARTILLERY, ArmyColor.RED, t1);
        assertEquals(t1, army1.getTerritory());
        army1.moveTo(t2);
        assertEquals(t2, army1.getTerritory());
    }
}
