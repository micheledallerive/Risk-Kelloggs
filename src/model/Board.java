package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Describes the board of the game containing the map of the world.
 * @author moralj@usi.ch, dallem@usi.ch
 */
public class Board {
    //region CONSTANTS
    public static final String PATH_ADJ = "src/model/data/adjacency.txt";
    //endregion

    //region FIELDS
    public final HashMap<Integer, ArrayList<Integer>> adjacency;
    private final HashMap<String, Integer> mapTerritoryToIdx;
    private final ArrayList<Territory> territories;
    private final ArrayList<Continent> continents;
    //endregion

    //region CONSTRUCTOR
    /**
     * Constructor.
     */
    public Board() {
        this.adjacency = new HashMap<>();
        this.mapTerritoryToIdx = new HashMap<>();
        this.territories = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.initMap();
    }
    //endregion

    //region GETTERS AND SETTERS
    public ArrayList<Territory> getTerritories() {
        return this.territories;
    }

    public ArrayList<Continent> getContinents() {
        return this.continents;
    }

    public int getTerritoryIdx(final String name) {
        return this.mapTerritoryToIdx.get(name);
    }

    public HashMap<Integer, ArrayList<Integer>> getAdjacency() {
        return this.adjacency;
    }
    //endregion

    //region METHODS
    private void initMap() {
        try {
            final Scanner scanContinents = new Scanner(new File(Continent.PATH_CONTINENTS));
            final Scanner scanBonus = new Scanner(new File(Continent.PATH_BONUS));
            final Scanner scanTerritories = new Scanner(new File(Territory.PATH_TERRITORIES));

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
            final Scanner scanAdj = new Scanner(new File(PATH_ADJ));
            for (final Territory terr : this.territories) {
                // foreach territory
                final String[] splitAdj = scanAdj.nextLine().split(",");
                final ArrayList<Integer> adjCurrent = new ArrayList<>();
                for (final String adjName : splitAdj) {
                    // get its adjacent
                    adjCurrent.add(this.getTerritoryIdx(adjName));
                }
                this.adjacency.put(this.getTerritoryIdx(terr.getName()), adjCurrent);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    //endregion
}
