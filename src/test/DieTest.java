package test;

import model.Die;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the functionality of the class Dice
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

    }
}
