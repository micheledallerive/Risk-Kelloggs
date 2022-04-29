package test;

import model.Dice;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the functionality of the class Dice
 */
public class DiceTest {
    @Test
    public void initTest() {
        Dice.init();
        ArrayList<Dice> dice = Dice.getDices();
        int red = 0, blue = 0;
        for (Dice die : dices) {
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


}
