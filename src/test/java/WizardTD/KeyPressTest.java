package WizardTD;


import processing.core.PApplet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class KeyPressTest {
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
        // tests when the user clicks the fast forward key
        game.key = 'f';
        game.fastForward = false;
        game.keyPressed();
        assert game.fastForward == true;
    
        game.keyPressed();
        assert game.fastForward == false;
    }
    @Test
    public void testMousePause1() {
        // tests when the user clicks the pause key
        game.key = 'p';
        game.pause = false;
        game.keyPressed();
        assert game.pause == true;
    
        game.keyPressed();
        assert game.pause == false;
    }

    @Test
    public void testMouseBuildShooter1() {
        // tests when the user clicks the build shooter key if buildSprayer is false
        game.key = 't';
        game.buildShooter = false;
        game.keyPressed();
        assert game.buildShooter == true;
    
        game.keyPressed();
        assert game.buildShooter == false;
    }

    @Test
    public void testMouseBuildShooter2() {
        // tests when the user clicks the build shooter key if buildSprayer is true
        game.key = 't';
        game.buildShooter = false;
        game.buildSprayer = true;
        game.keyPressed();
        assert game.buildShooter == true;
        assert game.buildSprayer == false;

        game.keyPressed();
        assert game.buildShooter == false;
    }

    @Test
    public void testMouseSprayer1() {
        // tests when the user clicks the build sprayer key if buildShooter is false
        game.key = 'y';
        game.buildSprayer = false;
        game.keyPressed();
        assert game.buildSprayer == true;

        game.keyPressed();
        assert game.buildSprayer == false;
    }

    @Test
    public void testMouseSprayer2() {
        // tests when the user clicks the build sprayer key if buildShooter is true
        game.key = 'y';
        game.buildSprayer = false;
        game.buildShooter = true;
        game.keyPressed();
        assert game.buildSprayer == true;
        assert game.buildShooter == false;

        game.keyPressed();
        assert game.buildSprayer == false;
    }

    @Test
    public void testMouseUpgrade1() {
        // tests when the user clicks the upgrade 1 key
        game.key = '1';
        game.rangeUp = false;
        game.keyPressed();
        assert game.rangeUp == true;
    
        game.keyPressed();
        assert game.rangeUp == false;
    }
    @Test
    public void testMouseUpgrade2() {
        // tests when the user clicks the upgrade 2 key
        game.key = '2';
        game.firingSpeedUp = true;
        game.keyPressed();
        assert game.firingSpeedUp == false;
    
        game.keyPressed();
        assert game.firingSpeedUp == true;
    }

    @Test
    public void testMouseUpgrade3() {
        // tests when the user clicks the upgrade 3 key
        game.key = '3';
        game.damageUp = true;
        game.keyPressed();
        assert game.damageUp == false;
    
        game.keyPressed();
        assert game.damageUp == true;
    }

    @Test
    public void testMouseManaPoolSpell1() {
        // Tests a successful manaPool spell
        game.setup();
        game.key = 'm';
        game.manaPoolSpell = false;
        game.wizardMana.setMana(200);
        game.keyPressed();
        assert game.manaPoolSpell == false; // manaPoolSpell is turned true then false soon after
        assert game.wizardMana.getManaPoolCount() == 1;
        assert game.wizardMana.getMana() == 100;
        assert game.wizardMana.getManaCap() == 1500;
    }

    @Test
    public void testMouseManaPoolSpell2() {
        // tests a failed manaPool spell due to insufficient mana
        game.setup();
        game.key = 'm';
        game.manaPoolSpell = false;
        game.wizardMana.setMana(50);
        game.keyPressed();
        assert game.manaPoolSpell == false; // manaPoolSpell is turned true then false soon after
        assert game.wizardMana.getManaPoolCount() == 0;
        assert game.wizardMana.getMana() == 50;
        assert game.wizardMana.getManaCap() == 1000;
    }

    @Test
    public void testMouseManaPoolSpell3() {
        // tests a successful manaPool spell then a failed manaPool spell due to insufficient mana
        game.setup();
        game.key = 'm';
        game.manaPoolSpell = false;
        game.wizardMana.setMana(150);
        game.keyPressed();
        assert game.manaPoolSpell == false; // manaPoolSpell is turned true then false soon after
        assert game.wizardMana.getManaPoolCount() == 1;
        assert game.wizardMana.getMana() == 50;
        assert game.wizardMana.getManaCap() == 1500;

        game.keyPressed();
        assert game.wizardMana.getManaPoolCount() == 1;
        assert game.wizardMana.getMana() == 50;
        assert game.wizardMana.getManaCap() == 1500;
    }

   @Test
    public void testRestart1() {
        // tests when the user clicks the upgrade 1 key
        game.key = 'r';
        game.gameRun = false;
        game.keyPressed();
        assert game.gameRun == true;
    }

   @Test
    public void testRestart2() {
        // tests when the user clicks the upgrade 1 key
        game.key = 'r';
        game.gameRun = true;
        game.keyPressed();
        assert game.gameRun == true;
    }
}
