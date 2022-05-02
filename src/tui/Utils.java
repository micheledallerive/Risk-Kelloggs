package tui;

import model.Board;
import model.Game;

/**
 * Defines some static utility methods for printing on the console.
 */
public class Utils {

    /**
     * Auxiliary function to print easily an object.
     * @param message the message to print.
     */
    public static void print(Object message) {
        System.out.println(message);
    }

    /**
     * Auxiliary function to print multiple objects each on a new line.
     * @param messages the messages to print one per line.
     */
    public static void print(Object... messages) {
        for (Object message : messages) {
            print(message);
        }
    }

    /**
     * Auxiliary function to print a multiple choice menu.
     */
    public static void printOptions(String... options) {
        print("Enter the number of what you want to do next:");
        for (int i = 0; i < options.length; i++) {
            print("(" + (i+1) + ") " + options[i]);
        }
    }

    /**
     * Auxiliary function that prints information about Risk.
     */
    public static void printInfo() {
        print("Risk is a board game for 2-6 players.");
        print("The board of Risk represents the world divided in many countries");
        print("The goal of the game is to conquer the whole world, defeating all your enemies.");
        print("You start the game with some armies: " +
                "every players once a turn places armies on an unoccupied territory.");
        print("After the initial phase, every player in their own turn " +
                "can attack another adjacent territory with his armies");
        print("The attacker can choose an amount of armies to attack with " +
                "between 1 and 3, and the defender do the same");
        print("The attacker can attack with at most the number of armies he as on the territory" +
                "minus one, while the defender can use all the armies he has on the territory");
        print("Both the players will roll a number of dice equal to " +
                "the number of armies they attack with");
        print("The outcomes of each player are paired in descending order " +
                "(the attacking highest is paired with the defending highest, and so on.");
        print("The person who rolled the lowest die of each pair loses an army");
        print("This process can go on as long as the attacker wants, as long as he has at least 1 army");
        print("If the defender loses all his armies, he loses the territory and the attacker can move " +
                "some of his armies from the previous territory to the newly conquered one");
        print("If the player during his turn conquered a new territory, he picks a new card");
        print("The cards can create ");
    }

    /**
     * Auxiliary function to print the board map.
     * @param game the game to print the map of.
     */
    public static void printMap(Game game) {

    }
    /*
     ___   ___   ___
| 1 |-|   |-|   |
|_5_|-|___|-|___|
  |  /  |  /  |
 _|_/  _|_/  _|_
|   |-|   |-|   |
|___|-|___|-|___|
     */
}
