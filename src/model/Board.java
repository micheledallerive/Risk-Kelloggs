package model;

import java.util.ArrayList;

/**
 * Describes the board of the game containing the map of the world.
 */
public class Board {

    private ArrayList<Territory> territories;
    private ArrayList<Continent> continents;

    public Board() {
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.init();
    }

    public void init() {

    }

    /**
     * Returns the territories of the board.
     * @return the territories of the board.
     */
    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    /**
     * Returns the continents of the board.
     * @return the continents of the board.
     */
    public ArrayList<Continent> getContinents() {
        return continents;
    }

}
