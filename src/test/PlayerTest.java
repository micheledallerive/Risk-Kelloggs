package test;

import model.*;
import model.enums.ArmyColor;

import static org.junit.Assert.*;

import model.enums.ArmyType;
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

        int oldCardsCount = player.getCards().size();
        player.pickCard(game);
        assertEquals(oldCardsCount + 1, player.getCards().size());
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
        Player alice = new Player(ArmyColor.RED, "red");
        Player jane = new Player(ArmyColor.BLUE, "jane");
        Player john = new Player(ArmyColor.GREEN, "john");

        game.addPlayer(bob);
        game.addPlayer(alice);
        game.addPlayer(jane);
        game.addPlayer(john);

        game.initArmies();
        Territory territory = game.getBoard().getTerritories().get(0);

        bob.placeArmies(territory, 5);
        assertEquals(5, territory.getArmiesCount());
        assertEquals(bob, territory.getOwner());
        assertEquals(bob.getArmies().size() - 5,
                bob.getFreeArmies().size());

        bob.placeArmies(territory, bob.getFreeArmies().size());
        assertEquals(0, bob.getFreeArmies().size());

        bob.placeArmies(territory, -50);
    }

    @Test
    public void testRemoveArmies() {
        Game game = new Game();
        Player bob = new Player(ArmyColor.BLACK, "bob");
        Player alice = new Player(ArmyColor.RED, "red");
        Player jane = new Player(ArmyColor.BLUE, "jane");
        Player john = new Player(ArmyColor.GREEN, "john");

        game.addPlayer(bob);
        game.addPlayer(alice);
        game.addPlayer(jane);
        game.addPlayer(john);

        game.initArmies();
        Territory territory = game.getBoard().getTerritories().get(0);
        Territory other = game.getBoard().getTerritories().get(1);

        bob.addArmy(new Army(ArmyType.INFANTRY, bob.getColor(), other));

        bob.placeArmies(territory, 5);
        assertEquals(5, territory.getArmiesCount());
        assertEquals(bob, territory.getOwner());

        bob.removeArmies(territory, 2);
        assertEquals(3, territory.getArmiesCount());
        assertEquals(bob, territory.getOwner());

        assertEquals(bob.getArmies().size() - 4,
                bob.getFreeArmies().size());
        int oldArmiesSize = bob.getArmies().size();
        int oldFreeArmiesSize = bob.getFreeArmies().size();
        bob.addArmy(new Army(ArmyType.INFANTRY, bob.getColor(),null));
        assertEquals(bob.getFreeArmies().size(), oldFreeArmiesSize + 1);
        assertEquals(bob.getArmies().size(), oldArmiesSize + 1);
    }

    @Test
    public void testMoveArmies() {
        Game game = new Game();
        Player bob = new Player(ArmyColor.BLACK, "bob");
        Player alice = new Player(ArmyColor.RED, "red");
        Player jane = new Player(ArmyColor.BLUE, "jane");
        Player john = new Player(ArmyColor.GREEN, "john");

        game.addPlayer(bob);
        game.addPlayer(alice);
        game.addPlayer(jane);
        game.addPlayer(john);

        game.initArmies();

        Territory territory = game.getBoard().getTerritories().get(0);
        Territory other = game.getBoard().getTerritories().get(1);

        bob.placeArmies(territory, 10);
        bob.placeArmies(other, 10);
        bob.moveArmies(5, territory, other);
        assertEquals(5, territory.getArmiesCount());
        assertEquals(15, other.getArmiesCount());
        bob.removeArmy(territory.getArmies().get(0));

        // test territories
        assertEquals(2, bob.getTerritories().size());
        assertEquals(territory, bob.getTerritories().get(0)); // redo this test, ISN'T CONSISTENT.
    }

    @Test
    public void testGeneratePlayersRandomly() {
        Game game = new Game();
        ArrayList<Player> players =
                Player.generatePlayersRandomly(
                        (byte) 6, (byte) 1,
                        new String[] {"test"});
        for (Player player : players) {
            game.addPlayer(player);
        }
        assertEquals(6, game.getPlayers().size());
        assertEquals("test", game.getPlayers().get(0).getName());

        game = new Game();
        ArrayList<Player> players1 =
                Player.generatePlayersRandomly(
                        (byte) 6, (byte) 0,
                        new String[] {"test"});
        for (Player player : players1) {
            game.addPlayer(player);
        }
        assertEquals(6, game.getPlayers().size());
        assertTrue(game.getPlayers().get(0).isAI());

        game = new Game();
        ArrayList<Player> players2 =
                Player.generatePlayersRandomly((byte) 6);
        for (Player player : players2) {
            game.addPlayer(player);
        }
        assertEquals(6, game.getPlayers().size());
        assertFalse(game.getPlayers().get(0).isAI());
    }
}
