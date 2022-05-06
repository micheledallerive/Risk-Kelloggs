package test;

import model.AI;
import model.enums.ArmyColor;
import org.junit.Test;

import static org.junit.Assert.*;

public class AITest {

    @Test
    public void testConstructor() {
        AI ai = new AI();
        assertEquals(ArmyColor.BLACK, ai.getColor());
        assertTrue(ai.isAI());

        AI ai2 = new AI(ArmyColor.RED);
        assertEquals(ArmyColor.RED, ai2.getColor());
        assertTrue(ai2.isAI());
        //ai2.calculateNextMove();
    }

}
