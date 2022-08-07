package model;

import model.Square.Square;

public class Piece {

    private int stepCounter;
    private int maxSteps = 56;
    private int finalStepCounter;
    private int player;

    public Piece() {
        this.player = 0;
        stepCounter = -1;
        finalStepCounter = -1;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int newPlayer){
        player = newPlayer;
    }

    public void move(int steps){
        stepCounter = stepCounter + steps;
        if(stepCounter > maxSteps){
            int finalSteps = stepCounter - maxSteps;
            stepCounter = maxSteps;
            finalStepCounter = finalMoveCalculator(finalSteps);
        }
    }

    public void setNewPiece(Piece movingPiece){

        player = movingPiece.getPlayer();
        stepCounter = movingPiece.getStepCounter();
        finalStepCounter = movingPiece.getFinalStepCounter();

    }

    public void clearPiece(){
        this.player = 0;
        stepCounter = -1;
        finalStepCounter = -1;
    }


    public int getStepCounter() {
        return stepCounter;
    }

    public int getFinalStepCounter(){
        return finalStepCounter;
    }


    public int finalMoveCalculator(int steps){
        int tempFinalStepCounter = finalStepCounter + steps;
        //If the steps go over the end square
        if(tempFinalStepCounter>6){
            tempFinalStepCounter = 6 - (tempFinalStepCounter-6);
            if(tempFinalStepCounter<1){tempFinalStepCounter = 0;}
            return tempFinalStepCounter;
        }
        //If it reaches the end square
        if(tempFinalStepCounter == 7){
            return 7;
        }
        //If the steps don't reach the end square
        return tempFinalStepCounter;
    }






}
