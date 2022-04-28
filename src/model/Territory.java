package model;

import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Describes a Territory in the Risk map.
 */
public class Territory {

    private ArrayList<Territory> adjacent;
    private Player owner;
    private ArrayList<Army> armies;
    private TerritoryName name;

    public static HashMap<Territory, ArrayList<Territory>> adjacency = new HashMap<>();

    /**
     * Creates a new territory and initializes it.
     * @param name the name of the territory
     */
    public Territory(TerritoryName name) {
        this.adjacent = new ArrayList<>();
        this.owner = null;
        this.armies = new ArrayList<>();
        this.name = name;
    }

    /**
     * Creates a new territory and initializes it.
     * @param name the name of the territory
     * @param adjacent the territories that are adjacent to this territory
     */
    public Territory(TerritoryName name, ArrayList<Territory> adjacent) {
        this.adjacent = adjacent;
        this.owner = null;
        this.armies = new ArrayList<>();
        this.name = name;
    }

    /**
     * Checks if the current territory is occupied by a player.
     * @return returns true if some player owns the territory
     */
    public boolean isOccupied() {
        return this.owner != null;
    }

    /**
     * Returns the player that owns the territory.
     * @return the player that owns the territory
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Returns the territory name.
     * @return the territory name.
     */
    public TerritoryName getName() {
        return this.name;
    }

    /**
     * Returns the number of armies in the territory.
     * @return the number of armies in this territory
     */
    public int getArmiesCount() {
        return this.armies.size();
    }

    /**
     * Returns the Territories that are adjacent to this.
     * @return the adjacent territories.
     */
    public ArrayList<Territory> getAdjacent() {
        return adjacent;
    }

    /**
     * Returns the list of the armies owned by the player.
     * @return the armies owned by the player
     */
    public ArrayList<Army> getArmies() {
        return armies;
    }

    /**
     * Sets the owner of the territory
     * @param owner the player that owns the territory
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
