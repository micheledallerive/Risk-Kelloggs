package tui.command;

import model.Game;

import java.util.Scanner;

public abstract class Command {
    private String name;
    protected Game game;
    protected Scanner input;
    protected Scanner numInput;
    public Command(String name, Game game, Scanner input, Scanner numInput) {
        this.name = name;
        this.game = game;
        this.input = input;
        this.numInput = numInput;
    }
    public String getName() {
        return name;
    }

    public abstract boolean execute();
}
