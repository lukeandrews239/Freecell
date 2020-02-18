
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
import cs3500.freecell.hw04.MultiMoveFreecellModel;

/**
 * Test class for the MultiMoveFreecellModel implementation of the FreecellOperations interface.
 */
public class MultiMoveFreecellModelTests {

  private FreecellOperations model = new MultiMoveFreecellModel();

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
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String preMoveState = model.getGameState();
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 2);
    assertFalse(model.getGameState().equals(preMoveState));
    model.startGame(model.getDeck(), 4, 5, false);
    assertTrue(model.getGameState().equals(preMoveState));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveExceptionForNonCascadePile() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String preMoveState = model.getGameState();
    model.move(PileType.CASCADE, 1, 5, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveExceptionForCascadePile() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String preMoveState = model.getGameState();
    model.move(PileType.CASCADE, 1, 5, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveExceptionForFoundationPile() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String preMoveState = model.getGameState();
    model.move(PileType.CASCADE, 1, 5, PileType.FOUNDATION, 2);
  }


  @Test(expected = IllegalStateException.class)
  public void testMoveNoStart() {
    model = new MultiMoveFreecellModel();
    model.move(PileType.CASCADE, 3, 2, PileType.OPEN, 2);
  }

  @Test
  public void testMultiMove() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 50, false);
    model.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 2, 12, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 3, 12, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 0, 12, PileType.OPEN, 4);
    model.move(PileType.CASCADE, 1, 11, PileType.OPEN, 5);
    model.move(PileType.CASCADE, 2, 11, PileType.OPEN, 6);
    model.move(PileType.CASCADE, 3, 11, PileType.OPEN, 7);
    model.move(PileType.CASCADE, 0, 11, PileType.OPEN, 8);
    model.move(PileType.CASCADE, 1, 10, PileType.OPEN, 9);
    model.move(PileType.CASCADE, 1, 9, PileType.OPEN, 10);
    model.move(PileType.CASCADE, 1, 8, PileType.OPEN, 11);
    model.move(PileType.CASCADE, 2, 10, PileType.OPEN, 20);
    model.move(PileType.CASCADE, 2, 9, PileType.OPEN, 21);
    model.move(PileType.CASCADE, 2, 8, PileType.OPEN, 22);
    model.move(PileType.CASCADE, 2, 7, PileType.OPEN, 23);
    model.move(PileType.CASCADE, 2, 6, PileType.OPEN, 24);
    model.move(PileType.CASCADE, 2, 5, PileType.OPEN, 25);
    model.move(PileType.CASCADE, 2, 4, PileType.OPEN, 26);
    model.move(PileType.CASCADE, 2, 3, PileType.OPEN, 27);
    model.move(PileType.CASCADE, 2, 2, PileType.OPEN, 28);
    model.move(PileType.CASCADE, 2, 1, PileType.OPEN, 29);
    // Cascade column 2 cleared aside from one black 3
    // Proceed to add a red 2 to that pile, preparing our "multi-move"
    model.move(PileType.OPEN, 27, 0, PileType.CASCADE, 2);
    // Other altered pile ends on a red 4. Attempt to move the pile over in one move.
    // Assert beforehand that there are existing values in both index 0 and 1 for that pile
    assertTrue((model.getCard(PileType.CASCADE, 2, 0)) instanceof Card);
    assertTrue((model.getCard(PileType.CASCADE, 2, 1)) instanceof Card);
    // Make the move
    model.move(PileType.CASCADE, 2, 0, PileType.CASCADE, 1);
    // Further proceed to assert that both cards have transitioned
    boolean success = false;
    boolean secondSuccess = false;
    try {
      model.getCard(PileType.CASCADE, 2, 0);
    } catch (IllegalArgumentException e) {
      // Card has moved!
      success = true;
    }
    try {
      model.getCard(PileType.CASCADE, 2, 1);
    } catch (IllegalArgumentException e) {
      // Card has moved!
      secondSuccess = true;
    }
    assertTrue(success);
    assertTrue(secondSuccess);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadMoveParams() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.move(PileType.CASCADE, 5, 2, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadMoveParams2() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.move(PileType.FOUNDATION, 1, 2, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadMoveParams3() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.move(PileType.OPEN, 1, 2, PileType.OPEN, 2);
  }

  @Test
  public void testShuffle() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    String prior = model.getGameState();
    model.startGame(model.getDeck(), 4, 5, true);
    assertFalse(prior.equals(model.getGameState()));
  }

  @Test
  public void testGameNotOver() {
    model = new MultiMoveFreecellModel();
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
      // Retrieve the private method from the MultiMoveFreecell class that yields a model
      // with win conditions.
      Object freecellModelInstance =
              MultiMoveFreecellModel
                      .class
                      .getDeclaredConstructor()
                      .newInstance();
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
    model = new MultiMoveFreecellModel();
    model.getCard(PileType.OPEN, 3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardBadIndex() {
    model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 4, 5, false);
    model.getCard(PileType.OPEN, 1, 2);
  }

  @Test
  public void testGetCardSuccessfullyCascade() {
    MultiMoveFreecellModel model = new MultiMoveFreecellModel();
    model.startGame(model.getDeck(), 8, 5, false);
    Object c = model.getCard(PileType.CASCADE, 2, 1);
    assertTrue(c != null);
    assertTrue(c instanceof Card);
  }

  @Test
  public void testEmptyGameState() {
    model = new MultiMoveFreecellModel();
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