package model.Square;

import model.Piece;

import java.util.ArrayList;

public class Square {

    private int squareNumber;
    private ArrayList<Piece> currentPlayerPiece;

    private boolean isSafe;

    private boolean isTheEnd;

    public Square(int squareNumber,boolean isSafe) {
        this.squareNumber = squareNumber;
        currentPlayerPiece = new ArrayList<>();
        this.isSafe = isSafe;
        isTheEnd = false;
    }

    public int getSquareNumber() {
        return squareNumber;
    }


    public ArrayList<Piece> getCurrentPieces() {
        return currentPlayerPiece;
    }


    public boolean isSafe() {
        return isSafe;
    }


    public boolean isBlocked(){
        return currentPlayerPiece.size() == 2;
    }

    public void setCurrentPiece(Piece piece) {
        if(isSafe){
            currentPlayerPiece.add(piece);
        } else {
            if(currentPlayerPiece.size() == 1 ){
                if(currentPlayerPiece.get(0).getPlayer() == piece.getPlayer()){
                    currentPlayerPiece.add(piece);
                } else {
                    currentPlayerPiece.remove(0);
                    currentPlayerPiece.add(piece);
                }
            }else{
                currentPlayerPiece.add(piece);
            }
        }
    }

    public void removeCurrentPiece(int player){
        if(currentPlayerPiece.get(0).getPlayer() == player){
            currentPlayerPiece.remove(0);
        } else {
            currentPlayerPiece.remove(1);
        }
    }


    public void changeIsTheEnd() {
        isTheEnd = true;
    }
    public boolean isTheEnd() {
        return isTheEnd;
    }

}
