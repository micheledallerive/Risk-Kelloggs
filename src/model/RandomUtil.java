package model;

import java.util.Random;

/**
 * Class for initialize a unique random seed to generate pseudo-random code.
 *
 * @author dallem @usi.ch, moralj@usi.ch
 */
public final class RandomUtil {
    /**
     * The constant random.
     */
    public static Random random;

    private RandomUtil() {
        // eskere
    }

    /**
     * Init method to create a random object to store it in the class for the whole game.
     */
    public static void init() {
        random = new Random();
    }

}
