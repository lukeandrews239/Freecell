package cs3500.freecell.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * A class representing the model for a game of Freecell.
 */
public class FreecellModel implements FreecellOperations<Card> {

  private Piles piles;
  private List<Card> deck;

  /**
   * Constructor to begin a game of Freecell.
   */
  public FreecellModel() {
    this.piles = new Piles();
  }

  /**
   * Private factory method that generates an instance of a game of Freecell that has been won.
   *
   * @return "near win" Freecellmodel instance.
   */
  private FreecellModel nearWinModel() {
    FreecellModel model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 1, false);
    int acc = 1;
    for (ArrayList each : model.piles.cascades) {
      model.piles.foundations[acc] = each;
      if (each != null && !each.isEmpty()) {
        acc += 1;
      }
    }
    return model;
  }

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is no restriction
   * imposed on the ordering of these cards in the deck. An invalid deck is defined as a deck that
   * has one or more of these flaws: <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @return the deck of cards as a list
   */
  @Override
  public List<Card> getDeck() {
    if (deck != null) {
      return this.deck;
    }
    return Card.generateDeck();
  }

  /**
   * Deal a new game of freecell with the given deck, with or without shuffling it first. This
   * method first verifies that the deck is valid. It deals the deck among the cascade piles in
   * roundrobin fashion. Thus if there are 4 cascade piles, the 1st pile will get cards 0, 4, 8,
   * ..., the 2nd pile will get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10, ... and
   * the 4th pile will get cards 3, 7, 11, .... Depending on the number of cascade piles, they may
   * have a different number of cards
   *
   * @param deck            the deck to be dealt
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (!(FreecellModel.verifyDeckValidity(deck))) {
      throw new IllegalArgumentException("Invalid deck");
    }
    List<Card> deckCopy = new ArrayList<Card>();
    for (Card each : deck) {
      deckCopy.add(each);
    }
    if (shuffle) {
      Collections.shuffle(deckCopy);
    }
    this.deck = deckCopy;
    this.piles = new Piles();
    piles.organizeCascade(numCascadePiles, deckCopy, numOpenPiles);
  }

  private static boolean verifyDeckValidity(List<Card> deck) {
    if (deck == null || deck.size() != 52) {
      return false;
    }
    List<Card> seenCards = new ArrayList<Card>();
    for (Card card : deck) {
      try {
        card.cardNumber().cardValue();
      } catch (IllegalStateException e) {
        return false;
      }
      for (Card each : seenCards) {
        if (each.equals(card)) {
          return false;
        }
      }
      if (card.suit().stringValue() == null) {
        return false;
      }
      seenCards.add(card);
    }
    return true;
  }

  /**
   * Move a card from the given source pile to the given destination pile, if the move is valid.
   *
   * @param source         the type of the source pile see {@link PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destination    the type of the destination pile (see {@link PileType})
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalStateException    if the game is not started
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) {
    if (this.deck == null) {
      throw new IllegalStateException("Game hasn't started!");
    }
    int adjustedPileNum = pileNumber + 1;
    int adjustedDestNum = destPileNumber + 1;
    IllegalArgumentException invalidMoveException = new IllegalArgumentException("Invalid move.");
    if (source == null || destination == null || cardIndex < 0) {
      throw invalidMoveException;
    }
    if (!this.piles.verifyMoveAndExecute(source, adjustedPileNum, destination, adjustedDestNum)) {
      throw invalidMoveException;
    }
  }

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return piles.checkGameState();
  }

  /**
   * Return the card at the given index in the specified pile.
   *
   * @param pile       the type of the pile
   * @param pileNumber the pile number of the given type, starting from 0
   * @param cardIndex  the index of the card, starting from 0
   * @return the card
   * @throws IllegalStateException    if the game is not started
   * @throws IllegalArgumentException if there is no such card
   */
  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    if (this.deck == null) {
      throw new IllegalStateException("Game hasn't started!");
    }
    if (pile == null) {
      throw new IllegalArgumentException("Null pile");
    }
    return piles.getCard(pile, pileNumber + 1, cardIndex);
  }

  /**
   * Return the present state of the game as a string. The string is formatted as follows:
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in
   * order)
   * O1:[b]o11[n] (Cards in open pile 1)
   * O2:[b]o21[n] (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n] (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n] (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n] (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps (Cards in cascade pile s in order)
   *
   * where [b] is a single blankspace, [n] is newline. Note that there is no
   * newline on the last line
   * </pre>
   *
   * @return the formatted string as above, or empty string if the game is not started
   */
  @Override
  public String getGameState() {
    if (deck == null) {
      return "";
    }
    return piles.compileGameString();
  }


  /**
   * Private class for keeping track of the game's piles.
   */
  private class Piles {

    private ArrayList<Card>[] cascades;
    private ArrayList<Card>[] foundations;
    private Card[] opens;
    private int numCascadePiles = 8;
    boolean found = false;
    private int numOpens = 4;

    private Piles() {
      this.cascades = new ArrayList[9];
      this.foundations = new ArrayList[5];
      this.opens = new Card[5];
      for (int i = 1; i < 9; i++) {
        this.cascades[i] = new ArrayList<Card>();
      }
      for (int i = 1; i < 5; i++) {
        this.foundations[i] = new ArrayList<Card>();
      }
    }

    private boolean checkGameState() {
      int acc = 0;
      for (int i = 1; i < 5; i++) {
        acc += foundations[i].size();
      }
      return acc == 52;
    }

    private void organizeCascade(int numPiles, List<Card> deck, int numOpens) {
      int cascadeNum = 1;
      if (numPiles < 4 || numOpens < 1) {
        throw new IllegalArgumentException("Can't do that");
      }
      this.numCascadePiles = numPiles;
      this.numOpens = numOpens;
      this.opens = new Card[numOpens + 1];
      this.cascades = new ArrayList[numPiles + 1];
      for (int i = 1; i <= numPiles; i++) {
        this.cascades[i] = new ArrayList<Card>();
      }
      for (int i = 0; i < deck.size(); i++) {
        cascades[cascadeNum].add(deck.get(i));
        cascadeNum = cascadeNum == numPiles ? 1 : cascadeNum + 1;
      }
    }

    private boolean verifyMoveAndExecute(PileType sourcePile, int sourcePileNumber,
                                         PileType destPile, int destPileNum) {
      Card subjectOfInterest;
      switch (sourcePile) {
        case OPEN:
          if (sourcePileNumber > numOpens || sourcePileNumber < 1) {
            return false;
          }
          if (opens[sourcePileNumber] == null) {
            return false;
          }
          subjectOfInterest = opens[sourcePileNumber];
          break;
        case CASCADE:
          if (sourcePileNumber > numCascadePiles || sourcePileNumber < 1) {
            return false;
          }
          if (cascades[sourcePileNumber] == null || cascades[sourcePileNumber].isEmpty()) {
            return false;
          }
          subjectOfInterest = cascades[sourcePileNumber].get(cascades[sourcePileNumber].size() - 1);
          this.found = opens[destPileNum] == null;
          break;
        case FOUNDATION:
        default:
          return false;
      }
      if (subjectOfInterest == null) {
        return false;
      }
      switch (destPile) {
        case OPEN:
          if (destPileNum > numOpens || destPileNum < 1) {
            return false;
          }
          if (opens[destPileNum] != null) {
            return false;
          }
          opens[destPileNum] = subjectOfInterest;
          if (sourcePile == PileType.OPEN) {
            opens[sourcePileNumber] = null;
          } else {
            cascades[sourcePileNumber].remove(cascades[sourcePileNumber].size() - 1);
          }
          return true;
        case CASCADE:
          if (destPileNum > numCascadePiles || destPileNum < 1) {
            return false;
          }
          if (cascades[destPileNum] == null) {
            cascades[destPileNum] = new ArrayList<Card>(Arrays.asList(subjectOfInterest));
          } else if (cascades[destPileNum].isEmpty()) {
            cascades[destPileNum].add(subjectOfInterest);
          }
          Card previousCard = cascades[destPileNum].get(cascades[destPileNum].size() - 1);
          if (subjectOfInterest.deriveCardColor() != null
                  && previousCard.deriveCardColor() != null) {
            if (subjectOfInterest.deriveCardColor() != previousCard.deriveCardColor()
                    && subjectOfInterest.cardNumber().cardValue()
                    == previousCard.cardNumber().cardValue() - 1) {
              cascades[destPileNum].add(subjectOfInterest);
              if (sourcePile == PileType.OPEN) {
                opens[sourcePileNumber] = null;
              } else {
                cascades[sourcePileNumber].remove(cascades[sourcePileNumber].size() - 1);
              }
              return true;
            }
          }
          return false;
        case FOUNDATION:
          if (destPileNum > 4 || destPileNum < 1) {
            return false;
          }
          boolean shouldRemove = false;
          if (foundations[destPileNum] == null) {
            if (subjectOfInterest.cardNumber().cardValue() != 1) {
              return false;
            }
            foundations[destPileNum] = new ArrayList<>(Arrays.asList(subjectOfInterest));
            shouldRemove = true;
          } else if (foundations[destPileNum].isEmpty()) {
            if (subjectOfInterest.cardNumber().cardValue() != 1) {
              return false;
            }
            foundations[destPileNum].add(subjectOfInterest);
            shouldRemove = true;
          } else {
            Card prior = foundations[destPileNum].get(foundations[destPileNum].size() - 1);
            if (prior.suit() != subjectOfInterest.suit()) {
              return false;
            }
            if (prior.cardNumber().cardValue() + 1 != subjectOfInterest.cardNumber().cardValue()) {
              return false;
            }
            foundations[destPileNum].add(subjectOfInterest);
            shouldRemove = true;
          }
          if (shouldRemove) {
            if (sourcePile == PileType.OPEN) {
              opens[sourcePileNumber] = null;
            } else {
              cascades[sourcePileNumber].remove(cascades[sourcePileNumber].size() - 1);
            }
            return true;
          }
          return false;
        default:
          return false;
      }
    }

    private Card getCard(PileType pile, int pileNum, int cardIndex) {
      IllegalArgumentException exception = new IllegalArgumentException("Invalid parameters.");
      if (pileNum < 1 || cardIndex < 0) {
        throw exception;
      }
      switch (pile) {
        case OPEN:
          if (pileNum > numOpens || cardIndex != 0) {
            throw exception;
          }
          return opens[pileNum];
        case CASCADE:
          if (pileNum > numCascadePiles) {
            throw exception;
          }
          if (cascades[pileNum] != null) {
            if (cardIndex >= 0 && cardIndex < cascades[pileNum].size()) {
              return cascades[pileNum].get(cardIndex);
            }
          }
          throw exception;
        case FOUNDATION:
          if (pileNum < 5) {
            if (cardIndex < foundations[pileNum].size()) {
              return foundations[pileNum].get(cardIndex);
            }
          }
          throw exception;
        default:
          throw exception;
      }
    }

    private String compileGameString() {
      String acc = "";
      for (int i = 1; i < 5; i++) {
        String allCards = "";
        if (foundations[i] != null) {
          for (Card each : foundations[i]) {
            allCards += " " + each.toString() + ",";
          }
        }
        if (!allCards.isEmpty()) {
          allCards = allCards.substring(0, allCards.length() - 1) + "\n";
        } else {
          allCards += "\n";
        }
        acc += "F" + String.valueOf(i) + ":" + allCards;
      }
      for (int i = 1; i <= numOpens; i++) {
        String allCards = "";
        String openVal = opens[i] != null ? " " + opens[i].toString() : "";
        allCards = "O" + String.valueOf(i) + ":" + openVal + "\n";
        acc += allCards;
      }
      for (int i = 1; i <= numCascadePiles; i++) {
        String allCards = "";
        if (cascades[i] != null) {
          for (Card each : cascades[i]) {
            allCards += " " + each.toString() + ",";
          }
        }
        if (!allCards.isEmpty()) {
          allCards = allCards.substring(0, allCards.length() - 1) + "\n";
        } else {
          allCards += "\n";
        }
        acc += "C" + String.valueOf(i) + ":" + allCards;
      }
      return acc.substring(0, acc.length() - 1);
    }
  }
}