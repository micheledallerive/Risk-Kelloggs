package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Describes a Territory in the Risk map.
 * @author dallem@usi.ch
 */
public class Territory {
    //region CONSTANTS
    public static final byte INIT_NORTH_AMERICA = 0;
    public static final byte END_NORTH_AMERICA = 8;
    public static final byte INIT_SOUTH_AMERICA = 9;
    public static final byte END_SOUTH_AMERICA = 12;
    public static final byte INIT_EUROPE = 13;
    public static final byte END_EUROPE = 19;
    public static final byte INIT_ASIA = 20;
    public static final byte END_ASIA = 31;
    public static final byte INIT_AFRICA = 32;
    public static final byte END_AFRICA = 37;
    public static final byte INIT_AUSTRALIA = 38;
    public static final byte END_AUSTRALIA = 41;
    //endregion

    //region ENUM

    /**
     * Enum about the territories predefined in the Risk classic game.
     */
    public enum TerritoryName {
        // NORTH AMERICA [0, 8]
        ALASKA, NORTHWEST_TERRITORY, ALBERTA, ONTARIO, QUEBEC, GREENLAND, CENTRAL_AMERICA, WESTERN_UNITED_STATES, EASTERN_UNITED_STATES,
        // SOUTH AMERICA [9, 12]
        VENEZUELA, BRAZIL, PERU, ARGENTINA,
        // EUROPE [13, 19]
        ICELAND, GREAT_BRITAIN, WESTERN_EUROPE, SOUTHERN_EUROPE, NORTHERN_EUROPE, SCANDINAVIA, UKRAINE,
        // ASIA [20, 31]
        URAL, SIBERIA, AFGHANISTAN, CHINA, INDIA, IRKUTSK, JAPAN, KAMCHATKA, MIDDLE_EAST, MONGOLIA, SIAM, YAKUTSK,
        // AFRICA [32, 37]
        NORTH_AFRICA, EGYPT, CONGO, EAST_AFRICA, SOUTH_AFRICA, MADAGASCAR,
        // AUSTRALIA [38, 41]
        EASTERN_AUSTRALIA, INDONESIA, NEW_GUINEA, WESTERN_AUSTRALIA,
        // NONE territory
        NONE;
    }
    //endregion

    //region FIELDS
    public static List<List<TerritoryName>> adjacency = new ArrayList<>();
    private Player owner;
    private TerritoryName name;
    private ArrayList<Territory> adjacent;
    //endregion

    /**
     * Creates a new territory and initializes it.
     * @param name the name of the territory
     */
    public Territory(final TerritoryName name) {
        this(name, new ArrayList<Territory>());
    }

    /**
     * Creates a new territory and initializes it.
     * @param name the name of the territory
     * @param adjacent the territories that are adjacent to this territory
     */
    public Territory(final TerritoryName name, final ArrayList<Territory> adjacent) {
        this.adjacent = adjacent;
        this.owner = null;
        this.name = name;
    }

    /**
     * Returns the player that owns the territory.
     * @return the player that owns the territory
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Returns the name of the territory.
     * @return the territory name.
     */
    public TerritoryName getName() {
        return this.name;
    }

    /**
     * Returns the Territories that are adjacent to this.
     * @return the adjacent territories.
     */
    public ArrayList<Territory> getAdjacent() {
        return this.adjacent;
    }

    /**
     * Sets the owner of the territory.
     * @param newOwner the player that owns the territory
     */
    public void setOwner(final Player newOwner) {
        this.owner = newOwner;
    }
    //endregion

    //region METHODS
    /**
     * Creates all the territories adjacent territories in the map.
     */
    public static void init() {
        if (adjacency.isEmpty()) {
            try {
                final File file = new File("src/model/data/territory.adj");
                final Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    final String line = scanner.nextLine();
                    final String[] split = line.split(",");
                    final ArrayList<TerritoryName> adjacent = new ArrayList<>();
                    for (final String s : split) {
                        adjacent.add(TerritoryName.valueOf(s));
                    }
                    adjacency.add(adjacent);
                }
            } catch (final FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Returns the territory that corresponds to the TerritoryName.
     * @param game the game
     * @param territoryName the name of the territory
     */
    public static Territory getTerritory(Game game, TerritoryName territoryName) {
        return game.getBoard().getTerritories().get(territoryName.ordinal());
    }

    /**
     * Checks if the current territory is occupied by a player.
     * @return returns true if some player owns the territory
     */
    public boolean isOccupied() {
        return this.owner != null;
    }

    /**
     * Returns the armies that are placed in the territory.
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
     * @return the number of armies in this territory
     */
    public int getArmiesCount() {
        return this.getArmies().size();
    }

    /**
     * Sets the adjacent territories.
     * @param adjacent the territories that are adjacent to this territory
     */
    public void setAdjacent(final ArrayList<Territory> adjacent) {
        this.adjacent = adjacent;
    }
    //endregion
}
