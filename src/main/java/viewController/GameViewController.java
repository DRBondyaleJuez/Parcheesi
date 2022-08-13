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

    private Label[] houseLabelArray;

    private Label[] finishedLabelArray;

    //ELEMENTS FROM FXML

    //Start new Game Button
    @FXML
    private Button startNewGameButton;
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

        startNewGameButton.setOnMouseClicked(newGameReset());

        stepImageViewArray = new ImageView[60];
        for (int i = 0; i < stepImageViewArray.length; i++) {
            stepImageViewArray[i] = new ImageView();
        }

        finalStepsImageViewArray = new ImageView[4][7];
        for (int i = 0; i < finalStepsImageViewArray.length; i++) {
            for (int j = 0; j < finalStepsImageViewArray[0].length; j++) {
                finalStepsImageViewArray[i][j] = new ImageView();
            }
        }

        buildBoardSteps();
        buildFinalSteps();

        //House Label Array
        houseLabelArray = new Label[4];
        houseLabelArray[0] = house1Label;
        houseLabelArray[1] = house2Label;
        houseLabelArray[2] = house3Label;
        houseLabelArray[3] = house4Label;

        //Number of Pieces Finished
        finishedLabelArray = new Label[4];
        finishedLabelArray[0] = numberPiecesFinished1;
        finishedLabelArray[1] = numberPiecesFinished2;
        finishedLabelArray[2] = numberPiecesFinished3;
        finishedLabelArray[3] = numberPiecesFinished4;

        //Die ImageView building
        dieRollImageView.setOnMouseClicked(createDieRollImageViewClickedEventHandler());
        rollDiceButton.setOnMouseClicked(createDieRollImageViewClickedEventHandler());

        //Set BoardImage
        Image currentBoardImage = new Image(new ByteArrayInputStream(controller.getBoardImageData()));
        boardBackgroundImageView.setImage(currentBoardImage);
    }

    private EventHandler<MouseEvent> newGameReset(){
        return new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller = new GameController1DieMode();
                applyChangesToBoard();
            }
        };


    }

    private void buildBoardSteps(){
        //Array created to be able to reorder the steps when they go in the other direction
        ImageView[] temporaryImageViewArray = new ImageView[7];

        for (int i = 0; i < 60; i++) {

            if (i > -1 && i < 6) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                steps1To6HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i == 6) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step7HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 6 && i < 14) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                temporaryImageViewArray[i - 7] = stepImageViewArray[i];
                if (i == 13) {
                    for (int j = 6; j > -1; j--) {
                        step30VBox.getChildren().add(stepImageViewArray[j]);
                    }
                }
            }

            if (i == 14) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step15HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 14 && i < 22) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                steps16To22VBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i == 22) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step23HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 22 && i < 29) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                steps24To29HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i == 29) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step30VBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 29 && i < 36) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                temporaryImageViewArray[i - 30] = stepImageViewArray[i];
                if (i == 35) {
                    for (int j = 5; j > -1; j--) {
                        steps31To36HBox.getChildren().add(stepImageViewArray[j]);
                    }
                }
            }

            if (i == 36) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step37HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 36 && i < 44) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                steps38To44VBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i == 44) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step45HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 44 && i < 52) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                temporaryImageViewArray[i - 45] = stepImageViewArray[i];
                if (i == 51) {
                    for (int j = 6; j > -1; j--) {
                        steps46To52VBox.getChildren().add(stepImageViewArray[j]);
                    }
                }
            }

            if (i == 52) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step53HBox.getChildren().add(stepImageViewArray[i]);
            }

            if (i > 52 && i < 59) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                temporaryImageViewArray[i - 53] = stepImageViewArray[i];
                if (i == 58) {
                    for (int j = 6; j > -1; j--) {
                        steps54To59HBox.getChildren().add(stepImageViewArray[j]);
                    }
                }
            }

            if (i == 59) {
                stepImageViewArray[i].setOnMouseClicked(createStepImageViewClickedEventHandler(i, 0));
                step60VBox.getChildren().add(stepImageViewArray[i]);
            }
        }
    }
    private void buildFinalSteps(){

        //Player 1
        for (int i = 0; i < 6; i++) {

            finalStepsImageViewArray[0][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,1));
            if(i<5){
                finalSteps1To5Player1HBox.getChildren().add(stepImageViewArray[i]);
            }
            //Step6 is separate
            if(i==5){
                finalStep6Player1HBox.getChildren().add(stepImageViewArray[i]);
            }

        //Player 2
            finalStepsImageViewArray[1][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,2));
            if(i<5){
                finalSteps1To6Player2VBox.getChildren().add(stepImageViewArray[i]);
            }

        //Player 3
            finalStepsImageViewArray[2][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,3));
            if(i<5){
                finalSteps1To5Player3HBox.getChildren().add(stepImageViewArray[i]);
            }
            //Step6 is separate
            if(i==5){
                finalStep6Player3HBox.getChildren().add(stepImageViewArray[i]);
            }

        //Player 4
            finalStepsImageViewArray[3][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,4));
            if(i<5){
                finalSteps1To6Player4VBox.getChildren().add(stepImageViewArray[i]);
            }
        }
    }

    private EventHandler<MouseEvent> createStepImageViewClickedEventHandler(int position, int possiblePlayerFinalSteps){
        return new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(controller.isThereAWinner()){
                    return;
                }

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

                //Move Piece
                int playerBeforeMoving = controller.getCurrentPlayer().getIdNumber();
                controller.movePiece(position,possiblePlayerFinalSteps);

                int playerAfterMoving = controller.getCurrentPlayer().getIdNumber();

                applyChangesToBoard();

                //Messages in case a 6 has been rolled, a piece is captured or a piece reaches the end. In these situations the player does not changes
                // and must select a piece to move or roll again the die.
                if(playerBeforeMoving == playerAfterMoving){
                    int movingNumber = controller.getMovingNumber();
                    String text = "";
                    if(movingNumber == 10){
                        text = "It is still player" + playerAfterMoving + "'s turn. The piece moved reached the end." +
                                " Please, select a piece to move 10";
                    }
                    if(movingNumber == 20){
                        text = "It is still player" + playerAfterMoving + "'s turn. The piece moved captured another piece." +
                                " Please, select a piece to move 20";
                    }
                    if(movingNumber == 0){
                        text = "It is still player" + playerAfterMoving + "'s turn. This player rolled a 6 now you must roll the die again." +
                                " After rolling select a piece to move.";
                    }

                    instructionsTextArea.setText(text);
                } else {
                    instructionsTextArea.setText("It is player " + playerAfterMoving + "'s turn. First roll and then choose a piece to move.");
                }
            }
        };
    }

    private void applyChangesToBoard(){
        for (int i = 0; i < stepImageViewArray.length; i++) {
           changeBoardSquare(i,0);

        }

        for (int player = 1 ; player < finalStepsImageViewArray.length+1; player++){
            for (int i =0 ; i < finalStepsImageViewArray.length; i++){
                changeBoardSquare(i,player);
            }
        }

        for (int i = 0; i < houseLabelArray.length; i++) {
            houseLabelArray[i].setText("" + controller.getBoard().getHousePieces()[i]);
            finishedLabelArray[i].setText("" + controller.getBoard().getFinishedPieces(i+1));
        }



    }

    private void changeBoardSquare(int position, int player){

        if(player == 0){
            //Compare position to know if image should be vertical or horizontal
            String imageOrientation = "vertical";
            for (int i = 0; i < 15; i++) {
                if(position == i+22 || position == i+52%59){
                    imageOrientation = "horizontal";
                    break;
                }
            }

            //Find the information about the pieces in square to retrieve correct image in database
            int[] playersOfPiecesInSquare = new int[2];
            playersOfPiecesInSquare[0] = controller.getBoard().getBoardSquares()[position].getCurrentPieces()[0].getPlayer();
            playersOfPiecesInSquare[1] = controller.getBoard().getBoardSquares()[position].getCurrentPieces()[1].getPlayer();

            String playerPiecesInfo = "" + playersOfPiecesInSquare[0] + playersOfPiecesInSquare[1];
            if(playersOfPiecesInSquare[1] < playersOfPiecesInSquare[0]){
                playerPiecesInfo = "" + playersOfPiecesInSquare[1] + playersOfPiecesInSquare[0];
            }
            setImageInSquare(player, position, playerPiecesInfo, imageOrientation);

        } else {

            String imageOrientation = "vertical";
            if(player == 1 || player == 3){
                imageOrientation = "horizontal";
            }
            //Find the information about the pieces in square to retrieve correct image in database
            int[] playersOfPiecesInSquare = new int[2];
            playersOfPiecesInSquare[0] = controller.getBoard().getBoardSquares()[position].getCurrentPieces()[0].getPlayer();
            playersOfPiecesInSquare[1] = controller.getBoard().getBoardSquares()[position].getCurrentPieces()[1].getPlayer();

            String playerPiecesInfo = "" + playersOfPiecesInSquare[0] + playersOfPiecesInSquare[1];
            setImageInSquare(player, position, playerPiecesInfo, imageOrientation);

        }

    }

    private void setImageInSquare(int player, int position, String piecesInSquare, String imageOrientation){
        if(player == 0) {
            ImageView currentImageView = stepImageViewArray[position];
            Image currentPieceImage = new Image(new ByteArrayInputStream(controller.getPieceImageData(piecesInSquare, imageOrientation)));
            currentImageView.setImage(currentPieceImage);
        } else {
            ImageView currentImageView = finalStepsImageViewArray[player-1][position];
            Image currentPieceImage = new Image(new ByteArrayInputStream(controller.getPieceImageData(piecesInSquare, imageOrientation)));
            currentImageView.setImage(currentPieceImage);
        }


    }


    private EventHandler<MouseEvent> createDieRollImageViewClickedEventHandler(){
        return new EventHandler<>(){

            @Override
            public void handle(MouseEvent mouseEvent){

                if(controller.isThereAWinner()){
                    return;
                }

                if(controller.getMovingNumber() > 0) {
                    int currentPlayerNumber = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + currentPlayerNumber +" has already rolled the die or previously captured another piece or reached the end. Waiting for selection of piece to move.");
                    return;
                }

                int playerThatRolled = controller.getCurrentPlayer().getIdNumber();
                int dieNumber = controller.diceRoll();

                if(dieNumber == 0){

                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a 5. So a piece exited the house and entered to the board. Now is player " + playerRollingNow + "'s turn. First roll the die" );
                    applyChangesToBoard();
                    return;
                }

                if(dieNumber == -1){
                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a 6 3 times in a row. If possible, their most advanced piece returned to the house. Now is player " + playerRollingNow + "'s turn. First roll the die." );
                    applyChangesToBoard();
                    return;
                }

                if(controller.getBoard().getHousePieces()[playerThatRolled-1] == 4){
                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    if(dieNumber == 6){
                        instructionsTextArea.setText("Since player " + playerThatRolled +" rolled a " + dieNumber + " they can roll again. Remember you need a 5 so a piece can enter the board." );
                    } else {
                        instructionsTextArea.setText("Player " + playerThatRolled + " has rolled a " + dieNumber + ". You need a 5 so piece can enter the board. Now is player " + playerRollingNow + "'s turn.");
                    }
                    return;
                }

                instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a " + dieNumber + ". Click on the piece you want to move " + dieNumber + " squares. If it was a 6 after moving you roll again." );

                Image currentDieImage = new Image(new ByteArrayInputStream(controller.getDieImageData()));
                dieRollImageView.setImage(currentDieImage);

            }


        };
    }


}
