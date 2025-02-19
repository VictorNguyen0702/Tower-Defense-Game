package WizardTD;

import java.util.ArrayList;

/**
 * A subclass of the Projectile abstract class for the
 * projectiles fired by sprayer towers
 */
public class Ring extends Projectile {

    private int size;
    public int range;
    private ArrayList<Monster> targetsHit = new ArrayList<>();

    /**
     * Creates an instance of the ring class
     * 
     * @param x The x coordinate of the ring's start position
     * @param y The y coordinate of the ring's start position
     * @param damage The damage of the ring
     * @param range The range of the ring
     */
    public Ring(int x, int y, double damage, int range) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = 5;
        this.active = true;
        this.range = range;
        this.size = 0;
    }

    /**
     * Get the range of the ring
     * 
     * @return The range of the ring
     */
    public int getRange() {
        return range;
    }

    /**
     * Increases the current size of the ring
     */
    public void increaseSize() {
        size += speed;
    }

    /**
     * Retrieves the size of the ring
     * 
     * @return The size of the ring
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds a monster to the ring's list of targets hit
     * 
     * @param monster The monster that the ring hit
     */
    public void addTargetHit(Monster monster) {
        targetsHit.add(monster);  
    }

    /**
     * Checks if a monster has been hit by the ring already
     * 
     * @param monster A monster to check if it has been hit
     * @return A boolean representing if a monster has been hit already
     */
    public boolean checkHit(Monster monster) {
        if (targetsHit.contains(monster)) {
            return true;
        } else {
            return false;
        }
    }
}
