package Backend;

public class Location
{
    public static final int UNGUESSED = 0;
    public static final int HIT = 1;
    public static final int MISSED = 2;
    
    private int status;
    private boolean ship;
    private Ship typeShip; 
    
    // Location constructor. 
    public Location(){
        this.status = UNGUESSED;
    }
    
    // Was this Location a hit?
    public boolean checkHit(){
        if(ship && status == UNGUESSED){
            status = HIT;
            return true;
        }
        status = MISSED;
        return false;
    }
    
    // Was this location a miss?
    public boolean checkMiss(){
        if(!ship && status == UNGUESSED){
            status = MISSED;
            return true;
        }
        status = HIT;
        return false;
    }
    
    // Was this location unguessed?
    public boolean isUnguessed(){
        if(status == UNGUESSED){
            return true;
        }
        return false;
    }
    
    // Mark this location a hit.
    public void markHit(){
        setStatus(HIT);
        typeShip.incrementHitsTaken();
    }
    
    // Mark this location a miss.
    public void markMiss(){
        setStatus(MISSED);
    }
    
    // Return whether or not this location has a ship.
    public boolean hasShip(){
        return ship;
    }
    
    // Set the value of whether this location has a ship.
    public void setShip(boolean ship, Ship typeShip){
        this.ship = ship;
        this.typeShip = typeShip; 
    }
    
    // Set the status of this Location.
    public void setStatus(int status){
        this.status = status;
    }
    
    // Get the status of this Location.
    public int getStatus(){
        return status;
    }
}