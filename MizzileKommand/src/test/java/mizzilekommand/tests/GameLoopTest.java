package mizzilekommand.tests;

/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */

import mizzilekommand.layout.GamePlayScene;
import mizzilekommand.logics.GameLoop;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.EnemyMissile;
import mizzilekommand.nodes.EnemyMissileExplosion;
import mizzilekommand.nodes.Explosion;
import mizzilekommand.nodes.PlayerMissile;
import mizzilekommand.nodes.PlayerMissileExplosion;
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
    
    @Test
    public void statusResetWorks() {
        int level = gameloop.gameStatus.level;
        level = gameloop.levelUp();
        level = gameloop.levelUp();
        gameloop.resetGameStatus();
        assertEquals(1, gameloop.gameStatus.level);
    }

    @Test
    public void playerMissileLifeCycleWorks() {
        gameloop.launchNewPlayerMissile(0, 300.0, 300.0);
        assertEquals(1, gameloop.playerMissiles.size());
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
        PlayerMissile missile = (PlayerMissile)gameloop.playerMissiles.get(0);
        missile.setLayoutX(300.0);
        missile.setLayoutY(300.0);
        gameloop.playerMissiles.set(0, missile);
        gameloop.handlePlayerMissiles();
        assertEquals(1, gameloop.playerMissilesToRemove.size());
        assertEquals(0, gameloop.playerMissiles.size());
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
        assertEquals(1, gameloop.playerExplosions.size());
        gameloop.handlePlayerMissiles();
        assertEquals(0, gameloop.playerMissilesToRemove.size());
        assertEquals(0, gameloop.playerMissiles.size());
        PlayerMissileExplosion explosion = (PlayerMissileExplosion)gameloop.playerExplosions.get(0);
        explosion.burnUntil = System.currentTimeMillis();
        gameloop.playerExplosions.set(0, explosion);
        gameloop.handlePlayerMissileExplosions();
        assertEquals(1, gameloop.removeFromScene.size());
        assertEquals(1, gameloop.playerExplosionsToRemove.size());
        assertEquals(0, gameloop.playerExplosions.size());
        gameloop.handlePlayerMissileExplosions();
        assertEquals(0, gameloop.playerExplosionsToRemove.size());
        assertEquals(0, gameloop.playerExplosions.size());
    }

    @Test
    public void enemyMissileLifeCycleWorks() {
        gameloop.incoming();
        assertEquals(1, gameloop.enemyMissiles.size());
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
        EnemyMissile missile = (EnemyMissile)gameloop.enemyMissiles.get(0);
        missile.setLayoutX(300.0);
        missile.setLayoutY(480.0);
        gameloop.enemyMissiles.set(0, missile);
        gameloop.handleEnemyMissiles();
        assertEquals(1, gameloop.enemyMissilesToRemove.size());
        assertEquals(0, gameloop.enemyMissiles.size());
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
        assertEquals(1, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissiles();
        assertEquals(0, gameloop.enemyMissilesToRemove.size());
        assertEquals(0, gameloop.enemyMissiles.size());
        EnemyMissileExplosion explosion = (EnemyMissileExplosion)gameloop.enemyExplosions.get(0);
        explosion.burnUntil = System.currentTimeMillis();
        gameloop.enemyExplosions.set(0, explosion);
        gameloop.handleEnemyMissileExplosions();
        assertEquals(1, gameloop.removeFromScene.size());
        assertEquals(1, gameloop.enemyExplosionsToRemove.size());
        assertEquals(0, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissileExplosions();
        assertEquals(0, gameloop.enemyExplosionsToRemove.size());
        assertEquals(0, gameloop.enemyExplosions.size());
    }
    

}
