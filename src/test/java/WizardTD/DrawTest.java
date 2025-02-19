package WizardTD;


import processing.core.PApplet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DrawTest {
    App game;

    @BeforeEach
    public void setUp() {
        // Initialize an instance of App
        game = new App();
        game.noLoop();
        PApplet.runSketch(new String[] {"App"}, game);
        game.setup();
        game.delay(150);

    }

    @Test
    public void testDrawLayout1() {
        game.configPath = "src/test/java/WizardTD/configTest2.json";
        game.setup();
        game.draw();
    }

    @Test
    public void testDrawLayout2() {
        game.draw();
    }

    @Test
    public void testDrawLayout3() {
        game.configPath = "src/test/java/WizardTD/configTest1.json";
        game.setup();
        game.draw();    
    }

    @Test
    public void testDrawLayout4() {
        game.configPath = "src/test/java/WizardTD/configTest4.json";
        game.setup();
        game.draw();    
    }

    @Test
    public void testDrawTower1() {
        game.createTower(2, 0, "shooter");
        Tower towerUpgraded = game.towerList.get(0);
        for (int i = 0; i < 3; i ++) {
            towerUpgraded.increaseDamage();
            towerUpgraded.increaseFiringSpeed();
            towerUpgraded.increaseRange();
        }
        game.drawTower(towerUpgraded);
    }

    @Test
    public void testTowerRangeHover1() {
        game.createTower(2, 0, "sprayer");

        game.mouseX = 70;
        game.mouseY = 45;
        game.checkTowerRangeHover(); 
    }

    @Test
    public void testTowerUpgradeHover1() {
        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.createTower(2, 0, "sprayer");

        game.mouseX = 70;
        game.mouseY = 45;
        game.checkTowerUpgradeHover(); 
    }

    @Test
    public void testTowerUpgradeHover2() {
        game.rangeUp = false;
        game.firingSpeedUp = false;
        game.damageUp = false;
        game.createTower(2, 0, "sprayer");

        game.mouseX = 70;
        game.mouseY = 45;
        game.checkTowerUpgradeHover(); 
    }

    @Test
    public void testButtonHover1() {

        game.mouseX = 500;
        game.mouseY = 90;
        game.checkButtonHover(); 
    }

    @Test
    public void testButtonHover2() {

        game.mouseX = 675;
        game.mouseY = 80;
        game.checkButtonHover(); 
    }

    @Test
    public void testButtonHover3() {

        game.mouseX = 675;
        game.mouseY = 35;
        game.checkButtonHover(); 
    }

    @Test
    public void testPauseDraw1() {

        game.pause = true;
        game.gameRun = true;
        game.draw();
        game.pause = false;
        game.gameRun = true;
        game.draw();
        game.pause = true;
        game.gameRun = false;
        game.draw();
        game.pause = false;
        game.gameRun = false;
        game.draw();
    }
}
