import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;

/**
 * Test class for the FreecellModel implementation of the FreecellOperations interface.
 */
public class FreecellModelTests {

  // Type of this variable is the interface, as there are no other publicly accessible methods.
  private FreecellOperations model = new FreecellModel();

  @Test
  public void testGetDeck() {
    List<Card> deck = model.getDeck();
    assertEquals(deck.size(), 52);
    // Starting the game verifies the validity of a deck- attempt to do so.
    try {
      model.startGame(deck, 5, 2, false);
    } catch (IllegalArgumentException e) {
      fail("Deck was not valid.");
    }
    assertFalse(model.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameBadDeck() {
    model.startGame(new ArrayList<Card>(), 5, 3, true);
  }

  @Test
  public void testStartGameWellAndRepeat() {
    model.startGame(model.getDeck(), 5, 3, false);
    String prior = model.getGameState();
    model.startGame(model.getDeck(), 5, 3, false);
    assertEquals(prior, model.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartBadParams() {
    model.startGame(model.getDeck(), 5, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartBadParams2() {
    model.startGame(model.getDeck(), 3, 2, false);
  }

  @Test
  public void testMoveAndGetGameState() {
    model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String preMoveState = model.getGameState();
    model.move(PileType.CASCADE, 0, 2, PileType.OPEN, 2);
    assertFalse(model.getGameState().equals(preMoveState));
    model.startGame(model.getDeck(), 4, 5, false);
    assertTrue(model.getGameState().equals(preMoveState));
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveNoStart() {
    model = new FreecellModel();
    model.move(PileType.CASCADE, 3, 2, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadMoveParams() {
    model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.move(PileType.CASCADE, 5, 2, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadMoveParams2() {
    model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.move(PileType.FOUNDATION, 1, 2, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadMoveParams3() {
    model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.move(PileType.OPEN, 1, 2, PileType.OPEN, 2);
  }

  @Test
  public void testShuffle() {
    model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String prior = model.getGameState();
    model.startGame(model.getDeck(), 4, 5, true);
    assertFalse(prior.equals(model.getGameState()));
  }

  @Test
  public void testGameNotOver() {
    model = new FreecellModel();
    assertFalse(model.isGameOver());
    model.startGame(model.getDeck(), 4, 5, false);
    assertFalse(model.isGameOver());
  }

  private void failReflectionTest() {
    fail("Method reflection failure.");
  }

  // Here we use reflection to call a private method on the Freecell class which yields a
  // model that has been won.
  @Test
  public void testGameActuallyOver() {
    // Instantiate a "generator" object for obtaining the Freecell class below, and an "optional"
    // variable on from which to invoke the private generation method.
    FreecellModel otherModel = null;
    FreecellModel generator = new FreecellModel();
    try {
      // Retrieve the private "cheater :)" method from the Freecell class that yields a model
      // with win conditions.
      Object freecellModelInstance = FreecellModel.class.getDeclaredConstructor().newInstance();
      Method nearWinModelStaticGenerator =
              generator
                      .getClass()
                      .getDeclaredMethod("nearWinModel", null);
      // Make the method accessible at runtime
      nearWinModelStaticGenerator.setAccessible(true);
      // Grab our "won" model by invoking the private method.
      otherModel = (FreecellModel) nearWinModelStaticGenerator.invoke(freecellModelInstance, null);
    } catch (Exception e) {
      this.failReflectionTest();
    }
    if (!(otherModel instanceof FreecellModel)) {
      this.failReflectionTest();
    }
    assertFalse(otherModel.getGameState().equals(model.getGameState()));
    // Game over.
    assertTrue(otherModel.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardGameNotBegun() {
    model = new FreecellModel();
    model.getCard(PileType.OPEN, 3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardBadIndex() {
    model = new FreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.getCard(PileType.OPEN, 1, 2);
  }

  @Test
  public void testGetCardSuccessfullyCascade() {
    FreecellModel model = new FreecellModel();
    model.startGame(model.getDeck(), 8, 5, false);
    Object c = model.getCard(PileType.CASCADE, 2, 1);
    assertTrue(c != null);
    assertTrue(c instanceof Card);
  }

  @Test
  public void testEmptyGameState() {
    model = new FreecellModel();
    assertEquals("", model.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullParamStartGame() {
    model.startGame(null, 2, 3, false);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullParamMove() {
    model.move(null, 3, 2, null, 8);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullParamGetCard() {
    model.getCard(null, 3, 2);
  }

}
