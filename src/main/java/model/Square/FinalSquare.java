package model.Square;

import model.Piece;

public class FinalSquare implements Square{

    private int squareNumber;
    private int[] currentPieces;
    private int player;
    private Square nextFinalSquare;

    public FinalSquare(int squareNumber, int player) {
        this.squareNumber = squareNumber;
        this.player = player;
        currentPieces[0] = 0;
        currentPieces[1] = 0;
        nextFinalSquare = null;
    }

    @Override
    public int getSquareNumber() {
        return squareNumber;
    }

    @Override
    public int[] getCurrentPlayerPieces() {
        return currentPieces;
    }

    @Override
    public Square getNextSquare() {
        return null;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    @Override
    public void setCurrentPlayerPiece(int playerPiece) {

    }

    @Override
    public void removeCurrentPlayerPiece(int player) {

    }

    @Override
    public void setNextSquare(Square nextSquare) {
        nextFinalSquare = nextSquare;

    }
}
