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



}
