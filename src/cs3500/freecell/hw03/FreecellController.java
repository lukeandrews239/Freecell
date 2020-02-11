package cs3500.freecell.hw03;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;

/**
 * Represents a Controller for Freecell. Handle user moves by executing them using the model;
 * convey move outcomes to the user via the Appendable.
 */
public class FreecellController implements IFreecellController<Card> {

  private final Readable userInput;
  private final Appendable userOutput;
  private FreecellOperations<Card> model;
  private final Scanner scan;

  /**
   * Constructor to create an instance of this controller.
   *
   * @param rd a Readable object from which we can derive user moves.
   * @param ap an Appendable object to which we can provide the state of the game and
   *           user interactions.
   * @throws IllegalArgumentException if either parameter is null.
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Invalid parameter.");
    }
    this.userInput = rd;
    this.userOutput = ap;
    this.scan = new Scanner(this.userInput);
    this.model = null;
  }

  /**
   * Starts a game of Freecell.
   *
   * @param deck        a valid deck.
   * @param model       a valid model object that implements the FreecellOperations interface.
   * @param numCascades int representing the selected number of cascade piles for the game.
   * @param numOpens    int representing the selected number of open piles.
   * @param shuffle     boolean indicating whether or not the deck should be shuffled.
   * @throws IllegalArgumentException in the case of invalid parameters.
   * @throws IllegalStateException    if this object hasn't been properly initialized.
   */
  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model,
                       int numCascades, int numOpens, boolean shuffle) {
    if (deck == null || model == null) {
      throw new IllegalArgumentException("Invalid parameters.");
    } else {
      this.model = model;
    }
    if (this.userOutput == null || this.userInput == null) {
      throw new IllegalStateException("Controller not properly initialized.");
    }
    try {
      this.model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.writeToAppendable("Could not start game.", null);
      return;
    }
    this.writeToAppendable(this.model.getGameState(), "\n");
    MoveTracker tracker = new MoveTracker();
    while (!model.isGameOver()) {
      if (tracker.movePrepared()) {
        boolean success = true;
        try {
          model.move(tracker.source, tracker.sourceNum - 1, tracker.cardIndex - 1,
                  tracker.dest, tracker.destNum - 1);
        } catch (IllegalArgumentException e) {
          this.writeToAppendable("Invalid move. Try again.", null);
          success = false;
        } catch (IllegalStateException s) {
          this.writeToAppendable("Could not start game.", null);
          return;
        }
        if (success) {
          this.writeToAppendable(this.model.getGameState(), "\n");
        }
        tracker = new MoveTracker();
      }
      if (model.isGameOver()) {
        break;
      }
      String curVal = null;
      try {
        curVal = this.scan.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("User failed to provide input.");
      }
      MoveComponent comp = tracker.disambiguateNextValue(curVal);
      switch (comp) {
        case QUIT:
          this.writeToAppendable("\n", "Game quit prematurely.");
          return;
        case FAILURE:
          CompRequired missingComponent = tracker.nextNeeded();
          this.informUserOfMissingComponent(missingComponent);
          break;
        default:
          break;
      }
    }
    this.writeToAppendable(this.model.getGameState(), "\n");
    this.writeToAppendable("Game over.", null);
    return;
  }

  private void informUserOfMissingComponent(CompRequired missingComponent) {
    switch (missingComponent) {
      case DEST:
        this.writeToAppendable("Invalid destination, please input again.",
                null);
        break;
      case SOURCE:
        this.writeToAppendable("Invalid source, please input again.",
                null);
        break;
      case CARDINDEX:
        this.writeToAppendable("Invalid card index, please input again.",
                null);
        break;
      default:
        break;
    }
  }

  private boolean writeToAppendable(String given, String appended) {
    try {
      String retVal = appended != null ? given + appended : given;
      userOutput.append(retVal);
      return true;
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failure.");
    }
  }

  private enum MoveComponent {
    SUCCESS, FAILURE, QUIT, COMPLETE
  }

  private enum CompRequired {
    SOURCE, CARDINDEX, DEST
  }

  private class MoveTracker {
    private PileType source = null;
    private PileType dest = null;
    private Integer sourceNum = null;
    private Integer cardIndex = null;
    private Integer destNum = null;
    private Integer tempNum = null;

    private CompRequired nextNeeded() {
      if (source == null) {
        return CompRequired.SOURCE;
      } else if (cardIndex == null) {
        return CompRequired.CARDINDEX;
      } else {
        return CompRequired.DEST;
      }
    }

    private boolean movePrepared() {
      return source != null && dest != null & cardIndex != null
              & sourceNum != null & destNum != null;
    }

    private MoveComponent disambiguateNextValue(String next) {
      if (next.length() == 1) {
        if (next.equalsIgnoreCase("q")) {
          return MoveComponent.QUIT;
        }
      }
      if (source == null) {
        this.source = this.derivePileInfo(next);
        this.sourceNum = tempNum;
        tempNum = null;
        return this.source != null ? MoveComponent.SUCCESS : MoveComponent.FAILURE;
      }
      if (cardIndex == null) {
        Integer potentialIndex;
        try {
          potentialIndex = Integer.parseInt(next);
        } catch (NumberFormatException e) {
          return MoveComponent.FAILURE;
        }
        cardIndex = potentialIndex;
        return MoveComponent.SUCCESS;
      }
      if (dest == null) {
        this.dest = this.derivePileInfo(next);
        this.destNum = tempNum;
        tempNum = null;
        return this.dest != null ? MoveComponent.SUCCESS : MoveComponent.FAILURE;
      }
      return MoveComponent.COMPLETE;
    }

    private PileType derivePileInfo(String given) {
      if (given.length() < 2) {
        return null;
      }
      PileType pile;
      Character potentialPile = given.charAt(0);
      Integer potentialPileNum;
      try {
        potentialPileNum = Integer.parseInt(given.substring(1));
      } catch (NumberFormatException e) {
        return null;
      }
      switch (potentialPile) {
        case 'C':
          pile = PileType.CASCADE;
          tempNum = potentialPileNum;
          return pile;
        case 'O':
          pile = PileType.OPEN;
          tempNum = potentialPileNum;
          return pile;
        case 'F':
          pile = PileType.FOUNDATION;
          tempNum = potentialPileNum;
          return pile;
        default:
          tempNum = null;
          return null;
      }
    }
  }
}









































