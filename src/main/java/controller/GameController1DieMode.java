package controller;

import model.Board;
import model.Piece;
import model.Player;
import model.Square.NormalSquare;
import model.Square.Square;
import persistence.DatabaseManager;

import java.util.Random;

public class GameController1DieMode {

    private Player currentPlayer;
    private Player[] players;
    private int movingNumber;
    private int dieRepetition;
    private Board board;
    private int maxSteps = 56;
    private int isThereAWinner;

    private final DatabaseManager databaseManager;


    public GameController1DieMode() {
        createPlayers();
        movingNumber = 0;
        dieRepetition = 0;
        board = new Board();
        isThereAWinner = 0;
        databaseManager = DatabaseManager.getInstance();
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

    public Board getBoard(){return board;}


    // Method to simulate die rolling between 1 and 6 with special cases for rolling 5 and 6
    public int diceRoll(){

        if(movingNumber > 0){
            return 0;
        }

        Random randomGenerator = new Random();
        int die1Value = randomGenerator.nextInt(6) + 1;

        if(die1Value != 6){
            dieRepetition = 0;
        }

        if(die1Value == 5){
            if(newPieceEnters(currentPlayer.getIdNumber())){
                movingNumber = 0;
                nextPlayer();
                return 0;
            }
        }

        if(die1Value == 6){
            dieRepetition++;
        }

        if(dieRepetition == 3){
            repetitionPunishment();
            movingNumber = 0;
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

        //First check if there are still pieces in the house
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
        playerStartingNormalSquare.enterPiece(currentPlayer.getIdNumber());
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

        Piece mostAdvancePunishablePiece = board.getMostAdvancedPiece(punishedPlayer);

        //Stop the punishment if there has not been any piece punishable for that player
        if(mostAdvancePunishablePiece == null){
            return;
        }

        //If a punishable piece has been selected do the following with the piece and the board:
        //Eliminating piece from the board
        mostAdvancePunishablePiece.clearPiece();
        board.returnPieceToHouse(punishedPlayer);
    }

    //Moving piece after it has been clicked

    public boolean verifySquareClickedAndGameState(int position, int playerIfFinalSquare){

        //The die has not been thrown
        if(movingNumber == 0) return false;

        //If a final square that does not correspond to the current player was clicked
        if(currentPlayer.getIdNumber() != playerIfFinalSquare && playerIfFinalSquare>0) return false;

        //The square clicked does not have a piece or one that corresponds to the current player
        boolean isCorrectPieceThere = checkPlayerPieceInBoardPosition(position,playerIfFinalSquare);

        return isCorrectPieceThere;
    }

    //Method to verify the presence of a corresponding piece in a position of the board. BoardPosition is an integer associated with the square in the board
    // For normal squares goes from 0 to 59 and for final squares from 0 to 6
    public boolean checkPlayerPieceInBoardPosition(int boardPosition,int playerIfFinalSquare){

        int possiblePlayerPieceInSquare1 = 0;
        int possiblePlayerPieceInSquare2 = 0;

        //Reject action if there is no piece in the place clicked. To verify this, the board is called to show the corresponding square's two possible positions
        //Check format if the piece clicked is on the normal board squares
        if(playerIfFinalSquare == 0) {
            possiblePlayerPieceInSquare1 = board.getBoardSquares()[boardPosition].getCurrentPieces()[0].getPlayer();
            possiblePlayerPieceInSquare2 = board.getBoardSquares()[boardPosition].getCurrentPieces()[1].getPlayer();

        }

        //Check format if the piece clicked is on the final board squares
        if(playerIfFinalSquare > 0){
            possiblePlayerPieceInSquare1 = board.getFinalSquaresBoard()[playerIfFinalSquare-1][boardPosition].getCurrentPieces()[0].getPlayer();
            possiblePlayerPieceInSquare2 = board.getFinalSquaresBoard()[playerIfFinalSquare-1][boardPosition].getCurrentPieces()[1].getPlayer();

        }

        if (possiblePlayerPieceInSquare1 == currentPlayer.getIdNumber() || possiblePlayerPieceInSquare2 == currentPlayer.getIdNumber()) {
            return true;
        }

        return false;
    }


    public boolean movePiece(int boardPosition,int playerIfFinal){

        //Get theoretically piece in the corresponding board position that is about to move the number established by the die
        Piece movingPiece = getPieceInBoardPosition(currentPlayer.getIdNumber(), boardPosition, playerIfFinal);

        if(movingPiece == null){
            System.out.println("No piece was found even though it fitted all criteria");
            return false;
        }

        //Check for barriers. If there is a barrier the moving number will now be steps to barrier minus 1
        int stepsToBarrier = checkForBarriers(currentPlayer.getIdNumber(), boardPosition, movingNumber);
        //If the steps of the piece go over 56 before  the barrier position is irrelevant for the movement
        if(movingNumber > stepsToBarrier && movingPiece.getStepCounter() + stepsToBarrier < maxSteps){
            movingNumber = stepsToBarrier-1; //if barrier is encounter before reaching final squares the movement becomes steps to barrier minus 1.
        }

        //Change the step counter and calculate and get square where the piece is moving to
        Square newSquare;
        movingPiece.move(movingNumber);
        newSquare = board.getCorrespondingBoardSquare(currentPlayer.getIdNumber(), movingPiece.getFinalStepCounter(), movingPiece.getStepCounter());

        //Now knowing the square the piece will occupy we check if this square is the end or if a piece has been captured before moving the piece here
        //The corresponding changes to the conditions are also applied

        //Check if piece has reached the end
        if(newSquare.isTheEnd()) {
            newSquare.setCurrentPiece(movingPiece);
            movingPiece.clearPiece();
            board.pieceReachesTheEnd(currentPlayer.getIdNumber());
            newSquare.getCurrentPieces()[0].clearPiece();
            movingNumber = 10;
            return true;
        }

        if(hasMovingPieceCapturedAnotherPiece(newSquare)){
            newSquare.setCurrentPiece(movingPiece);
            movingPiece.clearPiece();
            movingNumber = 20;
            return true;
        }

        //If there is no particular event we simply change the attributes of the piece on the new position to match those of the moving piece
        newSquare.setCurrentPiece(movingPiece);
        movingPiece.clearPiece();

        movingNumber = 0;
        if(dieRepetition == 0){
            nextPlayer();
        }
        return true;
    }

    public Piece getPieceInBoardPosition(int piecePlayer,int boardPosition, int playerIfFinalSquares){
        //Get and modify correct Piece in board position
        Square tempSquare = board.getBoardSquares()[boardPosition];
        if(playerIfFinalSquares > 0) {
            tempSquare = board.getFinalSquaresBoard()[playerIfFinalSquares-1][boardPosition];
        }
        Piece movingPiece = null;

        if(tempSquare.getCurrentPieces()[1].getPlayer() == piecePlayer){
            movingPiece = tempSquare.getCurrentPieces()[1];
        } else if(tempSquare.getCurrentPieces()[0].getPlayer() == piecePlayer){
            movingPiece = tempSquare.getCurrentPieces()[0];
        }

        return movingPiece;
    }

    public Square getSquare(int position, int possiblePlayer){
        if(possiblePlayer == 0){
            return board.getBoardSquares()[position];
        } else {
            return board.getFinalSquaresBoard()[possiblePlayer][position];
        }
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


    private boolean hasMovingPieceCapturedAnotherPiece(Square squareWherePieceIsAboutToMoveTo){

        //Dismiss capturing if square is Safe
        if(squareWherePieceIsAboutToMoveTo.isSafe()){return  false;}

        int possibleCapturedPlayerPiece = squareWherePieceIsAboutToMoveTo.getCurrentPieces()[0].getPlayer();
        if(possibleCapturedPlayerPiece != 0 && possibleCapturedPlayerPiece != currentPlayer.getIdNumber()){
            board.returnPieceToHouse(possibleCapturedPlayerPiece);
            return true;
        }
        return false;
    }

    public int isThereAWinner(int player) {
        int numberOfPiecesFinished = board.getFinishedPieces(player);
        if(numberOfPiecesFinished==4) {
            isThereAWinner = player;
        }
        return isThereAWinner;
    }

    public boolean isThereAWinner(){
        return isThereAWinner != 0;
    }

    //METHODS FOR IMAGE HANDLING
    public byte[] getDieImageData(){
        return databaseManager.getDieImageData(movingNumber);
    }

    public byte[] getPieceImageData(String playerPieces, String orientation){
        return databaseManager.getPieceImageData(playerPieces, orientation);
    }

    public byte[] getBoardImageData(){
        return databaseManager.getBoardImageData(1);
    }


    // METHODS EXCLUSIVE FOR QUICK TESTING
    public boolean onlyForTestSetMovingNumber(int number){
        movingNumber = number;
        return true;
    }

    public int onlyForTestGetMovingNumber(){

        return movingNumber;
    }

}
