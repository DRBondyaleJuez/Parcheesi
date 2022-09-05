package persistence;

import model.Color;

public interface DatabaseTalker {

    /**
     * Load the full chip file from a certain color
     * @param color the chip color
     * @return the file of the specified chip color, or null if that file did not exist in our database
     */
    byte[] getPieceImageData(String players,String orientation);

    byte[] getBoardImageData(int version);

    byte[] getDieImageData(String dieImageLocation);

    byte[] getTrophyImageData(int player);



}
