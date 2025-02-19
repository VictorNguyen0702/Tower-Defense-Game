package WizardTD;

/**
 * An abstract class for projectiles from towers
 * to be created in game
 */
public abstract class Projectile {
    protected double speed;
    protected double damage;
    
    protected int x;
    protected int y;
    protected boolean active;

    /**
     * Sets the projectile to inactive
     */
    public void setInactive() {
        active = false;
    }

    /**
     * Retrieves if the projectile is active
     * 
     * @return A boolean representing if the projectile is active
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Retrieves the projectile's x coordinate
     * 
     * @return The projectile's x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the projectile's y coordinate
     * 
     * @return The projectile's y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Retrieves the projectile's damage
     * 
     * @return The projectile's damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Retrieves the projectile's speed
     * 
     * @return The projectile's speed
     */
    public double getSpeed() {
        return speed;
    }
}
