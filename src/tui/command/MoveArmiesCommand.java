package tui.command;

import model.Game;
import model.Player;
import model.Territory;

import java.util.Scanner;

import static tui.Utils.print;

public class MoveArmiesCommand extends Command {

    public MoveArmiesCommand(String name, Game game, Scanner input, Scanner numInput) {
        super(name, game, input, numInput);
    }

    @Override
    public boolean execute() {
        print("Where do you want to move the armies from?");

        String fromStr = input.nextLine();
        Territory fromTerritory = game.getBoard().getTerritories().get(
                Territory.TerritoryName.valueOf(fromStr.toUpperCase()).ordinal()
        );

        print("Where do you want to move the armies to?");

        String toStr = input.nextLine();
        Territory toTerritory = game.getBoard().getTerritories().get(
                Territory.TerritoryName.valueOf(toStr.toUpperCase()).ordinal()
        );

        print("How many armies do you want to move? (1 - "
                + (fromTerritory.getArmiesCount() - 1) + ")");
        byte amount = (byte)numInput.nextInt();
        Player player = game.getPlayers().get(game.getTurn());
        player.moveArmies(amount, fromTerritory, toTerritory);
        return false;
    }
}
