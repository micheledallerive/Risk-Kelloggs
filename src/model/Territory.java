package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // North America
        private static final TerritoryName[] ADJ_ALASKA = new TerritoryName[] {NORTHWEST_TERRITORY, ALBERTA, KAMCHATKA};
        private static final TerritoryName[] ADJ_NORTHWEST_TERRITORY = new TerritoryName[] {ALASKA, ALBERTA, ONTARIO, GREENLAND};
        private static final TerritoryName[] ADJ_ALBERTA = new TerritoryName[] {ALASKA, NORTHWEST_TERRITORY, ONTARIO, WESTERN_UNITED_STATES};
        private static final TerritoryName[] ADJ_ONTARIO = new TerritoryName[] {NORTHWEST_TERRITORY, ALBERTA, WESTERN_UNITED_STATES,
            EASTERN_UNITED_STATES, QUEBEC, GREENLAND};
        private static final TerritoryName[] ADJ_QUEBEC = new TerritoryName[] {GREENLAND, ONTARIO, EASTERN_UNITED_STATES};
        private static final TerritoryName[] ADJ_GREENLAND = new TerritoryName[] {NORTHWEST_TERRITORY, ONTARIO, QUEBEC, ICELAND};
        private static final TerritoryName[] ADJ_CENTRAL_AMERICA = new TerritoryName[] {WESTERN_UNITED_STATES, EASTERN_UNITED_STATES, VENEZUELA};
        private static final TerritoryName[] ADJ_WESTERN_UNITED_STATES = new TerritoryName[] {ALBERTA, ONTARIO, EASTERN_UNITED_STATES, CENTRAL_AMERICA};
        private static final TerritoryName[] ADJ_EASTERN_UNITED_STATES = new TerritoryName[] {CENTRAL_AMERICA, WESTERN_UNITED_STATES, ONTARIO, QUEBEC};

        // South America
        private static final TerritoryName[] ADJ_VENEZUELA = new TerritoryName[] {CENTRAL_AMERICA, BRAZIL, PERU};
        private static final TerritoryName[] ADJ_BRAZIL = new TerritoryName[] {VENEZUELA, PERU, ARGENTINA, NORTH_AFRICA};
        private static final TerritoryName[] ADJ_PERU = new TerritoryName[] {VENEZUELA, BRAZIL, ARGENTINA};
        private static final TerritoryName[] ADJ_ARGENTINA = new TerritoryName[] {BRAZIL, PERU};

        // Europe
        private static final TerritoryName[] ADJ_ICELAND = new TerritoryName[] {GREENLAND, SCANDINAVIA, GREAT_BRITAIN};
        private static final TerritoryName[] ADJ_GREAT_BRITAIN = new TerritoryName[] {ICELAND, SCANDINAVIA, NORTHERN_EUROPE, WESTERN_EUROPE};
        private static final TerritoryName[] ADJ_WESTERN_EUROPE = new TerritoryName[] {GREAT_BRITAIN, NORTHERN_EUROPE, SOUTHERN_EUROPE, NORTH_AFRICA};
        private static final TerritoryName[] ADJ_SOUTHERN_EUROPE = new TerritoryName[] {WESTERN_EUROPE, NORTHERN_EUROPE, UKRAINE,
            NORTH_AFRICA, EGYPT, MIDDLE_EAST};
        private static final TerritoryName[] ADJ_NORTHERN_EUROPE = new TerritoryName[] {SCANDINAVIA, GREAT_BRITAIN, UKRAINE,
            WESTERN_EUROPE, SOUTHERN_EUROPE};
        private static final TerritoryName[] ADJ_SCANDINAVIA = new TerritoryName[] {ICELAND, GREAT_BRITAIN, NORTHERN_EUROPE, UKRAINE};
        private static final TerritoryName[] ADJ_UKRAINE = new TerritoryName[] {SCANDINAVIA, NORTHERN_EUROPE, SOUTHERN_EUROPE,
            MIDDLE_EAST, AFGHANISTAN, URAL};

        // Asia
        private static final TerritoryName[] ADJ_URAL = new TerritoryName[] {UKRAINE, SIBERIA, CHINA, AFGHANISTAN};
        private static final TerritoryName[] ADJ_SIBERIA = new TerritoryName[] {URAL, YAKUTSK, IRKUTSK, MONGOLIA, CHINA};
        private static final TerritoryName[] ADJ_AFGHANISTAN = new TerritoryName[] {URAL, CHINA, INDIA, MIDDLE_EAST, UKRAINE};
        private static final TerritoryName[] ADJ_CHINA = new TerritoryName[] {URAL, SIBERIA, MONGOLIA, SIAM, INDIA, AFGHANISTAN};
        private static final TerritoryName[] ADJ_INDIA = new TerritoryName[] {CHINA, SIAM, AFGHANISTAN, MIDDLE_EAST};
        private static final TerritoryName[] ADJ_IRKUTSK = new TerritoryName[] {YAKUTSK, KAMCHATKA, MONGOLIA, SIBERIA};
        private static final TerritoryName[] ADJ_JAPAN = new TerritoryName[] {KAMCHATKA, MONGOLIA};
        private static final TerritoryName[] ADJ_KAMCHATKA = new TerritoryName[] {YAKUTSK, IRKUTSK, MONGOLIA, JAPAN};
        private static final TerritoryName[] ADJ_MIDDLE_EAST = new TerritoryName[] {UKRAINE, SOUTHERN_EUROPE, EGYPT,
            EAST_AFRICA, AFGHANISTAN, INDIA};
        private static final TerritoryName[] ADJ_MONGOLIA = new TerritoryName[] {CHINA, SIBERIA, IRKUTSK, KAMCHATKA, JAPAN};
        private static final TerritoryName[] ADJ_SIAM = new TerritoryName[] {CHINA, INDIA, INDONESIA};
        private static final TerritoryName[] ADJ_YAKUTSK = new TerritoryName[] {SIBERIA, IRKUTSK, KAMCHATKA};

        // Africa
        private static final TerritoryName[] ADJ_NORTH_AFRICA = new TerritoryName[] {WESTERN_EUROPE, SOUTHERN_EUROPE, EGYPT, CONGO, EAST_AFRICA, BRAZIL};
        private static final TerritoryName[] ADJ_EGYPT = new TerritoryName[] {SOUTHERN_EUROPE, NORTH_AFRICA, MIDDLE_EAST, EAST_AFRICA};
        private static final TerritoryName[] ADJ_CONGO = new TerritoryName[] {NORTH_AFRICA, EAST_AFRICA, SOUTH_AFRICA};
        private static final TerritoryName[] ADJ_EAST_AFRICA = new TerritoryName[] {EGYPT, NORTH_AFRICA, CONGO, SOUTH_AFRICA, MADAGASCAR};
        private static final TerritoryName[] ADJ_SOUTH_AFRICA = new TerritoryName[] {CONGO, EAST_AFRICA, MADAGASCAR};
        private static final TerritoryName[] ADJ_MADAGASCAR = new TerritoryName[] {EAST_AFRICA, SOUTH_AFRICA};

        // Australia
        private static final TerritoryName[] ADJ_EASTERN_AUSTRALIA = new TerritoryName[] {WESTERN_AUSTRALIA, NEW_GUINEA};
        private static final TerritoryName[] ADJ_INDONESIA = new TerritoryName[] {NEW_GUINEA, WESTERN_AUSTRALIA, SIAM};
        private static final TerritoryName[] ADJ_NEW_GUINEA = new TerritoryName[] {INDONESIA, WESTERN_AUSTRALIA, EASTERN_AUSTRALIA};
        private static final TerritoryName[] ADJ_WESTERN_AUSTRALIA = new TerritoryName[] {INDONESIA, NEW_GUINEA, EASTERN_AUSTRALIA};

        private static final TerritoryName[][] ADJ_TOTAL = new TerritoryName[][] {
            // NORTH AMERICA
            ADJ_ALASKA, ADJ_NORTHWEST_TERRITORY, ADJ_ALBERTA, ADJ_ONTARIO, ADJ_QUEBEC, ADJ_GREENLAND, ADJ_CENTRAL_AMERICA,
            ADJ_WESTERN_UNITED_STATES, ADJ_EASTERN_UNITED_STATES,

            // SOUTH AMERICA
            ADJ_VENEZUELA, ADJ_BRAZIL, ADJ_PERU, ADJ_ARGENTINA,

            // EUROPE
            ADJ_ICELAND, ADJ_GREAT_BRITAIN, ADJ_WESTERN_EUROPE, ADJ_SOUTHERN_EUROPE, ADJ_NORTHERN_EUROPE, ADJ_SCANDINAVIA, ADJ_UKRAINE,

            // ASIA
            ADJ_URAL, ADJ_SIBERIA, ADJ_AFGHANISTAN, ADJ_CHINA, ADJ_INDIA, ADJ_IRKUTSK, ADJ_JAPAN, ADJ_KAMCHATKA,
            ADJ_MIDDLE_EAST, ADJ_MONGOLIA, ADJ_SIAM, ADJ_YAKUTSK,

            // AFRICA
            ADJ_NORTH_AFRICA, ADJ_EGYPT, ADJ_CONGO, ADJ_EAST_AFRICA, ADJ_SOUTH_AFRICA, ADJ_MADAGASCAR,

            // AUSTRALIA
            ADJ_EASTERN_AUSTRALIA, ADJ_INDONESIA, ADJ_NEW_GUINEA, ADJ_WESTERN_AUSTRALIA};
    }
    //endregion

    //region FIELDS
    public static List<List<TerritoryName>> adjacency = new ArrayList<>();
    private Player owner;
    private TerritoryName name;
    private ArrayList<Territory> adjacent;
    private ArrayList<Army> armies;
    //endregion

    //region CONSTRUCTORS
    /**
     * Creates a new territory and initializes it.
     * @param name the name of the territory
     */
    public Territory(TerritoryName name) {
        this(name, new ArrayList<Territory>());
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
    //endregion

    //region GETTERS AND SETTERS
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
     * Returns the Territories that are adjacent to this.
     * @return the adjacent territories.
     */
    public ArrayList<Territory> getAdjacent() {
        return this.adjacent;
    }

    /**
     * Returns the list of the armies owned by the player.
     * @return the armies owned by the player
     */
    public ArrayList<Army> getArmies() {
        return this.armies;
    }
    //endregion

    //region METHODS
    /**
     * Creates all the territories adjacent territories in the map.
     */
    public static void init() {
        if (adjacency.isEmpty()) {
            for (TerritoryName[] array : TerritoryName.ADJ_TOTAL) {
                adjacency.add(Arrays.asList(array));
            }
        }
    }

    /**
     * Checks if the current territory is occupied by a player.
     * @return returns true if some player owns the territory
     */
    public boolean isOccupied() {
        return this.owner != null;
    }

    /**
     * Returns the number of armies in the territory.
     * @return the number of armies in this territory
     */
    public int getArmiesCount() {
        int totalArmies = 0;
        for (Army army : this.armies) {
            totalArmies += army.getValue();
        }
        return totalArmies;
    }

    /**
     * Adds an army to the current territory.
     * @param army the army to add to the territory.
     */
    public void addArmy(final Army army) {
        this.armies.add(army);
    }

    /**
     * Sets the owner of the territory.
     * @param owner the player that owns the territory
     */
    public void setOwner(final Player owner) {
        this.owner = owner;
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
