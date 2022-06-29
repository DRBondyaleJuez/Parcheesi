package controller;

import model.Board;
import model.Piece;
import model.Player;
import model.Square.Square;

import java.util.Random;

public class GameController1DieMode {

    Player currentPlayer;
    Player[] players;
    int movingNumber;
    int dieRepetition;
    Board board;


    public GameController1DieMode() {
        createPlayers();
        movingNumber=0;
        board = new Board();
    }

    private void createPlayers() {
        players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i+1,"Player " + i);
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

    // Method to simulate die rolling between 1 and 6 with special cases for rolling 5 and 6
    public int diceRoll(){
        Random randomGenerator = new Random();
        int die1Value = randomGenerator.nextInt(6);

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
        int position = player-1;
        if(board.getNumberOfPiecesInHouse(position)<1){
            return false;
        }

        return board.pieceExitsHouse(player);

    }

    //Method to remove most advance piece that is not in the final squares
    public void repetitionPunishment(){

        //board.
    }

    //Moving piece after being selected
    public boolean movePiece(int piecePlayer,int boardPosition){
        //Check for piece presence in boardPosition
        boolean isCorrectPieceThere = checkPlayerPieceInBoardPosition(piecePlayer,boardPosition);
        if(!isCorrectPieceThere){
            return false;
        }

        //Get theoretically piece in the corresponding board position that is about to move the number established by the die
        Piece movingPiece;
        movingPiece = getPieceinBoardPosition(piecePlayer,boardPosition);

        if(movingPiece == null){
            System.out.println("No piece was found eventhough it fitted all criteria");
            return false;
        }

        //Check for barriers. If there is a barrier the moving number will now be steps to barrier minus 1
        int stepsToBarrier = checkForBarriers(piecePlayer,boardPosition,movingNumber);
        //If the steps of the piece go over 60 before the barrier the barrier position is irrelevant for the movement
        if(movingPiece.getStepCounter()+stepsToBarrier < 60){
            movingNumber = stepsToBarrier - 1; //if barrier is encounter before reaching final squares the movement becomes steps to barrier minus 1.
        }

        //Calculate and get square where the piece is moving to
        Square newSquare;
        int newPieceStepCounter = movingPiece.getStepCounter() + movingNumber;

        if(newPieceStepCounter > 60){
            int newPieceFinalStepCounter = newPieceStepCounter-60;
            //The final squares require special procedures if the steps overpass or reach the end
            if(newPieceFinalStepCounter > 8){
                newPieceFinalStepCounter = movingPiece.finalMoveCalculator(movingNumber);
            }
            newSquare = board.getFinalSquaresBoard()[piecePlayer-1][newPieceFinalStepCounter-1];
        } else {
            int newBoardPosition = movingPiece.boardPositionCalculator(newPieceStepCounter);
            newSquare = board.getBoardSquares()[newBoardPosition-1];
        }

        //Now knowing the steps and the new square we change the moving piece attributes and the board attributes
        movingPiece.Move(movingNumber,newSquare);

        if(movingPiece.getFinalStepCounter()>0) {
            //Check if piece has reached the end
            if (hasMovingPieceReachedTheEnd(movingPiece)) {
                board.getFinalSquaresBoard()[piecePlayer][8].setCurrentPlayerPiece(piecePlayer);
                board.pieceReachesTheEnd(piecePlayer);
                movingNumber=10;
                return true;

            }
            board.getFinalSquaresBoard()[piecePlayer][movingPiece.getFinalStepCounter()-1].setCurrentPlayerPiece(piecePlayer);
            movingNumber=0;
            return true;
        }



        if(hasMovingPieceCapturedAnotherPiece(movingPiece)){
            movingNumber = 20;
            pieceCaptured();
        }

        //Modifying the board to show
        board.getBoardSquares()[movingPiece.getBoardPosition()].setCurrentPlayerPiece(movingPiece.getPlayer());



    }

    //Method to verify the presence of a corresponding piece in a position of the board. BoardPosition is an integer asociated with the square in the board
    // For normal squares goes from 1 to 60 and for final squares from 1 to 8
    private boolean checkPlayerPieceInBoardPosition(int piecePlayer,int boardPosition){

        //Reject action if the piece clicked is not of the player currently playing or the die has not been rolled yet
        if(piecePlayer != currentPlayer.getIdNumber() || movingNumber == 0){
            return false;
        }

        //Reject action if no piece in the place clicked. For that board is called to show the corresponding square's two possible positions
        //Check format if the piece clicked is on the normal board squares
        if(boardPosition <= 60) {
            int possiblePlayerPieceInSquare1 = board.getBoardSquares()[boardPosition - 1].getCurrentPlayerPieces()[0];
            int possiblePlayerPieceInSquare2 = board.getBoardSquares()[boardPosition - 1].getCurrentPlayerPieces()[1];
            if (possiblePlayerPieceInSquare1 != piecePlayer && possiblePlayerPieceInSquare2 != piecePlayer) {
                return false;
            }
        }

        //Check format if the piece clicked is on the normal board squares
        if(boardPosition > 60){
            int possiblePlayerPieceInSquare1 = board.getFinalSquaresBoard()[piecePlayer][boardPosition-60].getCurrentPlayerPieces()[0];
            int possiblePlayerPieceInSquare2 = board.getFinalSquaresBoard()[piecePlayer][boardPosition-60].getCurrentPlayerPieces()[0];
            if (possiblePlayerPieceInSquare1 != piecePlayer && possiblePlayerPieceInSquare2 != piecePlayer) {
                return false;
            }
        }
        return true;
    }

    private Piece getPieceInBoardPosition(int piecePlayer,int boardPosition){
        //Get and modify correct Piece in board position
        Piece movingPiece;
        Piece[] allPlayerPieces = board.getPlayerPieces(piecePlayer);
        for (int i = 0; i < allPlayerPieces.length; i++) {
            if (boardPosition > 60) {
                if (allPlayerPieces[i].getFinalStepCounter() == boardPosition) {
                    movingPiece = allPlayerPieces[i];
                }
            }
            if (boardPosition <= 60) {
                if (allPlayerPieces[i].getBoardPosition() == boardPosition) {
                    movingPiece = allPlayerPieces[i];
                }
            }
        }
        return movingPiece;
    }

    //This method goes along the squares a piece goes through if there is a barrier the method returns the steps to the barrier encounter
    private int checkForBarriers(int player, int startingBoardPosition, int movingSteps){
        int stepsBeforeBarrier = movingSteps;
        for (int i = 1; i < movingSteps+1; i++) {
           Square testedSquareForBarrier = board.getBoardSquares()[startingBoardPosition+i];
           if(testedSquareForBarrier.isBlocked()){
               stepsBeforeBarrier = i-1;
               break;
           }
        }
        return stepsBeforeBarrier;
    }

    private boolean hasMovingPieceReachedTheEnd(Piece movingPiece){
        return movingPiece.getFinalStepCounter()==8;
    }

    private boolean hasMovingPieceCapturedAnotherPiece(Piece movingPiece){
        Square currentSquareOfMovingPiece = movingPiece.getCurrentSquare();
        if(currentSquareOfMovingPiece.getCurrentPlayerPieces()[0] != movingPiece.getPlayer()){
            return true;
        }
        return false;
    }

    private void pieceCaptured(){


    }

}
