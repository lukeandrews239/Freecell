package cs3500.freecell.hw03;

import java.util.List;

import cs3500.freecell.hw02.FreecellOperations;

/**
 * Generic interface to represent the mandatory functionality of a FreecellController.
 */
public interface IFreecellController<K> {

  /**
   * Starts a game of Freecell.
   *
   * @param deck        a valid deck.
   * @param model       a valid model object that implements the FreecellOperations interface.
   * @param numCascades int representing the selected number of cascade piles for the game.
   * @param numOpens    int representing the selected number of open piles.
   * @param shuffle     boolean indicating whether or not the deck should be shuffled.
   * @throws IllegalArgumentException in the case of invalid parameters.
   * @throws IllegalStateException    if this object hasn't been properly initalized.
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades,
                int numOpens, boolean shuffle);

}
