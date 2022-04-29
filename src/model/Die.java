package model;

import model.enums.DieColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a single dice in the game.
 */
public class Die implements Comparable<Die>{

    private final DieColor color;
    private final Random random;
    private int number;
    private static ArrayList<Die> dice;

    public static ArrayList<Die> getDice() {
        return dice;
    }

    /**
     * Initializes the Dice by creating 3 attack dice and 3 defence dice
     */
    public static void init() {
        dice = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dice.add(new Die(DieColor.RED));
        }
        for (int i = 0; i < 3; i++) {
            dice.add(new Die(DieColor.BLUE));
        }
    }

    /**
     * Compare the current dice to another one (used for sorting).
     * @param other the object to be compared.
     * @return the comparison between the dice numbers.
     */
    @Override
    public int compareTo(Die other) {
        return Integer.compare(this.number, other.number);
    }

    /**
     * Creates a new dice with a specific color.
     * @param color the color of the dice, blue if it is a defensive dice, red if it is an attack dice
     */
    public Die(DieColor color) {
        this.color = color;
        this.random = new Random();
        this.number = -1;
    }

    /**
     * Returns the color of the dice.
     * @return the color of the dice.
     */
    public DieColor getColor() {
        return this.color;
    }

    /**
     * Returns the number of the last outcome of the dice roll.
     * @return the last outcome of the dice.
     */
    public int getLastOutcome() {
        return this.number;
    }

    /**
     * Rolls the dice and stores the outcome.
     */
    public void roll() {
        this.number = random.nextInt(6)+1;
    }

    /**
     * Resets the current dice to the default value (-1).
     */
    public void reset() {
        this.number = -1;
    }



    /**
     * Checks the current state of the dice and returns an array
     * in which each element is the winner of a pair of dice rolled.
     * @return an array of colors, each of which is the winner of a single roll.
     */
    public static ArrayList<DieColor> winner() {
        // The dice are divided between attack dice and defence dice.
        ArrayList<Die> redDice = new ArrayList<>();
        ArrayList<Die> blueDice = new ArrayList<>();
        for (Die die : dice) {
            if (die.getLastOutcome() != -1) {
                if (die.getColor() == DieColor.RED) {
                    redDice.add(die);
                } else {
                    blueDice.add(die);
                }
            }
        }

        ArrayList<DieColor> outcomes = new ArrayList<>();

        // The lists are sorted in order to be able to be compared.
        Collections.sort(redDice, Collections.<Die>reverseOrder());
        Collections.sort(blueDice, Collections.<Die>reverseOrder());

        for (int i = 0; i < redDice.size(); i++) {
            if (redDice.get(i).getLastOutcome() == -1 || blueDice.get(i).getLastOutcome() == -1) {
                break;
            }
            if (redDice.get(i).getLastOutcome() > blueDice.get(i).getLastOutcome()) {
                outcomes.add(DieColor.RED);
            } else {
                outcomes.add(DieColor.BLUE);
            }
        }

        for (Die die : dice) {
            die.reset();
        }

        return outcomes;
    }

}
