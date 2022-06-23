package model;

public class Player {

    private int idNumber;
    private String name;
    private Color color;

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
