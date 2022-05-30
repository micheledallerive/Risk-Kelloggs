package tui.command;

import model.Game;

import java.util.Scanner;

/**
 * The End turn command.
 *
 * @author dallem@usi.ch
 */
public class EndTurnCommand extends Command {
    /**
     * Default constructor.
     *
     * @param name     the name of the command
     * @param game     the instance of the game
     * @param input    the scanner.
     * @param numInput the number scanner
     */
    public EndTurnCommand(String name, Game game, Scanner input, Scanner numInput) {
        super(name, game, input, numInput);
    }

    /**
     * Executes the command.
     *
     * @return if the player turn is over.
     */
    @Override
    public boolean execute() {
        return true;
    }
}
