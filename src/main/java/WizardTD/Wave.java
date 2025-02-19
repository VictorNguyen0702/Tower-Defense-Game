
package WizardTD;

import java.util.ArrayList;
import processing.data.JSONObject;
import processing.data.JSONArray;
import java.util.Random;

/**
 * A class to be instantiated for each wave in the level
 * containing the list of monsters in the wave
 */
public class Wave {
    private double preWavePause;
    private int duration;
    private ArrayList<Monster> monstersList;
    private int monsterCount;
    private double spawnCooldown;
    private double spawnCooling;

    public Random random = new Random();

    /**
     * Instantiates an instance of the wave class and retrieves data from the
     * waveData JSONObject, making a monsterList with the data
     * 
     * @param waveData A JSONObject containing the data for the wave
     * @param pathwayList An arraylist containing the different possible pathways
     */
    public Wave(JSONObject waveData, ArrayList<ArrayList<int[]>> pathwayList) {
        monstersList = new ArrayList<>();
        duration = waveData.getInt("duration") * 60;
        preWavePause = waveData.getDouble("pre_wave_pause") * 60;
        JSONArray monstersData = waveData.getJSONArray("monsters");
        for (int i = 0; i < monstersData.size(); i ++) {
            JSONObject monstersGroup = monstersData.getJSONObject(i);
            int quantity = monstersGroup.getInt("quantity");
            for (int j = 0; j < quantity; j ++) {
                int pathwayNumber = random.nextInt(pathwayList.size());
                ArrayList<int[]> pathway = new ArrayList<>(pathwayList.get(pathwayNumber));
                monstersList.add(new Monster(monstersGroup,pathway));
            }
        }
        monsterCount = monstersList.size();
        this.spawnCooldown = duration / monsterCount;
        this.spawnCooling = duration / monsterCount;


    }

    /**
     * Retrieves an arraylist containing the monsters from the wave
     * 
     * @return An arraylist containing the monsters from the wave
     */
    public ArrayList<Monster> getMonstersList() {
        return monstersList;
    }

    /**
     * Skips the cooling of the monster spawn
     */
    public void skipCooling() {
        spawnCooling = 0;
    }

    /**
     * Decreases the cooldown between each monster spawning by 1
     */
    public void decreaseCooling() {
        spawnCooling -= 1;
    }

    /**
     * Resets the cooldown between each monster spawning
     */
    public void resetCooling() {
        spawnCooling = spawnCooldown;
    }

    /**
     * Retrieves the current state of the cooldown
     * 
     * @return The current state of the cooldown
     */
    public double getCooling() {
        return spawnCooling;
    }

    /**
     * Retrieves the pause length before the wave
     * 
     * @return The prewave pause length
     */
    public double getPause() {
        return preWavePause;
    }

    /**
     * Retrieves the duration of the wave
     * 
     * @return The length of the wave
     */
    public int getDuration() {
        return duration;
    }

    
}