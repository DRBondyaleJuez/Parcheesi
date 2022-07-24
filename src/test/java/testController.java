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

        Assertions.assertArrayEquals(expectedBoolean,piecesEntering,"The method piece entering returns true for the first 4 pieces only");
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

        Assertions.assertArrayEquals(expectedBoolean,piecesEntering,"The method piece entering returns true for the first 4 pieces only");
    }

    @Test
    public void testMovePiece1(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        controllerForTests.newPieceEnters(1);

        int dieValue = controllerForTests.diceRoll();

        System.out.println(dieValue);

        boolean pieceHasMoved = controllerForTests.movePiece(1,4);

        //So the current player is the same and there is moving number
        for (int i = 0; i < 3; i++) {
            controllerForTests.nextPlayer();
        }
        int dieValue1 = controllerForTests.diceRoll();

        boolean movedPieceInNewPosition = controllerForTests.checkPlayerPieceInBoardPosition(1,4+dieValue);

        boolean[] expectedBoolean = {true,true};
        boolean[] actualBoolean = {pieceHasMoved,movedPieceInNewPosition};

        Assertions.assertArrayEquals(expectedBoolean,actualBoolean,"The piece from the player moved adequately");
    }

    @Test
    public void testPieceCapturePieceInNormalSquare(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        controllerForTests.newPieceEnters(1);

        boolean firstPieceMoved = controllerForTests.onlyForTestSetMovingNumber(1);

        controllerForTests.movePiece(1,4);

        //Player 4 to test capture
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        controllerForTests.newPieceEnters(4);

        controllerForTests.onlyForTestSetMovingNumber(16);
        boolean secondPieceMoved = controllerForTests.movePiece(4,49);
        boolean movedPieceInNewPosition = controllerForTests.checkPlayerPieceInBoardPosition(4,5);
        boolean wasAnyPieceCaught = controllerForTests.onlyForTestGetMovingNumber()==20;


        boolean[] expectResult = {true,true,true,true};
        boolean[] actualResult = {firstPieceMoved,secondPieceMoved,movedPieceInNewPosition,wasAnyPieceCaught};

        Assertions.assertArrayEquals(expectResult,actualResult,"It does not seem the piece has capture another piece as expected with their positions");

    }
    @Test
    public void testPieceCapturePieceInSafeSquare(){
        GameController1DieMode controllerForTests = new GameController1DieMode();

        controllerForTests.newPieceEnters(1);

        boolean firstPieceMoved = controllerForTests.onlyForTestSetMovingNumber(1);

        //Player 4 to test capture
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();

        controllerForTests.newPieceEnters(4);

        controllerForTests.onlyForTestSetMovingNumber(15);
        boolean secondPieceMoved = controllerForTests.movePiece(4,49);
        boolean wasAnyPieceCaught = controllerForTests.onlyForTestGetMovingNumber()==20;

        //Changing moving number only so the checking could work
        controllerForTests.onlyForTestSetMovingNumber(1);

        //currentplayer should be on 1
        boolean movedPieceInNewPosition1 = controllerForTests.checkPlayerPieceInBoardPosition(1,4);

        //change CurrentPlayer to 4 before checking
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        controllerForTests.nextPlayer();
        boolean movedPieceInNewPosition4 = controllerForTests.checkPlayerPieceInBoardPosition(4,4);

        boolean[] expectResult = {true,true,true,true,false};
        boolean[] actualResult = {firstPieceMoved,secondPieceMoved,movedPieceInNewPosition1,movedPieceInNewPosition4,wasAnyPieceCaught};

        Assertions.assertArrayEquals(expectResult,actualResult,"It does not seem the piece moving into a safe square qith another different piece worked properly");

    }



}
