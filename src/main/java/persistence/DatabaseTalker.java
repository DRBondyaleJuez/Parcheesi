package persistence;

import model.Color;

public interface DatabaseTalker {

    /**
     * Load the full chip file from a certain color
     * @param color the chip color
     * @return the file of the specified chip color, or null if that file did not exist in our database
     */
    byte[] getPieceImageData(String players,String position);

    byte[] getBoardImageData(int version);



}
