package model;

import static model.enums.TerritoryName.*;

import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describes a Territory in the Risk map.
 * @author dallem@usi.ch
 */
public class Territory {
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
        adjacency = Arrays.asList(
                Arrays.asList(NORTH_WEST_TERRITORY, ALBERTA, KAMCHATKA),                        // ALASKA
                Arrays.asList(ALASKA, ALBERTA, ONTARIO, GREENLAND),                             // NORTH_WEST_TERRITORY
                Arrays.asList(ALASKA, NORTH_WEST_TERRITORY, ONTARIO, WESTERN_UNITED_STATES),    // ALBERTA
                Arrays.asList(NORTH_WEST_TERRITORY, ALBERTA, WESTERN_UNITED_STATES,             // ONTARIO
                        EASTERN_UNITED_STATES, QUEBEC, GREENLAND),
                Arrays.asList(GREENLAND, ONTARIO, EASTERN_UNITED_STATES),                       // QUEBEC
                Arrays.asList(NORTH_WEST_TERRITORY, ONTARIO, QUEBEC, ICELAND),                  // GREENLAND
                Arrays.asList(WESTERN_UNITED_STATES, EASTERN_UNITED_STATES, VENEZUELA),         // CENTRAL_AMERICA
                Arrays.asList(ALBERTA, ONTARIO, EASTERN_UNITED_STATES, CENTRAL_AMERICA),        // WESTERN_UNITED_STATES
                Arrays.asList(CENTRAL_AMERICA, WESTERN_UNITED_STATES, ONTARIO, QUEBEC),         // EASTERN_UNITED_STATES

                Arrays.asList(CENTRAL_AMERICA, BRAZIL, PERU),                                   // VENEZUELA
                Arrays.asList(VENEZUELA, PERU, ARGENTINA, NORTH_AFRICA),                        // BRAZIL
                Arrays.asList(VENEZUELA, BRAZIL, ARGENTINA),                                    // PERU
                Arrays.asList(BRAZIL, PERU),                                                    // ARGENTINA

                Arrays.asList(GREENLAND, SCANDINAVIA, GREAT_BRITAIN),                           // ICELAND
                Arrays.asList(ICELAND, SCANDINAVIA, NORTHERN_EUROPE, WESTERN_EUROPE),           // GREAT_BRITAIN
                Arrays.asList(GREAT_BRITAIN, NORTHERN_EUROPE, SOUTHERN_EUROPE, NORTH_AFRICA),   // WESTERN_EUROPE
                Arrays.asList(WESTERN_EUROPE, NORTHERN_EUROPE, UKRAINE,                         // SOUTHERN_EUROPE
                        NORTH_AFRICA, EGYPT, MIDDLE_EAST),
                Arrays.asList(SCANDINAVIA, GREAT_BRITAIN, UKRAINE,                              // NORTHERN_EUROPE
                        WESTERN_EUROPE, SOUTHERN_EUROPE),
                Arrays.asList(ICELAND, GREAT_BRITAIN, NORTHERN_EUROPE, UKRAINE),                // SCANDINAVIA
                Arrays.asList(SCANDINAVIA, NORTHERN_EUROPE, SOUTHERN_EUROPE,                    // UKRAINE
                        MIDDLE_EAST, AFGHANISTAN, URAL),

                Arrays.asList(UKRAINE, SIBERIA, CHINA, AFGHANISTAN),                            // URAL
                Arrays.asList(URAL, YAKUTSK, IRKUTSK, MONGOLIA, CHINA),                         // SIBERIA
                Arrays.asList(URAL, CHINA, INDIA, MIDDLE_EAST, UKRAINE),                        // AFGHANISTAN
                Arrays.asList(URAL, SIBERIA, MONGOLIA, SIAM, INDIA, AFGHANISTAN),               // CHINA
                Arrays.asList(CHINA, SIAM, AFGHANISTAN, MIDDLE_EAST),                           // INDIA
                Arrays.asList(YAKUTSK, KAMCHATKA, MONGOLIA, SIBERIA),                           // IRKUTSK
                Arrays.asList(KAMCHATKA, MONGOLIA),                                             // JAPAN
                Arrays.asList(YAKUTSK, IRKUTSK, MONGOLIA, JAPAN),                               // KAMCHATKA
                Arrays.asList(UKRAINE, SOUTHERN_EUROPE, EGYPT,                                  // MIDDLE_EAST
                        EAST_AFRICA, AFGHANISTAN, INDIA),
                Arrays.asList(CHINA, SIBERIA, IRKUTSK, KAMCHATKA, JAPAN),                       // MONGOLIA
                Arrays.asList(CHINA, INDIA, INDONESIA),                                         // SIAM
                Arrays.asList(SIBERIA, IRKUTSK, KAMCHATKA),                                     // YAKUTSK

                Arrays.asList(WESTERN_EUROPE, SOUTHERN_EUROPE, EGYPT, CONGO, EAST_AFRICA, BRAZIL),  // NORTH_AFRICA
                Arrays.asList(SOUTHERN_EUROPE, NORTH_AFRICA, MIDDLE_EAST, EAST_AFRICA),             // EGYPT
                Arrays.asList(NORTH_AFRICA, EAST_AFRICA, SOUTH_AFRICA),                             // CONGO
                Arrays.asList(EGYPT, NORTH_AFRICA, CONGO, SOUTH_AFRICA, MADAGASCAR),                // EAST_AFRICA
                Arrays.asList(CONGO, EAST_AFRICA, MADAGASCAR),                                      // SOUTH_AFRICA
                Arrays.asList(EAST_AFRICA, SOUTH_AFRICA),                                           // MADAGASCAR

                Arrays.asList(WESTERN_AUSTRALIA, NEW_GUINEA),                                       // EASTERN_AUSTRALIA
                Arrays.asList(NEW_GUINEA, WESTERN_AUSTRALIA, SIAM),                                 // INDONESIA
                Arrays.asList(INDONESIA, WESTERN_AUSTRALIA, EASTERN_AUSTRALIA),                     // NEW_GUINEA
                Arrays.asList(INDONESIA, NEW_GUINEA, EASTERN_AUSTRALIA)                             // WESTERN_AUSTRALIA
        );
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
        return this.armies.size();
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
