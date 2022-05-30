package tui.command;

import static tui.Utils.print;

import model.Game;
import model.Player;
import model.Territory;

import java.util.Scanner;

/**
 * Move armies command.
 *
 * @author dallem @usi.ch
 */
public class MoveArmiesCommand extends Command {

    /**
     * Default constructor.
     *
     * @param name     Command name.
     * @param game     Game instance.
     * @param input    Scanner instance.
     * @param numInput Number scanner instance.
     */
    public MoveArmiesCommand(String name, Game game, Scanner input, Scanner numInput) {
        super(name, game, input, numInput);
    }

    /**
     * Execute the command.
     *
     * @return if the player turn is over.
     */
    @Override
    public boolean execute() {
        print("Where do you want to move the armies from?");

        String fromStr = input.nextLine();
        int fromTerritoryIndex = game.getBoard().getTerritoryIdx(fromStr);
        Territory fromTerritory = game.getBoard().getTerritories().get(fromTerritoryIndex);

        print("Where do you want to move the armies to?");

        String toStr = input.nextLine();
        int toIndex = game.getBoard().getTerritoryIdx(toStr);
        Territory toTerritory = game.getBoard().getTerritories().get(toIndex);

        print("How many armies do you want to move? (1 - "
            + (fromTerritory.getArmiesCount() - 1) + ")");
        byte amount = (byte) numInput.nextInt();
        Player player = game.getPlayers().get(game.getTurn());
        player.moveArmies(amount, fromTerritory, toTerritory);
        return false;
    }
}
