package viewController;

import controller.GameController1DieMode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable{

    private GameController1DieMode controller;
    private ImageView[] stepImageViewArray;
    private ImageView[][] finalStepsImageViewArray;

    //ELEMENTS FROM FXML

    //Labels
    //Current Player
    @FXML
    private Label playerTurnLabel;

    //House
    @FXML
    private Label house1Label;

    @FXML
    private Label house2Label;

    @FXML
    private Label house3Label;

    @FXML
    private Label house4Label;

    //Finished
    @FXML
    private Label numberPiecesFinished1;

    @FXML
    private Label numberPiecesFinished2;

    @FXML
    private Label numberPiecesFinished3;

    @FXML
    private Label numberPiecesFinished4;


    //Board Sections
    @FXML
    private HBox steps1To6HBox;

    @FXML
    private HBox step7HBox;

    @FXML
    private VBox steps8To14VBox;

    @FXML
    private HBox step15HBox;

    @FXML
    private VBox steps16To22VBox;

    @FXML
    private HBox step23HBox;

    @FXML
    private HBox steps24To29HBox;

    @FXML
    private VBox step30VBox;

    @FXML
    private HBox steps31To36HBox;

    @FXML
    private HBox step37HBox;

    @FXML
    private VBox steps38To44VBox;

    @FXML
    private HBox step45HBox;

    @FXML
    private VBox steps46To52VBox;

    @FXML
    private HBox step53HBox;

    @FXML
    private HBox steps54To59HBox;

    @FXML
    private VBox step60VBox;

    @FXML
    private HBox finalSteps1To5Player1HBox;
    @FXML
    private HBox finalStep6Player1HBox;

    @FXML
    private VBox finalSteps1To6Player2VBox;

    @FXML
    private HBox finalSteps1To5Player3HBox;
    @FXML
    private HBox finalStep6Player3HBox;

    @FXML
    private VBox finalSteps1To6Player4VBox;


    //Die
    @FXML
    private ImageView dieRollImageView;

    @FXML
    private Button rollDiceButton;


    //Board Background
    @FXML
    private ImageView boardBackgroundImageView;

    @FXML
    private TextArea instructionsTextArea;



    //CONSTRUCTOR
    public GameViewController() {
        controller = new GameController1DieMode();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stepImageViewArray = new ImageView[60];
        finalStepsImageViewArray = new ImageView[4][7];
        buildBoardSteps();
        buildFinalSteps();

        //Die ImageView building
        dieRollImageView.setOnMouseClicked(createDieRollImageViewClickedEventHandler());
        rollDiceButton.setOnMouseClicked(createDieRollImageViewClickedEventHandler());


    }

    private void buildBoardSteps(){

        for (int i = 0; i < 60; i++) {

            if(i>-1 && i<6){

                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,0));
                steps1To6HBox.getChildren().add(stepImageViewArray[i]);



            }

            if(i==6){

            }

            if(i>6 && i<14){

            }

            if(i==14){

            }

            if(i>14 && i<22){

            }

            if(i==22){

            }

            if(i>22 && i<29){

            }

            if(i==29){

            }

            if(i>29 && i<36){

            }

            if(i==36){

            }

            if(i>36 && i<44){

            }

            if(i==44){

            }

            if(i>44 && i<52){

            }

            if(i==52){

            }

            if(i>52 && i<59){

            }

            if(i==59){

            }




        }

    }
    private void buildFinalSteps(){

        //Player 1
        for (int i = 0; i < 5; i++) {

        }
        //Step6 is separate

        //Player 2
        for (int i = 0; i < 5; i++) {

        }

        //Player 3
        for (int i = 0; i < 5; i++) {

        }
        //Step6 is separate

        //Player 4
        for (int i = 0; i < 5; i++) {

        }


    }

    private EventHandler<MouseEvent> createStepImageViewClickedEventHandler(int position, int possiblePlayerFinalSteps){
        return new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(controller.getMovingNumber() == 0){
                    int currentPlayerNumber = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText(" Player " + currentPlayerNumber +" must roll the die first.");
                    return;
                }

                if(!controller.verifySquareClickedAndGameState(position, possiblePlayerFinalSteps)){
                    int currentPlayerNumber = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("It is player " + currentPlayerNumber +"'s turn perhaps you have clicked a wrong square. Try again.");
                    return;
                }

                //TO DO: PIECE MOVING CALLING CONTROLLER AND THEN METHOD TO REDRAW BOARD



            }
        };
    }

    //IT MAY BE TOO COMPLICATED TO TELL THE VIEW CONTROLLER TO CHANGE BOARD SQUARES AFTER A PIECE IS CAPTURED AND THINGS LIKE THAT SO THE GAMEVIEW CONTROLER WILL GO THROUGH THE BOARD CHANGING EVERY SQUARE IF NECESARY
    private void changeBoardSquare(int position, int player){

        //If the position given

    }

    private EventHandler<MouseEvent> createDieRollImageViewClickedEventHandler(){
        return new EventHandler<>(){

            @Override
            public void handle(MouseEvent mouseEvent){

                if(controller.getMovingNumber() == 0) {
                    int currentPlayerNumber = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + currentPlayerNumber +" has already rolled the die. Waiting for selection of piece to move.");
                    return;
                }

                int playerThatRolled = controller.getCurrentPlayer().getIdNumber();
                int dieNumber = controller.diceRoll();

                if(dieNumber == 0){
                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a 5. So a piece exited the house and entered to the board. Now is player " + playerRollingNow + "'s turn. First roll the die" );
                    return;
                }

                instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a " + dieNumber + ". Click on the piece you want to move " + dieNumber + " squares." );

                Image currentDieImage = new Image(new ByteArrayInputStream(controller.getDieImageData()));

            }


        };
    }


}
