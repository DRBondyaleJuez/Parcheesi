package model.Square;

import model.Piece;

import java.util.Arrays;

public class NormalSquare extends Square{

    private int isStart;

    public NormalSquare(int squareNumber,boolean isSafe,int isStart) {
        super(squareNumber,isSafe);
        this.isStart = isStart;
    }

    public int getIsStart() {
        return isStart;
    }

    public boolean enterPiece(int newPlayer){
        Piece enteringPiece = new Piece();
        enteringPiece.setPlayer(newPlayer);
        enteringPiece.move(1);
        if(isBlocked()){
            return  false;
        }
        setCurrentPiece(enteringPiece);
        return true;
    }


}
