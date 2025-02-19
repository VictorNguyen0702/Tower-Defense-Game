package WizardTD;

public interface MoveableObject {

    /**
     * Move the object's x coordinate
     * 
     * @param pixels The number of pixels to move in the x direction
     */
    public void moveX(int pixels);

    /**
     * Move the object's y coordinate
     * 
     * @param pixels The number of pixels to move in the x direction
     */
    public void moveY(int pixels);
}
