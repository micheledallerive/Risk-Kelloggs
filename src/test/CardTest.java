package test;

import static org.junit.Assert.assertEquals;

import model.Card;
import model.enums.CardType;

import org.junit.Test;

/**
 * This class tests the functionality of the class Card
 */
public class CardTest {

    @Test
    public void testConstructorGetters() {
        Card card = new Card(CardType.INFANTRY, "AFGHANISTAN");
        assertEquals(CardType.INFANTRY, card.getType());
        assertEquals("AFGHANISTAN", card.getTerritory());
    }
}
