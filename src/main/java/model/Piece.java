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

    public int getPlayer() {
        return player;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }

    public void Move(int steps){
        stepCounter = stepCounter + steps;
        if(stepCounter > 60){
            int finalSteps = stepCounter - 60;
            stepCounter = 60;
            finalMove(finalSteps);
        }
    }

    public int finalMove(int steps){
        finalStepCounter = finalStepCounter + steps;
        //If the steps go over the final square
        if(finalStepCounter>8){
            finalStepCounter = 8 - (finalStepCounter-8);
            if(finalStepCounter<1){finalStepCounter = 1;}
            return finalStepCounter;
        }
        //If it reaches the final square
        if(finalStepCounter==8){
            finalStepCounter = 10;
            return finalStepCounter;
        }
        //If the steps don't reach the final square
        return finalStepCounter;

    }



}
