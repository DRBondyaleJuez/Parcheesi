package model;

public class Piece {

    private int stepCounter;
    private int finalStepCounter;
    private int player;
    private Square currentSquare;

    public Piece(int player, Square currentSquare) {
        this.player = player;
        this.currentSquare = currentSquare;
        stepCounter = 0;
        finalStepCounter = 0;
    }



}
