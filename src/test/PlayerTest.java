package test;

import model.*;
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
        player.addCard(new Card(CardType.ARTILLERY, null));
        player.addCard(new Card(CardType.ARTILLERY, null));
        player.addCard(new Card(CardType.ARTILLERY, null));
        assertEquals(1, player.getCardCombinations().size());
    }

    @Test
    public void testAttack() {
        Game game = new Game();
        Player bob = new Player(ArmyColor.BLACK, "bob");
        Player alice = new Player(ArmyColor.RED, "red");
        game.addPlayer(bob);
        game.addPlayer(alice);
        game.initArmies();
        Territory from = game.getBoard().getTerritories().get(0);
        Territory to = game.getBoard().getTerritories().get(1);

        bob.placeArmies(from, 15);
        alice.placeArmies(to, 4);

        Integer[] result = bob.attack(from, to, 2, 1);
        assertEquals(2, result.length);
        assertTrue(result[0] >= 0 && result[0] < 2);
        assertTrue(result[1] >= 0 && result[1] < 2);

        Integer[] result2 = bob.attack(from, to, 2);
        assertEquals(2, result2.length);
        assertTrue(result2[0] >= 0 && result2[0] < 4);
        assertTrue(result2[1] >= 0 && result2[1] < 4);

        int count = 10;
        while (to.getArmiesCount() > 0 && count > 0) {
            bob.attack(from, to, 3);
            count--;
        }
    }

    @Test
    public void testPlaceArmies() {
        Game game = new Game();
        Player bob = new Player(ArmyColor.BLACK, "bob");
        game.addPlayer(bob);
        game.initArmies();
        Territory territory = game.getBoard().getTerritories().get(0);

        System.out.println("Armies: " + bob.getArmies().size());
        bob.placeArmies(territory, 5);
        assertEquals(5, territory.getArmiesCount());
        assertEquals(bob, territory.getOwner());
        assertEquals(bob.getArmies().size() - 5,
                bob.getFreeArmies().size());

        bob.placeArmies(territory, bob.getFreeArmies().size());
        assertEquals(0, bob.getFreeArmies().size());

        bob.placeArmies(territory, -50);
    }

}
