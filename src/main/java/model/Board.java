package model;

public class Board {

    Square[] boardSquares;
    FinalSquare[][] finalSquaresBoard;
    int[] housePieces; //This is the pieces not played by each player

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
    }

    public void pieceExitsHouse(int player){
        int position = player-1;
        housePieces[position]--;
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
}
