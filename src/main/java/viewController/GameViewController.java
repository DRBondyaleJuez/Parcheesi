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
import javafx.scene.paint.Color;

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


    //Centre Trophee Image
    private ImageView tropheeImageView;



    //CONSTRUCTOR
    public GameViewController() {
        controller = new GameController1DieMode();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Start NewGame Button
        startNewGameButton.setOnMouseClicked(newGameReset());

        // Normal Steps Creation and addition of corresponding imageView
        stepImageViewArray = new ImageView[60];
        for (int i = 0; i < stepImageViewArray.length; i++) {
            stepImageViewArray[i] = new ImageView();
            String emptyImagePathOrientation = "horizontal";
            //Specifying dimensions to avoid image size issues
            if ((i > 6 && i < 22 ) || (i > 36 && i < 52)){
                stepImageViewArray[i].setFitHeight(44.4);
                stepImageViewArray[i].setFitWidth(88.8);
                emptyImagePathOrientation = "vertical";
            } else if(i == 6 || i == 22 || i == 36 || i == 52) {
                stepImageViewArray[i].setFitHeight(44.4);
                stepImageViewArray[i].setFitWidth(44.4);
            }else{
                stepImageViewArray[i].setFitHeight(88.8);
                stepImageViewArray[i].setFitWidth(44.4);
            }
            stepImageViewArray[i].setPreserveRatio(true);

            Image emptyImage = new Image(new ByteArrayInputStream(controller.getPieceImageData("00",emptyImagePathOrientation)));
            stepImageViewArray[i].setImage(emptyImage);
        }

        // Final Steps creation and addition of corresponding imageView

        finalStepsImageViewArray = new ImageView[4][7];
        for (int i = 0; i < finalStepsImageViewArray.length; i++) {
            for (int j = 0; j < finalStepsImageViewArray[0].length; j++) {
                finalStepsImageViewArray[i][j] = new ImageView();
                String emptyImagePathOrientation = "vertical";
                if(i ==0 || i==2){
                    emptyImagePathOrientation = "horizontal";
                    finalStepsImageViewArray[i][j].setFitHeight(88.8);
                    finalStepsImageViewArray[i][j].setFitWidth(44.4);
                } else {
                    finalStepsImageViewArray[i][j].setFitHeight(44.4);
                    finalStepsImageViewArray[i][j].setFitWidth(88.8);
                }

                Image emptyImage = new Image(new ByteArrayInputStream(controller.getPieceImageData("00",emptyImagePathOrientation)));
                finalStepsImageViewArray[i][j].setImage(emptyImage);
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
                        steps8To14VBox.getChildren().add(temporaryImageViewArray[j]);
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
                        steps31To36HBox.getChildren().add(temporaryImageViewArray[j]);
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
                        steps46To52VBox.getChildren().add(temporaryImageViewArray[j]);
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
                    for (int j = 5; j > -1; j--) {
                        steps54To59HBox.getChildren().add(temporaryImageViewArray[j]);
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
                finalSteps1To5Player1HBox.getChildren().add(finalStepsImageViewArray[0][i]);
            }
            //Step6 is separate
            if(i==5){
                finalStep6Player1HBox.getChildren().add(finalStepsImageViewArray[0][i]);
            }

        //Player 2
            finalStepsImageViewArray[1][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,2));
            finalSteps1To6Player2VBox.getChildren().add(finalStepsImageViewArray[1][i]);


        //Player 3
            finalStepsImageViewArray[2][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,3));
            if(i<5){
                finalSteps1To5Player3HBox.getChildren().add(finalStepsImageViewArray[2][4-i]);
            }
            //Step6 is separate
            if(i==5){
                finalStep6Player3HBox.getChildren().add(finalStepsImageViewArray[2][i]);
            }

        //Player 4
            finalStepsImageViewArray[3][i].setOnMouseClicked(createStepImageViewClickedEventHandler(i,4));
            finalSteps1To6Player4VBox.getChildren().add(finalStepsImageViewArray[3][5-i]);

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

                                //Messages in case a 6 has been rolled, a piece is captured or a piece reaches the end. In these situations the player does not changes
                // and must select a piece to move or roll again the die.
                if(playerBeforeMoving == playerAfterMoving){
                    int movingNumber = controller.getMovingNumber();
                    String text = "";
                    if(movingNumber == 10){
                        text = "It is still player " + playerAfterMoving + "'s turn. The piece moved reached the end." +
                                "\nPlease, select a piece to move 10";
                    }
                    if(movingNumber == 20){
                        text = "It is still player " + playerAfterMoving + "'s turn. The piece moved captured another piece." +
                                "\nPlease, select a piece to move 20";
                    }
                    if(movingNumber == 0){
                        text = "It is still player " + playerAfterMoving + "'s turn. This player rolled a 6 now you must roll the die again." +
                                "\nAfter rolling select a piece to move.";
                    }

                    instructionsTextArea.setText(text);
                } else {
                    instructionsTextArea.setText("It is player " + playerAfterMoving + "'s turn.\nFirst roll and then choose a piece to move.");
                }

                applyChangesToBoard();
            }
        };
    }

    private void applyChangesToBoard(){
        for (int i = 0; i < stepImageViewArray.length; i++) {
           changeBoardSquare(i,0);
        }

        for (int player = 1 ; player < finalStepsImageViewArray.length+1; player++){
            for (int i = 0 ; i < finalStepsImageViewArray[0].length; i++){
                changeBoardSquare(i,player);
            }
        }

        applyChangesToLabels();
    }

    private void applyChangesToLabels(){
        for (int i = 0; i < houseLabelArray.length; i++) {
            houseLabelArray[i].setText("" + controller.getBoard().getHousePieces()[i]);
            finishedLabelArray[i].setText("" + controller.getBoard().getFinishedPieces(i+1));
        }

        String playerText = "Player " + controller.getCurrentPlayer().getIdNumber();
        playerTurnLabel.setText(playerText);
        switch (controller.getCurrentPlayer().getIdNumber()){
            case 1:
                playerTurnLabel.setTextFill(Color.color(0, 0, 0.9));
                break;
            case 2:
                playerTurnLabel.setTextFill(Color.color(0.8, 0.7, 0.2));
                break;
            case 3:
                playerTurnLabel.setTextFill(Color.color(0, 0.7, 0));
                break;
            case 4:
                playerTurnLabel.setTextFill(Color.color(0.8, 0, 0));
                break;
            default:
                break;
        }

        if(controller.isThereAWinner()){
            playerTurnLabel.setText(playerTurnLabel.getText() + " WINS");
            winnerTrophyImage(controller.getCurrentPlayer().getIdNumber());
        }
    }

    //This method applies the changes to the image on a particular board square to the corresponding one based on the controller's information
    private void changeBoardSquare(int position, int player){

        //Determine the pieces required for the image
        int[] playerPiecesInSquare = new int[2];
        String playerPieceInfo = "" + playerPiecesInSquare[0] + playerPiecesInSquare[1]; //Start assuming empty state

        //Determine orientation of the image
        String imageOrientation = "horizontal"; //Start assuming horizontal orientation

        //The state of the square consultation depends on whether it is a final square or a normal square i.e. if player is 0 or >0
        if(player == 0){
            //Compare position to know if image should be vertical or horizontal
            if ((position > 6 && position < 22 ) || (position > 36 && position < 52)){
                imageOrientation = "vertical";
            }

            //Find the information about the pieces in square to retrieve correct image in database
            if(controller.getBoard().getBoardSquares()[position].isBlocked()) {
                playerPiecesInSquare[0] = controller.getBoard().getBoardSquares()[position].getCurrentPieces().get(0).getPlayer();
                playerPiecesInSquare[1] = controller.getBoard().getBoardSquares()[position].getCurrentPieces().get(1).getPlayer();
            } else if(!controller.getBoard().getBoardSquares()[position].getCurrentPieces().isEmpty()){
                playerPiecesInSquare[0] = controller.getBoard().getBoardSquares()[position].getCurrentPieces().get(0).getPlayer();
            }

            playerPieceInfo = "" + playerPiecesInSquare[0] + playerPiecesInSquare[1];
            if(playerPiecesInSquare[1] < playerPiecesInSquare[0]){
                playerPieceInfo = "" + playerPiecesInSquare[1] + playerPiecesInSquare[0];
            }
            setImageInSquare(player, position, playerPieceInfo, imageOrientation);

        } else {

            if(player == 2 || player == 4){
                imageOrientation = "vertical";
            }
            //Find the information about the pieces in square to retrieve correct image in database
            if(controller.getBoard().getFinalSquaresBoard()[player-1][position].isBlocked()) {
                playerPiecesInSquare[0] = controller.getBoard().getFinalSquaresBoard()[player-1][position].getCurrentPieces().get(0).getPlayer();
                playerPiecesInSquare[1] = controller.getBoard().getFinalSquaresBoard()[player-1][position].getCurrentPieces().get(1).getPlayer();
            } else if(!controller.getBoard().getFinalSquaresBoard()[player-1][position].getCurrentPieces().isEmpty()){
                playerPiecesInSquare[0] = controller.getBoard().getFinalSquaresBoard()[player-1][position].getCurrentPieces().get(0).getPlayer();
            }

            playerPieceInfo = "" + playerPiecesInSquare[0] + playerPiecesInSquare[1];
            if(playerPiecesInSquare[1] < playerPiecesInSquare[0]){
                playerPieceInfo = "" + playerPiecesInSquare[1] + playerPiecesInSquare[0];
            }

            setImageInSquare(player, position, playerPieceInfo, imageOrientation);
        }

    }

    private void setImageInSquare(int player, int position, String piecesInSquare, String imageOrientation){
        ImageView currentImageView;
        if(player == 0) {
            currentImageView = stepImageViewArray[position];
        } else {
            currentImageView = finalStepsImageViewArray[player - 1][position];
        }
        Image currentPieceImage = new Image(new ByteArrayInputStream(controller.getPieceImageData(piecesInSquare, imageOrientation)));
        currentImageView.setImage(currentPieceImage);


    }

    ////// HANDLE DIE CLICKED

    private EventHandler<MouseEvent> createDieRollImageViewClickedEventHandler(){
        return new EventHandler<>(){

            @Override
            public void handle(MouseEvent mouseEvent){

                if(controller.isThereAWinner()){
                    return;
                }

                if(controller.getMovingNumber() > 0) {
                    int currentPlayerNumber = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + currentPlayerNumber + " has already rolled the die. Waiting for selection of piece to move.");
                    if(controller.getMovingNumber() == 6){
                        instructionsTextArea.setText("Player " + currentPlayerNumber + " has already rolled the die. Waiting for selection of piece to move.\n" +
                                "Since they rolled a 6 after moving roll the die again.");
                    }
                    if(controller.getMovingNumber() == 20){
                        instructionsTextArea.setText("Player " + currentPlayerNumber +" has captured another piece.\n" +
                                "Waiting for selection of piece to move 20.");
                    }
                    if(controller.getMovingNumber() == 10){
                        instructionsTextArea.setText("Player " + currentPlayerNumber +" has reached the end with one piece.\n" +
                                "Waiting for selection of piece to move 10.");
                    }
                    return;
                }

                int playerThatRolled = controller.getCurrentPlayer().getIdNumber();
                int dieNumber = controller.diceRoll();

                if(dieNumber == 5 && controller.getMovingNumber() == 0){
                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    Image currentDieImage = new Image(new ByteArrayInputStream(controller.getDieImageData(dieNumber)));
                    dieRollImageView.setImage(currentDieImage);
                    applyChangesToBoard();
                    instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a 5. So a piece exited the house and entered to the board.\n" +
                            "It is now player " + playerRollingNow + "'s turn. First roll the die" );
                    return;
                }

                if(dieNumber == -1){
                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a 6 three times in a row.\n" +
                            "If possible, this player's most advanced piece returned to the house. " +
                            "\nIt is now player " + playerRollingNow + "'s turn. First roll the die." );
                    applyChangesToBoard();
                    return;
                }

                if(controller.getBoard().getHousePieces()[playerThatRolled-1] == 4){
                    int playerRollingNow = controller.getCurrentPlayer().getIdNumber();
                    if(dieNumber == 6){
                        instructionsTextArea.setText("Since player " + playerThatRolled +" rolled a " + dieNumber + " they can roll again.\n" +
                                "Remember you need a 5 so a piece can enter the board." );
                    } else {
                        instructionsTextArea.setText("Player " + playerThatRolled + " has rolled a " + dieNumber + ".\n" +
                                "You need a 5 so a piece can enter the board. It is now player " + playerRollingNow + "'s turn.");
                    }
                    Image currentDieImage = new Image(new ByteArrayInputStream(controller.getDieImageData(dieNumber)));
                    dieRollImageView.setImage(currentDieImage);
                    applyChangesToLabels();
                    return;
                }

                instructionsTextArea.setText("Player " + playerThatRolled +" has rolled a " + dieNumber + ".\n" +
                        "Click on the piece you want to move " + dieNumber + " squares." );
                if(dieNumber == 6){
                    instructionsTextArea.setText(instructionsTextArea.getText() + "\nSince it was a 6 after moving this player rolls again.");
                }

                Image currentDieImage = new Image(new ByteArrayInputStream(controller.getDieImageData()));
                dieRollImageView.setImage(currentDieImage);

            }


        };
    }

    //Handle trophy image when winner

    //TODO: Check if the display of the trophies when there is a winner in the centre image
    //TODO: Finish the display of die of different colours associated with the player that rolled it
    private void winnerTrophyImage(int player){
        Image winnerTrophyImage = new Image(new ByteArrayInputStream(controller.getTrophyImageData(player)));
        dieRollImageView.setImage(winnerTrophyImage);
    }


}
