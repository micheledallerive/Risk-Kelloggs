package test;

import model.Card;
import model.enums.CardType;
import model.enums.TerritoryName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the functionality of the class Card
 */
public class CardTest {

    @Test
    public void testConstructorGetters() {
        Card card = new Card(CardType.ARTILLERY, TerritoryName.AFGHANISTAN);
        assertEquals(CardType.ARTILLERY, card.getType());
        assertEquals(TerritoryName.AFGHANISTAN, card.getTerritory());
    }
}
