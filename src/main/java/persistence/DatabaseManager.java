package persistence;

public class DatabaseManager {
    // Singleton for the sake of unique calls
    private final DatabaseTalker databaseTalker;
    private static DatabaseManager instance;

    private DatabaseManager() {
        databaseTalker = new FileSystemTalkerDatabase();
        instance = null;
    }

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public byte[] getPieceImageData(String players,String orientation){
        return databaseTalker.getPieceImageData(players, orientation);
    }

    public byte[] getBoardImageData(int version) {
        return databaseTalker.getBoardImageData(version);
    }

    public byte[] getTrophyImageData(int player) {
        return databaseTalker.getTrophyImageData(player);
    }

    public byte[] getDieImageData(int number){ return databaseTalker.getDieImageData(number);}
}
