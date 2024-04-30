package Backend;

public class Grid
{
    private Location[][] grid;

    // Constants for number of rows and columns.
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;
    
    // Create a new Grid. Initialize each Location in the grid
    // to be a new Location object.
    public Grid(){
        grid = new Location[NUM_ROWS][NUM_COLS];
        for(int r = 0; r < NUM_ROWS; r++){
            for(int c = 0; c < NUM_COLS; c++){
                grid[r][c] = new Location();
            }
        }
    }
    
    // Mark a hit in this location by calling the markHit method
    // on the Location object.  
    public void markHit(int row, int col){
        grid[row][col].markHit();
    }
    
    // Mark a miss on this location.    
    public void markMiss(int row, int col){
        grid[row][col].markMiss();
    }
    
    // Set the status of this location object.
    public void setStatus(int row, int col, int status){
        grid[row][col].setStatus(status);
    }
    
    // Get the status of this location in the grid  
    public int getStatus(int row, int col){
        return grid[row][col].getStatus();
    }
    
    // Return whether or not this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col){
        if(grid[row][col].getStatus() == Location.UNGUESSED){
            return false;
        }
        return true;
    } 
    
    // Set whether or not there is a ship at this location to the val   
    public void setShip(int row, int col, boolean val, Ship ship){
        grid[row][col].setShip(val, ship);
    }
    
    // Return whether or not there is a ship here   
    public boolean hasShip(int row, int col){
        return grid[row][col].hasShip();
    }
    
    // Return the number of rows in the Grid
    public int numRows(){
        return NUM_ROWS;
    }
    
    // Return the number of columns in the grid
    public int numCols(){
        return NUM_COLS;
    }
    
    /**
     * This method can be called on your own grid. To add a ship
     * we will go to the ships location and mark a true value
     * in every location that the ship takes up.
     */
    public void addShip(Ship s){
        int length = s.getLength();
        int direction = s.getDirection();
        int r = s.getRow();
        int c = s.getCol();
        
        for(int i = 0; i < length; i++){
            grid[r][c].setShip(true, s);
            if(direction == 1){
                r++;
            } else if (direction == 0){
                c++;
            }
        }
        
    }
    
    /**
     * Method to check if location is within the grid
     */
    public boolean inBounds(int row, int col)
    {
        if(row < 10 && row > -1 && col > -1 && col < 10){
            return true;
        }
        return false;
    }
    
    /**
    * Code to determine if ship is able to be placed at given location
    * Must check two things:
    * if it goes off the grid
    * if there is already a ship in the location
    */
    public boolean canPlaceShip(Ship s){
        int dir = s.getDirection();
        int len = s.getLength();
        int row = s.getRow();
        int col = s.getCol();
        
        if(dir == Ship.VERTICAL){
            for(int i = 0; i < len; i++){
                if(!inBounds(row+i,col) || hasShip(row+i, col)){
                    return false;
                }
            }
            return true;
            
        } else {
            for(int i = 0; i < len; i++){
                if(!inBounds(row, col+i) || hasShip(row, col+i)){
                    return false;
                }
            }
            return true;
        }
    }
    
    // returns true if the ship has sunk
    public boolean hasSunk(Ship s){
        if(s.getHitsTaken() == s.getLength()) return true;
        
        return false;
    }
    
    
    // Get the Location object at this row and column position
    public Location get(int row, int col){
        return grid[row][col];
    }
    
    // Print the Grid status including a header at the top
    // that shows the columns 1-10 as well as letters across
    // the side for A-J
    // If there is no guess print a -
    // If it was a miss print a O
    // If it was a hit, print an X
    // A sample print out would look something like this:
    // 
    //   1 2 3 4 5 6 7 8 9 10 
    // A - - - - - - - - - - 
    // B - - - - - - - - - - 
    // C - - - O - - - - - - 
    // D - O - - - - - - - - 
    // E - X - - - - - - - - 
    // F - X - - - - - - - - 
    // G - X - - - - - - - - 
    // H - O - - - - - - - - 
    // I - - - - - - - - - - 
    // J - - - - - - - - - - 
    public String[][] getGridStatus() {
        String[][] statusGrid = new String[10][11];
        int letter = 65;
        for(int r = 0; r < NUM_ROWS; r++){
            statusGrid[r][0] = ((char) (letter+r) + "");
            for(int c = 0; c < NUM_COLS; c++){
                if(grid[r][c].getStatus() == Location.UNGUESSED){
                    statusGrid[r][c+1] = "-";
                } else if (grid[r][c].getStatus() == Location.HIT){
                    statusGrid[r][c+1] = "X";
                } else if (grid[r][c].getStatus() == Location.MISSED){
                    statusGrid[r][c+1] = "O";
                }
            }
        }
        return statusGrid;
    }

    
    // Print the grid and whether there is a ship at each location.
    // If there is no ship, you will print a - and if there is a
    // ship you will print a X. You can find out if there was a ship
    // by calling the hasShip method.
    //
    //   1 2 3 4 5 6 7 8 9 10 
    // A - - - - - - - - - - 
    // B - X - - - - - - - - 
    // C - X - - - - - - - - 
    // D - - - - - - - - - - 
    // E X X X - - - - - - - 
    // F - - - - - - - - - - 
    // G - - - - - - - - - - 
    // H - - - X X X X - X - 
    // I - - - - - - - - X - 
    // J - - - - - - - - X - 

    public String[][] getGridShips() {
        String[][] gridShips = new String[10][11];
        int letter = 65;
        for(int r = 0; r < NUM_ROWS; r++){
            gridShips[r][0] = (char) (letter+r) + " ";
            for(int c = 0; c < NUM_COLS; c++){
                if (grid[r][c].getStatus() == Location.MISSED){
                    gridShips[r][c+1] = "O";
                } else if(grid[r][c].hasShip()){
                    gridShips[r][c+1] = "X";
                } else if (!grid[r][c].hasShip()){
                    gridShips[r][c+1] = "-";
                }
            }
        }
        return gridShips;
    }


}