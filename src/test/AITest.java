package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.AI;
import model.Game;
import model.Player;
import model.Territory;
import model.callback.Callback;
import model.enums.ArmyColor;

import org.junit.Test;

/**
 * The type Ai test.
 */
public class AITest {

    /**
     * Test constructor.
     */
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

    /**
     * Test attack.
     */
    @Test
    public void testAttack() {
        AI blue = new AI(ArmyColor.BLUE);
        AI red = new AI(ArmyColor.RED);
        AI black = new AI(ArmyColor.BLACK);
        Player player = new Player(ArmyColor.GREEN, "Player");
        Game game = new Game();
        game.addPlayer(blue);
        game.addPlayer(red);
        game.addPlayer(black);
        game.addPlayer(player);

        game.initArmies();

        blue.placeArmies(game.getBoard().getTerritories().get(0), 10);
        blue.placeArmies(game.getBoard().getTerritories().get(10), 1);
        player.placeArmies(game.getBoard().getTerritories().get(game.getBoard().getAdjacency().get(0).get(0)), 10);

        // foreach territory adjacent to the first territory in list
        for (final int index : game.getBoard().getAdjacency().get(0)) {
            Territory territory = game.getBoard().getTerritories().get(index);
            if (territory.getOwner() == null) {
                red.placeArmies(territory, 2);
                //System.out.println(territory.getName().toString() + " : " + territory.getOwner());
            }
        }

        final boolean[] playerAttacked = {false};
        while (!playerAttacked[0] && game.getBoard().getTerritories().get(0).getOwner() == blue) {
            blue.attack(game.getBoard(), new Callback() {
                @Override
                public void onPlayerAttacked(Player attacker, Player attacked, Territory fromTerritory,
                                             Territory attackedTerritory) {
                    assertEquals(attacker, blue);
                    assertEquals(attacked, player);
                    assertEquals(fromTerritory.getOwner(), blue);
                    assertEquals(attackedTerritory.getOwner(), player);
                    playerAttacked[0] = true;
                }

                @Override
                public void onAIAttacked(Player attacker, Player attacked, Territory fromTerritory,
                                         Territory attackedTerritory) {
                    if (fromTerritory.getOwner() != blue || attackedTerritory.getOwner() == null) {
                        return;
                    }
                    assertEquals(attacker, blue);
                    assertTrue(attacked.isAI());
                    assertEquals(fromTerritory, game.getBoard().getTerritories().get(0));
                    assertEquals(fromTerritory.getOwner(), blue);
                    assertEquals(attackedTerritory.getOwner(), attacked);
                }
            });
        }
    }

    /**
     * Test place army.
     */
    @Test
    public void testPlaceArmy() {
        AI blue = new AI(ArmyColor.BLUE);
        AI red = new AI(ArmyColor.RED);
        AI black = new AI(ArmyColor.BLACK);
        Player player = new Player(ArmyColor.GREEN, "Player");
        Game game = new Game();
        game.addPlayer(blue);
        game.addPlayer(red);
        game.addPlayer(black);
        game.addPlayer(player);

        game.initArmies();

        blue.placeArmy(game, true);
        assertEquals(1, blue.getArmies().size() - blue.getFreeArmies().size());
        assertEquals(1, blue.getTerritories().size());

        for (Territory territory : game.getBoard().getTerritories()) {
            if (territory.getOwner() == null) {
                red.placeArmies(territory, 1);
            }
        }

        blue.placeArmy(game, false);
        assertTrue(blue.getArmies().size() - blue.getFreeArmies().size() > 1);
        assertTrue(blue.getTerritories().size() > 1 || blue.getTerritories().get(0).getArmiesCount() > 1);

        blue.placeArmies(blue.getTerritories().get(0), blue.getFreeArmies().size());
        assertEquals(0, blue.getFreeArmies().size());

        blue.placeArmy(game, false);
    }

}
