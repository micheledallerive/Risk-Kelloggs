package model;

import model.enums.CardType;
import model.enums.TerritoryName;

/**
 * Describes a card with territory and figure.
 */
public class Card {
    private CardType type;
    private TerritoryName territory;

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
