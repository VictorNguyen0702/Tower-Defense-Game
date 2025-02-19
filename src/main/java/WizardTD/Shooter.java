package WizardTD;

import processing.data.JSONObject;

/**
 * A sub class of the Tower abstract class for the sprayer
 * type of tower
 */
public class Shooter extends Tower {

    /**
     * Creates an instance of the shooter class
     * 
     * @param col The column of the tile for the shooter
     * @param row The row of the tile for the shooter
     * @param config The config file of the game
     */
    public Shooter(int col, int row, JSONObject config) {

        // get starting values
        this.range = config.getInt("initial_tower_range");
        this.firingSpeed = config.getDouble("initial_tower_firing_speed");
        this.baseDamage = config.getDouble("initial_tower_damage");

        this.damage = this.baseDamage;

        this.shotCooldown = 60 / firingSpeed;
        this.shotCooling = 60 / firingSpeed;
        set_coords(col, row);
        setImg();
    }

    /**
     * Sets the image of the shooter based on its level
     */
    public void setImg() {
        
        if (rangeLvl >= 2 && firingSpeedLvl >= 2 && damageLvl >= 2) {
            img = "shooter2";
        } else if (rangeLvl >= 1 && firingSpeedLvl >= 1 && damageLvl >= 1) {
            img = "shooter1";
        } else {//if (rangeLvl >= 0 && firingSpeedLvl >= 0 && damageLvl >= 0) 
            img = "shooter0";
        }
    }
    
    /**
     * Increases the range level and range of the shooter
     */
    public void increaseRange() {
        range += 32;
        rangeLvl += 1;
    }

    /**
     * Increases the firing speed level and firing speed of the shooter
     */
    public void increaseFiringSpeed() {
        firingSpeed += 0.5;
        firingSpeedLvl += 1;
        shotCooldown = Math.ceil(60 / firingSpeed);
    }

    /**
     * Increases the damage level and damage of the shooter
     */
    public void increaseDamage() {
        damage += 1/2 * baseDamage;
        damageLvl += 1;
    }

}


