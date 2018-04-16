package mizzilekommand.tests;

/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */

import mizzilekommand.layout.GamePlayScene;
import mizzilekommand.logics.GameLoop;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.Explosion;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaakkovilenius
 */
public class GameLoopTest {
    
    GameLoop gameloop;
    
    @Before
    public void setUp() {
        gameloop = new GameLoop();
    }
    
    @Test
    public void explosionCloseDestroysBase() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l);
        Base base = new Base();
        base.setLayoutX(20.0);
        base.setLayoutY(20.0);
        assertTrue(gameloop.didDestroyBase(explosion, base));
    }

    @Test
    public void explosionFarDestroysBaseNot() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l);
        Base base = new Base();
        base.setLayoutX(100.0);
        base.setLayoutY(20.0);
        assertFalse(gameloop.didDestroyBase(explosion, base));
    }
    

}
