package model;

import model.Territory.TerritoryName;
import model.enums.CardType;

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

    /**
     * Checks if the trio of cards is a valid trio to get a bonus.
     * @param c1 the first card
     * @param c2 the second card
     * @param c3 the third card
     * @return true if the cards are a valid trio.
     */
    public static boolean validTrio(final Card c1, final Card c2, final Card c3) {
        return sameTypes(c1, c2, c3) || differentTypes(c1, c2, c3) || wildTrio(c1, c2, c3);
    }

    /**
     * Returns what type of trio it is.
     * @param cards the trio of cards
     * @return the type of the trio: 1 if same type, 2 if different, 3 if wild
     */
    public static int trioType(final Card[] cards) {
        if (sameTypes(cards[0], cards[1], cards[2])) {
            return 1;
        } else if (differentTypes(cards[0], cards[1], cards[2])) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Checks if the three cards have the same type.
     * @param c1 the first card
     * @param c2 the second card
     * @param c3 the third card
     * @return true if the cards have the same type
     */
    private static boolean sameTypes(final Card c1, final Card c2, final Card c3) {
        return c1.type == c2.type && c2.type == c3.type;
    }

    /**
     * Checks if the three cards have different type.
     * @param c1 the first card
     * @param c2 the second card
     * @param c3 the third card
     * @return true if the cards have a different type
     */
    private static boolean differentTypes(final Card c1, final Card c2, final Card c3) {
        return c1.type != c2.type && c1.type != c3.type && c2.type != c3.type;
    }

    /**
     * Checks if the trio of cards is a valid trio containing a wild card.
     * @param c1 the first card
     * @param c2 the second card
     * @param c3 the third card
     * @return true if the trio is valid combination with a wild card.
     */
    private static boolean wildTrio(final Card c1, final Card c2, final Card c3) {
        return c1.type == c2.type && c3.type == CardType.WILD
                || c1.type == c3.type && c2.type == CardType.WILD
                || c2.type == c3.type && c1.type == CardType.WILD;
    }

    /**
     * Function - gives representation of the card as String.
     * @return The String with card data info.
     */
    @Override
    public String toString() {
        return "Card: ("
                + this.getType().name()
                + "-"
                + this.getTerritory().name()
                + ")";
    }
    //endregion
}
