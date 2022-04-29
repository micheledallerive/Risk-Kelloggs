package model;

import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static model.enums.TerritoryName.*;

/**
 * Describes a Territory in the Risk map.
 */
public class Territory {

    private ArrayList<Territory> adjacent;
    private Player owner;
    private ArrayList<Army> armies;
    private TerritoryName name;

    public static List<List<TerritoryName>> adjacency = Arrays.asList(
//            ALASKA,
            Arrays.asList(NORTH_WEST_TERRITORY, ALBERTA, KAMCHATKA),
//            NORTH_WEST_TERRITORY,
            Arrays.asList(ALASKA, ALBERTA, ONTARIO, GREENLAND),
//            ALBERTA,
            Arrays.asList(ALASKA, NORTH_WEST_TERRITORY, ONTARIO, WESTERN_UNITED_STATES),
//            ONTARIO,
            Arrays.asList(NORTH_WEST_TERRITORY, ALBERTA, WESTERN_UNITED_STATES, EASTERN_UNITED_STATES, QUEBEC, GREENLAND),
//            QUEBEC,
            Arrays.asList(GREENLAND, ONTARIO, EASTERN_UNITED_STATES)
//            GREENLAND,
            Arrays.asList(NORTH_WEST_TERRITORY, ONTARIO, QUEBEC, ICELAND),
//            CENTRAL_AMERICA,
            Arrays.asList(WESTERN_UNITED_STATES, EASTERN_UNITED_STATES, VENEZUELA),
//            WESTERN_UNITED_STATES,
            Arrays.asList(ALBERTA, ONTARIO, EASTERN_UNITED_STATES, CENTRAL_AMERICA),
//            EASTERN_UNITED_STATES,
            Arrays.asList(CENTRAL_AMERICA, WESTERN_UNITED_STATES, ONTARIO, QUEBEC),
//            VENEZUELA,
            Arrays.asList(CENTRAL_AMERICA, BRAZIL, PERU),
//            BRAZIL,
            Arrays.asList(VENEZUELA, PERU, ARGENTINA, NORTH_AFRICA),
//            PERU,
            Arrays.asList(VENEZUELA, BRAZIL, ARGENTINA),
//            ARGENTINA,
            Arrays.asList(BRAZIL, PERU),
//            ICELAND,
            Arrays.asList(GREENLAND, SCANDINAVIA, GREAT_BRITAIN),
//            GREAT_BRITAIN,
            Arrays.asList(ICELAND, SCANDINAVIA, NORTHERN_EUROPE, WESTERN_EUROPE),
//            WESTERN_EUROPE,
            Arrays.asList(GREAT_BRITAIN, NORTHERN_EUROPE, SOUTHERN_EUROPE, NORTH_AFRICA),
//            SOUTHERN_EUROPE,
            Arrays.asList(WESTERN_EUROPE, NORTHERN_EUROPE, UKRAINE, NORTH_AFRICA, EGYPT, MIDDLE_EAST),
//            NORTHERN_EUROPE,
            Arrays.asList(SCANDINAVIA, GREAT_BRITAIN, UKRAINE, WESTERN_EUROPE, SOUTHERN_EUROPE),
//            SCANDINAVIA,
            Arrays.asList(ICELAND, GREAT_BRITAIN, NORTHERN_EUROPE, UKRAINE),
//            UKRAINE,
            Arrays.asList(SCANDINAVIA, NORTHERN_EUROPE, SOUTHERN_EUROPE, MIDDLE_EAST, AFGHANISTAN, URAL),
//            URAL,
            Arrays.asList(UKRAINE, SIBERIA, CHINA, AFGHANISTAN),
//            SIBERIA,
            Arrays.asList(URAL, YAKUTSK, IRKUTSK, MONGOLIA, CHINA),
//            AFGHANISTAN,
            Arrays.asList(URAL, CHINA, INDIA, MIDDLE_EAST, UKRAINE),
//            CHINA,
            Arrays.asList(URAL, SIBERIA, MONGOLIA, SIAM, INDIA, AFGHANISTAN),
//            INDIA,
            Arrays.asList(CHINA, SIAM, AFGHANISTAN, MIDDLE_EAST),
//            IRKUTSK,
            Arrays.asList(YAKUTSK, KAMCHATKA, MONGOLIA, SIBERIA),
//            JAPAN,
            Arrays.asList(KAMCHATKA, MONGOLIA),
//            KAMCHATKA,
            Arrays.asList(YAKUTSK, IRKUTSK, MONGOLIA, JAPAN),
//            MIDDLE_EAST,
            Arrays.asList(UKRAINE, SOUTHERN_EUROPE, EGYPT, EAST_AFRICA, AFGHANISTAN, INDIA),
//            MONGOLIA,
            Arrays.asList(CHINA, SIBERIA, IRKUTSK, KAMCHATKA, JAPAN),
//            SIAM,
            Arrays.asList(CHINA, INDIA, INDONESIA),
//            YAKUTSK,
            Arrays.asList(SIBERIA, IRKUTSK, KAMCHATKA),
//            CONGO,
            Arrays.asList(NORTH_AFRICA, EAST_AFRICA, SOUTH_AFRICA),
//            EAST_AFRICA,
            Arrays.asList(),
//            SOUTH_AFRICA,
            Arrays.asList(),
//            EASTERN_AUSTRALIA,
            Arrays.asList(),
//            INDONESIA,
            Arrays.asList(),
//            NEW_GUINEA,
            Arrays.asList(),
//            WESTERN_AUSTRALIA
            Arrays.asList()
    );

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
