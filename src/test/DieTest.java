package test;

import model.Die;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the class Dice
 * @author moralj@usi.ch
 */
public class DieTest {
    @Test
    public void initTest() {
        Die.init();
        ArrayList<Die> dice = Die.getDice();
        int red = 0, blue = 0;
        for (Die die : dice) {
            switch (die.getColor()) {
                case RED:
                    red++;
                    break;
                case BLUE:
                    blue++;
                    break;
            }
        }
        assertEquals(3, red);
        assertEquals(3, blue);
    }

    @Test
    public void rollTest() {
        Die.init();
        Die.getDice().get(0).roll();
        Die.getDice().get(1).roll();
        Die.getDice().get(3).roll();
        Die.getDice().get(4).roll();
        Die.getDice().get(5).roll();
        assertEquals(2, Die.winner().size());
    }
}
