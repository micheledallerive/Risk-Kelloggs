package test;
import model.Player;
import model.enums.ArmyColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the functionality of the class Player
 */
public class PlayerTest {

    @Test
    public void testConstructor() {
        Player player1  = new Player(ArmyColor.BLACK);
        Player player2 = new Player(ArmyColor.RED);
        assertEquals(player1.getArmies().size(), 0);
        assertEquals(player2.getTerritories().size(), 0);
    }

}
