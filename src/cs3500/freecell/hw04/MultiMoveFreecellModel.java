package cs3500.freecell.hw04;

import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;

/**
 * A subclass of Freecell model representing a game of Freecell that allows for the moving of
 * multiple cards at once.
 */
public final class MultiMoveFreecellModel extends FreecellModel {

  /**
   * Move one card or multiple cards from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param source         the type of the source pile see {@link PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved, such that all subsequent
   *                       cards in the source pile will moved together, if they
   *                       constitute a valid build
   * @param destination    the type of the destination pile (see {@link PileType})
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalStateException if the game is not started
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) {
    if (!this.gameHasBegun()) {
      throw new IllegalStateException("Game hasn't started!");
    }
    int adjustedPileNum = pileNumber + 1;
    int adjustedDestNum = destPileNumber + 1;
    IllegalArgumentException invalidMoveException = new IllegalArgumentException("Invalid move.");
    if (source == null || destination == null || cardIndex < 0) {
      throw invalidMoveException;
    }
    if (this.oneCardMove(source, adjustedPileNum, cardIndex)) {
      super.move(source, pileNumber, cardIndex, destination, destPileNumber);
      return;
    }
    if (!this.verifyBuildValidity(source, adjustedPileNum, cardIndex)
            || destination != PileType.CASCADE) {
      throw invalidMoveException;
    }
    int availableOpens = this.deriveOpenSpotCount(OpenSpotType.OPENS);
    int availableCascades = this.deriveOpenSpotCount(OpenSpotType.CASCADES);
    int maximumMove = (int) ((availableOpens + 1) * (Math.pow(2.0, availableCascades)));
    if (maximumMove < this.cardsToBeMoved(source, adjustedPileNum, cardIndex).size()) {
      throw invalidMoveException;
    } else {
      this.performMultiMove(source, adjustedPileNum, cardIndex, destination, adjustedDestNum);
    }
  }
}
