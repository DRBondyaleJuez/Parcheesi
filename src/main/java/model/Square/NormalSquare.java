package model.Square;

import model.Piece;

/**
 * Provides a particular children of the Square class i.e. extends Square, which represents those square which are not he finalSquares of the board.
 */
public class NormalSquare extends Square{

    private final int isStart;

    /**
     * This is the constructor which follows the square requirements with the addition of the isStart int.
     * @param squareNumber the index position between 1 and 60
     * @param isSafe boolean indicating if the square is safe, able to hold 2 pieces
     * @param isStart int the number of the player this square is the start of. 1 to 4
     */
    public NormalSquare(int squareNumber,boolean isSafe,int isStart) {
        super(squareNumber,isSafe);
        this.isStart = isStart;
    }

    public int getIsStart() {
        return isStart;
    }

    /**
     * Initial method managing the start of a piece on the board. This requires initializing a piece and setting it
     * in the square.
     * @param newPlayer The player whose piece is going to enter the board
     * @return boolean true if the piece entered with no issue. False if the piece could not enter the board because the
     * square is blocked.
     */
    public boolean enterPiece(int newPlayer){
        Piece enteringPiece = new Piece();
        enteringPiece.setPlayer(newPlayer);
        enteringPiece.move(1);
        if(isBlocked()){
            return  false;
        }
        setCurrentPiece(enteringPiece);
        return true;
    }
}
