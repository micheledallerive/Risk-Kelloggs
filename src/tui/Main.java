package tui;

import model.Game;
import model.Player;
import model.enums.GameStatus;

import java.util.ArrayList;

/**
 * TUI class.
 * 
 * @author moralj@usi.ch
 */
public class Main {

    public static void mainMenu() {
        return;
    }

    public static void playing() {

    }

    /**
     * Procedure - static main method.
     * @param args arguments to set terminal interface
     */
    public static void main(String[] args) {
        Game game = new Game();
        while(game.getStatus() != GameStatus.EXIT) {

        }
    }

    /**
     * Procedure - main method.
     */
    public void main() {
        // fill
    }
}
