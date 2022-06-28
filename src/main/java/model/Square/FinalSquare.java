package model.Square;

import model.Piece;

public class FinalSquare implements Square{

    private int squareNumber;
    private Piece currentPiece;
    private int player;
    private FinalSquare nextFinalSquare;

    public FinalSquare(int squareNumber, Piece currentPiece,int player) {
        this.squareNumber = squareNumber;
        this.currentPiece = currentPiece;
        this.nextFinalSquare = nextFinalSquare;
    }

    @Override
    public int getSquareNumber() {
        return 0;
    }

    @Override
    public int[] getCurrentPlayerPieces() {
        return new int[0];
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
    public void exitCurrentPlayerPiece(int player) {

    }

    @Override
    public void setNextSquare(Square nextSquare) {

    }
}
