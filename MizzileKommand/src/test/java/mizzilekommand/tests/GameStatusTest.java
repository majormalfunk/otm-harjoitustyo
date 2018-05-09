package mizzilekommand.tests;

/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
import mizzilekommand.logics.GameStatus;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author jaakkovilenius
 */
public class GameStatusTest {

    GameStatus gameStatus;

    @Before
    public void setUp() {
        gameStatus = new GameStatus();
    }

    @Test
    public void threeBasesOkAtStart() {
        for (int i = 0; i < 3; i++) {
            assertTrue(gameStatus.baseNotDestroyed(i));
        }
    }

    @Test
    public void sixCitiesOkAtStart() {
        for (int i = 0; i < 6; i++) {
            assertTrue(gameStatus.cityNotDestroyed(i));
        }
    }

    @Test
    public void levelUpWorks() {
        int level = gameStatus.level;
        level = gameStatus.levelUp();
        level = gameStatus.levelUp();
        assertEquals(3, gameStatus.level);
    }

    @Test
    public void cityGetsDestroyed() {
        gameStatus.destroyCity(0);
        assertFalse(gameStatus.cityNotDestroyed(0));
    }

    @Test
    public void falseCityIdsDestroyNoCities() {
        gameStatus.destroyCity(-1);
        assertFalse(gameStatus.cityNotDestroyed(-1));
        gameStatus.destroyCity(99);
        assertFalse(gameStatus.cityNotDestroyed(99));
        assertEquals(6, gameStatus.citiesLeft());

    }

    @Test
    public void threeCitiesDestroyedIsEnough() {
        gameStatus.destroyCity(0);
        gameStatus.destroyCity(1);
        gameStatus.destroyCity(2);
        assertTrue(gameStatus.citiesForLevelDestructed());
    }

    @Test
    public void cityDestructionCounterResetsAtLevelUp() {
        gameStatus.destroyCity(0);
        gameStatus.destroyCity(1);
        gameStatus.destroyCity(2);
        assertEquals(3, gameStatus.citiesLeft());
        gameStatus.levelUp();
        assertFalse(gameStatus.citiesForLevelDestructed());
        gameStatus.destroyCity(3);
        gameStatus.destroyCity(4);
        assertEquals(1, gameStatus.citiesLeft());
        gameStatus.levelUp();
        gameStatus.destroyCity(5);
        assertFalse(gameStatus.citiesForLevelDestructed());
        assertEquals(0, gameStatus.citiesLeft());
    }

    @Test
    public void basesGetRebuiltAtLevelUp() {
        gameStatus.destroyBase(0);
        gameStatus.destroyBase(1);
        gameStatus.destroyBase(2);
        assertEquals(0, gameStatus.basesLeft());
        gameStatus.levelUp();
        assertEquals(3, gameStatus.basesLeft());
    }

    @Test
    public void baseGetsDestroyed() {
        gameStatus.destroyBase(0);
        assertFalse(gameStatus.baseNotDestroyed(0));
    }

    @Test
    public void falseBaseIdsDestroyNoBases() {
        gameStatus.destroyBase(-1);
        assertFalse(gameStatus.baseNotDestroyed(-1));
        gameStatus.destroyBase(99);
        assertFalse(gameStatus.baseNotDestroyed(99));
        assertEquals(3, gameStatus.basesLeft());
    }

    @Test
    public void countsIncomingMissilesLeftCorrectly() {
        gameStatus.incomingMissilesDecrease();
        gameStatus.incomingMissilesDecrease();
        gameStatus.incomingMissilesDecrease();
        int before = gameStatus.numberOfIncomingLeft();
        gameStatus.levelUp();
        int after = gameStatus.numberOfIncomingLeft();
        assertTrue(after > before);
    }

    @Test
    public void countsIncomingMissilesLeftCorrectly2() {
        int before = gameStatus.numberOfIncomingLeft();
        for (int i = before; i > 0; i--) {
            gameStatus.incomingMissilesDecrease();
        }
        assertFalse(gameStatus.incomingMissilesLeft());
    }
    
    @Test
    public void missileCountDoesntGoNegative() {
        int m = gameStatus.missilesLeft[0];
        for (int i = 0; i <= m; i++) {
            gameStatus.substractMissileFromBase(0);
        }
        assertTrue(gameStatus.missilesLeft[0] == 0);
    }
    
    @Test
    public void highScoreCountingWorks() {
        
        gameStatus.setStatisticDao(new FakeDao());
        gameStatus.score = 1130;
        gameStatus.level = 2;
        gameStatus.missilesLeft[0] = 10;
        gameStatus.missilesLeft[1] = 7;
        gameStatus.missilesLeft[2] = 10;
        gameStatus.enemyMissilesDestroyed = 3;
        gameStatus.recordMissileBonus();
        gameStatus.recordCityBonus();
        assertTrue(gameStatus.isTopScore());
        gameStatus.recordCurrentScore("1ST");
        assertEquals(1, gameStatus.getStatistics().get(0).getRank());
        assertEquals(2000, gameStatus.getStatistics().get(0).getScore());
        gameStatus.score = 1995;
        gameStatus.level = 3;
        gameStatus.missilesLeft[0] = 10;
        gameStatus.missilesLeft[1] = 7;
        gameStatus.missilesLeft[2] = 10;
        gameStatus.cityOk[0] = false;
        gameStatus.cityOk[5] = false;
        gameStatus.enemyMissilesDestroyed = 6;
        gameStatus.recordMissileBonus();
        gameStatus.recordCityBonus();
        assertTrue(gameStatus.isTopScore());
        gameStatus.recordCurrentScore("2ND");
        assertEquals(1, gameStatus.getStatistics().get(0).getRank());
        assertEquals(3000, gameStatus.getStatistics().get(0).getScore());
        assertEquals("2ND", gameStatus.getStatistics().get(0).getInitials());
        assertEquals("1ST", gameStatus.getStatistics().get(1).getInitials());
        assertTrue(gameStatus.isTopScore());
        gameStatus.recordCurrentScore("3RD");
        assertEquals(1, gameStatus.getStatistics().get(0).getRank());
        assertEquals(3000, gameStatus.getStatistics().get(0).getScore());
        assertEquals("1ST", gameStatus.getStatistics().get(2).getInitials());
    }
    
}
