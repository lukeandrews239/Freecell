package cs3500.freecell.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to represent a playing Card.
 */
public class Card {

  private final CardNumber cardNumber;
  private final Suit suit;

  /**
   * Constructor to instantiate a playing card object.
   *
   * @param value a CardNumber representing the value of this playing card.
   * @param suit  a Suit enum to represent the suit of this playing card.
   */
  public Card(CardNumber value, Suit suit) {
    this.cardNumber = value;
    this.suit = suit;
  }

  /**
   *  Method to retrieve the CardNumber associated with this Card.
   *
   * @return This object;s CardNumber.
   */
  public CardNumber cardNumber() {
    return this.cardNumber;
  }

  /**
   *  Method to retrieve the Suit associated with this Card.
   *
   * @return This object's Suit.
   */
  public Suit suit() {
    return this.suit;
  }

  /**
   * Static method to generate a new "deck" of playing cards.
   *
   * @return A List of Cards to represent a deck of playing cards.
   */
  public static List<Card> generateDeck() {
    List<Suit> availableSuits = Arrays.asList(Suit.values());
    List<CardNumber> availableCards = Arrays.asList(CardNumber.values());
    List<Card> deck = new ArrayList<Card>();
    for (Suit suit : availableSuits) {
      for (CardNumber card : availableCards) {
        deck.add(new Card(card, suit));
      }
    }
    return deck;
  }

  @Override
  public String toString() {
    return this.cardNumber.stringVal() + this.suit.stringValue();
  }

  /**
   * Method that returns a char representing the color of this card.
   *
   * @return A character representing this card's color.
   */
  public Character deriveCardColor() {
    switch (suit) {
      case DIAMONDS:
      case HEARTS:
        return 'r';
      case CLUBS:
      case SPADES:
        return 'b';
      default:
        throw new IllegalStateException("Unhandled Suit type");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Card)) {
      return false;
    }
    Card otherCard = (Card) o;
    return this.cardNumber == otherCard.cardNumber && this.suit == otherCard.suit;
  }

  // Use any unique 4 numbers to represent these codes.
  @Override
  public int hashCode() {
    int code = 0;
    switch (suit) {
      case HEARTS:
        code += 1000;
        break;
      case SPADES:
        code += 1;
        break;
      case DIAMONDS:
        code += 2030;
        break;
      case CLUBS:
        code += 212121;
        break;
      default:
        return 0;
    }
    return code + this.cardNumber.cardValue();
  }
}
