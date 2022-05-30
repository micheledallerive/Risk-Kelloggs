package tui.command;

import static tui.Utils.askTerritory;
import static tui.Utils.consolePause;
import static tui.Utils.print;

import model.Board;
import model.Game;
import model.Player;
import model.Territory;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The attack command.
 *
 * @author dallem @usi.ch
 */
public class AttackCommand extends Command {

    /**
     * The default constructor.
     *
     * @param name     the name of the command.
     * @param game     the game.
     * @param input    the input scanner.
     * @param numInput the number input scanner.
     */
    public AttackCommand(String name, Game game, Scanner input, Scanner numInput) {
        super(name, game, input, numInput);
    }

    /**
     * Executes the command.
     *
     * @return if the player turn is over.
     */
    @Override
    public boolean execute() {
        Board board = game.getBoard();
        ArrayList<Territory> territories = board.getTerritories();

        Player player = game.getPlayers().get(game.getTurn());
        String fromTerritoryStr = askTerritory(
            "What territory do you want to attack from?",
            input,
            (tn) -> player.getTerritories().stream().anyMatch(t -> t.getName().equals(tn)),
            board);
        Territory fromTerritory = territories.get(board.getTerritoryIdx(fromTerritoryStr));

        if (fromTerritory.getName() != null) {
            ArrayList<ArrayList<Integer>> adjacency = board.getAdjacency();

            String toAttack = askTerritory(
                "Which territory do you want to attack?",
                input,
                (tn) -> adjacency.get(board.getTerritoryIdx(fromTerritory.getName()))
                    .stream().anyMatch(t -> t == board.getTerritoryIdx(tn))
                    && territories.get(board.getAdjacency().get(board.getTerritoryIdx(fromTerritory.getName()))
                    .stream().filter(t -> t == board.getTerritoryIdx(tn))
                    .findFirst().get()).getOwner() != player,
                board
            );
            Territory attackedTerritory = territories.get(board.getTerritoryIdx(toAttack));
            Player attackedPlayer = attackedTerritory.getOwner();
            boolean canAttack = false;
            canAttack = fromTerritory.getArmiesCount() > 1;
            if (canAttack && attackedTerritory.getOwner() != player) {
                int attackerMaxArmies = Math.min(fromTerritory.getArmiesCount() - 1, 3);
                print("How many armies do you want to use to attack (1 - "
                    + attackerMaxArmies + ")");
                int attackerArmies = numInput.nextInt();
                attackerArmies = Math.min(attackerArmies, attackerMaxArmies);
                int defenderArmies = attackedTerritory.getArmiesCount();
                int[] losses = player.getAttackOutcome(fromTerritory, attackedTerritory, attackerArmies);
                print(player.getName() + " lost " + losses[0] + " armies");
                print(attackedPlayer
                    + " lost " + losses[1] + " armies");
                if (losses[1] == defenderArmies) {
                    print("You conquered " + attackedTerritory.getName().toString() + "!");
                }
            } else {
                print("You can't attack that territory...");
            }
        }

        consolePause(input);
        return false;
    }
}
