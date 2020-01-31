import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.CardNumber;
import cs3500.freecell.hw02.Suit;

/**
 * Test class for the Card object.
 */
public class CardTests {

  private Card aceOfDiamonds = new Card(CardNumber.ACE, Suit.DIAMONDS);
  private Card twoOfDiamonds = new Card(CardNumber.TWO, Suit.DIAMONDS);
  private Card twoOfHearts = new Card(CardNumber.TWO, Suit.HEARTS);
  private Card tenOfSpades = new Card(CardNumber.TEN, Suit.SPADES);
  private Card jackOfClubs = new Card(CardNumber.TEN, Suit.CLUBS);

  @Test
  public void testDeckGeneration() {
    List<Card> deck = null;
    assertEquals(deck, null);
    deck = Card.generateDeck();
    assertEquals(deck.size(), 52);
  }

  @Test
  public void testStringRepresentation() {
    assertEquals(aceOfDiamonds.toString(), "A♦");
    assertEquals(twoOfDiamonds.toString(), "2♦");
    assertEquals(twoOfHearts.toString(), "2♥");
  }

  @Test
  public void deriveCardColor() {
    assertEquals(String.valueOf(aceOfDiamonds.deriveCardColor()), "r");
    assertEquals(String.valueOf(twoOfDiamonds.deriveCardColor()), "r");
    assertEquals(String.valueOf(twoOfHearts.deriveCardColor()), "r");
    assertEquals(String.valueOf(tenOfSpades.deriveCardColor()), "b");
    assertEquals(String.valueOf(jackOfClubs.deriveCardColor()), "b");
  }

  @Test
  public void testEqualsAndHashCode() {
    assertFalse(aceOfDiamonds.equals(twoOfDiamonds));
    assertFalse(aceOfDiamonds.equals(tenOfSpades));
    assertFalse(twoOfHearts.equals(twoOfDiamonds));
    assertTrue(aceOfDiamonds.equals(new Card(CardNumber.ACE, Suit.DIAMONDS)));
  }

  @Test
  public void testSuitAndValueStringsAndSuitAndCardNumber() {
    assertEquals(aceOfDiamonds.cardNumber().stringVal(), "A");
    assertEquals(twoOfDiamonds.cardNumber().stringVal(), "2");
    assertEquals(aceOfDiamonds.suit().toString(), twoOfDiamonds.suit().toString());
    assertEquals(aceOfDiamonds.suit().stringValue(), "♦");
  }

  @Test
  public void testCardValueAndCardNumber() {
    assertEquals(aceOfDiamonds.cardNumber().cardValue(), 1);
    assertEquals(twoOfDiamonds.cardNumber().cardValue(), 2);
    assertEquals(twoOfHearts.cardNumber().cardValue(), twoOfDiamonds.cardNumber().cardValue());
  }

}
