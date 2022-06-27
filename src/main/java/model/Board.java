package model;

public class Board {

    Square[] boardSquares;
    FinalSquare[][] finalSquaresBoard;
    Piece[][] pieces;
    int[] housePieces; //This is the pieces not played by each player
    int[] finishedPieces; //This is the pieces that have reached the end
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
        boardSquares = new Square[60];
        for (int i = 0; i < boardSquares.length; i++) {
            Square currentSquare = new Square(i+1);
            if(i==0){
                continue;
            }
            if(i==59){
                currentSquare.setNextSquare(boardSquares[0]);
                continue;
            }
            boardSquares[i-1].setNextSquare(currentSquare);
        }

        //Building finalSquaresBoard
        finalSquaresBoard = new FinalSquare[8][4];
        for (int player = 0; player < finalSquaresBoard[0].length; player++) {
            for (int i = 0; i < finalSquaresBoard.length; i++) {
                FinalSquare currentFinalSquare = new FinalSquare(i+1,null,player);
            }
        }

        //Building Pieces
        pieces = new Piece[4][4];
        for (int player = 0; player < 4; player++) {
            for (int number = 0; number < 4; number++) {

                pieces[player][number] = new Piece(player+1,null);

            }

        }
    }

    public boolean pieceExitsHouse(int player){
        int position = player-1; // correction due to player number and position in array being different

        // First checking for pieces in house ready to exit. If this number is 0 no piece can enter and the 5 has to be used for movement i.e. return false to controller
        if(housePieces[position]<1){
            return false;
        }

        //Declaring starting square of the corresponding player
        Square playerStartingSquare = boardSquares[startSquares[player-1]-1];
        if (playerStartingSquare.getIsStart() == player){ //Perhaps this is unnecessary but check that the square is confirmed to be a start square
            //Check if the square is blocked by the presence of two pieces
            if(playerStartingSquare.isBlocked()){
                return false;
            }
            //Put piece in starting square
            playerStartingSquare.setCurrentPlayerPiece(player);
            int pieceNumber = 4 - housePieces[position];
            pieces[position][pieceNumber].setCurrentSquare(playerStartingSquare);
            housePieces[position]--;
            return true;
        }
        return false;
    }


    public void pieceReturnsToHouse(int player){
        int position = player-1;
        housePieces[position]++;
    }

    public int getNumberOfPiecesInHouse(int player){
        int position = player-1;
        return housePieces[position];
    }

    public Square[] getBoardSquares() {
        return boardSquares;
    }

    public FinalSquare[][] getFinalSquaresBoard() {
        return finalSquaresBoard;
    }
    public void pieceReachesTheEnd(int player){
        int position = player-1;
        finishedPieces[position]++;
    }
}
