package model;

public class FinalSquare {

    private int squareNumber;
    private Piece currentPiece;
    private int player;
    private FinalSquare nextFinalSquare;

    public FinalSquare(int squareNumber, Piece currentPiece,int player) {
        this.squareNumber = squareNumber;
        this.currentPiece = currentPiece;
        this.nextFinalSquare = nextFinalSquare;
    }
}
