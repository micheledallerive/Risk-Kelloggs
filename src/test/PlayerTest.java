package test;

import model.AI;
import model.Card;
import model.Game;
import model.Player;
import model.enums.ArmyColor;

import static org.junit.Assert.*;

import model.enums.CardType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * This class tests the functionality of the class Player
 */
public class PlayerTest {

    @Test
    public void testConstructor() {
        Player player1  = new Player(ArmyColor.BLACK, "bob");
        Player player2 = new Player(ArmyColor.RED, "john");
        assertEquals(player1.getArmies().size(), 0);
        //assertEquals(player2.getTerritories().size(), 0);
        assertEquals(0, player1.getCards().size());
        Card c = new Card(CardType.ARTILLERY, null);
        player1.addCard(c);
        assertEquals(c, player1.getCards().get(0));
        assertEquals(1, player1.getCards().size());
        assertFalse(player1.isAI());
    }

    @Test
    public void testPlayerCards() {
        Game game = new Game();
        Player player = new Player(ArmyColor.RED, "eskere");
        int prePickSize = player.getCards().size();
        player.pickCard(game);
        assertNotEquals(prePickSize, player.getCards().size());
        assertEquals(1, player.getCards().size());
        player.getCardCombinations();
    }

}
