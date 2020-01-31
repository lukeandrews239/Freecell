package cs3500.freecell.hw02;

/**
 * Enum to represent a playing card's numerical value.
 */
public enum CardNumber {
  ACE("A"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9"),
  TEN("10"),
  JACK("J"),
  QUEEN("Q"),
  KING("K");

  private final String value;

  /**
   * Constructor to instantiate a CardNumber object.
   *
   * @param a String correlating with the associated card value enum case.
   */
  CardNumber(String a) {
    this.value = a;
  }

  /**
   * Method to derive the String Value of this object.
   *
   * @return A String representing the value of this CardNumber.
   */
  public String stringVal() {
    return this.value;
  }

  /**
   * A method to derive the card value of this object in int form.
   *
   * @return an int representing the value of this card.
   */
  public int cardValue() {
    switch (this) {
      case ACE:
        return 1;
      case TWO:
        return 2;
      case THREE:
        return 3;
      case FOUR:
        return 4;
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
        return 10;
      case JACK:
        return 11;
      case QUEEN:
        return 12;
      case KING:
        return 13;
      default:
        throw new IllegalStateException("Unexpected value: " + this);
    }
  }
}
