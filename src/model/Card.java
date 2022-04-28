package model;

import model.enums.CardType;
import model.enums.TerritoryName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describes a card with territory and figure.
 */
public class Card {
    private CardType type;
    private TerritoryName territory;

    public static List<List<CardType>> combinations = Arrays.asList(
            Arrays.asList(CardType.ARTILLERY, CardType.ARTILLERY, CardType.ARTILLERY),
            Arrays.asList(CardType.CAVALRY, CardType.CAVALRY, CardType.CAVALRY),
            Arrays.asList(CardType.INFANTRY, CardType.INFANTRY, CardType.INFANTRY),
            Arrays.asList(CardType.ARTILLERY, CardType.CAVALRY, CardType.INFANTRY),
            Arrays.asList(CardType.ARTILLERY, CardType.ARTILLERY, CardType.WILD)
    );

    /**
     * Creates a new card.
     * @param type the type of the card that is represented
     *             in the bottom (infantry, cavallry, artillery, wild).
     * @param territory the territory represented in the top of the card.
     */
    public Card(CardType type, TerritoryName territory) {
        this.type = type;
        this.territory = territory;
    }

    /**
     * Returns the type of the card.
     * @return the type of the card
     */
    public CardType getType() {
        return this.type;
    }

    /**
     * Returns the territory of the card.
     * @return the territory of the card.
     */
    public TerritoryName getTerritory() {
        return this.territory;
    }

}
