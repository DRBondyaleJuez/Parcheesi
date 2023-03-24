package persistence;

/**
 * Provides and instance of the object that mediates between the controller and the retrieval of files (images) from
 * resources folder.
 */
public class DatabaseManager {
    // Singleton for the sake of unique calls
    private final DatabaseTalker databaseTalker;
    private static DatabaseManager instance;

    private DatabaseManager() {
        databaseTalker = new FileSystemTalkerDatabase();
        instance = null;
    }

    /** Following the singleton design pattern the constructor is private and only a same instance of the class
     * can be used invoking this static method.
     * @return DatabaseManager the same instance of database manager assigned to the instance attribute
     */
    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }


    /**
     * Get from the resources the corresponding image of the piece or pieces in the form of a byte array.
     * @param players String that corresponds to two numbers which indicate the combination of pieces desired.
     *                If only the piece corresponding to player 2 was required it would be 02. If both player 3 and
     *                player 1 had a piece and the square is safe it would be 13. If player 4 had two pieces in one
     *                square forming a barrier it would be 44.
     * @param orientation String informing of the orientation of the piece image required depending on the position of
     *                    the square in the board.
     * @return byte[] byte array corresponding to the desired piece image
     */
    public byte[] getPieceImageData(String players,String orientation){
        return databaseTalker.getPieceImageData(players, orientation);
    }

    /**
     * Get from the resources the corresponding image of the board in the form of a byte array.
     * @param version int deprecated argument since only one version of the board is in use
     * @return byte[] byte array corresponding to the desired board image
     */
    public byte[] getBoardImageData(int version) {
        return databaseTalker.getBoardImageData(version);
    }

    /**
     * Get from the resources the corresponding image of the trophy in the form of a byte array.
     * @param player int between 1 and 4 corresponding to the player who won to get its trophy version
     * @return byte[] byte array corresponding to the desired trophy image
     */
    public byte[] getTrophyImageData(int player) {
        return databaseTalker.getTrophyImageData(player);
    }

    /**
     * Get from the resources the corresponding image of the die in the form of a byte array.
     * @param dieImageLocation
     * @return byte[] byte array corresponding to the desired die image
     */
    public byte[] getDieImageData(String dieImageLocation){ return databaseTalker.getDieImageData(dieImageLocation);}
}
