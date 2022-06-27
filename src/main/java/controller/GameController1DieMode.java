package controller;

import model.Board;
import model.Player;

import java.util.Random;

public class GameController1DieMode {

    Player currentPlayer;
    Player[] players;
    int diceNumber;
    Board board;


    public GameController1DieMode() {
        createPlayers();
        diceNumber=0;
        board = new Board();
    }

    private void createPlayers() {
        players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i+1,"Player " + i);
        }
        currentPlayer = players[0];
    }
    
    public void nextPlayer(){
        int nextPlayerPos = currentPlayer.getIdNumber();
        if(nextPlayerPos==4){
            nextPlayerPos=0;
        }
        currentPlayer = players[nextPlayerPos];
    }

    public int diceRoll(){
        Random randomGenerator = new Random();
        int die1Value = randomGenerator.nextInt(6);

        if(die1Value == 5){
            if(newPieceEnters(currentPlayer.getIdNumber())){
                nextPlayer();
                return 0;
            } else{
                return 5;
            }
        }

        return die1Value;
    }

    public boolean newPieceEnters(int player){
        int position = player-1;
        if(board.getNumberOfPiecesInHouse(position)<1){
            return false;
        } else{
            board.
        }

    }

    public boolean
    
}
