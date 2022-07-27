package controller;

import model.Board;
import model.Piece;
import model.Player;
import model.Square.NormalSquare;
import model.Square.Square;

import java.util.Random;

public class GameController1DieMode {

    private Player currentPlayer;
    private Player[] players;
    private int movingNumber;
    private int dieRepetition;
    private Board board;
    private int maxSteps = 56;
    private int isThereAWinner;


    public GameController1DieMode() {
        createPlayers();
        movingNumber = 0;
        dieRepetition = 0;
        board = new Board();
        isThereAWinner = 0;
    }

    private void createPlayers() {
        players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i+1,"Player " + (i+1));
        }
        currentPlayer = players[0];
    }

    //Method to change current player in order 1-2-3-4-1...
    public void nextPlayer(){
        int nextPlayerPos = currentPlayer.getIdNumber();
        if(nextPlayerPos==4){
            nextPlayerPos=0;
        }
        currentPlayer = players[nextPlayerPos];
    }

    //Get current player number
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    //Get moving number
    public int getMovingNumber(){
        return movingNumber;
    }


    // Method to simulate die rolling between 1 and 6 with special cases for rolling 5 and 6
    public int diceRoll(){
        Random randomGenerator = new Random();
        int die1Value = randomGenerator.nextInt(6) + 1;

        if(die1Value != 6){
            dieRepetition = 0;
        }

        if(die1Value == 5){
            if(newPieceEnters(currentPlayer.getIdNumber())){
                nextPlayer();
                return 0;
            }
        }

        if(die1Value == 6){
            dieRepetition++;
        }

        if(dieRepetition == 3){
            repetitionPunishment();
            nextPlayer();
            return -1;
        }

        movingNumber = die1Value;
        return die1Value;
    }

    //Method to handle rolling a 6 which requires repetition and registering repetitions
    public int dieRoll6(){
        Random randomGenerator = new Random();
        int die1Value = randomGenerator.nextInt(6);

        if(die1Value != 6){
            dieRepetition = 0;
        }

        if(die1Value == 5){
            if(newPieceEnters(currentPlayer.getIdNumber())){
                nextPlayer();
                return 0;
            } else{
                return 5;
            }
        }

        if(die1Value == 6){
            dieRepetition++;
        }
        if(dieRepetition == 3){
            repetitionPunishment();
            return 0;
        }

        return 6+die1Value;

    }

    //Method to handle rolling a 5 which may require a piece entering
    public boolean newPieceEnters(int player){

        if(board.getNumberOfPiecesInHouse(player)<1){
            return false;
        }

        //Declaring starting square for the corresponding player
        int startingBoardPosition = board.getStartSquares()[player-1]-1;
        NormalSquare playerStartingNormalSquare = board.getBoardSquares()[startingBoardPosition];

        //Check if the square is blocked by the presence of two pieces
        if(playerStartingNormalSquare.isBlocked()){
            return false;
        }

        //Put piece in starting square
        playerStartingNormalSquare.setCurrentPlayerPiece(player);
        int pieceNumber = 4 - board.getNumberOfPiecesInHouse(player);
        Piece enteringPiece = board.getPlayerPiece(player,pieceNumber);
        enteringPiece.move(1,playerStartingNormalSquare);
        board.exitPieceFromHouse(player);
        return true;

    }


    //Method to remove most advance piece that is not in the final squares
    public void repetitionPunishment(){

        int punishedPlayer = currentPlayer.getIdNumber();
        //first make sure there is a piece on the board
        if(board.getNumberOfPiecesInHouse(punishedPlayer) == 4){
            return;

        }

        Piece mostAdvancePunishablePiece;
        int mostAdvancePosition = -1;
        int pieceNumberPosition = -1;

        for (int i = 0; i < 4; i++) {
            Piece currentPiece = board.getPlayerPiece(punishedPlayer,i);
            if(currentPiece.getFinalStepCounter()>0 || currentPiece.getStepCounter()<0){
                continue;
            }
            if(currentPiece.getStepCounter() >= mostAdvancePosition){
                mostAdvancePosition = currentPiece.getStepCounter();
                pieceNumberPosition = i;
            }
        }

        //Stop the punishment if there has not been any piece punishable for that player
        if(pieceNumberPosition == -1){
            return;
        } else{
            mostAdvancePunishablePiece = board.getPlayerPiece(punishedPlayer,pieceNumberPosition);
        }

        //If a punishable piece has been selected do the following with the piece and the board:
        //Eliminating piece from the board
        int punishablePieceBoardPosition = mostAdvancePunishablePiece.getBoardPosition();
        board.getBoardSquares()[punishablePieceBoardPosition-1].removeCurrentPlayerPiece(punishedPlayer);
        //Modifying the piece
        mostAdvancePunishablePiece.setCurrentSquare(null);
        board.returnPieceToHouse(punishedPlayer);
    }

    //Moving piece after being selected

    public boolean verifySquareClickedAndGameState(int position, int playerIfFinalSquare){

        //The die has not been thrown
        if(movingNumber == 0) return false;

        //If a final square that does not correspond to the current player was clicked
        if(currentPlayer.getIdNumber() != playerIfFinalSquare && playerIfFinalSquare>0) return false;

        //The square clicked does not have a piece or one that corresponds to the current player
        boolean isCorrectPieceThere = checkPlayerPieceInBoardPosition(playerIfFinalSquare,position);

        return isCorrectPieceThere;
    }

    //Method to verify the presence of a corresponding piece in a position of the board. BoardPosition is an integer asociated with the square in the board
    // For normal squares goes from 1 to 60 and for final squares from 1 to 7
    public boolean checkPlayerPieceInBoardPosition(int boardPosition,int playerIfFinalSquare){

        int possiblePlayerPieceInSquare1 = 0;
        int possiblePlayerPieceInSquare2 = 0;

        //Reject action if there is no piece in the place clicked. To verify this, the board is called to show the corresponding square's two possible positions
        //Check format if the piece clicked is on the normal board squares
        if(playerIfFinalSquare > 0) {
            possiblePlayerPieceInSquare1 = board.getBoardSquares()[boardPosition].getCurrentPlayerPieces()[0];
            possiblePlayerPieceInSquare2 = board.getBoardSquares()[boardPosition].getCurrentPlayerPieces()[1];

        }

        //Check format if the piece clicked is on the final board squares
        if(playerIfFinalSquare == 0){
            possiblePlayerPieceInSquare1 = board.getFinalSquaresBoard()[playerIfFinalSquare][boardPosition].getCurrentPlayerPieces()[0];
            possiblePlayerPieceInSquare2 = board.getFinalSquaresBoard()[playerIfFinalSquare][boardPosition].getCurrentPlayerPieces()[1];

        }

        if (possiblePlayerPieceInSquare1 == currentPlayer.getIdNumber() || possiblePlayerPieceInSquare2 != currentPlayer.getIdNumber()) {
            return true;
        }

        return false;
    }


    public boolean movePiece(int boardPosition){

        //Get theoretically piece in the corresponding board position that is about to move the number established by the die
        Piece movingPiece = getPieceInBoardPosition(currentPlayer.getIdNumber(), boardPosition);

        if(movingPiece == null){
            System.out.println("No piece was found even though it fitted all criteria");
            return false;
        }

        //Check for barriers. If there is a barrier the moving number will now be steps to barrier minus 1
        int stepsToBarrier = checkForBarriers(currentPlayer.getIdNumber(), boardPosition,movingNumber);
        //If the steps of the piece go over 56 before  the barrier position is irrelevant for the movement
        if(movingNumber>stepsToBarrier && movingPiece.getStepCounter()+stepsToBarrier < maxSteps){
            movingNumber = stepsToBarrier; //if barrier is encounter before reaching final squares the movement becomes steps to barrier minus 1.
        }

        //Calculate and get square where the piece is moving to
        Square newSquare;
        int newPieceStepCounter = movingPiece.getStepCounter() + movingNumber;

        if(newPieceStepCounter > maxSteps){
            int newPieceFinalStepCounter = newPieceStepCounter-maxSteps;
            //The final squares require special procedures if the steps overpass or reach the end
            if(newPieceFinalStepCounter > 7){
                newPieceFinalStepCounter = movingPiece.finalMoveCalculator(newPieceFinalStepCounter);
            }
            newSquare = board.getFinalSquaresBoard()[currentPlayer.getIdNumber()-1][newPieceFinalStepCounter-1];
        } else {
            int newBoardPosition = movingPiece.boardPositionCalculator(newPieceStepCounter);
            newSquare = board.getBoardSquares()[newBoardPosition-1];
        }

        //Now knowing the steps and the new square we change the moving piece attributes and the board attributes
        movingPiece.move(movingNumber,newSquare);

        if(movingPiece.getFinalStepCounter()>0) {
            //Check if piece has reached the end
            if (hasMovingPieceReachedTheEnd(movingPiece)) {
                board.pieceReachesTheEnd(currentPlayer.getIdNumber());
                movingNumber=10;
                return true;
            }
            board.getFinalSquaresBoard()[currentPlayer.getIdNumber()-1][movingPiece.getFinalStepCounter()-1].setCurrentPlayerPiece(currentPlayer.getIdNumber());
            movingNumber=0;
            return true;
        }

        if(hasMovingPieceCapturedAnotherPiece(movingPiece)){
            movingNumber = 20;
            board.getBoardSquares()[movingPiece.getBoardPosition()-1].setCurrentPlayerPiece(movingPiece.getPlayer());
            return true;
        }

        //Modifying the board to show
        int newBoardPosition = movingPiece.getBoardPosition();
        if(newBoardPosition>60){
            board.getFinalSquaresBoard()[currentPlayer.getIdNumber()-1][newBoardPosition - 60-1].setCurrentPlayerPiece(movingPiece.getPlayer());
        } else {
            board.getBoardSquares()[movingPiece.getBoardPosition() - 1].setCurrentPlayerPiece(movingPiece.getPlayer());
        }

        movingNumber = 0;
        if(dieRepetition == 0){
            nextPlayer();
        }
        return true;
    }

    public Piece getPieceInBoardPosition(int piecePlayer,int boardPosition){
        //Get and modify correct Piece in board position
        Piece movingPiece = null;
        for (int i = 0; i < 4; i++) {
            Piece currentPiece = board.getPlayerPiece(piecePlayer,i);
            if (currentPiece.getBoardPosition() == boardPosition) {
                movingPiece = currentPiece;
                break;
            }
        }
        return movingPiece;
    }

    //This method goes along the squares a piece goes through if there is a barrier the method returns the steps to the barrier encounter
    private int checkForBarriers(int player, int startingBoardPosition, int movingSteps){
        int stepsToBarrier = movingSteps;
        for (int i = 1; i < movingSteps+1; i++) {
           Square testedSquareForBarrier = board.getBoardSquares()[(startingBoardPosition+i)%60];
           if(testedSquareForBarrier.isBlocked()){
               stepsToBarrier = i;
               break;
           }
        }
        return stepsToBarrier;
    }

    private boolean hasMovingPieceReachedTheEnd(Piece movingPiece){
        return movingPiece.getFinalStepCounter()==7;
    }

    private boolean hasMovingPieceCapturedAnotherPiece(Piece movingPiece){
        Square currentSquareOfMovingPiece = movingPiece.getCurrentSquare();

        //Dismiss capturing if square is Safe
        if(currentSquareOfMovingPiece.isSafe()){return  false;}

        int possibleCapturedPlayerPiece = currentSquareOfMovingPiece.getCurrentPlayerPieces()[0];
        if(possibleCapturedPlayerPiece != 0 && possibleCapturedPlayerPiece != movingPiece.getPlayer()){
            pieceCaptured(movingPiece.getBoardPosition(),possibleCapturedPlayerPiece);
            return true;
        }
        return false;
    }

    private void pieceCaptured(int capturedPieceBoardPosition, int capturedPiecePlayer){

        for (int i = 0; i < 4; i++) {
            Piece currentPiece = board.getPlayerPiece(capturedPiecePlayer,i);
            if(currentPiece.getBoardPosition() == capturedPieceBoardPosition){
                currentPiece.setCurrentSquare(null);
            }
        }

        board.returnPieceToHouse(capturedPiecePlayer);

    }

    public int isThereAWinner(int player) {
        int numberOfPiecesFinished = board.getFinishedPieces(player);
        if(numberOfPiecesFinished==4) {
            isThereAWinner = player;
        }
        return isThereAWinner;
    }

    public boolean onlyForTestSetMovingNumber(int number){
        movingNumber = number;
        return true;
    }

    public int onlyForTestGetMovingNumber(){

        return movingNumber;
    }

}
