package model;

import model.Square.Square;

public class Piece {

    private int stepCounter;
    private int maxSteps = 56;
    private int finalStepCounter;
    private int player;
    private Square currentSquare;
    private int boardPosition;

    public Piece(int player) {
        this.player = player;
        currentSquare = null;
        stepCounter = -1;
        finalStepCounter = -1;
        boardPosition = boardPositionCalculator(stepCounter);
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

    public int getStepCounter() {
        return stepCounter;
    }

    public int getFinalStepCounter(){
        return finalStepCounter;
    }

    public int getBoardPosition() {
        return boardPosition;
    }

    public void move(int steps, Square newSquare){
        //Changing the step counter with possible nuance if final squares are reached
        stepCounter = stepCounter + steps;
        if(stepCounter > maxSteps){
            int finalSteps = stepCounter - maxSteps;
            stepCounter = maxSteps;
            finalStepCounter = finalMoveCalculator(finalSteps);
        }
        //Change the square where the piece is currently after moving
        setCurrentSquare(newSquare);
        boardPosition = boardPositionCalculator(stepCounter);
    }

    public int finalMoveCalculator(int steps){
        int tempFinalStepCounter = finalStepCounter;
        tempFinalStepCounter = tempFinalStepCounter + steps;
        //If the steps go over the end square
        if(tempFinalStepCounter>8){
            tempFinalStepCounter = 8 - (tempFinalStepCounter-8);
            if(tempFinalStepCounter<1){tempFinalStepCounter = 1;}
            return tempFinalStepCounter;
        }
        //If it reaches the end square
        if(finalStepCounter==8){
            finalStepCounter = 10;
            return finalStepCounter;
        }
        //If the steps don't reach the end square
        return finalStepCounter;

    }

    public int boardPositionCalculator(int currentStepCounter){

        //If piece has not left the house it has boardPosition 0
        if(currentStepCounter == -1){
            return -1;
        }

        int startingPosition = 0;
        int newBoardPosition;
        switch(player){
            case 1:
                startingPosition = 4;
                break;
            case 2:
                startingPosition = 19;
                break;
            case 3:
                startingPosition = 24;
                break;
            case 4:
                startingPosition = 39;
                break;
            default:
                break;
        }
        newBoardPosition = (startingPosition + currentStepCounter)%60;
        return newBoardPosition;
    }



}
