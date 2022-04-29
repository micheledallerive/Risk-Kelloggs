package model;

import model.enums.ContinentName;
import model.enums.TerritoryName;

import java.util.ArrayList;

/**
 * Describes the board of the game containing the map of the world.
 * @author dallem@usi.ch
 */
public class Board {

    private final ArrayList<Territory> territories;
    private final ArrayList<Continent> continents;

    /**
     * Constructor.
     */
    public Board() {
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.initTerritories();
        this.initContinents();
    }

    /**
     * Initializes the territories of the board.
     */
    private void initTerritories() {
        for (TerritoryName territoryName : TerritoryName.values()) {
            this.territories.add(new Territory(territoryName));
        }
        for (Territory territory : this.territories) {
            TerritoryName name = territory.getName();
            int territoryIndex = name.ordinal();
            ArrayList<Territory> adjacent = new ArrayList<>();
            // Territory.adjacency contains all the TerritoryName adjacent to the given territory
            for (TerritoryName adjacentName : Territory.adjacency.get(territoryIndex)) {
                int adjacentIndex = adjacentName.ordinal();
                adjacent.add(this.territories.get(adjacentIndex));
            }
            territory.setAdjacent(adjacent);
        }
    }

    /**
     * Initializes the continents of the board.
     */
    private void initContinents() {
        for (ContinentName continentName : ContinentName.values()) {
            int continentIndex = continentName.ordinal();
            int continentValue = Continent.VALUES[continentIndex];
            ArrayList<Territory> continentTerritories = new ArrayList<>();
            for (TerritoryName territoryName : Continent.TERRITORIES.get(continentIndex)) {
                Territory territory = this.territories.get(territoryName.ordinal());
                continentTerritories.add(territory);
            }
            this.continents.add(new Continent(
                    continentName,
                    continentTerritories,
                    continentValue
            ));
        }
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
