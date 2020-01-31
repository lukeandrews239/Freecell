package cs3500.freecell.hw02;

/**
 * Enum to represent the suit of a playing card.
 */
public enum Suit {
  CLUBS("♣"),
  DIAMONDS("♦"),
  HEARTS("♥"),
  SPADES("♠");

  private final String suit;

  /**
   * Constructor to instantiate a Suit instance with a raw enum value.
   */
  Suit(String s) {
    this.suit = s;
  }

  /**
   * A method to return the String representing this suit.
   *
   * @return A string representing this suit.
   */
  public String stringValue() {
    return this.suit;
  }
}
