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
    public void falseCityIdDestroyesNoCity() {
        assertFalse(gameStatus.cityNotDestroyed(99));
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
        gameStatus.levelUp();
        assertFalse(gameStatus.citiesForLevelDestructed());
    }

    @Test
    public void basesGetRebuiltAtLevelUp() {
        gameStatus.destroyBase(0);
        gameStatus.destroyBase(1);
        gameStatus.destroyBase(2);
        gameStatus.levelUp();
        assertEquals(3, gameStatus.basesLeft());
    }

    @Test
    public void baseGetsDestroyed() {
        gameStatus.destroyBase(0);
        assertFalse(gameStatus.baseNotDestroyed(0));
    }

    @Test
    public void falseBaseIdDestroyesNoBase() {
        assertFalse(gameStatus.baseNotDestroyed(99));
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
}
