package model;

public class FinalSquare {

    private int squareNumber;
    private Piece currentPiece;
    private Player player;
    private FinalSquare nextFinalSquare;

    public FinalSquare(int squareNumber, Piece currentPiece,Player player) {
        this.squareNumber = squareNumber;
        this.currentPiece = currentPiece;
        this.next = nextFinalSquare;
    }
}
