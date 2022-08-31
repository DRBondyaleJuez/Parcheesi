import controller.GameController1DieMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testController {

    @BeforeEach
    public void  something(){
        //Stuff to be initialized, and sometimes executed, before each test
        GameController1DieMode controllerForTests = new GameController1DieMode();
    }

    @Test
    public void testDieWorkingProperly(){
        GameController1DieMode controllerForTests = new GameController1DieMode();
        int dieResult = controllerForTests.diceRoll();

        Assertions.assertTrue(dieResult < 7 && dieResult > 0, "Die rolled number " + dieResult + ". Which is between 0 and 7.");

    }

    @Test
    public void testMoreThan2PieceEnteringIfBlocking(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        boolean[] piecesEntering = new boolean[5];
        for (int i = 0; i < 5; i++) {
           piecesEntering[i] = controllerForTests.newPieceEnters(1);
        }

        boolean[] expectedBoolean = {true,true,false,false,false}; //Only two pieces enter due to blocking

        Assertions.assertArrayEquals(expectedBoolean,piecesEntering,"The method piece entering returns true for more than the first 2 pieces");
    }

    @Test
    public void testRepetitionPunishmentAfter2PiecesEntered(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        boolean[] piecesEntering = new boolean[5];
        for (int i = 0; i < 5; i++) {
            if(i==2){
                controllerForTests.repetitionPunishment();
            }
            piecesEntering[i] = controllerForTests.newPieceEnters(1);

        }

        boolean[] expectedBoolean = {true,true,true,false,false}; //Only two pieces enter due to blocking

        Assertions.assertArrayEquals(expectedBoolean,piecesEntering,"Either a piece entered when it couldn't or a third piece did not enter when it could");
    }

    @Test
    public void testMovePiece1(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        controllerForTests.newPieceEnters(1);
        controllerForTests.newPieceEnters(1);

        int dieValue = controllerForTests.diceRoll();
        System.out.println(dieValue);

        boolean pieceHasMoved = controllerForTests.movePiece(3,0);

        //Some of the following lines are to make sure the current player is the same and there is moving number
        for (int i = 0; i < 3; i++) {
            controllerForTests.nextPlayer();
        }

        boolean movedPieceInNewPosition = controllerForTests.checkPlayerPieceInBoardPosition(3+dieValue, 0);

        boolean[] expectedBoolean = {true,true};
        boolean[] actualBoolean = {pieceHasMoved,movedPieceInNewPosition};

        Assertions.assertArrayEquals(expectedBoolean,actualBoolean,"The piece from the player moved adequately");
    }

    @Test
    public void testPieceCapturePieceInNormalSquare(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        controllerForTests.newPieceEnters(1);

         controllerForTests.onlyForTestSetMovingNumber(1);

        boolean firstPieceMoved = controllerForTests.movePiece(3,0);

        //Player 4 to test capture
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        controllerForTests.newPieceEnters(4);

        controllerForTests.onlyForTestSetMovingNumber(16);
        boolean secondPieceMoved = controllerForTests.movePiece(48,0);
        boolean movedPieceInNewPosition = controllerForTests.checkPlayerPieceInBoardPosition(4,0);
        boolean wasAnyPieceCaught = controllerForTests.onlyForTestGetMovingNumber()==20;


        boolean[] expectResult = {true,true,true,true};
        boolean[] actualResult = {firstPieceMoved,secondPieceMoved,movedPieceInNewPosition,wasAnyPieceCaught};

        Assertions.assertArrayEquals(expectResult,actualResult,"It does not seem the piece has capture another piece as expected with their positions");

    }
    @Test
    public void testPieceCapturePieceInSafeSquare(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        controllerForTests.newPieceEnters(1);

        //Player 4 to test capture
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        controllerForTests.newPieceEnters(4);

        controllerForTests.onlyForTestSetMovingNumber(15);
        boolean secondPieceMoved = controllerForTests.movePiece(48,0);
        boolean wasAnyPieceCaught = controllerForTests.onlyForTestGetMovingNumber()==20;

        //Changing moving number only so the checking could work
        controllerForTests.onlyForTestSetMovingNumber(1);

        //currentPlayer should be on 1
        boolean movedPieceInNewPosition1 = controllerForTests.checkPlayerPieceInBoardPosition(3,0);

        //change CurrentPlayer to 4 before checking
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        boolean movedPieceInNewPosition4 = controllerForTests.checkPlayerPieceInBoardPosition(3,0);

        boolean[] expectResult = {true,true,true,false};
        boolean[] actualResult = {secondPieceMoved,movedPieceInNewPosition1,movedPieceInNewPosition4,wasAnyPieceCaught};

        Assertions.assertArrayEquals(expectResult,actualResult,"It does not seem the piece moving into a safe square with another different piece worked properly");
    }

    @Test
    public void testPieceMovingWhenBlockInFront() {
        GameController1DieMode controllerForTests = new GameController1DieMode();

        //Creating a wall by two pieces at start square of player 1
        controllerForTests.newPieceEnters(1);
        controllerForTests.newPieceEnters(1);

        //Player 4 to test capture
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        controllerForTests.newPieceEnters(4);

        //Move that will force the piece of player 4 to encounter the block
        controllerForTests.onlyForTestSetMovingNumber(20);
        boolean onlyPieceMoved = controllerForTests.movePiece(48,0);

        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        //Changing moving number only so the checking could work
        controllerForTests.onlyForTestSetMovingNumber(1);
        boolean movedPieceToNewPosition4 = controllerForTests.checkPlayerPieceInBoardPosition(2, 0);

        Assertions.assertTrue(movedPieceToNewPosition4,"The piece was not blocked by blocked square");
    }

    @Test
    public void testPieceMovingToFinalSquares() {
        GameController1DieMode controllerForTests = new GameController1DieMode();

        //Player 1 pieces entering
        controllerForTests.newPieceEnters(1);

        //Move that will force the piece of player 1 to enter the final squares
        controllerForTests.onlyForTestSetMovingNumber(57);
        boolean firstPieceMoved = controllerForTests.movePiece(3,0);

        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        //Changing moving number only so the checking could work
        controllerForTests.onlyForTestSetMovingNumber(1);
        boolean movedPieceInNewPosition = controllerForTests.checkPlayerPieceInBoardPosition(0, 1);

        Assertions.assertTrue(movedPieceInNewPosition,"The piece was not in expected FinalSquare");
    }

    @Test
    public void testPieceMovingInFinalSquares() {

        GameController1DieMode controllerForTests = new GameController1DieMode();

        //Player 1 pieces entering
        controllerForTests.newPieceEnters(1);

        //Move that will force the piece of player 1 to go past the last final square by 2
        controllerForTests.onlyForTestSetMovingNumber(65);
        boolean firstPieceMoved = controllerForTests.movePiece(3,0);

        //Back to current player 1
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        //Changing moving number only so the checking could work
        controllerForTests.onlyForTestSetMovingNumber(1);
        boolean movedPieceInNewPosition1 = controllerForTests.checkPlayerPieceInBoardPosition(4, 1);

        //Move that will force the piece of player 1 to go past the last final square by a lot
        controllerForTests.onlyForTestSetMovingNumber(15);
        boolean secondPieceMoved = controllerForTests.movePiece(4,1);

        //Back to current player 1
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        //Changing moving number only so the checking could work
        controllerForTests.onlyForTestSetMovingNumber(1);
        boolean movedPieceInNewPosition2 = controllerForTests.checkPlayerPieceInBoardPosition(0, 1);

        boolean[] expectResult = {true,true};
        boolean[] actualResult = {movedPieceInNewPosition1,movedPieceInNewPosition2};

        Assertions.assertArrayEquals(expectResult,actualResult,"The piece was not in expected FinalSquare");
    }

    @Test
    public void test4PiecesFinishingWinner() {

        GameController1DieMode controllerForTests = new GameController1DieMode();

        //Player 1 pieces entering and going to the end repeat 4 times
        boolean[] movePieceToTheEnd = new boolean[4];
        for (int i = 0; i < 4; i++) {
            controllerForTests.newPieceEnters(1);
            controllerForTests.onlyForTestSetMovingNumber(63);
            movePieceToTheEnd[i] = controllerForTests.movePiece(3,0);

            controllerForTests.nextPlayer();
            controllerForTests.nextPlayer();
            controllerForTests.nextPlayer();
            controllerForTests.nextPlayer();
        }

        boolean isPlayer1Winner = controllerForTests.isThereAWinner(1) == 1;

        boolean[] expectResult = {true, true, true, true, true};
        boolean[] actualResult = new boolean[5];
        for (int i = 0; i < movePieceToTheEnd.length; i++) {
            actualResult[i] = movePieceToTheEnd[i];
        }
        actualResult[4] = isPlayer1Winner;

        Assertions.assertArrayEquals(expectResult, actualResult, "Some piece has not reach the end or player 1 is not the winner");
    }



}
