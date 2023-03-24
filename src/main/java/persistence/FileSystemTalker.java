package persistence;

/**
 * Provides the abstract methods necessary for a proper use by calls from the AssetManager Class basically for retrieving
 * The appropriate images from the resource folder. The class which implements this interface will need to implement the methods
 *  to read images from resources.
 */
public interface FileSystemTalker {

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
    byte[] getPieceImageData(String players,String orientation);

    /**
     * Get from the resources the corresponding image of the board in the form of a byte array.
     * @param version int deprecated argument since only one version of the board is in use
     * @return byte[] byte array corresponding to the desired board image
     */
    byte[] getBoardImageData(int version);

    /**
     * Get from the resources the corresponding image of the die in the form of a byte array.
     * @param dieImageLocation
     * @return byte[] byte array corresponding to the desired die image
     */
    byte[] getDieImageData(String dieImageLocation);

    /**
     * Get from the resources the corresponding image of the trophy in the form of a byte array.
     * @param player int between 1 and 4 corresponding to the player who won to get its trophy version
     * @return byte[] byte array corresponding to the desired trophy image
     */
    byte[] getTrophyImageData(int player);



}
