package model.Square;


/**
 * Provides objects that represent the final squares of the board. These are children of square. which means they extend square
 */
public class FinalSquare extends Square{

    private final int player;

    /**
     * This is the constructor which follows that of the Square Class with the addition of the attribute player which indicates which player this is the square of. Also in case it is the last final square
     * (squareNumber equal 7) it calls method to change isTheEnd attribute.
     * @param squareNumber int index of the square position for final squares it is between 1 and 7
     * @param player int indicating what player this is the final squares of between 1 and 4.
     */
    public FinalSquare(int squareNumber, int player) {
        super(squareNumber,true);
        this.player = player;
        if(squareNumber == 7){
            changeIsTheEnd();
        }
    }

    public int getPlayer() {
        return player;
    }



}
