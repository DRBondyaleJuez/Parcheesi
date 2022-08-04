package persistence;

import model.Color;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemTalkerDatabase implements DatabaseTalker {

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
    public byte[] getDieImageData(int number) {
        if( number > 7){
        number = 7;
        }
            String path = "/images/DieFaces/Die" + number + ".jpg";
        return loadFileData(path);
    }



    private byte[] loadFileData(String path) {
        try {
            URI fileUri = getClass().getResource(path).toURI();
            Path completePath = Paths.get(fileUri);
            byte[] fileContent = Files.readAllBytes(completePath);
            return fileContent;
        } catch (URISyntaxException e) {
            // TODO log
            System.out.println("Unable to get resource URI");
            e.printStackTrace();
            return new byte[0];
        } catch (IOException e) {
            // TODO: log
            System.out.println("Could not find " + path);
            e.printStackTrace();
            return new byte[0];
        }
    }
}
