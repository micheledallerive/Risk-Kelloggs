package model;

import model.Territory.TerritoryName;
import model.enums.ContinentName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


/**
 * Represents a continent in the Risk map.
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class Continent {
    //region FIELDS
    public static final List<List<TerritoryName>> TERRITORIES = new ArrayList<>();
    public static final int[] EXTRA_VALUES = new int[] {5, 2, 5, 7, 3, 2};

    private final ContinentName name;
    private final ArrayList<Territory> countries;
    private final int value;  // index of the continent in territories
    //endregion

    //region CONSTRUCTOR
    /**
     * Creates a new continent in the map.
     * @param name      the name of the continent
     * @param countries the countries that are inside the continent
     * @param value     the value if a player owns the whole continent
     */
    public Continent(ContinentName name, ArrayList<Territory> countries, int value) {
        this.name = name;
        this.countries = countries;
        this.value = value;
    }
    //endregion

    //region GETTERS AND SETTERS

    /**
     * Function - get continent values of a specified range in TerritoryName ordered enum declarations.
     * @param start Start index number
     * @param end End index number
     * @return Territories of a continent ranged enum values in array collection.
     */
    private static TerritoryName[] getContinentValues(final byte start, final byte end) {
        TerritoryName[] array = new TerritoryName[end + 1 - start];
        for (byte i = start; i <= end; i++) {
            array[i - start] = TerritoryName.values()[i];
        }
        return array;
    }

    /**
     * Function - get continent territories specified by continent name enum value.
     * @param continent Continent enum value to get territories from.
     * @return Territories enum array.
     */
    public static TerritoryName[] getContinentTerritories(final ContinentName continent) {
        TerritoryName[] result = null;
        switch (continent) {
            case NORTH_AMERICA:
                result = getContinentValues(Territory.INIT_NORTH_AMERICA, Territory.END_NORTH_AMERICA);
                break;
            case SOUTH_AMERICA:
                result = getContinentValues(Territory.INIT_SOUTH_AMERICA, Territory.END_SOUTH_AMERICA);
                break;
            case EUROPE:
                result = getContinentValues(Territory.INIT_EUROPE, Territory.END_EUROPE);
                break;
            case ASIA:
                result = getContinentValues(Territory.INIT_ASIA, Territory.END_ASIA);
                break;
            case AFRICA:
                result = getContinentValues(Territory.INIT_AFRICA, Territory.END_AFRICA);
                break;
            case AUSTRALIA:
                result = getContinentValues(Territory.INIT_AUSTRALIA, Territory.END_AUSTRALIA);
                break;
            default:break;
        }
        return result;
    }

    /**
     * Initialize the Continent static values.
     */
    public static void init() {
        for (final ContinentName continentName : ContinentName.values()) {
            final TerritoryName[] continentTerritories = getContinentTerritories(continentName);
            TERRITORIES.add(Arrays.asList(continentTerritories));
        }
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
    //endregion

    //region METHODS
    /**
     * Checks if the whole continent is occupied by a single person.
     * @return true if a player owns all the continent
     */
    public boolean isOccupied() {
        if (countries.isEmpty()) {
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

    @Override
    public String toString() {
        String str = name.toString();
        String[] parts = str.split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part.substring(0,1).toUpperCase(Locale.US));
            builder.append(part.substring(1).toLowerCase(Locale.US));
            builder.append(" ");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }
    //endregion
}
