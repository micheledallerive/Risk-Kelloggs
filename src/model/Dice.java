package model;

import model.enums.DiceColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a single dice in the game.
 */
public class Dice implements Comparable<Dice>{

    private DiceColor color;
    private Random random;
    private int number;
    private static ArrayList<Dice> dices;

    public static ArrayList<Dice> getDices() {
        return dices;
    }

    /**
     * Initializes the Dice by creating 3 attack dice and 3 defence dice
     */
    public static void init() {
        dices = new ArrayList<Dice>();
        for (int i = 0; i < 3; i++) {
            dices.add(new Dice(DiceColor.RED));
        }
        for (int i = 0; i < 3; i++) {
            dices.add(new Dice(DiceColor.BLUE));
        }
    }

    /**
     * Compare the current dice to another one (used for sorting).
     * @param other the object to be compared.
     * @return the comparison between the dice numbers.
     */
    @Override
    public int compareTo(Dice other) {
        return Integer.compare(this.number, other.number);
    }

    /**
     * Creates a new dice with a specific color.
     * @param color the color of the dice, blue if it is a defensive dice, red if it is an attack dice
     */
    public Dice(DiceColor color) {
        this.color = color;
        this.random = new Random();
        this.number = -1;
    }

    /**
     * Returns the color of the dice.
     * @return the color of the dice.
     */
    public DiceColor getColor() {
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
     * Checks the current state of the dices and returns an array
     * in which each element is the winner of a pair of dices rolled.
     * @return an array of colors, each of which is the winner of a single roll.
     */
    public static ArrayList<DiceColor> winner() {
        // The dice are divided between attack dice and defence dice.
        ArrayList<Dice> redDices = new ArrayList<>();
        ArrayList<Dice> blueDices = new ArrayList<>();
        for (Dice dice : dices) {
            if (dice.getLastOutcome() != -1) {
                if (dice.getColor() == DiceColor.RED) {
                    redDices.add(dice);
                } else {
                    blueDices.add(dice);
                }
            }
        }

        ArrayList<DiceColor> outcomes = new ArrayList<>();

        // The lists are sorted in order to be able to be compared.
        Collections.sort(redDices, Collections.<Dice>reverseOrder());
        Collections.sort(blueDices, Collections.<Dice>reverseOrder());

        for (int i = 0; i < redDices.size(); i++) {
            if (redDices.get(i).getLastOutcome() == -1 || blueDices.get(i).getLastOutcome() == -1) {
                break;
            }
            if (redDices.get(i).getLastOutcome() > blueDices.get(i).getLastOutcome()) {
                outcomes.add(DiceColor.RED);
            } else {
                outcomes.add(DiceColor.BLUE);
            }
        }

        for (Dice dice : dices) {
            dice.reset();
        }

        return outcomes;
    }

}
