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


    public GameController1DieMode() {
        createPlayers();
        movingNumber = 0;
        dieRepetition = 0;
        board = new Board();
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
            if(currentPiece.getStepCounter() > mostAdvancePosition){
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
    public boolean movePiece(int piecePlayer,int boardPosition){
        //Check for piece presence in boardPosition
        boolean isCorrectPieceThere = checkPlayerPieceInBoardPosition(piecePlayer,boardPosition);
        if(!isCorrectPieceThere){
            return false;
        }

        //Get theoretically piece in the corresponding board position that is about to move the number established by the die
        Piece movingPiece;
        movingPiece = getPieceInBoardPosition(piecePlayer,boardPosition);

        if(movingPiece == null){
            System.out.println("No piece was found even though it fitted all criteria");
            return false;
        }

        //Check for barriers. If there is a barrier the moving number will now be steps to barrier minus 1
        int stepsToBarrier = checkForBarriers(piecePlayer,boardPosition,movingNumber);
        //If the steps of the piece go over 56 before  the barrier position is irrelevant for the movement
        if(movingNumber>stepsToBarrier && movingPiece.getStepCounter()+stepsToBarrier < maxSteps){
            movingNumber = stepsToBarrier - 1; //if barrier is encounter before reaching final squares the movement becomes steps to barrier minus 1.
        }

        //Calculate and get square where the piece is moving to
        Square newSquare;
        int newPieceStepCounter = movingPiece.getStepCounter() + movingNumber;

        if(newPieceStepCounter > maxSteps){
            int newPieceFinalStepCounter = newPieceStepCounter-maxSteps;
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
        movingPiece.move(movingNumber,newSquare);

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
            board.getBoardSquares()[movingPiece.getBoardPosition()-1].setCurrentPlayerPiece(movingPiece.getPlayer());
            return true;
        }

        //Modifying the board to show
        board.getBoardSquares()[movingPiece.getBoardPosition()-1].setCurrentPlayerPiece(movingPiece.getPlayer());

        movingNumber = 0;
        if(dieRepetition == 0){
            nextPlayer();
        }
        return true;
    }

    //Method to verify the presence of a corresponding piece in a position of the board. BoardPosition is an integer asociated with the square in the board
    // For normal squares goes from 1 to 60 and for final squares from 1 to 8
    public boolean checkPlayerPieceInBoardPosition(int piecePlayer,int boardPosition){

        //Reject action if the piece clicked is not of the player currently playing or the die has not been rolled yet
        if(piecePlayer != currentPlayer.getIdNumber() || movingNumber == 0){
            return false;
        }

        //Reject action if there is no piece in the place clicked. To verify this, the board is called to show the corresponding square's two possible positions
        //Check format if the piece clicked is on the normal board squares
        if(boardPosition <= 60) {
            int possiblePlayerPieceInSquare1 = board.getBoardSquares()[boardPosition - 1].getCurrentPlayerPieces()[0];
            int possiblePlayerPieceInSquare2 = board.getBoardSquares()[boardPosition - 1].getCurrentPlayerPieces()[1];
            if (possiblePlayerPieceInSquare1 != piecePlayer && possiblePlayerPieceInSquare2 != piecePlayer) {
                return false;
            }
        }

        //Check format if the piece clicked is on the final board squares
        if(boardPosition > 60){
            int possiblePlayerPieceInSquare1 = board.getFinalSquaresBoard()[piecePlayer][boardPosition-60].getCurrentPlayerPieces()[0];
            int possiblePlayerPieceInSquare2 = board.getFinalSquaresBoard()[piecePlayer][boardPosition-60].getCurrentPlayerPieces()[1];
            if (possiblePlayerPieceInSquare1 != piecePlayer && possiblePlayerPieceInSquare2 != piecePlayer) {
                return false;
            }
        }
        return true;
    }

    public Piece getPieceInBoardPosition(int piecePlayer,int boardPosition){
        //Get and modify correct Piece in board position
        Piece movingPiece = new Piece(0);
        for (int i = 0; i < 4; i++) {
            Piece currentPiece = board.getPlayerPiece(piecePlayer,i);
            if (boardPosition > 60) {
                if (currentPiece.getFinalStepCounter() == boardPosition) {
                    movingPiece = currentPiece;
                }
            }
            if (boardPosition <= 60) {
                if (currentPiece.getBoardPosition() == boardPosition) {
                    movingPiece = currentPiece;
                }
            }
        }
        return movingPiece;
    }

    //This method goes along the squares a piece goes through if there is a barrier the method returns the steps to the barrier encounter
    private int checkForBarriers(int player, int startingBoardPosition, int movingSteps){
        int stepsBeforeBarrier = movingSteps;
        for (int i = 1; i < movingSteps+1; i++) {
           Square testedSquareForBarrier = board.getBoardSquares()[(startingBoardPosition+i)%60];
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

    public boolean onlyForTestSetMovingNumber(int number){
        movingNumber = number;
        return true;
    }

    public int onlyForTestGetMovingNumber(){

        return movingNumber;
    }

}
