package model;

/**
 * Provides the objects that represent the players.
 * <p>
 *     Each player describes its idNumber, name and color

 * </p>
 */
public class Player {

    private int idNumber;
    private String name;
    private Color color;

    /**
     * This is the constructor. The attributes idNumber and name are assigned based on arguments and the color
     * is initialized based on the idNumber between 1 and 4
     * @param idNumber int between 1 and 4
     * @param name String corresponds to the name of the player
     */
    public Player(int idNumber, String name) {
        this.idNumber = idNumber;
        this.name = name;
        switch (idNumber){
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.RED;
                break;
            default:
                color = null;
                break;
        }

        if(color == null){
            System.out.println("ERROR: No Color assigned to player");
        }

    }

    //GETTERS
    public int getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
