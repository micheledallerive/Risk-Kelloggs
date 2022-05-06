package model;

import model.enums.ContinentName;
import model.enums.TerritoryName;

import java.util.ArrayList;

/**
 * Describes the board of the game containing the map of the world.
 * @author dallem@usi.ch
 */
public class Board {
    //region FIELDS
    private final ArrayList<Territory> territories;
    private final ArrayList<Continent> continents;
    //endregion

    //region CONSTRUCTOR
    /**
     * Constructor.
     */
    public Board() {
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.initTerritories();
        this.initContinents();
    }
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Return the territories of the board.
     * @return The territories of the board.
     */
    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    /**
     * Return the continents of the board.
     * @return The continents of the board.
     */
    public ArrayList<Continent> getContinents() {
        return continents;
    }
    //endregion

    //region METHODS
    /**
     * Initializes the territories of the board.
     */
    private void initTerritories() {
        // get all territories but "NONE" enum value
        for (final TerritoryName territoryName : TerritoryName.values()) {
            if (territoryName != TerritoryName.NONE) {
                this.territories.add(new Territory(territoryName));
            }
        }

        // foreach territory get the name, indexed it and make its adjacent list
        for (final Territory territory : this.territories) {
            final TerritoryName name = territory.getName();
            final byte territoryIndex = (byte) name.ordinal();
            final ArrayList<Territory> adjacent = new ArrayList<>();
            // Territory.adjacency contains all the TerritoryName adjacent to the given territory
            for (final TerritoryName adjacentName : Territory.adjacency.get(territoryIndex)) {
                final int adjacentIndex = adjacentName.ordinal();
                adjacent.add(this.territories.get(adjacentIndex));
            }
            territory.setAdjacent(adjacent);
        }
    }

    /**
     * Initializes the continents of the board.
     */
    private void initContinents() {
        // foreach continent
        for (final ContinentName continentName : ContinentName.values()) {
            final int continentIndex = continentName.ordinal();
            final int continentValue = Continent.VALUES[continentIndex]; // get number of territories in continent
            final ArrayList<Territory> continentTerritories = new ArrayList<>();

            // foreach territory in a continent, add it to this current continent object
            for (final TerritoryName territoryName : Continent.TERRITORIES.get(continentIndex)) {
                final Territory territory = this.territories.get(territoryName.ordinal());
                continentTerritories.add(territory);
            }

            // compose the continent
            this.continents.add(new Continent(
                continentName,
                continentTerritories,
                continentValue
            ));
        }
    }
    //endregion
}
