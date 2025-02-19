package WizardTD;

/**
 * A subclass of the Projectile abstract class for the
 * projectiles fired by shooter towers
 */
public class Fireball extends Projectile  implements MoveableObject {

    private Monster target;

    /**
     * Creates an instance of the fireball class
     * 
     * @param x The x coordinate of the fireball's position
     * @param y The y coordinate of the fireball's  position
     * @param damage The damage of the fireball
     * @param target The target of the fireball
     */
    public Fireball(int x, int y, double damage, Monster target) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = 5;
        this.target = target;
        this.active = true;
    }

    /**
     * Move the fireball's x coordinate
     * 
     * @param pixels The number of pixels to move in the x direction
     */
    public void moveX(int pixels) {
        this.x += pixels;
    }

    /**
     * Move the fireball's y coordinate
     * 
     * @param pixels The number of pixels to move in the y direction
     */
    public void moveY(int pixels) {
        this.y += pixels;
    }

    /**
     * Retrieves the fireball's target
     * 
     * @return The target of the fireball
     */
    public Monster getTarget() {
        return target;
    }

}
