package cs3500.freecell.hw04;

import cs3500.freecell.hw02.FreecellModel;

/**
 * A factory object for selecting a type of freecell game via the static "create" method.
 */
public final class FreecellModelCreator {

  /**
   * An enum to represent the possible types of freecell one can play.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * A static factory method for generating a Freecell Model, single-card or multi-card capable.
   *
   * @param type A GameType indicating the type of freecell solitaire this method will generate
   * @return a FreecellModel object
   * @throws IllegalArgumentException for null or unhandled GameType enum cases.
   */
  public static final FreecellModel create(GameType type) {
    IllegalArgumentException badTypeCheck = new IllegalArgumentException("Unhandled game type");
    if (type == null) {
      throw badTypeCheck;
    }
    switch (type) {
      case MULTIMOVE:
        return new MultiMoveFreecellModel();
      case SINGLEMOVE:
        return new FreecellModel();
      default:
        throw badTypeCheck;
    }
  }
}
