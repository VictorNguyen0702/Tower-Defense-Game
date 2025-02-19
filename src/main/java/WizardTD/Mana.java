package WizardTD;

import java.lang.Math;
import processing.data.JSONObject;

/**
 * A class to be instantiated for the mana of the 
 * player in game
 */
public class Mana {
    private int manaPoolCount = 0;

    private double mana;
    private double manaCap;

    private double baseManaCap;
    private double baseManaRegen;
    private double regenCooldown;
    private double regenCooling = 0;

    private int manaPoolCost;
    private int manaPoolCostIncrease;
    private double manaPoolCapMultiplier;
    private double manaPoolRegenMultiplier;
    

    /**
     * Creates an instance of the Mana class
     * 
     * @param config A JSONObject with the game data
     */
    public Mana(JSONObject config) {
        // Retrive mana data
        this.mana = config.getInt("initial_mana");
        this.baseManaCap = config.getInt("initial_mana_cap");
        this.baseManaRegen = config.getInt("initial_mana_gained_per_second");
        this.manaCap = baseManaCap;
        this.regenCooldown = 60 / baseManaRegen;


        // Retrieves mana pool spell data
        this.manaPoolCost = config.getInt("mana_pool_spell_initial_cost");
        this.manaPoolCostIncrease = config.getInt("mana_pool_spell_cost_increase_per_use");
        this.manaPoolCapMultiplier = config.getDouble("mana_pool_spell_cap_multiplier");
        this.manaPoolRegenMultiplier = config.getDouble("mana_pool_spell_mana_gained_multiplier");


    }

    /**
     * Increases the wizard mana by 1
     */
    public void regenOneMana() {
        mana += 1;
        if (mana >= manaCap) {
            mana = manaCap;
        }
    }

    /**
     * Decreases the regen cooling by 1
     */
    public void decreaseCooling() {
        regenCooling -= 1;
    }

    /**
     * Resets the regen cooling
     */
    public void resetCooling() {
        regenCooling = regenCooldown;
    }

    /**
     * Retrieves the current regen cooling
     * 
     * @return The current regen cooling
     */
    public double getCooling() {
        return regenCooling;
    }

    /**
     * Increases the mana by a certain amount and
     * accounts for the mana gain multiplier
     * 
     * @param baseManaGain the mana gained without consideration for multiplier
     */
    public void gainMana(double baseManaGain) {
        double manaGainMultiplier = 1 + (manaPoolCount * (manaPoolRegenMultiplier - 1));
        mana += (baseManaGain * manaGainMultiplier);
        if (mana >= manaCap) {
            mana = manaCap; // sets it to the cap if it is about to go over
        }
    }

    /**
     * Decreases the mana by a certain amount
     * 
     * @param manaCost The amount of mana to be lost
     */
    public void loseMana(double manaCost) {
        mana -= manaCost;
    }
    
    /**
     * Sets the mana to a certain amount
     * 
     * @param newMana The new mana to be set
     */
    public void setMana(double newMana) {
        mana = newMana;
    }

    /**
     * Retrieves the current mana
     * 
     * @return The current mana
     */
    public double getMana() {
        return mana;
    }

    /**
     * Retrieves the mana cap
     * 
     * @return The mana cap
     */
    public double getManaCap() {
        return manaCap;    
    }

    /**
     * Retrieves the number of mana pool spells activated
     * 
     * @return The number of mana pool spells activated
     */
    public int getManaPoolCount() {
        return manaPoolCount;
    }

    /**
     * Attempts to create a mana pool spell if there is enough 
     * mana and updates the regen speed and mana cap
     */
    public void tryCreateManaPool() {
        double spellCost = (manaPoolCost + (manaPoolCount * manaPoolCostIncrease));
        if (mana >= spellCost) {
            mana -= spellCost;
            manaPoolCount += 1;
            double manaGainMultiplier = 1 + (manaPoolCount * (manaPoolRegenMultiplier - 1));
            regenCooldown = 60 / (baseManaRegen * manaGainMultiplier);
            double manaCapMultiplier = Math.pow(manaPoolCapMultiplier, manaPoolCount);
            manaCap = baseManaCap * manaCapMultiplier;
        }
    }

    /**
     * Retrieves the mana pool spell cost
     * 
     * @return The mana pool spell cost
     */
    public double getSpellCost() {
        double spellCost = (manaPoolCost + (manaPoolCount * manaPoolCostIncrease));
        return spellCost;
    }

}