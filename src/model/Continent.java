package model;

import static model.enums.TerritoryName.AFGHANISTAN;
import static model.enums.TerritoryName.ALASKA;
import static model.enums.TerritoryName.ALBERTA;
import static model.enums.TerritoryName.ARGENTINA;
import static model.enums.TerritoryName.BRAZIL;
import static model.enums.TerritoryName.CENTRAL_AMERICA;
import static model.enums.TerritoryName.CHINA;
import static model.enums.TerritoryName.CONGO;
import static model.enums.TerritoryName.EASTERN_AUSTRALIA;
import static model.enums.TerritoryName.EASTERN_UNITED_STATES;
import static model.enums.TerritoryName.EAST_AFRICA;
import static model.enums.TerritoryName.EGYPT;
import static model.enums.TerritoryName.GREAT_BRITAIN;
import static model.enums.TerritoryName.GREENLAND;
import static model.enums.TerritoryName.ICELAND;
import static model.enums.TerritoryName.INDIA;
import static model.enums.TerritoryName.INDONESIA;
import static model.enums.TerritoryName.IRKUTSK;
import static model.enums.TerritoryName.JAPAN;
import static model.enums.TerritoryName.KAMCHATKA;
import static model.enums.TerritoryName.MADAGASCAR;
import static model.enums.TerritoryName.MIDDLE_EAST;
import static model.enums.TerritoryName.MONGOLIA;
import static model.enums.TerritoryName.NEW_GUINEA;
import static model.enums.TerritoryName.NORTHERN_EUROPE;
import static model.enums.TerritoryName.NORTH_AFRICA;
import static model.enums.TerritoryName.NORTH_WEST_TERRITORY;
import static model.enums.TerritoryName.ONTARIO;
import static model.enums.TerritoryName.PERU;
import static model.enums.TerritoryName.QUEBEC;
import static model.enums.TerritoryName.SCANDINAVIA;
import static model.enums.TerritoryName.SIAM;
import static model.enums.TerritoryName.SIBERIA;
import static model.enums.TerritoryName.SOUTHERN_EUROPE;
import static model.enums.TerritoryName.SOUTH_AFRICA;
import static model.enums.TerritoryName.UKRAINE;
import static model.enums.TerritoryName.URAL;
import static model.enums.TerritoryName.VENEZUELA;
import static model.enums.TerritoryName.WESTERN_AUSTRALIA;
import static model.enums.TerritoryName.WESTERN_EUROPE;
import static model.enums.TerritoryName.WESTERN_UNITED_STATES;
import static model.enums.TerritoryName.YAKUTSK;

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

    private ContinentName name;
    private ArrayList<Territory> countries;
    private int value;

    public static final List<List<TerritoryName>> TERRITORIES = Arrays.asList(
            Arrays.asList(ALASKA, NORTH_WEST_TERRITORY, GREENLAND,
                    ALBERTA, ONTARIO, QUEBEC, WESTERN_UNITED_STATES,
                    EASTERN_UNITED_STATES, CENTRAL_AMERICA),
            Arrays.asList(VENEZUELA, BRAZIL, PERU, ARGENTINA),
            Arrays.asList(ICELAND, SCANDINAVIA, GREAT_BRITAIN,
                    NORTHERN_EUROPE, UKRAINE, WESTERN_EUROPE,
                    SOUTHERN_EUROPE),
            Arrays.asList(NORTH_AFRICA, EGYPT, CONGO,
                    EAST_AFRICA, SOUTH_AFRICA, MADAGASCAR),
            Arrays.asList(URAL, SIBERIA, YAKUTSK, IRKUTSK,
                    KAMCHATKA, AFGHANISTAN, CHINA, MONGOLIA,
                    JAPAN, MIDDLE_EAST, INDIA, SIAM),
            Arrays.asList(INDONESIA, NEW_GUINEA,
                    WESTERN_AUSTRALIA, EASTERN_AUSTRALIA)
    );

    public static final int[] VALUES = new int[]{5,2,5,3,7,2};

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
     * Checks if the whole continent is occupied by a single person.
     * @return true if a player owns all the continent
     */
    public boolean isOccupied() {
        for (int i = 1; i < countries.size(); i++) {
            if (countries.get(i).getOwner() != countries.get(i - 1).getOwner()) {
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
