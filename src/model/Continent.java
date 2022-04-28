package model;

import model.enums.ContinentName;

import java.util.ArrayList;

/**
 * Represents a continent in the Risk map.
 */
public class Continent {

    private ContinentName name;
    private ArrayList<Territory> countries;
    private int value;

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
            if (countries.get(i).getOwner() != countries.get(i-1).getOwner()) {
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
