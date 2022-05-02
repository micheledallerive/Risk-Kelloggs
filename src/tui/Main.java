package tui;

import model.Game;
import model.enums.GameStatus;

import java.util.Scanner;

import static tui.Utils.*;

/**
 * TUI class.
 * 
 * @author moralj@usi.ch
 */
public class Main {

    private Game game;
    private Scanner input;

    /**
     * Creates a new TUI game.
     */
    public Main() {
        init();
        this.input = new Scanner(System.in);
    }

    private void init() {
        this.game = new Game();
    }

    /**
     * Handles the behaviour of the game in the main menu.
     */
    private void mainMenu() {
        print("Welcome to Risk Kellogg's");
        printOptions(
                "Play a new game of Risk Kellogg's",
                "Exit the game"
        );
        int choice = input.nextInt();
        if (choice == 1) {
            this.game.setStatus(GameStatus.SETUP);
        } else {
            this.game.setStatus(GameStatus.EXIT);
        }
    }

    /**
     * Handles the behaviour of the game while setting up the game.
     */
    private void setupGame() {

    }

    /**
     * Handles the behaviour of the game while it is being played.
     */
    private void playing() {

    }

    /**
     * Handles the behaviour of the game after the game is over.
     */
    private void end() {
        print("The game is over.");
        printOptions("Player another game.", "Exit the game");
        int choice = input.nextInt();
        if (choice == 1) {
            // Wants to play again.
            init();
            this.play();
        } else {
            this.game.setStatus(GameStatus.EXIT);
        }
    }


    public void play() {
        while(game.getStatus() != GameStatus.EXIT) {
            switch(game.getStatus()) {
                case MENU:
                    mainMenu();
                    break;
                case SETUP:
                    setupGame();
                case PLAYING:
                    playing();
                case END:
                    end();
            }
        }
    }

    /**
     * Procedure - static main method.
     * @param args arguments to set terminal interface
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.play();
    }
}
