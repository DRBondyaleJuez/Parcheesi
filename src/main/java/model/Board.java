package model;

import model.Square.FinalSquare;
import model.Square.HouseSquare;
import model.Square.NormalSquare;
import model.Square.Square;

public class Board {

    private NormalSquare[] boardNormalSquares;
    private FinalSquare[][] finalSquaresBoard;
    private Piece[][] pieces;
    private int[] housePieces; //This is the pieces not played by each player
    private int[] finishedPieces; //This is the pieces that have reached the end
    private int[] startSquares = {4,19,34,49};
    private int[] safeSquares = {4,11,15,19,26,30,34,41,45,49,56,60};
    private int[] finalSquares = {45,30,15,60};

    public Board() {
        //Setting initial configuration of houses
        housePieces = new int[4];
        for (int numberOfPieces:housePieces) {
            numberOfPieces=4;
        };

        //Building boardSquares
        boardNormalSquares = new NormalSquare[60];
        for (int i = 0; i < boardNormalSquares.length; i++) {
            NormalSquare currentNormalSquare = new NormalSquare(i+1);
            if(i==0){
                continue;
            }
            if(i==59){
                currentNormalSquare.setNextSquare(boardNormalSquares[0]);
                continue;
            }
            boardNormalSquares[i-1].setNextSquare(currentNormalSquare);
        }

        //Building finalSquaresBoard
        finalSquaresBoard = new FinalSquare[9][4];
        for (int player = 0; player < finalSquaresBoard[0].length; player++) {
            for (int i = 0; i < finalSquaresBoard.length; i++) {
                FinalSquare currentFinalSquare = new FinalSquare(i+1,player);
            }
        }
        //Building Pieces
        pieces = new Piece[4][4];
        for (int player = 0; player < 4; player++) {
            for (int number = 0; number < 4; number++) {

                Square initialHouse = new HouseSquare(player+1,number+1);
                pieces[player][number] = new Piece(player+1,initialHouse);

            }
        }
    }

    //Piece exiting houseSquares and entering normalSquares
    public boolean pieceExitsHouse(int player){
        int playerPosition = player-1; // correction due to player number and position in array being different

        // First checking for pieces in house ready to exit. If this number is 0 no piece can enter and the 5 has to be used for movement i.e. return false to controller
        if(housePieces[playerPosition]<1){
            return false;
        }

        //Declaring starting square for the corresponding player
        NormalSquare playerStartingNormalSquare = boardNormalSquares[startSquares[player-1]-1];

        //Check if the square is blocked by the presence of two pieces
        if(playerStartingNormalSquare.isBlocked()){
            return false;
        }

        //Put piece in starting square
        playerStartingNormalSquare.setCurrentPlayerPiece(player);
        int pieceNumber = 4 - housePieces[playerPosition];
        Piece enteringPiece = pieces[playerPosition][pieceNumber];
        enteringPiece.move(1,playerStartingNormalSquare);
        housePieces[playerPosition]--;
        return true;

    }


    public void pieceReturnsToHouse(int player){
        int position = player-1;
        housePieces[position]++;
    }

    public int getNumberOfPiecesInHouse(int player){
        int position = player-1;
        return housePieces[position];
    }

    public NormalSquare[] getBoardSquares() {
        return boardNormalSquares;
    }

    public Square getParticularBoardSquare(int boardPosition){
         boardPosition = (boardPosition-1)%60;
         return boardNormalSquares[boardPosition];
    }

    public FinalSquare[][] getFinalSquaresBoard() {
        return finalSquaresBoard;
    }
    public void pieceReachesTheEnd(int player){
        int position = player-1;
        finishedPieces[position]++;
    }
    
    public Piece[] getPlayerPieces(int player){
        Piece[] playerPieces = new Piece[4];
        for (int i = 0; i < playerPieces.length; i++) {
            playerPieces[i] = pieces[player-1][i];
        }
        
        return playerPieces;
    }
}
