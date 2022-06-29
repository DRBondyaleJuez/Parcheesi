package model.Square;

public class HouseSquare implements Square{

    int player;
    int squareNumber;

    public HouseSquare(int player, int squareNumber) {
        this.player = player;
        this.squareNumber = squareNumber;
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
    public void removeCurrentPlayerPiece(int player) {

    }

    @Override
    public void setNextSquare(Square nextSquare) {

    }
}
