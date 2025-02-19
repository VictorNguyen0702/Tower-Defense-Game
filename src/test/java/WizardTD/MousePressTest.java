package WizardTD;


import processing.core.PApplet;
import processing.event.MouseEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MousePressTest {
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
    public void testMouseFastForward1() {
        // tests when the user clicks the fast forward button
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 685, 50, 1, 1);
        game.fastForward = false;
        game.mousePressed(testMouse);
        assert game.fastForward == true;
    
        game.mousePressed(testMouse);
        assert game.fastForward == false;
    }
    @Test
    public void testMousePause1() {
        // tests when the user clicks the pause button
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 685, 100, 1, 1);
        game.pause = false;
        game.mousePressed(testMouse);
        assert game.pause == true;
    
        game.mousePressed(testMouse);
        assert game.pause == false;
    }

    @Test
    public void testMouseBuildShooter1() {
        // tests when the user clicks the build shooter button if buildSprayer is false
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 674, 150, 1, 1);
        game.buildShooter = false;
        game.mousePressed(testMouse);
        assert game.buildShooter == true;
    
        game.mousePressed(testMouse);
        assert game.buildShooter == false;
    }

    @Test
    public void testMouseBuildShooter2() {
        // tests when the user clicks the build shooter button if buildSprayer is true
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 663, 150, 1, 1);
        game.buildShooter = false;
        game.buildSprayer = true;
        game.mousePressed(testMouse);
        assert game.buildShooter == true;
        assert game.buildSprayer == false;

        game.mousePressed(testMouse);
        assert game.buildShooter == false;
    }

    @Test
    public void testMouseSprayer1() {
        // tests when the user clicks the build sprayer button if buildShooter is false
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 655, 200, 1, 1);
        game.buildSprayer = false;
        game.mousePressed(testMouse);
        assert game.buildSprayer == true;

        game.mousePressed(testMouse);
        assert game.buildSprayer == false;
    }

    @Test
    public void testMouseSprayer2() {
        // tests when the user clicks the build sprayer button if buildShooter is true
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 660, 200, 1, 1);
        game.buildSprayer = false;
        game.buildShooter = true;
        game.mousePressed(testMouse);
        assert game.buildSprayer == true;
        assert game.buildShooter == false;

        game.mousePressed(testMouse);
        assert game.buildSprayer == false;
    }

    @Test
    public void testMouseUpgrade1() {
        // tests when the user clicks the upgrade 1 button
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 690, 250, 1, 1);
        game.rangeUp = false;
        game.mousePressed(testMouse);
        assert game.rangeUp == true;
    
        game.mousePressed(testMouse);
        assert game.rangeUp == false;
    }
    @Test
    public void testMouseUpgrade2() {
        // tests when the user clicks the upgrade 2 button
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 650, 300, 1, 1);
        game.firingSpeedUp = true;
        game.mousePressed(testMouse);
        assert game.firingSpeedUp == false;
    
        game.mousePressed(testMouse);
        assert game.firingSpeedUp == true;
    }

    @Test
    public void testMouseUpgrade3() {
        // tests when the user clicks the upgrade 3 button
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 650, 350, 1, 1);
        game.damageUp = true;
        game.mousePressed(testMouse);
        assert game.damageUp == false;
    
        game.mousePressed(testMouse);
        assert game.damageUp == true;
    }

    @Test
    public void testMouseManaPoolSpell1() {
        // Tests a successful manaPool spell
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 690, 400, 1, 1);
        game.manaPoolSpell = false;
        game.wizardMana.setMana(200);
        game.mousePressed(testMouse);
        assert game.manaPoolSpell == false; // manaPoolSpell is turned true then false soon after
        assert game.wizardMana.getManaPoolCount() == 1;
        assert game.wizardMana.getMana() == 100;
        assert game.wizardMana.getManaCap() == 1500;
    }

    @Test
    public void testMouseManaPoolSpell2() {
        // tests a failed manaPool spell due to insufficient mana
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 690, 400, 1, 1);
        game.manaPoolSpell = false;
        game.wizardMana.setMana(50);
        game.mousePressed(testMouse);
        assert game.manaPoolSpell == false; // manaPoolSpell is turned true then false soon after
        assert game.wizardMana.getManaPoolCount() == 0;
        assert game.wizardMana.getMana() == 50;
        assert game.wizardMana.getManaCap() == 1000;
    }

    @Test
    public void testMouseManaPoolSpell3() {
        // tests a successful manaPool spell then a failed manaPool spell due to insufficient mana
        game.setup();
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 690, 400, 1, 1);
        game.manaPoolSpell = false;
        game.wizardMana.setMana(150);
        game.mousePressed(testMouse);
        assert game.manaPoolSpell == false; // manaPoolSpell is turned true then false soon after
        assert game.wizardMana.getManaPoolCount() == 1;
        assert game.wizardMana.getMana() == 50;
        assert game.wizardMana.getManaCap() == 1500;

        game.mousePressed(testMouse);
        assert game.wizardMana.getManaPoolCount() == 1;
        assert game.wizardMana.getMana() == 50;
        assert game.wizardMana.getManaCap() == 1500;
    }

    @Test
    public void testMouseOutOfRange1() {
        // tests when the user clicks outside of any of the buttons or board
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 700, 400, 1, 1);
        game.mousePressed(testMouse);
        assert game.fastForward == false;
        assert game.pause == false;
        assert game.buildShooter == false;
        assert game.buildSprayer == false;
        assert game.rangeUp == false;
        assert game.firingSpeedUp == false;
        assert game.damageUp == false;
    }

    @Test
    public void testMouseOutOfRange2() {
        // tests when the user clicks outside of any of the buttons or board
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 200, 60, 1, 1);
        game.mousePressed(testMouse);
        testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 200, 1000, 1, 1);
        game.mousePressed(testMouse);
        testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, -50, 400, 1, 1);
        game.mousePressed(testMouse);
        testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 1000, 400, 1, 1);
        game.mousePressed(testMouse);
        assert game.fastForward == false;
        assert game.pause == false;
        assert game.buildShooter == false;
        assert game.buildSprayer == false;
        assert game.rangeUp == false;
        assert game.firingSpeedUp == false;
        assert game.damageUp == false;
    }

    @Test 
    public void testMouseCreateTower1() {
        // test creating one tower and upgrading that tower
        game.buildShooter = true;
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 200, 120, 1, 1);
        game.mousePressed(testMouse);
        assert game.towerList.size() == 1 && game.shooterList.size() == 1;
        assert game.towerList.get(0).getCoords()[0] == 6;
        assert game.towerList.get(0).getCoords()[1] == 2;

        game.rangeUp = true;
        game.damageUp = true;
        game.mousePressed(testMouse);
        assert game.towerList.get(0).getRangeLvl() == 1 && game.towerList.get(0).getDamageLvl() == 1;
    }

    @Test 
    public void testMouseCreateTower2() {
        // test creating two towers and upgrading the second tower
        game.wizardMana.setMana(300);
        game.buildShooter = true;
        MouseEvent testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 200, 120, 1, 1);
        game.mousePressed(testMouse);

        game.buildShooter = false;
        game.buildSprayer = true;
        testMouse = new MouseEvent(game, 1, MouseEvent.PRESS, 0, 70, 45, 1, 1);
        game.mousePressed(testMouse);
        assert game.towerList.size() == 2;
        assert game.shooterList.size() == 1 && game.sprayerList.size() == 1;

        game.rangeUp = true;
        game.damageUp = true;
        game.mousePressed(testMouse);
        assert game.towerList.get(1).getRangeLvl() == 1 && game.towerList.get(1).getDamageLvl() == 1;

    }
}
