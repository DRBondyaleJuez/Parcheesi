package model.Square;

public class Square {

    private int squareNumber;
    private int[] currentPlayerPiece;

    private boolean isSafe;

    public Square(int squareNumber,boolean isSafe) {
        this.squareNumber = squareNumber;
        this.currentPlayerPiece[0] = 0;
        this.currentPlayerPiece[1] = 0;
    }

    public int getSquareNumber() {
        return squareNumber;
    }


    public int[] getCurrentPlayerPieces() {
        return currentPlayerPiece;
    }


    public boolean isSafe() {
        return isSafe;
    }


    public boolean isBlocked(){
        if(currentPlayerPiece[0]>0 && currentPlayerPiece[1]>0){
            return true;
        }
        else{
            return false;
        }
    }

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

    public void removeCurrentPlayerPiece(int player){
        if(currentPlayerPiece[1] == player){
            currentPlayerPiece[1] = 0;
        } else {
            currentPlayerPiece[0] = 0;
        }
        reorderPlayerPieces();
        return;
    }

    private void reorderPlayerPieces(){
        if(currentPlayerPiece[0] == 0){
            currentPlayerPiece[0] = currentPlayerPiece[1];
            currentPlayerPiece[1] = 0;
        }
        return;
    }

}
