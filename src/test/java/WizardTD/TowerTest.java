package WizardTD;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TowerTest {
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
    public void testCreateTower1() {
        // test that creating a shooter adds it to the right list and tests if damage upgrades
        game.damageUp = true;
        game.wizardMana.setMana(400);
        game.createTower(2, 0, "shooter");
        assert (game.towerList.size() == 1 && game.shooterList.size() == 1 && game.sprayerList.size() == 0);
        assert (game.towerList.get(0).getDamageLvl() == 1);


    }
    
    @Test
    public void testCreateTower2() {
        // test that creating a sprayer adds it to the right list and tests if range upgrades
        game.rangeUp = true;
        game.wizardMana.setMana(400);
        game.createTower(2, 0, "sprayer");
        assert (game.towerList.size() == 1 && game.shooterList.size() == 0 && game.sprayerList.size() == 1);
        assert (game.towerList.get(0).getRangeLvl() == 1);
    }

    @Test
    public void testCreateTower3() {
        // test that creating a tower on a shrub does not create a tower or add it to the lists
        game.rangeUp = true;
        game.createTower(1, 10, "sprayer");
        assert (game.towerList.size() == 0 && game.shooterList.size() == 0 && game.sprayerList.size() == 0);
    }

    @Test
    public void testCreateTower4() {
        // test that creating a tower without enough mana does not make a tower
        game.wizardMana.setMana(5);
        game.createTower(2, 0, "sprayer");
        assert (game.towerList.size() == 0 && game.shooterList.size() == 0 && game.sprayerList.size() == 0);
        assert game.wizardMana.getMana() == 5;
    }

    @Test
    public void testCreateTower5() {
        // test that creating a tower without enough mana for upgrades only does the ones with enough mana for
        game.wizardMana.setMana(145);
        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.createTower(2, 0, "sprayer");
        assert game.wizardMana.getMana() == 5;
        assert game.towerList.get(0).getRangeLvl() == 1;
        assert game.towerList.get(0).getFiringSpeedLvl() == 1;
        assert game.towerList.get(0).getDamageLvl() == 0;
    }

    @Test
    public void testCreateTower6() {
        // test that creating a tower with invalid name does not make a tower
        game.wizardMana.setMana(5);
        game.createTower(2, 0, "splasher");
        assert (game.towerList.size() == 0 && game.shooterList.size() == 0 && game.sprayerList.size() == 0);
    
    }

    @Test
    public void testSetImg1() {
        //tests that the images set are correct throughout the upgrades
        game.wizardMana.setMana(400);
        game.createTower(2, 0, "shooter");
        assert (game.towerList.get(0).getImg().equals("shooter0"));
        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.upgradeTower(game.towerList.get(0));
        assert (game.towerList.get(0).getImg().equals("shooter1"));
        game.upgradeTower(game.towerList.get(0));
        assert (game.towerList.get(0).getImg().equals("shooter2"));


    }

    @Test
    public void testSetImg2() {
        // tests that the image is correct for each type of tower and updates when the level updates
        game.wizardMana.setMana(400);
        game.createTower(2, 0, "shooter");
        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.createTower(7, 2, "sprayer");
        assert (game.towerList.get(0).getImg().equals("shooter0"));
        assert (game.towerList.get(1).getImg().equals("sprayer1"));
        game.upgradeTower(game.towerList.get(1));
        assert (game.towerList.get(1).getImg().equals("sprayer2"));

    }

    @Test
    public void testConstructor() {
        // tests that default values are used if there are none in config
        game.configPath = "src/test/java/WizardTD/configTest1.json";
        game.setup();
        game.createTower(2, 0, "sprayer");
        assert game.towerList.get(0).getRange() == 64;
        assert game.towerList.get(0).getFiringSpeed() == 1.5;
        assert game.towerList.get(0).getDamage() == 20;
    }
    
    @Test
    public void testTryShoot1() {
        // tests that range updates properly when updated and can shoot targets within range
        game.wizardMana.setMana(500);
        game.createTower(3, 1, "shooter");

        assert game.towerList.get(0).getRange() == 96;
        for (int i = 0; i < 5; i ++) {
            game.towerList.get(0).increaseRange();
        }
        assert game.towerList.get(0).getRange() == 256;

        for (int i = 0; i < 60; i ++) {
            game.draw();
        }
        assert game.ringList.size() == 0;
        assert game.fireballList.size() == 1;

    }

    public void testTryShoot2() {
        // tests that range updates properly when updated and can shoot targets within range
        game.wizardMana.setMana(500);
        game.createTower(3, 1, "sprayer");

        assert game.towerList.get(0).getRange() == 64;
        for (int i = 0; i < 5; i ++) {
            game.towerList.get(0).increaseRange();
        }
        assert game.towerList.get(0).getRange() == 184;

        for (int i = 0; i < 60; i ++) {
            game.draw();
        }
        assert game.ringList.size() == 1;
        assert game.fireballList.size() == 0;

    }

}
