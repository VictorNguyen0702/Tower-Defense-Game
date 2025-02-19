package WizardTD;

import processing.data.JSONObject;
import java.util.ArrayList;

/**
 * A class to be instantiated for each monster 
 * in a wave
 */
public class Monster implements MoveableObject {
    private String type;
    private int hp;
    private int maxHp;
    private double speed;
    private double armour;
    private double manaOnKill;

    private boolean spawned = false;
    private boolean alive = true;
    private boolean dying = false;
    private int dyingCount = 0;
    private boolean manaCollected = false;

    private ArrayList<int[]> pathway;
    private ArrayList<int[]> originalPathway;

    private int x;
    private int y;

    /**
     * Creates an instance of the monster class
     * 
     * @param monstersGroup A JSONObject with the monster's data
     * @param pathway A pathway for the monster to take
     */
    public Monster(JSONObject monstersGroup, ArrayList<int[]> pathway) {
        this.type = monstersGroup.getString("type");
        this.hp = monstersGroup.getInt("hp");
        this.maxHp = hp;
        this.speed = monstersGroup.getDouble("speed");
        this.armour = monstersGroup.getDouble("armour");
        this.manaOnKill = monstersGroup.getDouble("mana_gained_on_kill");

        this.spawned = false;
        this.alive = true;
        this.originalPathway = new ArrayList<>(pathway);
        this.pathway = pathway;
        this.x = pathway.get(0)[0] * 32 + 6;
        this.y = pathway.get(0)[1] * 32 + 40 + 6;
        this.manaCollected = false;
    }

    /**
     * Retrieves the type of monster
     * 
     * @return The type of monster
     */
    public String getType() {
        if (dyingCount > 0) {
            return type + String.valueOf((int) Math.ceil((float) dyingCount / 4));
        }
        return type;
    }

    /**
     * Retrieves the current hp of monster
     * 
     * @return The current hp of monster
     */
    public int getHp() {
        return hp;
    }

    /**
     * Retrieves the max hp of monster
     * 
     * @return The max hp of monster
     */   
    public double getMaxHp() {
        return maxHp;
    }

    /**
     * Sets the monster's hp to a new hp
     * 
     * @param newHp The monster's new hp
     */
    public void setHp(int newHp) {
        hp = newHp;
    }

    /**
     * Changes the monster's hp by how much hp they lose
     * 
     * @param damage The amount of damage the monster takes
     */
    public void loseHp (double damage) {
        hp -= damage * armour;
        if (hp <= 0) {
            dying = true;
            alive = false;
        }
    }

    /**
     * Retrieves the speed of monster
     * 
     * @return The speed of monster
     */   
    public double getSpeed() {
        return speed;
    }

    /**
     * Retrieves the mana gained on kill of the monster
     * 
     * @return The mana gained on kill of the monster
     */   
    public double getManaOnKill() {
        return manaOnKill;
    }

    /**
     * Retrieves the alive status
     * 
     * @return If the monster is alive
     */   
    public boolean getAlive() {
        return alive;
    }

    /**
     * Sets the monster's spawned field to true
     */
    public void spawn() {
        spawned = true;
    }

    /**
     * Retrieves the spawn status
     * 
     * @return If the monster is spawned
     */   
    public boolean getSpawned() {
        return spawned;
    }
    
    /**
     * Increases the number of frames the monster
     * has been dying for to change the death 
     * animation image
     */
    public void increaseDyingCount() {
        dyingCount += 1;
        if (dyingCount > 20) {
            dying = false;
        }
    }

    /**
     * Retrieves the dying status
     * 
     * @return If the monster is dying
     */   
    public boolean getDying() {
        return this.dying;
    }

    /**
     * Changes the mana collected status of the monster 
     * to true
     */
    public void collect() {
        manaCollected = true;
    }

    /**
     * Retrieves the monster's mana collected status
     * @return The monster's mana collected status
     */
    public boolean getCollected() {
        return manaCollected;
    }

    /**
     * Move the monster's x coordinate
     * 
     * @param pixels The number of pixels to move in the x direction
     */
    public void moveX(int pixels) {
        x += pixels;
    }

    /**
     * Move the monster's y coordinate
     * 
     * @param pixels The number of pixels to move in the y direction
     */
    public void moveY(int pixels) {
        y += pixels;
    }
 
    /**
     * Retrieves the monster's x coordinate
     * 
     * @return The monster's x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the monster's y coordinate
     * 
     * @return The monster's y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Retrieves the monster's predetermined pathway
     * 
     * @return The monster's pathway
     */
    public ArrayList<int[]> getPathway() {
        return pathway;
    }

    /**
     * Banishes the monster and resets its position
     */
    public void banish() {
        pathway = new ArrayList<> (originalPathway);
        x = pathway.get(0)[0] * 32 + 6;
        y = pathway.get(0)[1] * 32 + 40 + 6;
    }

    /**
     * Remove a tile from the monster's pathway 
     * 
     * @param index The index of the tile in the pathway to remove
     */
    public void removePathTile(int index) {
        pathway.remove(index);
    }
}
