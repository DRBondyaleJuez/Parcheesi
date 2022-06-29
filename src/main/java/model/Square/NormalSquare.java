package model.Square;

import java.util.Arrays;

public class NormalSquare implements Square{

    private int[] startSquaresPositions = {4,19,34,49};
    private int[] safeSquaresPositions = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquaresPositions = {45,30,15,60};
    private int squareNumber;
    private int[] currentPlayerPiece;
    private boolean isSafe;
    private int isStart;
    private Square nextSquare;
    private FinalSquare finalBranchFirstSquare;

    public NormalSquare(int squareNumber) {
        this.squareNumber = squareNumber;
        this.currentPlayerPiece[0] = 0;
        this.currentPlayerPiece[1] = 0;
        this.nextSquare = null;
        //Verifying if the squareNumber is in the array of safeSquares
        isSafe = Arrays.stream(safeSquaresPositions).anyMatch(i->i==squareNumber);
        //Determining which players start square if any is this square
        isStart = 0;
        for (int i = 0; i < startSquaresPositions.length; i++) {
            if (startSquaresPositions[i] == squareNumber){
                isStart = i+1;
            }
        }

    }

    @Override
    public int getSquareNumber() {
        return squareNumber;
    }

    @Override
    public int[] getCurrentPlayerPieces() {
        return currentPlayerPiece;
    }


    public boolean isSafe() {
        return isSafe;
    }

    public int getIsStart() {
        return isStart;
    }

    @Override
    public Square getNextSquare() {
        return nextSquare;
    }

    public FinalSquare getFinalBranchFirstSquare() {
        return finalBranchFirstSquare;
    }


    @Override
    public boolean isBlocked(){
        if(currentPlayerPiece[0]>0 && currentPlayerPiece[1]>0){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void setCurrentPlayerPiece(int playerPiece) {
        if(isSafe){
            if(currentPlayerPiece[0]>0){
                currentPlayerPiece[1] = playerPiece;
            }
        } else {
            if(currentPlayerPiece[0]==playerPiece){
                currentPlayerPiece[1] = playerPiece;
            }else{
                currentPlayerPiece[0] = playerPiece;
            }
        }
    }

    private void reorderPlayerPieces(){
        if(currentPlayerPiece[0] == 0){
            currentPlayerPiece[0] = currentPlayerPiece[1];
            currentPlayerPiece[1] = 0;
        }
        return;
    }

    @Override
    public void removeCurrentPlayerPiece(int player){
        if(currentPlayerPiece[1] == player){
            currentPlayerPiece[1] = 0;
        } else {
            currentPlayerPiece[0] = 0;
        }
        reorderPlayerPieces();
        return;
    }


    public void setFinalBranchFirstSquare(FinalSquare finalBranchFirstSquare) {
        this.finalBranchFirstSquare = finalBranchFirstSquare;
    }

    @Override
    public void setNextSquare(Square nextSquare) {
        this.nextSquare = nextSquare;
    }
}
