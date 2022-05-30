package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Describes the board of the game containing the map of the world.
 *
 * @author moralj @usi.ch, dallem@usi.ch
 */
public class Board {
    //region CONSTANTS
    /**
     * The constant PATH_ADJ.
     */
    public static final String PATH_ADJ = "src/model/data/adjacency.txt";
    //endregion

    //region FIELDS
    /**
     * The Adjacency.
     */
    public final ArrayList<ArrayList<Integer>> adjacency;
    private final HashMap<String, Integer> mapTerritoryToIdx;
    private final ArrayList<Territory> territories;
    private final ArrayList<Continent> continents;
    //endregion

    //region CONSTRUCTOR
    /**
     * Constructor.
     */
    public Board() {
        this.adjacency = new ArrayList<>();
        this.mapTerritoryToIdx = new HashMap<>();
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.initMap();
    }
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Getter of territories field.
     *
     * @return ArrayList of territories.
     */
    public ArrayList<Territory> getTerritories() {
        return this.territories;
    }

    /**
     * Getter of continents field.
     *
     * @return ArrayList of continents.
     */
    public ArrayList<Continent> getContinents() {
        return this.continents;
    }

    /**
     * Getter of index of given territory name.
     *
     * @param name String name of the territory.
     * @return index int of the position of the territory in territories field.
     */
    public int getTerritoryIdx(final String name) {
        return this.mapTerritoryToIdx.get(name);
    }

    /**
     * Getter of the adjacency field.
     *
     * @return adjacency list.
     */
    public ArrayList<ArrayList<Integer>> getAdjacency() {
        return this.adjacency;
    }

    /**
     * Getters of the adjacent field.
     *
     * @param territory object territory.
     * @return arraylist of adjacent territory to the given one.
     */
    public ArrayList<Territory> getAdjacent(Territory territory) {
        ArrayList<Territory> adjacent = new ArrayList<>();
        for (final int idx : this.adjacency.get(this.getTerritoryIdx(territory.getName()))) {
            adjacent.add(this.territories.get(idx));
        }
        return adjacent;
    }
    //endregion

    //region METHODS

    /**
     * Procedure - initialize contients and territories, setting adjacency list.
     */
    private void initMap() {
        try (final Scanner scanContinents = new Scanner(new File(Continent.PATH_CONTINENTS));
             final Scanner scanBonus = new Scanner(new File(Continent.PATH_BONUS));
             final Scanner scanTerritories = new Scanner(new File(Territory.PATH_TERRITORIES));
             final Scanner scanAdj = new Scanner(new File(PATH_ADJ))) {

            // create every continent
            while (scanContinents.hasNextLine() && scanBonus.hasNextLine()) {
                final String continentName = scanContinents.nextLine();
                final int bonus = Integer.parseInt(scanBonus.nextLine());
                final ArrayList<Territory> continentTerritories = new ArrayList<>();
                // foreach continent, get its territories
                final String[] splitTerritories = scanTerritories.nextLine().split(",");
                for (final String territoryName : splitTerritories) {
                    final Territory tmp = new Territory(territoryName);
                    this.territories.add(tmp);
                    this.mapTerritoryToIdx.put(territoryName, this.territories.size() - 1);
                    continentTerritories.add(tmp);
                }
                this.continents.add(new Continent(continentName, continentTerritories, bonus));
            }

            // get adjacency
            for (int i = 0; i < this.territories.size(); i++) {
                // foreach territory
                final String[] splitAdj = scanAdj.nextLine().split(",");
                this.adjacency.add(new ArrayList<>());
                for (final String adjName : splitAdj) {
                    // get its adjacent
                    this.adjacency.get(i).add(this.getTerritoryIdx(adjName));
                }
            }
        } catch (final FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
    //endregion
}
