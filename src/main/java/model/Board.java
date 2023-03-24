package model;

import model.Square.FinalSquare;
import model.Square.NormalSquare;
import model.Square.Square;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides an object that represents a parcheesi board made of arrays of Square objects
 */
public class Board {

    private NormalSquare[] boardNormalSquares;
    private FinalSquare[][] boardFinalSquares;
    private int[] housePieces; //These are the pieces not played by each player
    private int[] finishedPieces; //This is the pieces that have reached the end
    private int[] startSquares = {4,19,34,49};
    private int[] safeSquares = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquares = {60,15,30,45};
    private int maxSteps = 57;

    /**
     * This is the constructor. When declared it builds the array of squares and the matrix of final squares where
     * each row corresponds to a player.
     */
    public Board() {

        //Setting initial configuration of houses
        housePieces = new int[4];
        for (int i=0; i<housePieces.length; i++ ) {
            housePieces[i] = 4;
        };

        //Building boardSquares, finalSquares and Pieces
        boardNormalSquares = buildBoardSquares();

        //Building finalSquaresBoard
        boardFinalSquares = buildBoardFinalSquares();


        //Initializing the finished array
        finishedPieces = new int[4];

    }

    //Method to build the attribute boardSquares
    private NormalSquare[] buildBoardSquares(){
        NormalSquare[] newBoardSquares = new NormalSquare[60];

        for (int i = 0; i < newBoardSquares.length; i++) {
            boolean isSafe = isSquareSafe(i+1);
            int isStart = 0;
            for (int j = 0; j < startSquares.length; j++) {
                if (startSquares[j] == i+1){
                    isStart = j+1;
                    break;
                }
            }
            newBoardSquares[i] = new NormalSquare(i+1,isSafe,isStart);
        }
        return newBoardSquares;
    }

    //This method is used to compare a squareNumber with the numbers in the array of safeSquares
    private boolean isSquareSafe(int squareNumber){
        for (int i = 0; i < safeSquares.length; i++) {
            if(squareNumber == safeSquares[i]) {
                return true;
            }
        }
        return false;

    }

    //Method to build the attribute finalSquares
    private FinalSquare[][] buildBoardFinalSquares(){
        FinalSquare[][] newBoardFinalSquares = new FinalSquare[4][7];
        for (int player = 0; player < newBoardFinalSquares.length; player++) {
            for (int i = 0; i < newBoardFinalSquares[0].length; i++) {
                newBoardFinalSquares[player][i]= new FinalSquare(i+1,player);
            }
        }
        return newBoardFinalSquares;
    }

    /**
     * This method is triggered when a player rolls three 6s causing their most advance piece to return to the house
     * @param player The player whose most advance piece needs to be returned.
     * @return boolean true if the punishment took place correctly
     */
    public boolean punishMostAdvancePiece(int player){
        int furthestBoardPosition = finalSquares[player-1];
        for (int i = furthestBoardPosition-1; i > furthestBoardPosition-maxSteps-1; i--) {
            int pos=i;
            if(i<0){
                pos=60+i;
            }
            ArrayList<Piece> currentSquarePieces = boardNormalSquares[pos].getCurrentPieces();
            if(currentSquarePieces.isEmpty()) {
                continue;
            }
            if(currentSquarePieces.size() == 1) {
                if(currentSquarePieces.get(0).getPlayer() == player){
                    currentSquarePieces.remove(0);
                    return true;
                }
            }

            if(currentSquarePieces.size() == 2) {
                if(currentSquarePieces.get(1).getPlayer() == player){
                    currentSquarePieces.remove(1);
                    return true;
                }
                if(currentSquarePieces.get(0).getPlayer() == player){
                    currentSquarePieces.remove(0);
                    return true;
                }
            }
        }
        return false;
    }

// GETTERS
    public int getNumberOfPiecesInHouse(int player){
        int position = player-1;
        return housePieces[position];
    }

    public NormalSquare[] getBoardSquares() {
        return boardNormalSquares;
    }

    public FinalSquare[][] getFinalSquaresBoard() {
        return boardFinalSquares;
    }

    public int[] getStartSquares() {
        return startSquares;
    }

    public int[] getHousePieces(){ return housePieces;}
    public int getFinishedPieces(int player){
        return finishedPieces[player-1];
    }
    /**
     * Modify counters related to the number of pieces in house. In this case reducing the number that corresponded to
     * one of the players.
     * @param player int that corresponds to the player whose piece is leaving the house
     */
    public void exitPieceFromHouse(int player){
        housePieces[player-1]--;
    }

    /**
     * Modify counters related to the number of pieces in house. In this case icreasing the number that corresponded to
     * one of the players.
     * @param player int that corresponds to the player whose piece is returnin to the house
     */
    public void returnPieceToHouse(int player){
        housePieces[player-1]++;
    }

    /**
     * Increasing counters associated with pieces reaching the end of the board when a piece finishes its whole path
     * @param player int  corresponding to the player whose piece has reached the end
     */
    public void pieceReachesTheEnd(int player){
        int position = player-1;
        finishedPieces[position]++;
    }

    /**
     * Get a particular board square
     * @param player int corresponding to the player 1 to 4
     * @param finalStepCounter int number of steps of the final path
     * @param stepCounter int number of steps covered from the starting position
     * @return Square which corresponds to the square a piece would be provided all the parameters. This means the player
     * to determine the starting position or the row in the finalSquares then using the steps the corresponding square
     * of the arrays in the attributes of the board can be found and returned.
     */
    public Square getCorrespondingBoardSquare(int player, int finalStepCounter, int stepCounter){

        if(finalStepCounter > -1){
            return getFinalSquaresBoard()[player-1][finalStepCounter];
        }

        int startingPosition = 0;
        int newBoardPosition;
        startingPosition = startSquares[player-1]-1;
        newBoardPosition = (startingPosition + stepCounter)%60;
        return getBoardSquares()[newBoardPosition];
    }

}
