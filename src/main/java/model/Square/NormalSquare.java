package model.Square;

import java.util.Arrays;

public class NormalSquare extends Square{

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
