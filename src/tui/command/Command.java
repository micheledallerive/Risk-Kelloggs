package tui.command;

import model.Game;

import java.util.Scanner;

/**
 * Abstract class for the Command Pattern.
 * @author dallem@usi.ch
 */
public abstract class Command {
    private String name;
    protected Game game;
    protected Scanner input;
    protected Scanner numInput;

    /**
     * Default constructor.
     * @param name the name of the command
     * @param game the game instance
     * @param input the input scanner
     * @param numInput the number input scanner
     */
    public Command(String name, Game game, Scanner input, Scanner numInput) {
        this.name = name;
        this.game = game;
        this.input = input;
        this.numInput = numInput;
    }

    /**
     * Get the name of the command.
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Execute the command.
     * @return the result of the command
     */
    public abstract boolean execute();
}
