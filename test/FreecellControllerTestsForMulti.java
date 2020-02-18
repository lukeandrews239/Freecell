import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import cs3500.freecell.hw03.FreecellController;
import cs3500.freecell.hw03.IFreecellController;
import cs3500.freecell.hw04.MultiMoveFreecellModel;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the FreecellController implementation of the IFreecellController interface,
 * specifically to test the MultiModel.
 */
public class FreecellControllerTestsForMulti {

  String newLine = "\n";

  @Test
  public void testMultiMoveDisplayStr() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "C1 Q O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(m.getDeck(), m, 4, 5, false);
    String correctOutPut =
            "F1:"
                    .concat(newLine)
                    .concat("F2:")
                    .concat(newLine)
                    .concat("F3:")
                    .concat(newLine)
                    .concat("F4:")
                    .concat(newLine)
                    .concat("O1:")
                    .concat(newLine)
                    .concat("O2:")
                    .concat(newLine)
                    .concat("O3:")
                    .concat(newLine)
                    .concat("O4:")
                    .concat(newLine)
                    .concat("O5:")
                    .concat(newLine)
                    .concat("C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠")
                    .concat(newLine)
                    .concat("C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠")
                    .concat(newLine)
                    .concat("C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠")
                    .concat(newLine)
                    .concat("C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠")
                    .concat(newLine)
                    .concat(newLine)
                    .concat("Game quit prematurely.");
    assertEquals(correctOutPut, gameLog.toString());
  }


  @Test
  public void testUpperCaseQuitInDifferentPositionsMulti() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "C1 Q O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(m.getDeck(), m, 4, 5, false);
    String correctOutPut =
            "F1:"
                    .concat(newLine)
                    .concat("F2:")
                    .concat(newLine)
                    .concat("F3:")
                    .concat(newLine)
                    .concat("F4:")
                    .concat(newLine)
                    .concat("O1:")
                    .concat(newLine)
                    .concat("O2:")
                    .concat(newLine)
                    .concat("O3:")
                    .concat(newLine)
                    .concat("O4:")
                    .concat(newLine)
                    .concat("O5:")
                    .concat(newLine)
                    .concat("C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠")
                    .concat(newLine)
                    .concat("C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠")
                    .concat(newLine)
                    .concat("C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠")
                    .concat(newLine)
                    .concat("C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠")
                    .concat(newLine)
                    .concat(newLine)
                    .concat("Game quit prematurely.");
    assertEquals(correctOutPut, gameLog.toString());
  }

  @Test
  public void testUpperCaseQuitInDifferentPositions2Multi() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "Q Q O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(m.getDeck(), m, 4, 5, false);
    String correctOutPut =
            "F1:"
                    .concat(newLine)
                    .concat("F2:")
                    .concat(newLine)
                    .concat("F3:")
                    .concat(newLine)
                    .concat("F4:")
                    .concat(newLine)
                    .concat("O1:")
                    .concat(newLine)
                    .concat("O2:")
                    .concat(newLine)
                    .concat("O3:")
                    .concat(newLine)
                    .concat("O4:")
                    .concat(newLine)
                    .concat("O5:")
                    .concat(newLine)
                    .concat("C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠")
                    .concat(newLine)
                    .concat("C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠")
                    .concat(newLine)
                    .concat("C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠")
                    .concat(newLine)
                    .concat("C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠")
                    .concat(newLine)
                    .concat(newLine)
                    .concat("Game quit prematurely.");
    assertEquals(correctOutPut, gameLog.toString());
  }

  @Test
  public void testLowerCaseQuitInDifferentPositions2Multi() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "q Q O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(m.getDeck(), m, 4, 5, false);
    String correctOutPut =
            "F1:"
                    .concat(newLine)
                    .concat("F2:")
                    .concat(newLine)
                    .concat("F3:")
                    .concat(newLine)
                    .concat("F4:")
                    .concat(newLine)
                    .concat("O1:")
                    .concat(newLine)
                    .concat("O2:")
                    .concat(newLine)
                    .concat("O3:")
                    .concat(newLine)
                    .concat("O4:")
                    .concat(newLine)
                    .concat("O5:")
                    .concat(newLine)
                    .concat("C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠")
                    .concat(newLine)
                    .concat("C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠")
                    .concat(newLine)
                    .concat("C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠")
                    .concat(newLine)
                    .concat("C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠")
                    .concat(newLine)
                    .concat(newLine)
                    .concat("Game quit prematurely.");
    assertEquals(correctOutPut, gameLog.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadInstantiationReadableMulti() {
    IFreecellController controller = new FreecellController(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadInstantiationAppendableMulti() {
    IFreecellController controller = new FreecellController(new StringReader(""), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadDeckParamPostQuitMulti() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "Q Q O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(null, m, 4, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadModelParamPreQuitMulti() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "C1 2 O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(m.getDeck(), null, 4, 5, false);
  }

  @Test
  public void testInvalidModelParametersMulti() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    String coordinates = "C1 2 O1";
    FreecellController c = new FreecellController(new StringReader(coordinates), gameLog);
    c.playGame(m.getDeck(), m, 2, 5, true);
    assertEquals(gameLog.toString(), "Could not start game.");
  }

  @Test
  public void testOnlyBadInputMulti() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    StringBuilder gameLog = new StringBuilder();
    FreecellController c = new FreecellController(new StringReader("Csef1 -23a fds q"), gameLog);
    c.playGame(m.getDeck(), m, 4, 4, false);
    String correctOutPut =
            "F1:"
                    .concat(newLine)
                    .concat("F2:")
                    .concat(newLine)
                    .concat("F3:")
                    .concat(newLine)
                    .concat("F4:")
                    .concat(newLine)
                    .concat("O1:")
                    .concat(newLine)
                    .concat("O2:")
                    .concat(newLine)
                    .concat("O3:")
                    .concat(newLine)
                    .concat("O4:")
                    .concat(newLine)
                    .concat("C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠")
                    .concat(newLine)
                    .concat("C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠")
                    .concat(newLine)
                    .concat("C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠")
                    .concat(newLine)
                    .concat("C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠")
                    .concat(newLine)
                    .concat("Invalid source, please input again.Invalid source, ")
                    .concat("please input again.Invalid source, please input again.")
                    .concat(newLine)
                    .concat("Game quit prematurely.");
    assertEquals(correctOutPut, gameLog.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendableBehaviorMulti() {
    MultiMoveFreecellModel m = new MultiMoveFreecellModel();
    Appendable gameLog = new FailingAppendable();
    FreecellController c = new FreecellController(new StringReader("Csef1 -23a fds q"), gameLog);
    c.playGame(m.getDeck(), m, 4, 4, false);
  }

  /**
   * A mock to simulate a failure to write to the Appendable.
   */
  private class FailingAppendable implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("Fail!");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("Fail!");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("Fail!");
    }
  }
}