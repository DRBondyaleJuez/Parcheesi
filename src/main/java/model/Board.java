package model;

import model.Square.FinalSquare;
import model.Square.NormalSquare;

import java.util.Arrays;

public class Board {

    private NormalSquare[] boardNormalSquares;
    private FinalSquare[][] boardFinalSquares;
    private Piece[][] pieces;
    private int[] housePieces; //These are the pieces not played by each player
    private int[] finishedPieces; //This is the pieces that have reached the end
    private int[] startSquares = {4,19,34,49};
    private int[] safeSquares = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquares = {45,30,15,60};

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

        //Building Pieces
        pieces = buildPieces();

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

    //Method to build the attribute pieces
    private Piece[][] buildPieces(){
        Piece[][] newPieces = new Piece[4][4];
        for (int player = 0; player < 4; player++) {
            for (int number = 0; number < 4; number++) {

                newPieces[player][number] = new Piece(player+1);

            }
        }
        return newPieces;
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
    
    public Piece getPlayerPiece(int player, int number){
        
        return pieces[player-1][number];
    }
}
