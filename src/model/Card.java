package model;

import model.enums.CardType;
import model.enums.TerritoryName;

//import java.util.Arrays;
//import java.util.List;

/**
 * Describes a card with territory and figure.
 * @author dallem@usi.ch
 */
public class Card {
    //region FIELDS
    /* public static List<List<CardType>> combinations = Arrays.asList(
            Arrays.asList(CardType.ARTILLERY, CardType.ARTILLERY, CardType.ARTILLERY),
            Arrays.asList(CardType.CAVALRY, CardType.CAVALRY, CardType.CAVALRY),
            Arrays.asList(CardType.INFANTRY, CardType.INFANTRY, CardType.INFANTRY),
            Arrays.asList(CardType.ARTILLERY, CardType.CAVALRY, CardType.INFANTRY),
            Arrays.asList(CardType.ARTILLERY, CardType.ARTILLERY, CardType.WILD)
    ); // find usage to generate cards */

    private final CardType type;
    private final TerritoryName territory;
    //endregion

    //region CONSTRUCTOR
    /**
     * Create a new card.
     * @param type The type of the card that is represented
     *             in the bottom (infantry, cavalry, artillery, wild).
     * @param territory The territory represented in the top of the card.
     */
    public Card(final CardType type, final TerritoryName territory) {
        this.type = type;
        this.territory = territory;
    }
    //endregion

    //region GETTERS AND SETTERS
    /**
     * Returns the type of the card.
     * @return The type of the card
     */
    public CardType getType() {
        return this.type;
    }

    /**
     * Returns the territory of the card.
     * @return The territory of the card.
     */
    public TerritoryName getTerritory() {
        return this.territory;
    }
    //endregion

    //region METHODS
    public static boolean validTrio(final Card c1, final Card c2, final Card c3) {
        return sameTypes(c1, c2, c3) || differentTypes(c1, c2, c3) || wildTrio(c1, c2, c3);
    }

    private static boolean sameTypes(final Card c1, final Card c2, final Card c3) {
        return c1.type == c2.type && c2.type == c3.type;
    }

    private static boolean differentTypes(final Card c1, final Card c2, final Card c3) {
        return c1.type != c2.type && c1.type != c3.type && c2.type != c3.type;
    }

    private static boolean wildTrio(final Card c1, final Card c2, final Card c3) {
        return (c1.type == c2.type && c3.type == CardType.WILD)
                || (c1.type == c3.type && c2.type == CardType.WILD)
                || (c2.type == c3.type && c1.type == CardType.WILD);
    }

    /**
     * Function - gives representation of the card as String.
     * @return The String with card data info.
     */
    @Override
    public String toString() {
        return "Card: (" + this.getType().name() + "-" + this.getTerritory().name() +")";
    }
    //endregion
}
