package test;

import static org.junit.Assert.assertEquals;

import model.Die;
import model.enums.DieColor;

import org.junit.Test;

/**
 * This class tests the functionality of the class Dice
 *
 * @author moralj @usi.ch
 */
public class DieTest {
    /**
     * Init test.
     */
    @Test
    public void initTest() {
        Die.init();
        int red = Die.getRedDice().size();
        int blue = Die.getBlueDice().size();
        assertEquals(DieColor.RED, Die.getRedDice().get(0).getColor());
        assertEquals(3, red);
        assertEquals(3, blue);
    }

    /**
     * Roll test.
     */
    @Test
    public void rollTest() {
        Die.init();
        Die.getBlueDice().get(0).roll();
        Die.getBlueDice().get(1).roll();
        Die.getRedDice().get(0).roll();
        Die.getRedDice().get(1).roll();
        Die.getRedDice().get(2).roll();
        assertEquals(2, Die.winner().size());
        for (Die die : Die.getRedDice()) {
            assertEquals(-1, die.getNumber());
        }

        Die.getBlueDice().get(0).roll();
        Die.getBlueDice().get(1).roll();
        Die.getBlueDice().get(2).roll();
        Die.getRedDice().get(0).roll();
        assertEquals(1, Die.winner().size());
    }
}
