package tui.command;

import static tui.Utils.askTerritory;
import static tui.Utils.consolePause;
import static tui.Utils.print;

import model.Game;
import model.Player;
import model.Territory;

import java.util.Scanner;

/**
 * The attack command.
 * @author dallem@usi.ch
 */
public class AttackCommand extends Command {

    /**
     * The default constructor.
     * @param name the name of the command.
     * @param game the game.
     * @param input the input scanner.
     * @param numInput the number input scanner.
     */
    public AttackCommand(String name, Game game, Scanner input, Scanner numInput) {
        super(name, game, input, numInput);
    }

    /**
     * Executes the command.
     * @return if the player turn is over.
     */
    @Override
    public boolean execute() {
        Player player = game.getPlayers().get(game.getTurn());
        Territory fromTerritory = game.getBoard().getTerritories().get(
            askTerritory(
                "What territory do you want to attack from?",
                input,
                (tn) -> player.getTerritories().stream().anyMatch(t -> t.getName() == tn)).ordinal()
        );

        if (fromTerritory.getName() != Territory.TerritoryName.NONE) {

            Territory.TerritoryName toAttack = askTerritory(
                "Which territory do you want to attack?",
                input,
                (tn) -> fromTerritory.getAdjacent().stream().anyMatch(t -> t.getName() == tn)
                    && fromTerritory.getAdjacent().stream().filter(t -> t.getName() == tn)
                        .findFirst().get().getOwner() != player
            );
            Territory attackedTerritory = game.getBoard().getTerritories().get(toAttack.ordinal());
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
                int[] losses = player.attack(fromTerritory, attackedTerritory, attackerArmies);
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
