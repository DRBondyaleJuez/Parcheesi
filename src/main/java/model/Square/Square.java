package model.Square;

import model.Piece;

public class Square {

    private int squareNumber;
    private Piece[] currentPlayerPiece;

    private boolean isSafe;

    private boolean isTheEnd;

    public Square(int squareNumber,boolean isSafe) {
        this.squareNumber = squareNumber;
        currentPlayerPiece = new Piece[2];
        this.currentPlayerPiece[0] = new Piece();
        this.currentPlayerPiece[1] = new Piece();
        this.isSafe = isSafe;
        isTheEnd = false;
    }

    public int getSquareNumber() {
        return squareNumber;
    }


    public Piece[] getCurrentPieces() {
        return currentPlayerPiece;
    }


    public boolean isSafe() {
        return isSafe;
    }


    public boolean isBlocked(){
        return currentPlayerPiece[0].getPlayer() > 0 && currentPlayerPiece[1].getPlayer() > 0;
    }

    public void setCurrentPiece(Piece piece) {
        if(isSafe){
            if(currentPlayerPiece[0].getPlayer()>0){
                currentPlayerPiece[1].setNewPiece(piece);
            }else{
                currentPlayerPiece[0].setNewPiece(piece);
            }
        } else {
            if(currentPlayerPiece[0].getPlayer() == piece.getPlayer()){
                currentPlayerPiece[1].setNewPiece(piece);
            }else{
                currentPlayerPiece[0].setNewPiece(piece);
            }
        }
    }

    public void removeCurrentPiece(int player){
        if(currentPlayerPiece[1].getPlayer() == player){
            currentPlayerPiece[1].clearPiece();
        } else {
            currentPlayerPiece[0].clearPiece();
        }
        reorderPlayerPieces();
    }

    private void reorderPlayerPieces(){
        if(currentPlayerPiece[0].getPlayer() == 0){
            currentPlayerPiece[0] = currentPlayerPiece[1];
            currentPlayerPiece[1].clearPiece();
        }
    }

    public void changeIsTheEnd() {
        isTheEnd = true;
    }
    public boolean isTheEnd() {
        return isTheEnd;
    }

}
