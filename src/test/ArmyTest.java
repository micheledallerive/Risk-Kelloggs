package test;

import model.Army;
import model.Territory;
import model.enums.ArmyColor;
import model.enums.ArmyType;
import model.Territory.TerritoryName;
import org.junit.Test;

import java.util.ArrayList;

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
        Army army2 = new Army(ArmyType.INFANTRY, ArmyColor.BLUE, territory);
        assertEquals(ArmyType.INFANTRY, army2.getType());
        assertEquals(ArmyColor.BLUE, army2.getColor());
        assertEquals(territory, army2.getTerritory());
        assertEquals(TerritoryName.ALASKA, army2.getTerritory().getName());
    }

    @Test
    public void testMoveTo() {
        Territory t1 = new Territory(TerritoryName.ALASKA);
        Territory t2 = new Territory(TerritoryName.ALBERTA);
        Army army1 = new Army(ArmyType.INFANTRY, ArmyColor.RED, t1);
        assertEquals(t1, army1.getTerritory());
        army1.setTerritory(t2);
        assertEquals(t2, army1.getTerritory());
    }

    @Test
    public void testToString() {
        Army army = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, null);
        Army army2 = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, new Territory(TerritoryName.ALASKA));
        assertTrue(army.toString().contains("no territory"));
        assertTrue(army2.toString().toLowerCase().contains("alaska"));
    }

    @Test
    public void testGetValue() {
        Army army = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, null);
        assertEquals(1, army.calculateValue());
    }

    @Test
    public void testArrayValue() {
        Army army = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, null);
        Army army2 = new Army(ArmyType.CAVALRY, ArmyColor.BLACK, null);
        ArrayList<Army> armies = new ArrayList<>();
        armies.add(army);
        armies.add(army2);
        assertEquals(6, Army.getValue(armies));
    }
}
