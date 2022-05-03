package model;

import java.util.Random;

public class StaticRandom {

    public static Random random;

    public static void init() {
        random = new Random();
    }

}
