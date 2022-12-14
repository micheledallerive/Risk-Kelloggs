package model;

import static java.util.Comparator.comparingInt;

import model.enums.DieColor;

import java.util.ArrayList;

/**
 * Represents a single dice in the game.
 *
 * @author dallem @usi.ch, moralj@usi.ch
 */
public class Die {
    //region CONSTANTS
    private static final byte MIN = 0;
    private static final byte MAX = 6;
    private static final byte INVALID = -1;
    private static final byte AMOUNT = 3;
    //endregion

    //region FIELDS
    private static ArrayList<Die> redDice;
    private static ArrayList<Die> blueDice;

    private final DieColor color;
    private byte number;
    //endregion

    //region CONSTRUCTOR

    /**
     * Creates a new dice with a specific color.
     *
     * @param color the color of the dice, blue if it is a defensive dice, red if it is an attacker dice
     */
    public Die(final DieColor color) {
        this.color = color;
        this.number = INVALID;
    }
    //endregion

    //region GETTERS AND SETTERS

    /**
     * Function - gives reference to red dice collection.
     *
     * @return reference to ArrayList containing red dice
     */
    public static ArrayList<Die> getRedDice() {
        return redDice;
    }

    /**
     * Function - gives reference to blue dice collection.
     *
     * @return reference to ArrayList containing blue dice
     */
    public static ArrayList<Die> getBlueDice() {
        return blueDice;
    }

    /**
     * Return the color of the dice.
     *
     * @return The color of the dice.
     */
    public DieColor getColor() {
        return this.color;
    }

    /**
     * Returns the number of the last outcome of the dice roll.
     *
     * @return The last outcome of the dice.
     */
    public byte getNumber() {
        return this.number;
    }
    //endregion

    //region METHODS

    /**
     * Reset all the dice to the default value -1.
     */
    public static void resetAll() {
        for (final Die die : redDice) {
            die.reset();
        }

        for (final Die die : blueDice) {
            die.reset();
        }
    }

    /**
     * Initializes the Dice by creating 3 attack dice and 2 defence dice.
     */
    public static void init() {
        redDice = new ArrayList<>();
        blueDice = new ArrayList<>();
        for (int i = 0; i < AMOUNT; i++) {
            redDice.add(new Die(DieColor.RED));
            blueDice.add(new Die(DieColor.BLUE));
        }
        //redDice.add(new Die(DieColor.RED));
    }

    /**
     * Checks the current state of the dice and returns an array
     * in which each element is the winner of a pair of dice rolled.
     *
     * @return An array of colors, each of which is the winner of a single roll.
     */
    public static ArrayList<DieColor> winner() {
        // The lists are sorted in order to be able to be compared.
        redDice.sort(comparingInt(Die::getNumber).reversed());
        blueDice.sort(comparingInt(Die::getNumber).reversed());

        ArrayList<DieColor> outcomes = new ArrayList<>();

        for (int i = 0; i < AMOUNT; i++) {
            if (redDice.get(i).getNumber() == INVALID || blueDice.get(i).getNumber() == INVALID) {
                break;
            }
            if (redDice.get(i).getNumber() > blueDice.get(i).getNumber()) {
                outcomes.add(DieColor.RED);
            } else {
                outcomes.add(DieColor.BLUE);
            }
        }

        Die.resetAll();
        return outcomes;
    }

    /**
     * Function - gives a random number used not in the scope of the risk game - Attack and Defense phase.
     *
     * @return A random number from one of the dice.
     */
    public static byte casualRoll() {
        // game should be initialized
        if (redDice == null) {
            return INVALID;
        }
        Die die = redDice.get(0);
        die.roll();
        byte number = die.getNumber();
        die.reset();
        return number;
    }

    /**
     * Rolls the dice and stores the outcome.
     */
    public void roll() {
        this.number = (byte) (RandomUtil.random.nextInt(MAX) + MIN + 1);
    }

    /**
     * Resets the current dice to the default value (-1).
     */
    public void reset() {
        this.number = INVALID;
    }
    //endregion
}
