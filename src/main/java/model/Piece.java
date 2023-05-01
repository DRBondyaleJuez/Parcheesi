package model;

/**
 * Provides objects that represent the pieces that move along the board
 */
public class Piece {

    private int stepCounter;
    private final int MAX_STEPS = 56;
    private int finalStepCounter;
    private int player;

    /**
     * This is the constructor.
     * The piece initializes without a player assigned (player attribute equal to 0) and with the steps ar -1 signifying
     * it is outside of the board
     */
    public Piece() {
        this.player = 0;
        stepCounter = -1;
        finalStepCounter = -1;
    }

    /**
     * Get player attribute
     * @return int the player assigned to the piece a number between 1 and 4 corresponding to a particular player
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Set the value of the attribute player
     * @param newPlayer int the player that is going to be assigned to the piece object
     */
    public void setPlayer(int newPlayer){
        player = newPlayer;
    }

    public void move(int steps){
        stepCounter = stepCounter + steps;
        if(stepCounter > MAX_STEPS){
            int finalSteps = stepCounter - MAX_STEPS;
            stepCounter = MAX_STEPS;
            finalStepCounter = finalMoveCalculator(finalSteps);
        }
    }

    /**
     * Get stepCounter attribute
     * @return int the number of steps the piece has advanced
     */
    public int getStepCounter() {
        return stepCounter;
    }

    /**
     * Get finalStepCounter attribute
     * @return int the number of steps advanced in the final section of the pieces path
     */
    public int getFinalStepCounter(){
        return finalStepCounter;
    }


    /**
     * Calculate de position of the piece after advancing a number of steps where the position is the number of steps
     * covered of the final section of the piece's path. In parcheesi if the piece does not reach the end it bounces
     * back.
     * @param steps int corresponding to the steps the piece is going to move
     * @return int corresponds to the steps in the final section of the path the piece has covered after moving the
     * steps provided as the argument
     */
    public int finalMoveCalculator(int steps){
        int tempFinalStepCounter = finalStepCounter + steps;
        //If the steps go over the end square
        if(tempFinalStepCounter>6){
            tempFinalStepCounter = 6 - (tempFinalStepCounter-6);
            if(tempFinalStepCounter<1){tempFinalStepCounter = 0;}
            return tempFinalStepCounter;
        }
        //If the steps don't reach the end square
        return tempFinalStepCounter;
    }
}
