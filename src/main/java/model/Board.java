package model;

import model.Square.FinalSquare;
import model.Square.NormalSquare;
import model.Square.Square;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private NormalSquare[] boardNormalSquares;
    private FinalSquare[][] boardFinalSquares;
    private int[] housePieces; //These are the pieces not played by each player
    private int[] finishedPieces; //This is the pieces that have reached the end
    private int[] startSquares = {4,19,34,49};
    private int[] safeSquares = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquares = {60,15,30,45};
    private int maxSteps = 57;

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

    public void exitPieceFromHouse(int player){
        housePieces[player-1]--;
    }

    public void returnPieceToHouse(int player){
        housePieces[player-1]++;
    }

    public void pieceReachesTheEnd(int player){
        int position = player-1;
        finishedPieces[position]++;
    }
    
    public int getFinishedPieces(int player){
        return finishedPieces[player-1];
    }

    public Square getCorrespondingBoardSquare(int player, int finalStepCounter, int stepCounter){

        if(finalStepCounter > -1){
            return getFinalSquaresBoard()[player-1][finalStepCounter];
        }

        int startingPosition = 0;
        int newBoardPosition;
        switch(player){
            case 1:
                startingPosition = 4;
                break;
            case 2:
                startingPosition = 19;
                break;
            case 3:
                startingPosition = 34;
                break;
            case 4:
                startingPosition = 49;
                break;
            default:
                break;
        }
        newBoardPosition = (startingPosition + stepCounter)%60;
        return getBoardSquares()[newBoardPosition-1];
    }

}
