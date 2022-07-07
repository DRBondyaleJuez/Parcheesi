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

    public byte[] getFullChipImageData(String players,String position){
        return databaseTalker.getPieceImageData(players, position);
    }

    public byte[] getBoardImageData(int version) {
        return databaseTalker.getBoardImageData(version);
    }
}
