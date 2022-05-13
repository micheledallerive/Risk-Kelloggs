package test;

import model.*;
import model.callback.GameCallback;
import model.enums.ArmyColor;
import model.enums.CardType;
import model.enums.GameStatus;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the functionality of the class Game
 */
public class GameTest {

    @Test
    public void testConstructorGetterSetter() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player(ArmyColor.BLACK, "bob");
        Player p2 = new Player(ArmyColor.RED, "bob");
        players.add(p1);
        players.add(p2);
        Game game = new Game(players);
        assertEquals(game.getPlayers(), players);
        game.addPlayer(new Player(ArmyColor.GREEN, "bob"));
        assertEquals(3, game.getPlayers().size());
        assertEquals(0, game.getTurn());
        assertFalse(game.isWorldConquered());
        assertEquals(GameStatus.MENU, game.getStatus());
    }

    @Test
    public void testInitArmies() {
        Game game = new Game();
        Player p1 = new Player(ArmyColor.RED, "bob");
        Player p2 = new Player(ArmyColor.BLUE, "chiara");
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.initArmies();
        assertTrue(p1.getArmies().isEmpty());
    }

    @Test
    public void testGiveBonus() {
        Territory.init();
        Continent.init();
        Game game = new Game();
        Player p1 = new Player(ArmyColor.RED, "bob");
        Player p2 = new Player(ArmyColor.BLUE, "chiara");
        Player p3 = new Player(ArmyColor.GREEN, "joe");
        Player p4 = new Player(ArmyColor.YELLOW, "sara");
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);

        game.initArmies();

        for (Territory territory : game.getBoard().getContinents().get(1).getTerritories()) {
            p1.placeArmies(territory, 1);
        }
        for (Territory territory : game.getBoard().getContinents().get(0).getTerritories()) {
            p2.placeArmies(territory, 1);
        }

        int[] p1Bonus = game.giveBonus(p1, -1);
        assertEquals(
                game.getBoard().getContinents().get(1).getTerritories().size() / 3,
                p1Bonus[0]);

        assertEquals(game.getBoard().getContinents().get(1).getValue(), p1Bonus[1]);

        assertEquals(0, p1Bonus[2]);

        p1.addCard(new Card(CardType.WILD, null));
        p1.addCard(new Card(CardType.ARTILLERY, null));
        p1.addCard(new Card(CardType.INFANTRY, null));

        p1Bonus = game.giveBonus(p1, 0);
        assertEquals(12, p1Bonus[2]);

    }

    @Test
    public void testCards() {
        Game game = new Game();
        assertNotNull(game.getRandomCard());
        for (int i = 0; i < 44; i++) { // 42 territory card + 2 wild cards
            assertNotNull(game.getRandomCard());
        }
        assertNull(game.getRandomCard());
    }

    @Test
    public void testWorldConquered() {
        Game game = new Game(new Board(), new ArrayList<Player>());
        ArrayList<Continent> continents = game.getBoard().getContinents();
        Player p1 = new Player(ArmyColor.RED, "bob");
        Player p2 = new Player(ArmyColor.BLUE, "chiara");
        for (Continent continent : continents) {
            for (Territory territory : continent.getTerritories()) {
                territory.setOwner(p1);
            }
        }
        continents.get(0).getTerritories().get(0).setOwner(p2);
        assertFalse(game.isWorldConquered());
        continents.get(0).getTerritories().get(0).setOwner(p1);
        assertTrue(game.isWorldConquered());
    }

    @Test
    public void testPlay() {
        Territory.init();
        Game game = new Game();
        int[] time = new int[]{0};
        game.play(new GameCallback() {
            @Override
            public boolean onMainMenu() {
                assertEquals(game.getStatus(), GameStatus.MENU);
                time[0]++;
                return time[0] == 2;
            }

            @Override
            public boolean onGameSetup() {
                assertEquals(game.getStatus(), GameStatus.SETUP);
                return true;
            }

            @Override
            public boolean onGamePlay() {
                assertEquals(game.getStatus(), GameStatus.PLAYING);
                return true;
            }

            @Override
            public boolean onGamePause() {
                assertEquals(game.getStatus(), GameStatus.PAUSE);
                return true;
            }

            @Override
            public boolean onGameEnd() {
                assertEquals(game.getStatus(), GameStatus.END);
                return true;
            }

            @Override
            public void onGameExit() {
                assertEquals(game.getStatus(), GameStatus.EXIT);
            }
        });
    }

    @Test
    public void testNextSetTurn() {
        Game game = new Game();
        Player p1 = new Player(ArmyColor.RED, "bob");
        Player p2 = new Player(ArmyColor.BLUE, "chiara");
        Player p3 = new Player(ArmyColor.GREEN, "pippo");
        Player p4 = new Player(ArmyColor.YELLOW, "paperino");
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.addPlayer(p4);

        assertEquals(0, game.getTurn());
        game.nextTurn();
        assertEquals(1, game.getTurn());
        game.nextTurn();
        assertEquals(2, game.getTurn());

        game.setTurn(3);
        assertEquals(3, game.getTurn());
    }

    @Test
    public void testSetStatus() {
        Game game = new Game();
        assertEquals(game.getStatus(), GameStatus.MENU);
        game.setStatus(GameStatus.SETUP);
        assertEquals(game.getStatus(), GameStatus.SETUP);
        game.nextStatus();
        assertEquals(game.getStatus(), GameStatus.PLAYING);
    }



}
