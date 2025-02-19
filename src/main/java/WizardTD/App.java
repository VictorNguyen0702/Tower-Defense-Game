package WizardTD;

/**
 * A class for the app itself including all the processing
 * and draw functions
 */
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.MouseEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class App extends PApplet {

    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;

    // declaring booleans for toggled functions in game
    public boolean gameRun;
    public boolean lose;
    public boolean win;

    public boolean fastForward;
    public boolean pause;
    public boolean buildShooter;
    public boolean buildSprayer;
    public boolean rangeUp;
    public boolean firingSpeedUp;
    public boolean damageUp;
    public boolean manaPoolSpell;

    // declaring wave variables
    public ArrayList<Wave> wavesList;
    public Wave current;
    public double nextStartCooldown;

    public int towerCost;
    public ArrayList<Monster> monsterList;
    public ArrayList<Tower> towerList;
    public ArrayList<Shooter> shooterList;
    public ArrayList<Sprayer> sprayerList;
    public ArrayList<Fireball> fireballList;
    public ArrayList<Ring> ringList;

    // declaring map variables
    public String layoutFile;
    public char[][] layout;
    public int houseCol;
    public int houseRow;
    public ArrayList<int[]> fullSpaces; // [x,y]

    // declaring other variables
    public String configPath;
    public JSONObject config;
    public HashMap<String, PImage> images;
    public Mana wizardMana;
    public Random random = new Random();




    /**
     * Initialises an instance of the app and sets the configPath
     */
    public App() {
        this.configPath = "src/main/java/WizardTD/config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. 
     * Initialise the elements such as the player, variables, 
     * enemies and map elements. This also resets all game data 
     */
	@Override
    public void setup() {
        frameRate(FPS);
        settings();

        // initialise variables
        gameRun = true;
        lose = false;
        win = false;

        fastForward = false;
        pause = false;
        buildShooter = false;
        buildSprayer = false;
        rangeUp = false;
        firingSpeedUp = false;
        damageUp = false;
        manaPoolSpell = false;

        monsterList = new ArrayList<>();
        towerList = new ArrayList<>();
        shooterList = new ArrayList<>();
        sprayerList = new ArrayList<>();
        fireballList = new ArrayList<>();
        ringList = new ArrayList<>();
        fullSpaces = new ArrayList<>();
        images = new HashMap<String, PImage>();

        // Load config data
        this.config = loadJSONObject(this.configPath);

        // Retrieves layout data
        this.layoutFile = this.config.getString("layout");
        this.layout = getLayoutData(layoutFile);
        ShortestPathAlgorithm shortestPaths = new ShortestPathAlgorithm(layout);

        // Retrieves wave data
        JSONArray allWavesData = config.getJSONArray("waves");
        wavesList = new ArrayList<Wave>();
        for (int i = 0; i < allWavesData.size(); i++) {
            JSONObject waveData = allWavesData.getJSONObject(i);
            wavesList.add(new Wave(waveData, shortestPaths.getPathwayList()));
        }
        this.current = null;
        nextStartCooldown = wavesList.get(0).getPause();
        
        String[] imageFiles = {
        "path0", "path1", "path2", "path3", "shrub", "grass", "wizard_house",
        "shooter0", "shooter1", "shooter2", "fireball", "sprayer0", "sprayer1", "sprayer2",
        "beetle", "beetle1", "beetle2", "beetle3", "beetle4", "beetle5", 
        "gremlin", "gremlin1", "gremlin2", "gremlin3", "gremlin4", "gremlin5",
        "worm", "worm1", "worm2", "worm3", "worm4", "worm5" };
        
        for (String imageFile : imageFiles) {
            String imagePath = "src/main/resources/WizardTD/" + imageFile + ".png";
            images.put(imageFile, loadImage(imagePath));
        }
        this.wizardMana = new Mana(config);
        this.towerCost = config.getInt("tower_cost");


    }


    /**
     * Toggles options in game if certain keys are pressed, to alter game function
     */
    @Override
    public void keyPressed() {
        // Check which key was pressed
        switch (key) {
            case 'f':
                fastForward ^= true;
                break;
            case 'p':
                pause ^= true;
                break;
            case 't':
                buildShooter ^= true;
                if (buildSprayer) {
                    buildSprayer = false;
                }
                break;
            case 'y': 
                buildSprayer ^= true;
                if (buildShooter) {
                    buildShooter = false;
                }
                break;
            case '1':
                rangeUp ^= true;
                break;
            case '2':
                firingSpeedUp ^= true;
                break;
            case '3':
                damageUp ^= true;
                break;
            case 'm':
                manaPoolSpell = true;
                int initialSpellCount = this.wizardMana.getManaPoolCount();
                this.wizardMana.tryCreateManaPool();
                int newSpellCount = this.wizardMana.getManaPoolCount();
                if (newSpellCount > initialSpellCount) {
                    drawGUI(); // draw to get flash of yellow click
                }
                manaPoolSpell = false;
                break;
            case 'r':
                if (!gameRun) {
                    setup();
                    break;
                }

        }
    }


    /**
     * Checks if the user has clicked a button to toggle it or clicked a 
     * tower to try and upgrade it
     * 
     * @param e A mouseEvent that occurs
     */
    @Override
    public void mousePressed(MouseEvent e) {

        int k = -1; // set it to out of range for the switch statements

        if (e.getX() >= 650 && e.getX() <= 690) {
            for (int i = 0; i < 8; i ++) {
                if (e.getY() >= 45 + (50 * i) && e.getY() <= 85 + (50 * i)) {
                    k = i;
                    break;
                }
            }
            switch (k) {
                case 0:
                    this.fastForward ^= true;
                    break;
                case 1:
                    pause ^= true;
                    break;
                case 2:
                    buildShooter ^= true;
                    if (buildSprayer) {
                        buildSprayer = false;
                    }
                    break;
                case 3:
                    buildSprayer ^= true;
                    if (buildShooter) {
                        buildShooter = false;
                    }
                    break;
                case 4:
                    rangeUp ^= true;
                    break;
                case 5:
                    firingSpeedUp ^= true;
                    break;
                case 6:
                    damageUp ^= true;
                    break;
                case 7:
                    manaPoolSpell = true;
                    int initialSpellCount = this.wizardMana.getManaPoolCount();
                    this.wizardMana.tryCreateManaPool();
                    int newSpellCount = this.wizardMana.getManaPoolCount();
                    if (newSpellCount > initialSpellCount) {
                        drawGUI(); // draw to get flash of yellow click
                    }
                    manaPoolSpell = false;
                    break;
            }
        }



        if (e.getX() >= 0 && e.getX() <= 20 * CELLSIZE && e.getY() >= TOPBAR && e.getY() <= 20 * CELLSIZE + TOPBAR) {
            int colPressed = (int) Math.floor(e.getX() / CELLSIZE);
            int rowPressed = (int) Math.floor((e.getY() - TOPBAR) / CELLSIZE);

            if (buildShooter && !getFull(colPressed, rowPressed)) {  
                createTower(colPressed, rowPressed, "shooter");
            } else if (buildSprayer && !getFull(colPressed, rowPressed)) {
                createTower(colPressed, rowPressed, "sprayer");
            } else {
                for (Tower tower: towerList) {
                    if (Arrays.equals(tower.getCoords(),new int[] {colPressed,rowPressed})) {
                        upgradeTower(tower);
                        break;
                    }
                }
            }
        }
    }
    



    /**
     * Draw all elements in the game by current frame and 
     * runs the code twice if fastForward is active
     */
	@Override
    public void draw() {
        int iterationCount;

        if (fastForward) {
            iterationCount = 2; // runs draw twice for each frame
        } else {
            iterationCount = 1; // runs draw once for each frame
        }
        for (int i = 0; i < iterationCount; i ++) {

            // mana regen every second
            wizardMana.decreaseCooling();
            if(gameRun && !pause && wizardMana.getCooling() <= 0){
                wizardMana.regenOneMana();
                wizardMana.resetCooling();
            }

            // starts new wave
            if (gameRun && !pause && nextStartCooldown == 0) {
                startNextWave();
                this.current.skipCooling(); // spawns first monster immediately
            }
        
            // checks for last wave and if all monsters are dead
            if (wavesList.indexOf(current) == wavesList.size() - 1) {
                boolean allDead = true;
                for (Monster monster : current.getMonstersList()) {
                    if (monster.getAlive()) {
                        allDead = false;
                        break;
                    }
                }
                if (allDead) {
                    gameRun = false;
                    win = true;
                }
            }
            drawLayout();

            // draw and attempt to shoot for each tower
            if (towerList != null) {
                for (Tower tower : towerList) {
                    drawTower(tower);
                    if (gameRun && !pause) { // accounts for game being paused
                        tower.decreaseCooling();
                        if (tower.getCooling() <= 0) {
                            boolean shotSuccess = tryShoot(tower);
                            tower.resetCooling();
                            if (!shotSuccess) {
                                tower.skipCooling();
                            }
                        }
                    }
                }
            }
            if (this.current != null) {
                spawnRandomMonster(); // already accounts for game being paused
                drawMoveMonsters(); // already accounts for game being paused
                if (ringList != null) {
                    for (Ring ring : ringList) {
                        if (ring.getActive())
                            drawRing(ring);
                            if (gameRun && !pause) { // accounts for game being paused
                                moveRing(ring);
                        }
                    }
                }
                if (fireballList != null) {
                    for (Fireball fireball : fireballList) {
                        if (fireball.getActive())
                            drawFireball(fireball);
                            if (gameRun && !pause) { // accounts for game being paused
                                moveFireball(fireball);
                        }
                    }
                }
            }
            drawHouse();
            if (gameRun) { // accounts for if the game is over
                checkTowerRangeHover();
            }

            drawGUI();
            checkTowerUpgradeHover();

            if (gameRun && !pause) {
                nextStartCooldown -= 1;
            }
            if (lose) {
                drawLose();
            } else if (win) {
                drawWin();
            }
        }
    }

    /**
     * Draws the tower based on its coordinates on the map and 
     * the upgrades that the tower currently has
     * 
     * @param tower The tower with upgrades to be drawn
     */
    public void drawTower(Tower tower) {

        // draw the base tower image
        int x = tower.getCoords()[0] * CELLSIZE;
        int y = tower.getCoords()[1] * CELLSIZE + TOPBAR;
        PImage towerImage = images.get(tower.getImg());

        image(towerImage, x, y);

        // draw upgrades on the tower
        drawUpgrades(tower);

    }


    /**
     * Takes in a coordinate and tower type and checks if the space is empty and 
     * if the user has enough mana and creates a tower. Also upgrades the tower
     * if the user has the upgrade toggles active and enough mana
     * 
     * @param colPressed The column chosen by the mouseEvent
     * @param rowPressed The row chosen by the mouseEvent
     * @param towerType The type of tower that is toggled to be built
     */
    public void createTower(int colPressed, int rowPressed, String towerType) {
        int[] location = {colPressed, rowPressed};
        if (this.layout[rowPressed][colPressed] == ' ') { // check if it is an empty square
            if (wizardMana.getMana() >= towerCost) {
                if (towerType.equals("shooter")) {
                    Shooter newShooter = new Shooter(colPressed, rowPressed, this.config);
                    this.towerList.add(newShooter);
                    shooterList.add(newShooter);
                    wizardMana.loseMana(towerCost);
                    upgradeTower(newShooter);
                    this.fullSpaces.add(location);
                } else if (towerType.equals("sprayer")) {
                    Sprayer newSprayer = new Sprayer(colPressed, rowPressed, this.config);
                    towerList.add(newSprayer);
                    sprayerList.add(newSprayer);
                    wizardMana.loseMana(towerCost);
                    upgradeTower(newSprayer);
                    this.fullSpaces.add(location);
                }
            }
        }
    }

    /**
     * Takes in a tower object and upgrades the tower depending on what
     * upgrade toggles are active. Goes from top to bottom and upgrades
     * them one by one if the user has enough mana. If the user has 
     * insufficient mana, only the upgrades they have enough mana for
     * will be upgraded
     * 
     * @param tower The tower to be upgraded
     */
    public void upgradeTower(Tower tower) {

        if (rangeUp && wizardMana.getMana() > tower.getRangeUpCost()) {
            wizardMana.loseMana(tower.getRangeUpCost());
            tower.increaseRange();
            tower.setImg();
        }
        if (firingSpeedUp && wizardMana.getMana() > tower.getFiringSpeedUpCost()) {
            wizardMana.loseMana(tower.getFiringSpeedUpCost());
            tower.increaseFiringSpeed();
            tower.setImg();
        }
        if (damageUp && wizardMana.getMana() > tower.getDamageUpCost()) {
            wizardMana.loseMana(tower.getDamageUpCost());
            tower.increaseDamage();
            tower.setImg();
        }
    }

    /**
     * Takes in a tower object and checks if there are any monsters in range
     * If there are any enemies in range, it spawns the respective projectile
     * depending on what type of tower it is
     * 
     * @param tower The tower attempting to shoot
     */
    public boolean tryShoot(Tower tower) {
        int towerCentreX = tower.getCoords()[0] * CELLSIZE + 16;
        int towerCentreY = tower.getCoords()[1] * CELLSIZE + TOPBAR + 16;

        // get closest monster alive
        Monster closestMonster = null;
        double minDistance = Double.MAX_VALUE;
        if (monsterList != null) {
            for (Monster monster : monsterList) {
                if (monster.getAlive() && monster.getSpawned()) {
                    int monsterCentreX = monster.getX() + 10;
                    int monsterCentreY = monster.getY() + 10;
                    float distance = dist(towerCentreX, towerCentreY, monsterCentreX, monsterCentreY);

                    if (distance <= minDistance) { 
                        minDistance = distance;
                        closestMonster = monster;
                    }
                }
            }

            // checks if in range
            if (minDistance <= tower.getRange()) {
                if (tower instanceof Shooter) {
                    int x = tower.getCoords()[0] * 32 + 13;
                    int y = tower.getCoords()[1] * 32 + 40 + 13;  
                    Fireball fireball = new Fireball(x, y, tower.getDamage(), closestMonster);
                    fireballList.add(fireball);
                } else if (tower instanceof Sprayer) {
                    int x = tower.getCoords()[0] * 32 + 16;
                    int y =  tower.getCoords()[1] * 32 + 40 + 16;
                    Ring ring = new Ring(x, y, tower.getDamage(), tower.getRange());
                    ringList.add(ring);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Draws extra icons for upgrades depending on the levels
     * of the tower
     * 
     * @param tower The tower to draw upgrades on
    */
    public void drawUpgrades(Tower tower) {
        int x = tower.getCoords()[0] * CELLSIZE;
        int y = tower.getCoords()[1] * CELLSIZE + TOPBAR;

        int rangeLvl = tower.getRangeLvl();
        int firingSpeedLvl = tower.getFiringSpeedLvl();
        int damageLvl = tower.getDamageLvl();
        int totalLvl;
        noFill();

        if (rangeLvl >= 2 && firingSpeedLvl >= 2 && damageLvl >= 2) {
            totalLvl = 2;
        } else if (rangeLvl >= 1 && firingSpeedLvl >= 1 && damageLvl >= 1) {
            totalLvl = 1;
        } else { //if (rangeLvl >= 0 && firingSpeedLvl >= 0 && damageLvl >= 0) 
            totalLvl = 0;
        }
        if (rangeLvl > totalLvl) {
            stroke(206, 24, 206);
            strokeWeight(1);
            for (int i = 0; i < rangeLvl - totalLvl; i ++) {
                ellipse(x + 3 + (9 * i), y + 3, 7 , 7);
            }
        }
        if (firingSpeedLvl > totalLvl) {
            stroke(123, 173, 247);
            strokeWeight(firingSpeedLvl - totalLvl + 1);
                rect(x  + 4, y  + 4, 22 , 22, 2);
            }
        if (damageLvl > totalLvl) {
            stroke(206, 24, 206);
            strokeWeight(1);

            int centreX = x + 2;
            int centreY = y + 28;
            for (int i = 0; i < damageLvl - totalLvl; i ++) {
                line(centreX + (7 * i) - 2, centreY - 3, centreX + (7 * i) + 2, centreY + 3);
                line(centreX + (7 * i) - 2, centreY + 3, centreX + (7 * i) + 2, centreY - 3);
            }
        }
    }


    /**
     * Draws the ring created by sprayers
     * 
     * @param ring The ring object to be drawn
     */

    public void drawRing(Ring ring) {
        noFill();
        strokeWeight(2);
        stroke(173, 216, 230);
        ellipse(ring.getX(), ring.getY(), ring.getSize(), ring.getSize());
    }

     /**
     * Changes the position of rings created by sprayers
     * 
     * @param ring The ring object to be moved
     */   
    public void moveRing(Ring ring) {
        ring.increaseSize();
        if (ring.getSize() / 2 >= ring.getRange()) {
            ring.setInactive();
        } else if (ring.getActive()) {
            for (Monster target: monsterList) {
                int targetCentreX = target.getX() + 10;
                int targetCentreY = target.getY() + 10;
                int ringCentreX = ring.getX();
                int ringCentreY = ring.getY();
                float distance = dist(ringCentreX, ringCentreY, targetCentreX, targetCentreY);
                if (distance <= ring.getSize() / 2 && !ring.checkHit(target)) {
                    target.loseHp(ring.getDamage());
                    ring.addTargetHit(target);
                    if (target.getHp() <= 0) {
                        target.setHp(0);
                    }
                    if (!target.getAlive() && !target.getCollected()) {
                        this.wizardMana.gainMana(target.getManaOnKill());
                        target.collect();
                    }
                }
            }
        }
    }

    /**
     * Draws the fireball created by shooters
     * 
     * @param fireball The fireball object to be drawn
     */
    public void drawFireball(Fireball fireball) {
        PImage img = images.get("fireball");
        image(img, fireball.getX(), fireball.getY(), 6, 6);
    }

    /**
     * Changes the position of fireballs created by shooters
     * 
     * @param fireball The fireball object to be moved
     */
    public void moveFireball(Fireball fireball) {
        Monster target = fireball.getTarget();

        if (!target.getAlive() && target.getSpawned()) {
            fireball.setInactive();
        } else if (fireball.getActive()) {
            int targetCentreX = target.getX() + 10;
            int targetCentreY = target.getY() + 10;
            int fireballCentreX = fireball.getX() + 3;
            int fireballCentreY = fireball.getY() + 3;

            float angle = atan2(targetCentreY - fireballCentreY, targetCentreX - fireballCentreX);
            float distance = dist(fireballCentreX, fireballCentreY, targetCentreX, targetCentreY);

            fireball.moveX((int) Math.round(cos(angle) * fireball.getSpeed()));
            fireball.moveY((int) Math.round(sin(angle) * fireball.getSpeed()));

            if (distance <= fireball.getSpeed()) {
                target.loseHp(fireball.getDamage());
                    if (target.getHp() <= 0) {
                        target.setHp(0);
                    }
                    if (!target.getAlive() && !target.getCollected()) {
                        this.wizardMana.gainMana(target.getManaOnKill());
                        target.collect();
                    }
                fireball.setInactive();
            }
        }

    }

    
    /**
     * Draws a monster object based on its position and type and draws
     * it's HP bar above the sprite.
     * 
     * @param monster The monster to be drawn
     */
    public void drawMonster(Monster monster) {
        PImage img = images.get(monster.getType());
        image(img, monster.getX(), monster.getY(), 20, 20);

        noStroke();

        //draw full hp bar (red)
        fill(247, 10, 7);
        rect(monster.getX() - 3, monster.getY() - 5, 26, 3);

        // draw percentile hp bar (green)
        double hpBarWidth = 26 * monster.getHp()/monster.getMaxHp();
        fill(78, 214, 70);
        rect(monster.getX() - 3, monster.getY() - 5, (float) hpBarWidth, 3);
    }

    /**
     * Changes the position of a monster object
     * 
     * @param monster The monster to be moved
     */
    public void moveMonster(Monster monster) {
        if (monster.getPathway().size() > 1) {
            
            // Calculate the angle and distance to the next tile
            int nextX = monster.getPathway().get(0)[0] * CELLSIZE;
            int nextY = monster.getPathway().get(0)[1] * CELLSIZE + TOPBAR;
            float angle = atan2((nextY + 6) - monster.getY(), (nextX + 6) - monster.getX());
            float distance = dist(monster.getX(), monster.getY(), nextX + 6, nextY + 6);
            
            // Move the image towards the next tile based on speed

            monster.moveX((int) Math.round(cos(angle) * monster.getSpeed()));
            monster.moveY((int) Math.round(sin(angle) * monster.getSpeed()));
            
            // Check if the image has reached the next tile
            if (distance <= monster.getSpeed()) {
                // Remove the current tile from the pathway
                monster.removePathTile(0);
            }
        } else { // if they reach the wizard house
            this.wizardMana.loseMana(monster.getHp());
            monster.banish();
            if (wizardMana.getMana() <= 0) {
                wizardMana.setMana(0);
                gameRun = false;
                lose = true;
            }
        }
    }

    /**
     * Loops through all the monsters in the monsterList and increases their dyingframe 
     * count if they are dying. It draws all alive monsters and mvoes them if they are 
     * alive and not dying
     * 
     */
    public void drawMoveMonsters() {

        for (Monster monster : monsterList) {
            if (monster.getDying() == true && !pause) {
                monster.increaseDyingCount();
            }
            if (monster.getSpawned() && (monster.getAlive() || monster.getDying())) {
                drawMonster(monster);
                if (gameRun && !pause && monster.getAlive()) {
                    moveMonster(monster);
                }
            }
        }
    }

    /**
     * Spawns a random monster from the current wave's monster list that has not 
     * been spawned yet
     */
    public void spawnRandomMonster() {
        if (gameRun && !pause && this.current.getMonstersList() != null) {
            ArrayList<Monster> available = new ArrayList<Monster>();
            for (Monster monster : current.getMonstersList()) {
                if (!monster.getSpawned()) {
                    available.add(monster);
                }
            }
            if (available.size() > 0) {
                int index = random.nextInt(available.size());
                Monster monster = available.get(index);
                current.decreaseCooling();
                if (!monster.getSpawned() && current.getCooling() <= 0) {
                    monster.spawn();
                    monsterList.add(monster);
                    current.resetCooling();
                    return;
                }
            }
        }
    }

    /**
     * Uses the layoutFile name to retrieve the map data inside
     * and stores it as a 2D char array
     * 
     * @param layoutFile A string with the level path 
     * @return A 2D char array representing the map
     */
    public char[][] getLayoutData(String layoutFile) {
        // Convert the map data into a char array char[row][col]
        
        try {
            Scanner layoutScanner = new Scanner(new File(layoutFile));
            int row = 0;
            char[][] layout = new char[20][20];
    
            while (layoutScanner.hasNextLine() && row < 20) {
                String line = layoutScanner.nextLine();
    
                for (int col = 0; col < line.length(); col++) {
                    layout[row][col] = Character.toUpperCase(line.charAt(col));
                }
    
                if (line.length() < 20) {
                    for (int col = line.length(); col < 20; col++) {
                        layout[row][col] = ' ';
                    }
                }
                row++;
            }
            layoutScanner.close();

            return layout;
        } catch (FileNotFoundException exception) {
            return null;
        }
    }

    /**
     * starts the first wave if game has just started, otherwise 
     * starts the next wave and also gets the timer until next wave
     * starts if it is not the last wave
     */
    public void startNextWave() {
        if (this.current == null) {
            this.current = wavesList.get(0);
            if (wavesList.size() > 1) {
                nextStartCooldown = current.getDuration() + wavesList.get(wavesList.indexOf(current) + 1).getPause();
            }

        } else if (wavesList.indexOf(current) < wavesList.size() - 1) {
            this.current = wavesList.get(wavesList.indexOf(current) + 1);
            
            if (wavesList.indexOf(current) < wavesList.size() - 1) { // if there is another wave
                nextStartCooldown = current.getDuration() + wavesList.get(wavesList.indexOf(current) + 1).getPause();
            }
        }
        
    }

    /**
     * Check if there is any tower in the tile that the mouse is hovering
     * over. If there is a tower, it draws a circle representing the tower's
     * range
     */
    public void checkTowerRangeHover() {
        int col = (int) Math.floor(mouseX / CELLSIZE);
        int row = (int) Math.floor((mouseY - TOPBAR) / CELLSIZE);
        Tower towerHovered = null;

        for (Tower tower : towerList) {
            if (tower.getCoords()[0] == col && tower.getCoords()[1] == row) {
                towerHovered = tower;
                break;
            }
        }

        if (towerHovered != null) {
            int towerCentreX = towerHovered.getCoords()[0] * CELLSIZE + 16;
            int towerCentreY = towerHovered.getCoords()[1] * CELLSIZE + TOPBAR + 16;
            int range = towerHovered.getRange();
            noFill();
            strokeWeight(1);
            stroke(233, 223, 67);
            ellipse(towerCentreX, towerCentreY, 2 * range, 2 * range);
        }
    }

    /**
     * Checks if there is any tower in the tile that the mouse is hovering
     * over. If there is a tower and any upgrade toggle is active, it draws
     * a white box in the bottom right of the GUI representing the cost of 
     * the tower upgrades
     */
    public void checkTowerUpgradeHover() {
        int col = (int) Math.floor(mouseX / CELLSIZE);
        int row = (int) Math.floor((mouseY - TOPBAR) / CELLSIZE);
        Tower towerHovered = null;

        for (Tower tower : towerList) {
            if (tower.getCoords()[0] == col && tower.getCoords()[1] == row) {
                towerHovered = tower;
                break;
            }
        }
        if (towerHovered != null) {

            int upgradeCount = 0;
            if (rangeUp) {
                upgradeCount += 1;
            }
            if (firingSpeedUp) {
                upgradeCount += 1;
            }
            if (damageUp) {
                upgradeCount += 1;
            }

            if (upgradeCount != 0) {
                strokeWeight(2);
                fill(255);
                rect(655, 500, 85, 25);
                rect(655, 525, 85, 20 * upgradeCount);
                rect(655, 525 + upgradeCount * 20, 85, 25);

                fill(0);
                textSize(12);
                int upgradesDrawn = 0;

                text("Upgrade cost", 660, 517);

                int totalCost = 0;
                if (damageUp) {
                    totalCost += towerHovered.getDamageUpCost();
                    text("damage:   " + towerHovered.getDamageUpCost(), 660, 519 + (upgradeCount - upgradesDrawn) * 20);
                    upgradesDrawn += 1;
                }
                if (firingSpeedUp) {
                    totalCost += towerHovered.getFiringSpeedUpCost();
                    text("speed:      " + towerHovered.getFiringSpeedUpCost(), 660, 519 + (upgradeCount - upgradesDrawn) * 20);
                    upgradesDrawn += 1;
                }
                if (rangeUp) {
                    totalCost += towerHovered.getRangeUpCost();
                    text("range:      " + towerHovered.getRangeUpCost(), 660, 519 + (upgradeCount - upgradesDrawn) * 20);
                    upgradesDrawn += 1;
                }
                text("Total:       " + totalCost, 660, 545 + upgradeCount * 20);
                }
        }
    }

    /**
     * Checks if the position of the mouse is hovering over any buttons and 
     * fills in the hovered button with a grey background if it is not currently
     * active
     */
    public void checkButtonHover() {
        stroke(0,0,0);
        strokeWeight(2);
        //draw toggle boxes
        noFill();
        for (int i = 0; i < 8; i ++) {
            if ((mouseX >= 650 && mouseX <= 690) && (mouseY >= 45 + (50 * i) && mouseY <= 85 + (50 * i))) {
                fill(200);
                rect(650, 45 + (50 * i), 40, 40, 10);
                break;
            }
        }
    }

    /**
     * Draws the layout of the map based on the 2D char array of the layout
     * created by getLayout() using imported images
     */
    public void drawLayout() {

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                int x = col * CELLSIZE;
                int y = row * CELLSIZE + TOPBAR;
                char symbol = layout[row][col]; 
                
                // Draw the corresponding image based on the symbol
                switch (symbol) {
                    case 'S':
                        image(images.get("shrub"), x, y, CELLSIZE, CELLSIZE);
                        break;
                    case 'X':
                        boolean left = false;
                        boolean up = false;
                        boolean right = false;
                        boolean down = false;

                        // mark which squares around are paths
                        if (row == 0 || layout[row - 1][col] == 'X') {
                            up = true;
                        }
                        if (row == (layout.length - 1) || layout[row + 1][col] == 'X') {
                            down = true;
                        }
                        if (col == 0 || layout[row][col - 1] == 'X') {
                            left = true;
                        }
                        if (col == (layout[row].length - 1) || layout[row][col + 1] == 'X') {
                            right = true;
                        }
                    
                        // create appropriate pathway
                        if (left && right && up && down) { // 4 sides
                            image(images.get("path3"), x, y, CELLSIZE, CELLSIZE);
                        } 
                        
                        else if (right && down && left){ // 3 sides
                            image(images.get("path2"), x, y, CELLSIZE, CELLSIZE);
                        } else if (down && left && up){ // 3 sides
                            image(rotateImageByDegrees(images.get("path2"), 90), x, y, CELLSIZE, CELLSIZE);
                        } else if (left && up && right){ // 3 sides
                            image(rotateImageByDegrees(images.get("path2"), 180), x, y, CELLSIZE, CELLSIZE);
                        } else if (up && right && down){ // 3 sides
                            image(rotateImageByDegrees(images.get("path2"), 270), x, y, CELLSIZE, CELLSIZE);
                        } 

                        else if (left && right) { // 2 sides straight
                            image(images.get("path0"), x, y, CELLSIZE, CELLSIZE);
                        } else if (up && down) { // 2 sides straight
                            image(rotateImageByDegrees(images.get("path0"), 90), x, y, CELLSIZE, CELLSIZE);
                        } 
                        
                        else if (down && left) { // 2 sides turn
                            image(images.get("path1"), x, y, CELLSIZE, CELLSIZE);
                        } else if (left && up) { // 2 sides turn
                            image(rotateImageByDegrees(images.get("path1"), 90), x, y, CELLSIZE, CELLSIZE);
                        } else if (up && right) { // 2 sides turn
                            image(rotateImageByDegrees(images.get("path1"), 180), x, y, CELLSIZE, CELLSIZE);
                        } else if (right && down) { // 2 sides turn
                            image(rotateImageByDegrees(images.get("path1"), 270), x, y, CELLSIZE, CELLSIZE);
                        }

                        else if (left) {
                            image(images.get("path0"), x, y, CELLSIZE, CELLSIZE);
                        } else if (up) {
                            image(rotateImageByDegrees(images.get("path0"), 90), x, y, CELLSIZE, CELLSIZE);
                        } else if (right) {
                            image(images.get("path0"), x, y, CELLSIZE, CELLSIZE);
                        } else { //if (down) {
                            image(rotateImageByDegrees(images.get("path0"), 90), x, y, CELLSIZE, CELLSIZE);
                        }

                        break;
                    case 'W':
                        image(images.get("grass"), x, y, CELLSIZE, CELLSIZE);
                        this.houseRow = row;
                        this.houseCol = col;
                        break;
                    case ' ':
                        image(images.get("grass"), x, y, CELLSIZE, CELLSIZE);
                        break;

                }
            }
        }

    }

    /**
     * Draws the wizard's house on top of the map and any surrounding tiles
     */
    public void drawHouse() {

        int houseX = this.houseCol * CELLSIZE - 8;
        int houseY = this.houseRow * CELLSIZE + TOPBAR - 8;
        // insert wizard house on top of map and surrounding grass
        if (houseRow != 0 && layout[houseRow - 1][houseCol] == 'X') { // up
            image(rotateImageByDegrees(images.get("wizard_house"), 270), houseX, houseY, 48, 48);
        } else if (houseRow != (layout.length - 1) && layout[houseRow + 1][houseCol] == 'X') { // down
            image(rotateImageByDegrees(images.get("wizard_house"), 90), houseX, houseY, 48, 48);
        } else if (houseCol != 0 && layout[houseRow][houseCol - 1] == 'X') { // left
            image(images.get("wizard_house"), houseX, houseY, 48, 48);
            image(rotateImageByDegrees(images.get("wizard_house"), 180), houseX, houseY, 48, 48);
        } else if (houseCol != (layout[houseRow].length - 1) && layout[houseRow][houseCol + 1] == 'X') { // right
            image(images.get("wizard_house"), houseX, houseY, 48, 48);
        }
    }

    /**
     * Draws the GUI of the game including the buttons, background and mana bar
     */
    public void drawGUI() {

        // draw brown background
        noStroke();
        fill(132, 115, 74);
        rect(0, 0, 760, 40);
        rect(640, 40, 120, 640);

        stroke(0,0,0);
        strokeWeight(2);

        checkButtonHover();
        drawButtons();
        drawWaveMana();


    }

    /**
     * Draws the buttons of the individual toggles
     */
    public void drawButtons() {
        stroke(0,0,0);
        strokeWeight(2);
        //draw toggle boxes
        boolean[] toggles = new boolean[] {fastForward, pause, buildShooter, buildSprayer,
            rangeUp, firingSpeedUp, damageUp, manaPoolSpell};
        
        String[] titles = new String[] {"FF", "P", "T", "Y", "U1", "U2", "U3", "M"};
        
        for (int i = 0; i < 8; i ++) {
            noFill();
            if (toggles[i]) {fill(255, 255, 8);}
            rect(650, 45 + (50 * i), 40, 40, 10);
        }

        strokeWeight(3);
        fill(0,0,0);
        textSize(25);
        for (int i = 0; i < titles.length; i ++) {

            text(titles[i], 655, 75 + (50 * i));
        }


        strokeWeight(3);
        fill(0,0,0);
        textSize(12);

        text("2x speed", 695, 60);
        text("PAUSE", 695, 110);
        text("Build", 695, 160);
        text("shooter", 695, 177);
        text("Build", 695, 210);
        text("sprayer", 695, 227);
        text("Upgrade", 695, 260);
        text("range", 695, 277);
        text("Upgrade", 695, 310);
        text("speed", 695, 327);
        text("Upgrade", 695, 360);
        text("damage", 695, 377);
        text("Mana pool", 695, 410);
        text("cost: " + (int) wizardMana.getSpellCost(), 695, 427);

    }

    /**
     * Draws the mana bar of the wizard and draws the timer until
     * the next wave starts
     */
    public void drawWaveMana() {
        stroke(0,0,0);
        strokeWeight(2);
        // Draw  mana bar background (white)

        fill(255, 255, 255);
        rect(380, 10, 350, 20, 10);

        // Draw percentile mana bar (light blue)
        double manaBarWidth = 350 * wizardMana.getMana() / wizardMana.getManaCap();
        fill(173, 216, 230);
        rect(380, 10, (float) (manaBarWidth), 20, 10);
      
        // Draw the frame of the mana bar (black)
        noFill();
        stroke(0);
        rect(380, 10, 350, 20, 10);

        // Display the current mana level on top of the bar
        textSize(20);
        fill(0);
        text("MANA: ", 305, 29);
        textSize(15);
        text((int) wizardMana.getMana() + " / " + (int) (wizardMana.getManaCap()), 510, 26);

        // Display time to next round
        textSize(25);
        fill(0);
        if (!(wavesList.indexOf(current) == wavesList.size() - 1)) {
            if (this.current != null) {
                double remainingTime = Math.ceil(this.nextStartCooldown / FPS) - 1;
                text("Wave " + (wavesList.indexOf(current) + 2) + " starts: " + (int) remainingTime, 13, 30);
            } else { // if it is before the first wave
                Wave firstWave = wavesList.get(0);
                if (wavesList.size() > 1) { // if there exists a second wave
                    Wave secondWave = wavesList.get(1);
                    double remainingTime = Math.ceil((firstWave.getDuration() + secondWave.getPause()) / FPS) - 1;
                    text("Wave 2 starts: " + (int) remainingTime, 13, 30);
                }
            }
        } 

    }

    /**
     * Draws the win text on the screen
     */
    public void drawWin() {
        strokeWeight(3);
        fill(0,0,0);
        textSize(45);
        text("YOU WIN", 260, 300);
    }

    /**
     * Draws the lose text on the screen
     */
    public void drawLose() {
        strokeWeight(3);
        fill(0,0,0);
        textSize(45);
        text("YOU LOST", 260, 300);

        textSize(30);
        text("Press 'r' to restart", 240, 340);
    }

    /**
     * Check if a tile already has a tower on it
     * 
     * @param col The column of the tile to check
     * @param row The row of the tile to check
     * @return A boolean which represents if the tile has a tower already 
     */
    public boolean getFull(int col, int row) {
            int[] location = {col, row};

            for (Tower tower: towerList) {
                if (Arrays.equals(tower.getCoords(),(location))) {
                    return true;
                }
            }
            return false;

    }

    public static void main(String[] args) {
        PApplet.main("WizardTD.App");

    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
