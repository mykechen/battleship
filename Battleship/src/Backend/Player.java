package Backend;

public class Player {

    // Write your Player class here
    // These are the lengths of all of the ships.
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    private static final int MAX_HITS = 17;

    private Grid playerGrid;
    private Grid opponentGrid;
    private Ship[] ships;
    private int numShips = 0;
    private int totalHitsTaken;
    private int totalHitsDelivered;
    private int prevHitCol = -1;
    private int prevHitRow = -1;
    private int initHitCol = -1;
    private int initHitRow = -1;
    private int nextHitRow = -1;
    private int nextHitCol = -1;

    public Player() {
        ships = new Ship[SHIP_LENGTHS.length];
        for (int i = 0; i < SHIP_LENGTHS.length; i++) {
            ships[i] = new Ship(SHIP_LENGTHS[i]);
        }

        totalHitsTaken = 0;
        totalHitsDelivered = 0;

        playerGrid = new Grid();
        opponentGrid = new Grid();
    }

    public int getTotalHitsDelivered() {
        return totalHitsDelivered;
    }

    public int getTotalHitsTaken() {
        return totalHitsTaken;
    }

    public Grid getOpponentGrid() {
        return opponentGrid;
    }

    public Grid getPlayerGrid() {
        return playerGrid;
    }

    // @param must have a ship at guessRow, guessCol
    // return ship at row, col
    // BUG -> doesnt work still
    public Ship getShip(int guessRow, int guessCol) {
        System.out.println("Before");
        for (Ship s : ships) {
            int dir = s.getDirection();
            int shipInitRow = s.getRow();
            int shipInitCol = s.getCol();
            int l = s.getLength();
            System.out.println("len: " + l);
            System.out.println("location: " + shipInitRow + ", " + shipInitCol);
            if (dir == Ship.HORIZONTAL) {
                // iterate each guessCol and see if shipInitCol = guessCol
                for (int curCol = shipInitCol; curCol < shipInitCol + l; curCol++) {
                    System.out.println("Horz: " + curCol + "InitCol: " + shipInitCol);
                    if (curCol == guessCol && shipInitRow == guessRow) {
                        System.out.println(s.getLength());
                        return s;
                    }
                }
            } else if (dir == Ship.VERTICAL) {
                for (int curRow = shipInitRow; curRow < shipInitRow + l; curRow++) {
                    System.out.println("Vert: " + curRow + "InitRow: " + shipInitRow);
                    if (curRow == guessRow && guessCol == shipInitCol) {
                        System.out.println(s.getLength());
                        return s;
                    }
                }
            }
        }
        return ships[0];

    }

    // Print your ships on the grid
    public void printMyShips() {
        playerGrid.getGridShips();
    }

    // Print opponent guesses
    public void printOpponentGuesses() {
        playerGrid.getGridStatus();
    }

    // Print your guesses
    public void printMyGuesses() {
        opponentGrid.getGridStatus();
    }

    public int getShipLengths(int i) {
        return SHIP_LENGTHS[i];
    }

    // Record a guess from the opponent
    public boolean recordOpponentGuess(int row, int col) {
        if (playerGrid.hasShip(row, col)) {
            playerGrid.markHit(row, col);
            totalHitsTaken++;
            return true;
        } else {
            playerGrid.markMiss(row, col);
            return false;
        }
    }

    public int getRandomRowGuess() {
        return Randomizer.nextInt(10);
    }

    public int getRandomColGuess() {
        return Randomizer.nextInt(10);
    }

    public int getRandomDirGuess() {
        return Randomizer.nextInt(2);
    }

    // place opponent ships randomly
    public void initializeShipsRandomly() {
        for (int i = 0; i < 5; i++) {
            Ship s = new Ship(getShipLengths(i));
            while (true) {
                s.setLocation(getRandomRowGuess(), getRandomColGuess());
                s.setDirection(getRandomDirGuess());
                if (playerGrid.canPlaceShip(s)) {
                    playerGrid.addShip(s);
                    ships[numShips] = s;
                    numShips++;
                    break;
                }

            }
        }
    }

    // player/comp has guessed right
    public void playerHit() {
        totalHitsDelivered++;
    }

    public void printHitsDelivered() {
        System.out.println("Total Hits = " + totalHitsDelivered + " out of 17");
    }

    public boolean hasWon() {
        if (totalHitsDelivered == MAX_HITS) {
            return true;
        }
        return false;
    }

    public Grid getGrid() {
        return playerGrid;
    }

    public int getShipLength() {
        return ships[numShips].getLength();
    }

    public int getNumShipsAdded() {
        return numShips;
    }

    public boolean makeGuess(int row, int col, Player p) {
        if (p.getGrid().hasShip(row, col)) {
            p.playerHit();
            p.getGrid().markHit(row, col);
            return true;
        } else {
            p.getGrid().markMiss(row, col);
            return false;
        }
    }

    public boolean alreadyGuessed(int row, int col) {
        if (playerGrid.getStatus(row, col) == Location.UNGUESSED) {
            return false;
        }
        return true;
    }

    public boolean addShip(int row, int col, int dir) {
        Ship s = new Ship(SHIP_LENGTHS[numShips]);
        s.setDirection(dir);
        s.setLocation(row, col);
        if (playerGrid.canPlaceShip(s)) {
            playerGrid.addShip(s);
            ships[numShips] = s;
            numShips++;
            return true;
        }
        return false;
    }

    public void setOpponentGrid(Grid grid) {
        opponentGrid = grid;
    }
    
    public boolean canHit(int row, int col, Player p){
        Grid grid = p.getGrid();
        if(grid.alreadyGuessed(row, col)){
            return false;
        }
        return true;
    }

    // picks next hit after getting a hit
    // returns true/false if it hit
    public boolean smartGuessAfterHit(int row, int col, Player p) {
        Grid grid = p.getGrid();
        this.prevHitCol = col;
        this.prevHitRow = row;
        if(grid.inBounds(row-1, col) && (!grid.alreadyGuessed(row-1, col) || grid.get(row-1, col).checkHit())){
            nextHitCol = col;
            nextHitRow = row - 1;
        }
        return true;
    }

}
