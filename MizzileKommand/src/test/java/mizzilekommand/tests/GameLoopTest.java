package mizzilekommand.tests;

/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import mizzilekommand.layout.GamePlayScene;
import mizzilekommand.logics.GameLoop;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import mizzilekommand.logics.SceneController;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.BaseExplosion;
import mizzilekommand.nodes.City;
import mizzilekommand.nodes.CityDestruction;
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
    SceneController controller;

    @Before
    public void setUp() {
        gameloop = new GameLoop();
        // Spoof the SceneController so that tests can be run
        controller = new SceneController(null);
        controller.setGameLoop(gameloop);
        gameloop.setSceneController(controller);

    }

    public void testAddCities(int n) {
        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i < n) {
                cities.add(new City(i));
                gameloop.gameStatus.cityOk[i] = true;
            } else {
                gameloop.gameStatus.cityOk[i] = false;
            }
        }
        gameloop.cities = cities;
        
    }

    public void testAddBases(int n) {
        ArrayList<Base> bases = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i < n) {
                bases.add(new Base());
                gameloop.gameStatus.baseOk[i] = true;
            } else {
                gameloop.gameStatus.baseOk[i] = false;
            }
        }
        gameloop.bases = bases;
    }
    @Test
    public void gameLoopPreparationAddsBasesAndCities() {
        controller.currentScene = new GamePlayScene(controller, gameloop.gameStatus);
        gameloop.prepareForGameLoop();
        assertEquals(3, gameloop.gameStatus.basesLeft());
        assertEquals(gameloop.gameStatus.basesLeft(), gameloop.bases.size());
        assertEquals(6, gameloop.gameStatus.citiesLeft());
        assertEquals(gameloop.gameStatus.citiesLeft(), gameloop.cities.size());
    }

    @Test
    public void explosionCloseDestroysBase() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l, "");
        Base base = new Base();
        base.setLayoutX(20.0);
        base.setLayoutY(20.0);
        assertTrue(gameloop.didDestroyBase(explosion, base));
    }

    @Test
    public void explosionFarDestroysBaseNot() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l, "");
        Base base = new Base();
        base.setLayoutX(100.0);
        base.setLayoutY(20.0);
        assertFalse(gameloop.didDestroyBase(explosion, base));
    }

    @Test
    public void explosionCloseDestroysCity() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l, "");
        City city = new City(0);
        city.setLayoutX(20.0);
        city.setLayoutY(20.0);
        assertTrue(gameloop.didDestroyCity(explosion, city));
    }

    @Test
    public void explosionFarDestroysCityNot() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l, "");
        City city = new City(0);
        city.setLayoutX(100.0);
        city.setLayoutY(20.0);
        assertFalse(gameloop.didDestroyCity(explosion, city));
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
    public void checkActionsDoneForAllActions() {
        testAddCities(6);
        gameloop.destructCity(gameloop.cities.get(0));
        assertFalse(gameloop.actionsDone());
        gameloop.cityDestructions.clear();
        testAddBases(3);
        gameloop.explodeBase(gameloop.bases.get(0));
        assertFalse(gameloop.actionsDone());
        gameloop.baseExplosions.clear();
        gameloop.controller.keyDown(KeyCode.DIGIT3, 10.0, 10.0);
        assertFalse(gameloop.actionsDone());
        gameloop.playerExplosions.add(gameloop.playerMissiles.get(0).detonate());
        gameloop.playerMissiles.clear();
        assertFalse(gameloop.actionsDone());
        gameloop.playerExplosions.clear();
        gameloop.incoming();
        assertFalse(gameloop.actionsDone());
        gameloop.enemyExplosions.add(gameloop.enemyMissiles.get(0).detonate());
        gameloop.enemyMissiles.clear();
        assertFalse(gameloop.actionsDone());
        gameloop.enemyExplosions.clear();
        assertTrue(gameloop.actionsDone());
    }
    
    @Test
    public void noMoreIncomingIfLevelIsOver() {
        // Check that no more missiles if all cities are destroyed
        gameloop.resetGameStatus();
        testAddCities(6);
        gameloop.allowIncoming = true;
        gameloop.destructCity(gameloop.cities.get(0));
        assertTrue(gameloop.allowIncoming);
        gameloop.destructCity(gameloop.cities.get(1));
        gameloop.destructCity(gameloop.cities.get(2));
        gameloop.destructCity(gameloop.cities.get(3));
        gameloop.destructCity(gameloop.cities.get(4));
        gameloop.destructCity(gameloop.cities.get(5));
        gameloop.cityDestructions.clear();
        gameloop.cities.clear();
        gameloop.checkLevelStatus();
        assertFalse(gameloop.allowIncoming);
        // Check that no more missiles if 3 cities are destroyed in level
        gameloop.resetGameStatus();
        testAddCities(6);
        gameloop.allowIncoming = true;
        gameloop.destructCity(gameloop.cities.get(0));
        gameloop.checkLevelStatus();
        assertTrue(gameloop.allowIncoming);
        gameloop.destructCity(gameloop.cities.get(1));
        gameloop.destructCity(gameloop.cities.get(2));
        gameloop.cityDestructions.clear();
        gameloop.checkLevelStatus();
        assertFalse(gameloop.allowIncoming);
        // Check that no more missiles if all incoming for the level have been deployed
        gameloop.resetGameStatus();
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingTotal = 3;
        gameloop.gameStatus.incomingLeft = 3;
        gameloop.gameStatus.incomingMissilesDecrease();
        gameloop.checkLevelStatus();
        assertTrue(gameloop.allowIncoming);
        gameloop.gameStatus.incomingMissilesDecrease();
        gameloop.gameStatus.incomingMissilesDecrease();
        gameloop.checkLevelStatus();
        assertFalse(gameloop.allowIncoming);
    }

    @Test
    public void cannotShootIfNoMissilesLeft() {
        gameloop.gameStatus.missilesLeft[0] = 0;
        controller.keyDown(KeyCode.DIGIT1, 300.0, 300.0);
        assertEquals(0, gameloop.playerMissiles.size());
        gameloop.gameStatus.missilesLeft[1] = 0;
        controller.keyDown(KeyCode.DIGIT2, 300.0, 300.0);
        assertEquals(0, gameloop.playerMissiles.size());
        gameloop.gameStatus.missilesLeft[2] = 0;
        controller.keyDown(KeyCode.DIGIT3, 300.0, 300.0);
        assertEquals(0, gameloop.playerMissiles.size());
    }
    
    @Test
    public void playerMissileLifeCycleWorks() {
        controller.keyDown(KeyCode.DIGIT1, 300.0, 300.0);
        assertEquals(1, gameloop.playerMissiles.size());
        PlayerMissile missile = (PlayerMissile) gameloop.playerMissiles.get(0);
        missile.setLayoutX(400.0);
        missile.setLayoutY(400.0);
        gameloop.playerMissiles.set(0, missile);
        // Has not yet reached target height
        gameloop.handlePlayerMissiles();
        assertEquals(0, gameloop.playerMissilesToRemove.size());
        assertEquals(1, gameloop.playerMissiles.size());
        missile.setLayoutX(300.0);
        missile.setLayoutY(300.0);
        // Has reached target height
        gameloop.handlePlayerMissiles();
        assertEquals(1, gameloop.playerMissilesToRemove.size());
        assertEquals(0, gameloop.playerMissiles.size());
        assertEquals(1, gameloop.playerExplosions.size());
        gameloop.handlePlayerMissiles();
        assertEquals(0, gameloop.playerMissilesToRemove.size());
        assertEquals(0, gameloop.playerMissiles.size());
        PlayerMissileExplosion explosion = (PlayerMissileExplosion) gameloop.playerExplosions.get(0);
        explosion.burnUntil = System.currentTimeMillis();
        gameloop.playerExplosions.set(0, explosion);
        gameloop.handlePlayerMissileExplosions();
        assertEquals(1, gameloop.playerExplosionsToRemove.size());
        assertEquals(0, gameloop.playerExplosions.size());
        gameloop.handlePlayerMissileExplosions();
        assertEquals(0, gameloop.playerExplosionsToRemove.size());
        assertEquals(0, gameloop.playerExplosions.size());
        assertTrue(gameloop.actionsDone());
    }

    @Test
    public void playerMissileExplosionDestroysEnemyMissile() {
        controller.keyDown(KeyCode.DIGIT2, 300.0, 300.0);
        PlayerMissile playerMissile = (PlayerMissile) gameloop.playerMissiles.get(0);
        playerMissile.setLayoutX(300.0);
        playerMissile.setLayoutY(300.0);
        gameloop.playerMissiles.set(0, playerMissile);
        gameloop.handlePlayerMissiles();
        gameloop.handlePlayerMissiles();
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.enemyMissiles.add(
                new EnemyMissile(System.currentTimeMillis(), 1.0, 0.0, 620.0, APP_HEIGHT * 0.8));
        EnemyMissile enemyMissile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        enemyMissile.setLayoutX(310.0);
        enemyMissile.setLayoutY(310.0);
        gameloop.enemyMissiles.set(0, enemyMissile);
        gameloop.handleEnemyMissiles();
        gameloop.handlePlayerMissileExplosions();
        gameloop.detonateMissile(enemyMissile);
        //gameloop.gameStatus.enemyMissileDestroyed();
        assertEquals(5, gameloop.gameStatus.score);
    }

    @Test
    public void incoming4MissilesWorks() {
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.gameStatus.incomingLeft = 5;
        gameloop.handleNewEnemyMissiles();
        gameloop.handleNewEnemyMissiles();
        gameloop.handleNewEnemyMissiles();
        gameloop.handleNewEnemyMissiles();
        assertEquals(4, gameloop.enemyMissiles.size());

    }

    @Test
    public void enemyMissileLifeCycleWorks() {
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.enemyMissiles.add(
                new EnemyMissile(System.currentTimeMillis(), 1.0, 0.0, 620.0, APP_HEIGHT * 0.8));
        assertEquals(1, gameloop.enemyMissiles.size());
        EnemyMissile missile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        missile.setLayoutX(300.0);
        missile.setLayoutY(200.0);
        gameloop.enemyMissiles.set(0, missile);
        // Has not yet reached target height
        gameloop.handleEnemyMissiles();
        assertEquals(0, gameloop.enemyMissilesToRemove.size());
        assertEquals(1, gameloop.enemyMissiles.size());
        missile.setLayoutX(300.0);
        missile.setLayoutY(480.0);
        // Has reached target height
        gameloop.handleEnemyMissiles();
        assertEquals(1, gameloop.enemyMissilesToRemove.size());
        assertEquals(0, gameloop.enemyMissiles.size());
        assertEquals(1, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissiles();
        assertEquals(0, gameloop.enemyMissilesToRemove.size());
        assertEquals(0, gameloop.enemyMissiles.size());
        EnemyMissileExplosion explosion = (EnemyMissileExplosion) gameloop.enemyExplosions.get(0);
        explosion.burnUntil = System.currentTimeMillis();
        gameloop.enemyExplosions.set(0, explosion);
        gameloop.handleEnemyMissileExplosions();
        assertEquals(1, gameloop.enemyExplosionsToRemove.size());
        assertEquals(0, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissileExplosions();
        assertEquals(0, gameloop.enemyExplosionsToRemove.size());
        assertEquals(0, gameloop.enemyExplosions.size());
        assertTrue(gameloop.actionsDone());
    }

    @Test
    public void enemyMissileExplosionDestroysCity() {
        gameloop.allowIncoming = true;
        gameloop.gameStatus.reset();
        gameloop.cityDestructions.clear();
        testAddCities(6);
        City city = gameloop.cities.get(0);
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.enemyMissiles.add(
                new EnemyMissile(System.currentTimeMillis(), 1.0, 0.0, city.getLayoutX(), city.getLayoutY()));
        EnemyMissile missile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        missile.setLayoutX(city.getLayoutX());
        missile.setLayoutY(city.getLayoutY());
        gameloop.gameStatus.incomingPace = 0.0;
        // Has reached target
        gameloop.handleEnemyMissiles();
        gameloop.handleEnemyMissileExplosions();
        assertTrue(gameloop.didDestroyCity(gameloop.enemyExplosions.get(0), city));
        gameloop.handleCityDestructions();
        gameloop.handleCities();
        assertEquals(5, gameloop.gameStatus.citiesLeft());
    }

    @Test
    public void enemyMissileExplosionDestroysBase() {
        gameloop.allowIncoming = true;
        gameloop.gameStatus.reset();
        gameloop.baseExplosions.clear();
        testAddBases(3);
        Base base = gameloop.bases.get(0);
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.enemyMissiles.add(
                new EnemyMissile(System.currentTimeMillis(), 1.0, 0.0, base.getLayoutX() - 30.0, base.getLayoutY()));
        EnemyMissile missile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        missile.setLayoutX(base.getLayoutX() - 30.0);
        missile.setLayoutY(base.getLayoutY());
        gameloop.gameStatus.incomingPace = 0.0;
        // Has reached target
        gameloop.handleEnemyMissiles();
        gameloop.handleEnemyMissileExplosions();
        assertTrue(gameloop.didDestroyBase(gameloop.enemyExplosions.get(0), base));
        gameloop.handleBaseExplosions();
        gameloop.handleBases();
        assertEquals(2, gameloop.gameStatus.basesLeft());
    }

    @Test
    public void baseLifeCycleWorks() {
        gameloop.gameStatus.reset();
        testAddBases(3);
        //gameloop.addBases();
        assertEquals(gameloop.gameStatus.basesLeft(), gameloop.bases.size());
        assertTrue(gameloop.gameStatus.baseNotDestroyed(0));
        assertTrue(gameloop.gameStatus.baseNotDestroyed(1));
        assertTrue(gameloop.gameStatus.baseNotDestroyed(2));
        gameloop.basesToRemove.add(gameloop.bases.get(0));
        gameloop.gameStatus.destroyBase(0);
        assertFalse(gameloop.gameStatus.baseNotDestroyed(0));
        assertEquals(3, gameloop.bases.size());
        gameloop.handleBaseExplosions();
        assertEquals(2, gameloop.bases.size());
        gameloop.handleBases();
        assertEquals(0, gameloop.basesToRemove.size());
        assertTrue(gameloop.actionsDone());
    }

    @Test
    public void cityLifeCycleWorks() {
        gameloop.gameStatus.reset();
        testAddCities(6);
//        gameloop.addCities();
        assertEquals(gameloop.gameStatus.citiesLeft(), gameloop.cities.size());
        assertTrue(gameloop.gameStatus.cityNotDestroyed(0));
        assertTrue(gameloop.gameStatus.cityNotDestroyed(1));
        assertTrue(gameloop.gameStatus.cityNotDestroyed(2));
        assertTrue(gameloop.gameStatus.cityNotDestroyed(3));
        assertTrue(gameloop.gameStatus.cityNotDestroyed(4));
        assertTrue(gameloop.gameStatus.cityNotDestroyed(5));
        gameloop.destructCity(gameloop.cities.get(0));
        assertFalse(gameloop.gameStatus.cityNotDestroyed(0));
        assertEquals(6, gameloop.cities.size());
        gameloop.handleCityDestructions();
        assertEquals(5, gameloop.cities.size());
        gameloop.handleCities();
        assertEquals(0, gameloop.citiesToRemove.size());
        gameloop.cityDestructions.clear();
        assertTrue(gameloop.actionsDone());
    }

    @Test
    public void allNodeListsAreIncludedInClearAll() {
        gameloop.playerMissiles.add(new PlayerMissile(1, 0.0, 0.0, 0.0));
        gameloop.playerExplosions.add(new PlayerMissileExplosion(0.0, 0.0, 0.0, 1));
        gameloop.enemyMissiles.add(new EnemyMissile(1, 0.0, 0.0, 0.0, 0.0));
        gameloop.enemyExplosions.add(new EnemyMissileExplosion(0.0, 0.0, 1));
        gameloop.baseExplosions.add(new BaseExplosion(0.0, 0.0, 0.0, 1));
        gameloop.cityDestructions.add(new CityDestruction(0.0, 0.0, 0.0, 1, 1));
        gameloop.clearNodes();
        assertTrue(gameloop.playerMissiles.isEmpty());
        assertTrue(gameloop.playerExplosions.isEmpty());
        assertTrue(gameloop.enemyMissiles.isEmpty());
        assertTrue(gameloop.enemyExplosions.isEmpty());
        assertTrue(gameloop.baseExplosions.isEmpty());
        assertTrue(gameloop.cityDestructions.isEmpty());
    }

}
