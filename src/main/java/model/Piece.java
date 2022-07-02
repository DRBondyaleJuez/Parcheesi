package model;

import model.Square.HouseSquare;
import model.Square.Square;

public class Piece {

    private int stepCounter;
    private int finalStepCounter;
    private int player;
    private Square currentSquare;
    private int boardPosition;

    public Piece(int player, Square newSquare) {
        this.player = player;
        currentSquare = newSquare;
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

    public int getBoardPosition() {
        return boardPosition;
    }

    public void move(int steps, Square newSquare){
        //Changing the step counter with possible nuance if final squares are reached
        stepCounter = stepCounter + steps;
        if(stepCounter > 60){
            int finalSteps = stepCounter - 60;
            stepCounter = 60;
            finalStepCounter = finalMoveCalculator(finalSteps);
        }
        //Change the square where the piece is currently after moving
        setCurrentSquare(newSquare);
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
            return 0;
        }

        int startingPosition = 0;
        int newBoardPosition = currentStepCounter;
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
        newBoardPosition = startingPosition + currentStepCounter;
        return newBoardPosition;
    }

    public int getFinalStepCounter(){
        return finalStepCounter;
    }

    public void returnToHouse(int housePosition){
        HouseSquare returningHouseSquare = new HouseSquare(player,housePosition);
        stepCounter = -1;
        finalStepCounter = -1;
        boardPosition = boardPositionCalculator(stepCounter);

    }


}
