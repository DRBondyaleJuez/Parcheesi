package model.Square;

import java.util.Arrays;

public class NormalSquare extends Square{

    private int[] startSquaresPositions = {4,19,34,49};
    private int[] safeSquaresPositions = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquaresPositions = {45,30,15,60};
    private int squareNumber;
    private int[] currentPlayerPiece;
    private boolean isSafe;
    private int isStart;

    public NormalSquare(int squareNumber,boolean isSafe,int isStart) {
        super(squareNumber,isSafe);
        this.isStart = isStart;
    }

    public int getIsStart() {
        return isStart;
    }

}
