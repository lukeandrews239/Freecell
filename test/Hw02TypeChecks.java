import java.util.List;

import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.FreecellOperations;

/**
 * Do not modify this file. This file should compile correctly with your code!
 */
public class Hw02TypeChecks {

  public static void main(String[] args) {
    helper(new FreecellModel());
  }

  private static <T> void helper(FreecellOperations<T> model) {
    List<T> deck = model.getDeck();
    model.startGame(deck, 8, 1, false);
  }
}
