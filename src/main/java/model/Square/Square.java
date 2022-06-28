package model.Square;

public interface Square {

    public int getSquareNumber();

    public int[] getCurrentPlayerPieces();

    public Square getNextSquare();

    public boolean isBlocked();

    public void setCurrentPlayerPiece(int playerPiece);

    public void removeCurrentPlayerPiece(int player);

    public void setNextSquare(Square nextSquare);

}
