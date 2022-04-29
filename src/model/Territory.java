package model;

import model.enums.TerritoryName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a Territory in the Risk map.
 * @author dallem@usi.ch
 */
public class Territory {

    private ArrayList<Territory> adjacent;
    private Player owner;
    private ArrayList<Army> armies;
    private TerritoryName name;

    public static List<List<TerritoryName>> adjacency = new ArrayList<>();

    /**
     * Procedure - get from file the adjacency territories and store it in adjacency list collection.
     * @throws IOException exception
     */
    public static void init() throws IOException {
        File file = new File("../data/adjacency.csv");
        if (file.exists()) {
            InputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                List<TerritoryName> adj = new ArrayList<TerritoryName>();
                for (String part : parts) {
                    adj.add(TerritoryName.valueOf(part));
                }
                adjacency.add(adj);
            }
            stream.close();
        } else {
            throw new FileNotFoundException("The territory adjacency file was not found");
        }
    }

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
     * Sets the owner of the territory.
     * @param owner the player that owns the territory
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Sets the adjacent territories.
     * @param adjacent the territories that are adjacent to this territory
     */
    public void setAdjacent(ArrayList<Territory> adjacent) {
        this.adjacent = adjacent;
    }
}
