package model;

import java.util.Arrays;

public class Square {

    private int[] startSquares = {4,19,34,49};
    private int[] safeSquares = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquares = {45,30,15,60};
    private int squareNumber;

    private Piece currentPiece;
    private boolean isSafe;
    private int isStart;
    private Square nextSquare;
    private FinalSquare finalBranchFirstSquare;

    public Square(int squareNumber) {
        this.squareNumber = squareNumber;
        this.currentPiece = null;
        this.nextSquare = null;
        //Verifying if the squareNumber is in the array of safeSquares
        isSafe = Arrays.stream(safeSquares).anyMatch(i->i==squareNumber);
        //Determining which players start square if any is this square
        isStart = 0;
        for (int i = 0; i < startSquares.length; i++) {
            if (startSquares[i] == squareNumber){
                isStart = i+1;
            }
        }

    }

    public int getSquareNumber() {
        return squareNumber;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public int getIsStart() {
        return isStart;
    }

    public Square getNextSquare() {
        return nextSquare;
    }

    public FinalSquare getFinalBranchFirstSquare() {
        return finalBranchFirstSquare;
    }


    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public void setFinalBranchFirstSquare(FinalSquare finalBranchFirstSquare) {
        this.finalBranchFirstSquare = finalBranchFirstSquare;
    }

    public void setNextSquare(Square nextSquare) {
        this.nextSquare = nextSquare;
    }
}
