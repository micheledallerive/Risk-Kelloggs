package model;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Represents a continent in the Risk map.
 *
 * @author dallem@usi.ch, moralj@usi.ch
 */
public class Continent {
    //region CONSTANTS
    public static final String PATH_CONTINENTS = "src/model/data/continents.txt";
    public static final String PATH_BONUS = "src/model/data/bonus.txt";
    //endregion

    //region FIELDS
    public static final ArrayList<ArrayList<Territory>> TERRITORIES = new ArrayList<>();

    private final String name;
    private final ArrayList<Territory> territories;
    private final int value;  // index of the continent in territories
    //endregion

    //region CONSTRUCTOR

    /**
     * Creates a new continent in the map.
     *
     * @param name        the name of the continent
     * @param territories the countries that are inside the continent
     * @param value       the value if a player owns the whole continent
     */
    public Continent(String name, ArrayList<Territory> territories, int value) {
        this.name = name;
        this.territories = territories;
        this.value = value;
    }
    //endregion

    //region GETTERS AND SETTERS

    /**
     * Returns the name of the continent.
     *
     * @return the name of the continent.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the list of the countries in the continent.
     *
     * @return the countries in the continent
     */
    public ArrayList<Territory> getTerritories() {
        return this.territories;
    }

    /**
     * Returns the value of the continent.
     *
     * @return the value of the continent.
     */
    public int getValue() {
        return value;
    }
    //endregion

    //region METHODS

    /**
     * Checks if the whole continent is occupied by a single person.
     *
     * @return true if a player owns all the continent
     */
    public boolean isOccupied() {
        if (territories.isEmpty()) {
            return false;
        }

        for (int i = 1; i < territories.size(); i++) {
            if (territories.get(i).getOwner() == null
                || territories.get(i).getOwner() != territories.get(i - 1).getOwner()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the owner of the whole continent. If there is no unique owner, it returns null.
     *
     * @return null if the country is now owned by a single person,
     * otherwise returns the person that owns all the continent
     */
    public Player getOwner() {
        return isOccupied() ? territories.get(0).getOwner() : null;
    }

    @Override
    public String toString() {
        String str = name.toString();
        String[] parts = str.split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part.substring(0, 1).toUpperCase(Locale.US));
            builder.append(part.substring(1).toLowerCase(Locale.US));
            builder.append(" ");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }
    //endregion
}
