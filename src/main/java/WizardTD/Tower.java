package WizardTD;

/**
 * An abstract class for towers to be created 
 * in game
 */
public abstract class Tower {

    protected static int towerCost;
    protected int range;
    protected double firingSpeed;
    protected double damage;

    protected double baseDamage;

    protected int rangeLvl = 0;
    protected int firingSpeedLvl = 0;
    protected int damageLvl = 0;

    protected double shotCooldown;
    protected double shotCooling;

    protected int[] coords;

    protected String img;
    
    /**
     * Increases the range level and range of the tower
     */
    public abstract void increaseRange();

    /**
     * Increases the firing speed level and firing speed of the tower
     */
    public abstract void increaseFiringSpeed();

    /**
     * Increases the damage level and damage of the tower
     */
    public abstract void increaseDamage();

    /**
     * Sets the image of the tower based on its level
     */
    public abstract void setImg();
    
    /**
     * Sets the coordinates of the tower
     * 
     * @param col The column of the tower's coordinates
     * @param row The row fo the tower's coordinates
     */
    public void set_coords(int col, int row) {
        coords = new int[] {col, row};
    }

    /**
     * Skips the cooling of the tower's shots
     */
    public void skipCooling() {
        shotCooling = 0;
    }
    
    /**
     * Decreases the shot cooldown of the tower by 1
     */
    public void decreaseCooling() {
        shotCooling -= 1;
    }

    /**
     * Resets the shot cooldown of the tower
     */
    public void resetCooling() {
        shotCooling = shotCooldown;
    }

    /**
     * Retrieves the current shot cooldown of the tower
     * 
     * @return The current shot cooldown of the tower
     */
    public double getCooling() {
        return shotCooling;
    }

    /**
     * Retrieves the range level of the tower
     * 
     * @return The range level of the tower
     */
    public int getRangeLvl() {
        return rangeLvl;
    }

    /**
     * Retrieves the firing speed level of the tower
     * 
     * @return The firing speed level of the tower
     */
    public int getFiringSpeedLvl() {
        return firingSpeedLvl;
    }

    /**
     * Retrieves the damage level of the tower
     * 
     * @return The damage level of the tower
     */
    public int getDamageLvl() {
        return damageLvl;
    }

    /**
     * Retrieves the cost to upgrade range of the tower
     * 
     * @return The cost to upgrade range of the tower
     */
    public int getRangeUpCost() {
        return 20 + (rangeLvl) * 10;
    }

    /**
     * Retrieves the cost to upgrade firing speed of the tower
     * 
     * @return The cost to upgrade firing speed of the tower
     */
    public int getFiringSpeedUpCost() {
        return 20 + (firingSpeedLvl) * 10;
    }

    /**
     * Retrieves the cost to upgrade damage of the tower
     * 
     * @return The cost to upgrade damage of the tower
     */
    public int getDamageUpCost() {
        return 20 + (damageLvl) * 10;
    }

    /**
     * Retrieves the range of the tower
     * 
     * @return The range of the tower
     */
    public int getRange() {
        return range;
    }

    /**
     * Retrieves the firing speed of the tower
     * 
     * @return The firing speed of the tower
     */
    public double getFiringSpeed() {
        return firingSpeed;
    }

    /**
     * Retrieves the damage of the tower
     * 
     * @return The damage of the tower
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Retrieves the image type of the tower
     * 
     * @return The image type of the tower
     */
    public String getImg() {
        return img;
    }
    
    /**
     * Retrieves the location of the tower
     * 
     * @return The locationof the tower
     */
    public int[] getCoords() {
        return coords;
    }


}


