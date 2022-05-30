package model;

import java.util.ArrayList;

/**
 * Describes a Territory in the Risk map.
 *
 * @author dallem @usi.ch, moralj@usi.ch
 */
public class Territory {
    //region CONSTANTS
    /**
     * The constant PATH_TERRITORIES.
     */
    public static final String PATH_TERRITORIES = "src/model/data/territories.txt";
    //endregion

    //region FIELDS
    private final String name;
    private Player owner;
    //endregion

    //region CONSTRUCTORS

    /**
     * Creates a new territory and initializes it.
     *
     * @param name the name of the territory
     */
    public Territory(final String name) {
        this.name = name;
        this.owner = null;
    }
    //endregion

    //region GETTERS AND SETTERS

    /**
     * Returns the player that owns the territory.
     *
     * @return the player that owns the territory
     */
    public final Player getOwner() {
        return this.owner;
    }

    /**
     * Returns the name of the territory.
     *
     * @return the territory name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Sets the owner of the territory.
     *
     * @param newOwner the player that owns the territory
     */
    public final void setOwner(final Player newOwner) {
        this.owner = newOwner;
    }
    //endregion

    //region METHODS

    /**
     * Checks if the current territory is occupied by a player.
     *
     * @return returns true if some player owns the territory
     */
    public boolean isOccupied() {
        return this.owner != null;
    }

    /**
     * Returns the armies that are placed in the territory.
     *
     * @return the armies placed in the territory.
     */
    public ArrayList<Army> getArmies() {
        final ArrayList<Army> armies = new ArrayList<>();
        if (this.owner == null) {
            return armies;
        }
        for (final Army army : this.owner.getArmies()) {
            if (army.getTerritory() == this) {
                armies.add(army);
            }
        }
        return armies;
    }

    /**
     * Returns the number of armies in the territory.
     *
     * @return the number of armies in this territory
     */
    public int getArmiesCount() {
        return this.getArmies().size();
    }
    //endregion
}
