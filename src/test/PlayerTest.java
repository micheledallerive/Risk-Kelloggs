package test;

import model.*;
import model.enums.ArmyColor;

import static org.junit.Assert.*;

import model.enums.ArmyType;
import model.enums.CardType;
import org.junit.Test;

import java.util.ArrayList;

/**
 * This class tests the functionality of the class Player
 */
public class PlayerTest {

    @Test
    public void testConstructor() {
        Player player1  = new Player(ArmyColor.BLACK, "bob");
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
        player.addCard(new Card(CardType.INFANTRY, null));
        assertEquals(1, player.getCardCombinations().size());

        int oldCardsCount = player.getCards().size();
        player.pickCard(game);
        assertEquals(oldCardsCount + 1, player.getCards().size());
    }

    @Test
    public void testPlayerArmies() {
        Game game = new Game();

        Player player = new Player(ArmyColor.RED, "eskere");
        Player test1 = new Player(ArmyColor.BLUE, "test1");
        Player test2 = new Player(ArmyColor.BLACK, "test2");
        Player test3 = new Player(ArmyColor.GREEN, "test3");

        game.addPlayer(player);
        game.addPlayer(test1);
        game.addPlayer(test2);
        game.addPlayer(test3);

        game.initArmies();

        player.addCard(new Card(CardType.ARTILLERY, null));
        player.addCard(new Card(CardType.ARTILLERY, null));
        player.addCard(new Card(CardType.ARTILLERY, null));

        player.placeArmies(game.getBoard().getTerritories().get(0), 5);

        assertEquals(1, player.getCardCombinations().size());
        Card[] cards = player.getCardCombinations().get(0);
        assertEquals(3, cards.length);
        assertEquals(CardType.ARTILLERY, cards[0].getType());

        int armiesGained = player.playCardsCombination(cards);
        assertEquals(4, armiesGained);

        // test all the combinations
        assertEquals(12, player.playCardsCombination(new Card[]{ // 10 + 2
                new Card(CardType.ARTILLERY, game.getBoard().getTerritories().get(0).getName()),
                new Card(CardType.INFANTRY, null),
                new Card(CardType.CAVALRY, null)}));
        assertEquals(14, player.playCardsCombination(new Card[]{
                new Card(CardType.ARTILLERY, game.getBoard().getTerritories().get(0).getName()),
                new Card(CardType.INFANTRY, game.getBoard().getTerritories().get(0).getName()),
                new Card(CardType.WILD, null)}));

        game.giveArmiesToPlayer(player, armiesGained);
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

        int[] results = bob.attack(from, to, 3, 3);
        assertTrue(results[0] >= 0 && results[0] <= 3);
        assertTrue(results[1] >= 0 && results[1] <= 3);

        int[] results2 = bob.attack(from, to, 3);
        assertTrue(results2[0] >= 0 && results2[0] <= 3);
        assertTrue(results2[1] >= 0 && results2[1] <= 3);
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
        bob.moveArmies((byte)5, territory, other);
        assertEquals(5, territory.getArmiesCount());
        assertEquals(15, other.getArmiesCount());
        bob.removeArmy(territory.getArmies().get(0));

        // test territories
        assertEquals(2, bob.getTerritories().size());
        //assertEquals(territory, bob.getTerritories().get(0)); // redo this test, ISN'T CONSISTENT.
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

    @Test
    public void testContinents() {
        Game game = new Game();
        Player p1 = new Player(ArmyColor.BLACK, "bob");
        Player p2 = new Player(ArmyColor.RED, "red");
        Player p3 = new Player(ArmyColor.BLUE, "jane");
        Player p4 = new Player(ArmyColor.GREEN, "john");

        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);

        game.initArmies();

        for(Territory t : game.getBoard().getContinents().get(0).getTerritories()) {
            t.setOwner(p1);
        }
        assertEquals(1, p1.getContinents(game).size());
        assertEquals(game.getBoard().getContinents().get(0), p1.getContinents(game).get(0));
    }
}
