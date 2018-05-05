package mizzilekommand.tests;

/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
import java.util.ArrayList;
import mizzilekommand.layout.GamePlayScene;
import mizzilekommand.logics.GameLoop;
import static mizzilekommand.logics.MizzileKommand.BASE_X;
import static mizzilekommand.logics.MizzileKommand.BASE_Y;
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

    @Before
    public void setUp() {
        gameloop = new GameLoop();
    }
    
    public void testAddCities() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(0));
        cities.add(new City(1));
        cities.add(new City(2));
        cities.add(new City(3));
        cities.add(new City(4));
        cities.add(new City(5));
        gameloop.cities = cities;
    }

    public void testAddBases() {
        ArrayList<Base> bases = new ArrayList<>();
        bases.add(new Base());
        bases.add(new Base());
        bases.add(new Base());
        gameloop.bases = bases;
    }

    /*
    @Test
    public void gameLoopPreparationAddsBasesAndCities() {
        gameloop.prepareForGameLoop();
        assertEquals(3, gameloop.gameStatus.basesLeft());
        assertEquals(gameloop.gameStatus.basesLeft(), gameloop.bases.size());
        assertEquals(6, gameloop.gameStatus.citiesLeft());
        assertEquals(gameloop.gameStatus.citiesLeft(), gameloop.cities.size());
    }
    */
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
    public void explosionCloseDestroysCity() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l);
        City city = new City(0);
        city.setLayoutX(20.0);
        city.setLayoutY(20.0);
        assertTrue(gameloop.didDestroyCity(explosion, city));
    }

    @Test
    public void explosionFarDestroysCityNot() {
        Explosion explosion = new Explosion(0.0, 0.0, 40.0, 1000l, 1000l);
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
    public void noMoreIncomingIfLevelIsOver() {
        // Check that no more missiles if all cities are destroyed
        gameloop.resetGameStatus();
        gameloop.cities.clear();
        gameloop.checkLevelStatus();
        assertFalse(gameloop.allowIncoming);
        // Check that no more missiles if 3 cities are destroyed in level
        gameloop.resetGameStatus();
        testAddCities();
//        ArrayList<City> cities = new ArrayList<>();
//        cities.add(new City(0));
//        cities.add(new City(1));
//        cities.add(new City(2));
//        gameloop.cities = cities;
        gameloop.destructCity(gameloop.cities.get(0));
        gameloop.destructCity(gameloop.cities.get(1));
        gameloop.destructCity(gameloop.cities.get(2));
        gameloop.checkLevelStatus();
        assertFalse(gameloop.allowIncoming);
        // Check that no more missiles if all incoming for the level have been deployed
        gameloop.resetGameStatus();
        gameloop.gameStatus.incomingTotal = 3;
        gameloop.gameStatus.incomingLeft = 3;
        gameloop.gameStatus.incomingMissilesDecrease();
        gameloop.gameStatus.incomingMissilesDecrease();
        gameloop.gameStatus.incomingMissilesDecrease();
        gameloop.checkLevelStatus();
        assertFalse(gameloop.allowIncoming);
    }

    @Test
    public void playerMissileLifeCycleWorks() {
        gameloop.launchNewPlayerMissile(0, 300.0, 300.0);
        assertEquals(1, gameloop.playerMissiles.size());
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
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
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
        assertEquals(1, gameloop.playerExplosions.size());
        gameloop.handlePlayerMissiles();
        assertEquals(0, gameloop.playerMissilesToRemove.size());
        assertEquals(0, gameloop.playerMissiles.size());
        PlayerMissileExplosion explosion = (PlayerMissileExplosion) gameloop.playerExplosions.get(0);
        explosion.burnUntil = System.currentTimeMillis();
        gameloop.playerExplosions.set(0, explosion);
        gameloop.handlePlayerMissileExplosions();
        assertEquals(1, gameloop.removeFromScene.size());
        assertEquals(1, gameloop.playerExplosionsToRemove.size());
        assertEquals(0, gameloop.playerExplosions.size());
        gameloop.handlePlayerMissileExplosions();
        assertEquals(0, gameloop.playerExplosionsToRemove.size());
        assertEquals(0, gameloop.playerExplosions.size());
        assertTrue(gameloop.actionsDone());
    }
    
    @Test
    public void playerMissileExplosionDestroysEnemyMissile() {
        gameloop.launchNewPlayerMissile(0, 300.0, 300.0);
        PlayerMissile playerMissile = (PlayerMissile) gameloop.playerMissiles.get(0);
        playerMissile.setLayoutX(300.0);
        playerMissile.setLayoutY(300.0);
        gameloop.playerMissiles.set(0, playerMissile);
        gameloop.handlePlayerMissiles();
        gameloop.handlePlayerMissiles();
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.handleNewEnemyMissiles();
        EnemyMissile enemyMissile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        enemyMissile.setLayoutX(310.0);
        enemyMissile.setLayoutY(310.0);
        gameloop.enemyMissiles.set(0, enemyMissile);
        gameloop.handleEnemyMissiles();
        gameloop.handlePlayerMissileExplosions();
        assertEquals(5, gameloop.gameStatus.score);
    }

    @Test
    public void enemyMissileLifeCycleWorks() {
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.handleNewEnemyMissiles();
        assertEquals(1, gameloop.enemyMissiles.size());
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
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
        assertEquals(1, gameloop.addToScene.size());
        gameloop.addToScene.clear();
        assertEquals(1, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissiles();
        assertEquals(0, gameloop.enemyMissilesToRemove.size());
        assertEquals(0, gameloop.enemyMissiles.size());
        EnemyMissileExplosion explosion = (EnemyMissileExplosion) gameloop.enemyExplosions.get(0);
        explosion.burnUntil = System.currentTimeMillis();
        gameloop.enemyExplosions.set(0, explosion);
        gameloop.handleEnemyMissileExplosions();
        assertEquals(1, gameloop.removeFromScene.size());
        assertEquals(1, gameloop.enemyExplosionsToRemove.size());
        assertEquals(0, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissileExplosions();
        assertEquals(0, gameloop.enemyExplosionsToRemove.size());
        assertEquals(0, gameloop.enemyExplosions.size());
        assertTrue(gameloop.actionsDone());
    }
/*
    @Test
    public void enemyMissileExplosionDestroysCity() {
        gameloop.gameStatus.reset();
        testAddCities();
//        gameloop.addCities();
        City city = gameloop.cities.get(0);
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.handleNewEnemyMissiles();
        EnemyMissile missile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        missile.setLayoutX(city.getLayoutX());
        missile.setLayoutY(city.getLayoutY());
        gameloop.enemyMissiles.set(0, missile);
        // Has reached target
        gameloop.handleEnemyMissiles();
        System.out.println("TEST: EnemyExplosions " + gameloop.enemyExplosions.size());
        assertEquals(1, gameloop.enemyExplosions.size());
        gameloop.handleEnemyMissileExplosions();
        assertTrue(gameloop.didDestroyCity(gameloop.enemyExplosions.get(0), city));
        gameloop.handleCityDestructions();
        assertEquals(1, gameloop.cityDestructions.size());
        gameloop.handleCities();
        assertEquals(5, gameloop.gameStatus.citiesLeft());
    }
    
    @Test
    public void enemyMissileExplosionDestroysBase() {
        gameloop.gameStatus.reset();
        testAddBases();
//        gameloop.addBases();
        Base base = gameloop.bases.get(0);
        gameloop.allowIncoming = true;
        gameloop.gameStatus.incomingPace = 1.0;
        gameloop.handleNewEnemyMissiles();
        EnemyMissile missile = (EnemyMissile) gameloop.enemyMissiles.get(0);
        missile.setLayoutX(base.getLayoutX());
        missile.setLayoutY(base.getLayoutY());
        gameloop.enemyMissiles.set(0, missile);
        // Has reached target
        gameloop.handleEnemyMissiles();
        gameloop.handleEnemyMissileExplosions();
        // -> assertEquals(1, gameloop.enemyExplosions.size());
        // -> assertTrue(gameloop.didDestroyBase(gameloop.enemyExplosions.get(0), base));
        gameloop.handleBaseExplosions();
        // -> assertEquals(1, gameloop.baseExplosions.size());
        gameloop.handleBases();
        assertEquals(2, gameloop.gameStatus.basesLeft());
    }
*/
    @Test
    public void baseLifeCycleWorks() {
        gameloop.gameStatus.reset();
        testAddBases();
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
        testAddCities();
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
        gameloop.enemyExplosions.add(new EnemyMissileExplosion(0.0, 0.0, 0.0, 1));
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
