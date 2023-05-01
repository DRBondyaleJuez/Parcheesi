package model.Square;

import model.Piece;

import java.util.ArrayList;

/**
 * Provides objects that represent the squares of the board
 */
public class Square {

    private final int squareNumber;
    private final ArrayList<Piece> currentPlayerPiece;

    private final boolean isSafe;

    private boolean isTheEnd;

    /**
     * This is the constructor where the attributes are initialized but the squareNumber and the isSafe boolean are
     * provided as arguments.
     * @param squareNumber int the index number of the square goes from 1 to 60 indicating the position in the board
     * @param isSafe boolean indicating if the square is safe which implies that it admits to pieces of different players
     *               this means piece can not be eaten if they are in this square
     */
    public Square(int squareNumber,boolean isSafe) {
        this.squareNumber = squareNumber;
        currentPlayerPiece = new ArrayList<>();
        this.isSafe = isSafe;
        isTheEnd = false;
    }

    /**
     * Get the piece or pieces in this square
     * @return ArrayList containing the piece or pieces in that square
     */
    public ArrayList<Piece> getCurrentPieces() {
        return currentPlayerPiece;
    }


    /**
     * Get the attribute isSafe of the square
     * @return boolean the status of the isSafe attribute. If the square is safe true.
     */
    public boolean isSafe() {
        return isSafe;
    }


    /**
     * Informs if a square is blocked or not. Blocked means having two pieces already in the square
     * @return boolean true if there are two piece in the square, hence the attribute currentPlayerPiece is size 2
     */
    public boolean isBlocked(){
        return currentPlayerPiece.size() == 2;
    }

    /**
     * Sets a piece in the square. This means adding a Piece to the arrayList attribute currentPlayerPiece. If the square
     * is safe a piece can be added regardless, but if it is not it will depend on if the piece already present belongs to
     * the same or a different player. If it is a different player before adding the other piece is removed.
     * @param piece Piece object of the piece that has reached this particular square in this players particular move.
     */
    public void setCurrentPiece(Piece piece) {
        if(isSafe){
            currentPlayerPiece.add(piece);
        } else {
            if(currentPlayerPiece.size() == 1){
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


    /**
     * Assign true to the isTheEnd attribute
     */
    public void changeIsTheEnd() {
        isTheEnd = true;
    }

    /**
     * Get the isTheEnd attribute
     * @return boolean the status of the isTheEnd attribute. True if this is a final square
     */
    public boolean isTheEnd() {
        return isTheEnd;
    }

}
