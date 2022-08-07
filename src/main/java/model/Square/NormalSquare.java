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
        Piece tempPiece = new Piece();
        tempPiece.setPlayer(newPlayer);
        tempPiece.move(1);
        if(getCurrentPieces()[0].getPlayer() == 0){
            getCurrentPieces()[0].setNewPiece(tempPiece);
            return true;
        }
        if(getCurrentPieces()[1].getPlayer() == 0){
            getCurrentPieces()[1].setNewPiece(tempPiece);
            return true;
        }
        return  false;
    }


}
