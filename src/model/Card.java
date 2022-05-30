package model;

import model.enums.CardType;

/**
 * Describes a card with territory and figure.
 *
 * @author dallem @usi.ch
 */
public class Card {
    //region FIELDS
    private final CardType type;
    private final String territory;
    //endregion

    //region CONSTRUCTOR

    /**
     * Create a new card.
     *
     * @param type      The type of the card that is represented                  in the bottom (infantry, cavalry,
     *                  artillery, wild).
     * @param territory The territory represented in the top of the card.
     */
    public Card(final CardType type, final String territory) {
        this.type = type;
        this.territory = territory;
    }
    //endregion

    //region GETTERS AND SETTERS

    /**
     * Returns the type of the card.
     *
     * @return The type of the card
     */
    public CardType getType() {
        return this.type;
    }

    /**
     * Returns the territory of the card.
     *
     * @return The territory of the card.
     */
    public String getTerritory() {
        return this.territory;
    }
    //endregion

    //region METHODS

    /**
     * Checks if the trio of cards is a valid trio to get a bonus.
     *
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
     *
     * @param cards the trio of cards
     * @return the type of the trio: 1 if same type, 2 if different, 3 if wild
     */
    public static int trioType(final Card[] cards) {
        if (sameTypes(cards[0], cards[1], cards[2])) {
            return 1;
        } else if (wildTrio(cards[0], cards[1], cards[2])) {
            return 3;
        }
        return 2;
    }

    /**
     * Checks if the three cards have the same type.
     *
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
     *
     * @param c1 the first card
     * @param c2 the second card
     * @param c3 the third card
     * @return true if the cards have a different type
     */
    private static boolean differentTypes(final Card c1, final Card c2, final Card c3) {
        return c1.type != c2.type
            && c1.type != c3.type
            && c2.type != c3.type
            && !wildTrio(c1, c2, c3);
    }

    /**
     * Checks if the trio of cards is a valid trio containing a wild card.
     *
     * @param c1 the first card
     * @param c2 the second card
     * @param c3 the third card
     * @return true if the trio is valid combination with a wild card.
     */
    private static boolean wildTrio(final Card c1, final Card c2, final Card c3) {
        int wildCount = 0;
        for (final Card card : new Card[] {c1, c2, c3}) {
            if (card.type == CardType.WILD) {
                wildCount++;
            }
        }
        return wildCount == 1;
    }

    /**
     * Returns the value of the card combination.
     *
     * @param cards the trio of cards
     * @return the value of the card combination
     */
    public static int getCombinationValue(final Card[] cards) {
        switch (Card.trioType(cards)) {
            case 1: // same trio,
                // 3 artillery (ordinal 2) = 4, 3 infantry (ordinal 0) = 6, 3 cavalry (ordinal 1) = 8
                return 4 + (2 * ((cards[0].getType().ordinal() + 1) % 3));
            case 2: // three different cards
                return 10;
            default:
                return 12;
        }
    }

    /**
     * Function - gives representation of the card as String.
     *
     * @return The String with card data info.
     */
    @Override
    public String toString() {
        return "Card: ("
            + this.getType().name()
            + " - "
            + this.getTerritory()
            + ")";
    }
    //endregion
}
