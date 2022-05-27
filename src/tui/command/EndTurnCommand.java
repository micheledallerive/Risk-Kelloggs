package tui.command;

import model.Game;

import java.util.Scanner;

public class EndTurnCommand extends Command {
    public EndTurnCommand(String name, Game game, Scanner input, Scanner numInput) {
        super(name, game, input, numInput);
    }

    @Override
    public boolean execute() {
        return true;
    }
}
