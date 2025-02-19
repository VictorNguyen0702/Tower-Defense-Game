package WizardTD;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonsterTest {
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
    public void testDamage1() {
        // test killing one enemy using one tower
        game.configPath = "src/test/java/WizardTD/configTest2.json";
        game.setup();
        game.createTower(5, 3, "shooter");

        for (int i = 0; i < 165; i ++) {
            game.draw();
        }
        assert game.monsterList.get(0).getHp() == 80;
    }

    @Test
    public void testKill1() {
        // test killing one enemy using one tower
        game.configPath = "src/test/java/WizardTD/configTest2.json";
        game.setup();
        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.createTower(5, 6, "shooter");

        for (int i = 0; i < 300; i ++) {
            game.draw();
        }
        assert game.wizardMana.getMana() == 59;
    }

    @Test
    public void testKill2() {
        // test fireballs deactive when target is dead
        game.configPath = "src/test/java/WizardTD/configTest2.json";
        game.setup();

        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.fastForward = true;
        game.createTower(5, 6, "shooter");
        for (int i = 0; i < 20; i ++) {
            game.towerList.get(0).increaseFiringSpeed();
        }
        
        for (int i = 0; i < 180; i ++) {
            game.draw();
        }
        
        boolean fireballActive = false;

        for (int i = 0; i < game.fireballList.size(); i ++) {
            if (game.fireballList.get(i).getActive()) {
                fireballActive = true;
            }
        }
        assert !fireballActive;

    }

    @Test
    public void testKill3() {
        // test killing one enemy using two towers
        game.configPath = "src/test/java/WizardTD/configTest2.json";
        game.setup();
        game.wizardMana.setMana(400);
        game.rangeUp = true;
        game.firingSpeedUp = true;
        game.damageUp = true;
        game.createTower(5, 4, "sprayer");
        game.createTower(3, 4, "sprayer");

        for (int i = 0; i < 300; i ++) {
            game.draw();
        }
        assert game.wizardMana.getMana() == 99;
    }

    @Test
    public void testBanish1() {
        // test banishing enemy when there is insufficient mana
        game.wizardMana.setMana(30);
        game.current = game.wavesList.get(0);
        for (int i = 0; i < 1500; i ++) {
            game.draw();
        }
        assert game.wizardMana.getMana() == 0;
        assert game.lose = true;

    }

    @Test
    public void testBanish2() {
        // test banishing enemy when there is excess mana
        game.configPath = "src/test/java/WizardTD/configTest2.json";
        game.setup();
        game.current = game.wavesList.get(0);
        for (int i = 0; i < 1500; i ++) {
            game.draw();
        }
        assert game.wizardMana.getMana() == 150;
    }
}
