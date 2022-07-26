package viewController;

import controller.GameController1DieMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable{

    private GameController1DieMode controller;

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

        buildBoardSteps();
        buildFinalSteps();

    }

    private void buildBoardSteps(){

        for (int i = 0; i < 60; i++) {

            if(i>-1 && i<6){

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

}
