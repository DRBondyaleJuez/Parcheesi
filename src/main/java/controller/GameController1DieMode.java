package controller;

import model.Board;
import model.Color;
import model.Player;

import java.util.Random;

public class GameController {

    Player currentPlayer;
    Player[] players;
    int diceNumber;
    Board board;


    public GameController() {
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
        int die1 = randomGenerator.nextInt(6);
        int die2 = randomGenerator.nextInt(6);
        return die1 + die2;
    }

    public boolean newPieceEnters()

    public boolean
    
}
