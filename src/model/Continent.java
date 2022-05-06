package model;

import static model.enums.TerritoryName.*;

import model.enums.ContinentName;
import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Represents a continent in the Risk map.
 * @author dallem@usi.ch
 */
public class Continent {
    //region FIELDS
    public static List<List<TerritoryName>> TERRITORIES;
    public static int[] VALUES;

    private final ContinentName name;
    private final ArrayList<Territory> countries;
    private final int value;  // index of the continent in TERRITORIES
    //endregion

    /**
     * Creates a new continent in the map.
     * @param name the name of the continent
     * @param countries the countries that are inside the continent
     * @param value the value if a player owns the whole continent
     */
    public Continent(ContinentName name, ArrayList<Territory> countries, int value) {
        this.name = name;
        this.countries = countries;
        this.value = value;
    }

    /**
     * Initialize the Continent static values.
     */
    public static void init() {
        TERRITORIES = Arrays.asList(
                Arrays.asList(ALASKA, NORTH_WEST_TERRITORY, GREENLAND,
                        ALBERTA, ONTARIO, QUEBEC, WESTERN_UNITED_STATES,
                        EASTERN_UNITED_STATES, CENTRAL_AMERICA),
                Arrays.asList(ICELAND, SCANDINAVIA, GREAT_BRITAIN,
                        NORTHERN_EUROPE, UKRAINE, WESTERN_EUROPE,
                        SOUTHERN_EUROPE),
                Arrays.asList(URAL, SIBERIA, YAKUTSK, IRKUTSK,
                        KAMCHATKA, AFGHANISTAN, CHINA, MONGOLIA,
                        JAPAN, MIDDLE_EAST, INDIA, SIAM),
                Arrays.asList(VENEZUELA, BRAZIL, PERU, ARGENTINA),
                Arrays.asList(NORTH_AFRICA, EGYPT, CONGO,
                        EAST_AFRICA, SOUTH_AFRICA, MADAGASCAR),
                Arrays.asList(INDONESIA, NEW_GUINEA,
                        WESTERN_AUSTRALIA, EASTERN_AUSTRALIA)
        );
        VALUES = new int[] {5, 5, 7, 2, 3, 2};
    }

    /**
     * Returns the name of the continent.
     * @return the name of the continent.
     */
    public ContinentName getName() {
        return name;
    }

    /**
     * Returns the list of the countries in the continent.
     * @return the countries in the continent
     */
    public ArrayList<Territory> getCountries() {
        return countries;
    }

    /**
     * Returns the value of the continent.
     * @return the value of the continent.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the territories in the continent.
     * @return the territories in the continent.
     */
    public ArrayList<Territory> getTerritories() {
        return this.countries;
    }

    /**
     * Checks if the whole continent is occupied by a single person.
     * @return true if a player owns all the continent
     */
    public boolean isOccupied() {
        if (countries.size() == 0) {
            return false;
        }
        for (int i = 1; i < countries.size(); i++) {
            if (countries.get(i).getOwner() == null
                || countries.get(i).getOwner() != countries.get(i - 1).getOwner()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the owner of the whole continent. If there is no unique owner, it returns null.
     * @return null if the country is now owned by a single person,
     *          otherwise returns the person that owns all the continent
     */
    public Player getOwner() {
        if (isOccupied()) {
            return countries.get(0).getOwner();
        }
        return null;
    }
}
