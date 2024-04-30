package Backend;

public class Ship
{
    public static final int UNSET = -1;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
    private int row;
    private int col;
    private int length;
    private int direction;
    private int hitsTaken;
    
    // Constructor. Create a ship and set the length.
    public Ship(int length){
        this.length = length;
        this.direction = UNSET;
        this.row = UNSET;
        this.col = UNSET;
        this.hitsTaken = 0;
    }
    
    // Has the location been initialized
    public boolean isLocationSet(){
        if(row != UNSET || col != UNSET){
            return true;
        }
        return false;
    }
    
    // Has the direction been initialized
    public boolean isDirectionSet(){
        if(direction != UNSET){
            return true;
        }
        return false;
    }
    
    // Set the location of the ship 
    public void setLocation(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    // Set the direction of the ship
    public void setDirection(int direction){
        this.direction = direction;
    }
    
    // Getter for the row value
    public int getRow(){
        return this.row;
    }
    
    // Getter for the column value
    public int getCol(){
        return this.col;
    }
    
    // Getter for the length of the ship
    public int getLength(){
        return this.length;
    }
    
    // Getter for the direction
    public int getDirection(){
        return this.direction;
    }
    
    public int getHitsTaken(){
        return this.hitsTaken;
    }
    
    public void incrementHitsTaken(){
        this.hitsTaken++;
    }
    
    // Helper method to get a string value from the direction
    private String directionToString(){
        if(direction == HORIZONTAL){
            return "horizontal";
        } else if (direction == UNSET){
            return "unset direction";
        } else if (direction == VERTICAL){
            return "vertical";
        } else {
            return "direction error";
        }
    }
    
    // Helper method to get a (row, col) string value from the location
    private String locationToString(){
        if(row == UNSET && col == UNSET){
            return "(unset location)";
        } else {
            return "(" + row + ", " + col + ")";
        }
    }
    
    // toString value for this Ship
    public String toString(){
        return directionToString() + " ship of length " + length + " at " + locationToString();
    }
}