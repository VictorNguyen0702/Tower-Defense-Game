package WizardTD;

import processing.data.JSONObject;


/**
 * A sub class of the Tower abstract class for the sprayer
 * type of tower
 */
public class Sprayer extends Tower {

    /**
     * Creates an instance of the sprayer class
     * 
     * @param col The column of the tile for the sprayer
     * @param row The row of the tile for the sprayer
     * @param config The config file of the game
     */
    public Sprayer(int col, int row, JSONObject config) {

        // get starting while checking if the value exists in the config
        if (config.hasKey("initial_sprayer_range")) {
            this.range = config.getInt("initial_sprayer_range");
        } else {
            this.range = 64;
        }

        if (config.hasKey("initial_sprayer_firing_speed")) {
            this.firingSpeed = config.getDouble("initial_sprayer_firing_speed");
        } else {
            this.firingSpeed = 1.5;
        }

        if (config.hasKey("initial_sprayer_damage")) {
            baseDamage = config.getDouble("initial_sprayer_damage");

        } else {
            this.baseDamage = 20;
        }

        this.damage = this.baseDamage;

        this.shotCooldown = 60 / firingSpeed;
        this.shotCooling = 60 / firingSpeed;

        set_coords(col, row);
        setImg();
    }

    /**
     * Sets the image of the sprayer based on its level
     */
    public void setImg() {
        
        if (rangeLvl >= 2 && firingSpeedLvl >= 2 && damageLvl >= 2) {
            img = "sprayer2";
        } else if (rangeLvl >= 1 && firingSpeedLvl >= 1 && damageLvl >= 1) {
            img = "sprayer1";
        } else { //if (rangeLvl >= 0 && firingSpeedLvl >= 0 && damageLvl >= 0) {
            img = "sprayer0";
        }
    }

    /**
     * Increases the range level and range of the sprayer
     */
    public void increaseRange() {
        range += 24;
        rangeLvl += 1;
    }

    /**
     * Increases the firing speed level and firing speed of the sprayer
     */
    public void increaseFiringSpeed() {
        firingSpeed += 0.5;
        firingSpeedLvl += 1;
        shotCooldown = Math.ceil(60 / firingSpeed);
    }

    /**
     * Increases the damage level and damage of the sprayer
     */
    public void increaseDamage() {
        damage += 1/2 * baseDamage;
        damageLvl += 1;
    }


}
