package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import model.Army;
import model.Territory;
import model.enums.ArmyColor;
import model.enums.ArmyType;

import java.util.ArrayList;

import org.junit.Test;

/**
 * This class tests the functionality of the class Army
 */
public class ArmyTest {

    /**
     * Test constructor.
     */
    @Test
    public void testConstructor() {
        Army army1 = new Army();
        assertEquals(ArmyType.INFANTRY, army1.getType());
        assertEquals(ArmyColor.BLACK, army1.getColor());
        assertNull(army1.getTerritory());

        Territory territory = new Territory("ALASKA");
        Army army2 = new Army(ArmyType.INFANTRY, ArmyColor.BLUE, territory);
        assertEquals(ArmyType.INFANTRY, army2.getType());
        assertEquals(ArmyColor.BLUE, army2.getColor());
        assertEquals(territory, army2.getTerritory());
        assertEquals("ALASKA", army2.getTerritory().getName());
    }

    /**
     * Test move to.
     */
    @Test
    public void testMoveTo() {
        Territory t1 = new Territory("ALASKA");
        Territory t2 = new Territory("ALBERTA");
        Army army1 = new Army(ArmyType.INFANTRY, ArmyColor.RED, t1);
        assertEquals(t1, army1.getTerritory());
        army1.setTerritory(t2);
        assertEquals(t2, army1.getTerritory());
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        Army army = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, null);
        Army army2 = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, new Territory("ALASKA"));
        assertTrue(army.toString().contains("no territory"));
        assertTrue(army2.toString().toLowerCase().contains("alaska"));
    }

    /**
     * Test get value.
     */
    @Test
    public void testGetValue() {
        Army army = new Army(ArmyType.INFANTRY, ArmyColor.BLACK, null);
        assertEquals(1, army.calculateValue());
    }

    /**
     * Test array value.
     */
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
