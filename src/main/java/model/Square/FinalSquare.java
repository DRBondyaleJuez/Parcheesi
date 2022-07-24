package model.Square;

import model.Piece;

public class FinalSquare extends Square{

    private int player;

    public FinalSquare(int squareNumber, int player) {
        super(squareNumber,true);
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
