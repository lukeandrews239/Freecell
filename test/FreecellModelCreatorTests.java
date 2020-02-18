import org.junit.Test;

import cs3500.freecell.hw04.FreecellModelCreator;
import cs3500.freecell.hw04.MultiMoveFreecellModel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class FreecellModelCreatorTests {

  private FreecellModelCreator factory = new FreecellModelCreator();

  @Test
  public void testCreateGame() {
    assertFalse(FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE)
            instanceof MultiMoveFreecellModel);
    assertTrue(FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE)
            instanceof MultiMoveFreecellModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadInput() {
    FreecellModelCreator.create(null);
  }
}
