package persistence;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Provides the implementation of the FileSystemTalker. It implements the methods to retrieve the image data in the form
 * of byte array from the resource folder. The image data is basically processed using a Files.readAllBytes method
 */
public class AssetFileSystemTalker implements FileSystemTalker {

    @Override
    public byte[] getPieceImageData(String players,String orientation) {
        String path = "/images/Pieces/" + orientation + "PiecePlayer" + players + ".png";

        return loadFileData(path);
    }

    @Override
    public byte[] getBoardImageData(int version) {
        String path = "";
        switch (version) {
            case 1:
                path = "/images/BackgroundBoard/BackgroundBoard1.png";
                break;
            case 2:
                path = "/images/BackgroundBoard/BackgroundBoard2.png";
                break;
            default:
                // TODO: log
                System.out.println("Somehow not an available board version");
                break;
        }

        return loadFileData(path);
    }

    @Override
    public byte[] getDieImageData(String dieImageLocation) {

            String path = "/images/DieFaces/"+dieImageLocation+".jpg";
        return loadFileData(path);
    }

    @Override
    public byte[] getTrophyImageData(int player) {
        String path = "/images/Trophy/trophy" + player + ".png";
        return loadFileData(path);
    }


    private byte[] loadFileData(String path) {
        try {
            InputStream currentInputStream = AssetFileSystemTalker.class.getResourceAsStream(path);
            if(currentInputStream == null ){
                throw new IOException();
            }

            return IOUtils.toByteArray(currentInputStream);
        } catch (IOException e) {
            // TODO: log
            System.out.println("Could not find " + path);
            e.printStackTrace();
            return new byte[0];
        }
    }
}
