package WizardTD;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManaTest {
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
    public void testRegen1() {
        // test that the mana regen has regened the right amount after 121 frames
        game.setup();
        game.gameRun = true;
        game.pause = false;
        game.wizardMana.setMana(200);
        for (int i = 0; i < 121; i ++) {
            game.wizardMana.decreaseCooling();
            if(game.gameRun && !game.pause && game.wizardMana.getCooling() <= 0){
                game.wizardMana.regenOneMana();
                game.wizardMana.resetCooling();
            }
        }
        assert ((int) game.wizardMana.getMana() == 205);    
    }

    @Test
    public void testRegen2() {
        // test that the mana regen does not continue after mana has capped
        game.gameRun = true;
        game.pause = false;
        game.wizardMana.setMana(game.wizardMana.getManaCap());
        for (int i = 0; i < 60; i ++) {
            game.wizardMana.decreaseCooling();
            if(game.gameRun && !game.pause && game.wizardMana.getCooling() <= 0){
                game.wizardMana.regenOneMana();
                game.wizardMana.resetCooling();
            }

        }
        assert (game.wizardMana.getMana() == 1000);    
    }
    @Test
    public void testManaPool1() {
        // test that the mana pool only activates with enough mana
        game.gameRun = true;
        game.pause = false;
        game.wizardMana.setMana(150);
        game.wizardMana.tryCreateManaPool();
        assert (game.wizardMana.getManaPoolCount() == 1);    
        game.wizardMana.tryCreateManaPool();
        assert (game.wizardMana.getMana() == 50);
        assert (game.wizardMana.getManaPoolCount() == 1);    
    }

    @Test
    public void testGainMana1() {
        game.wizardMana.setMana(game.wizardMana.getManaCap() - 5);
        game.wizardMana.gainMana(20);
        assert game.wizardMana.getMana() == game.wizardMana.getManaCap();
    }
}
