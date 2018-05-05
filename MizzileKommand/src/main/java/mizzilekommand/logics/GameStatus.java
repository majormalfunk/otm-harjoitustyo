/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;


import javafx.scene.text.Text;
import mizzilekommand.dao.StatisticDao;
import static mizzilekommand.logics.MizzileKommand.CITY_BONUS;
import static mizzilekommand.logics.MizzileKommand.SCORE;
import static mizzilekommand.logics.MizzileKommand.INCOMING;
import static mizzilekommand.logics.MizzileKommand.LEVEL;
import static mizzilekommand.logics.MizzileKommand.MISSILE_BONUS;
import static mizzilekommand.logics.MizzileKommand.MAX_BONUS_LEVEL_FACTOR;

/**
 * This Class handles the game status during play
 *
 * @author jaakkovilenius
 */
public class GameStatus {

    public int score;
    public int level;
    public int incomingTotal;
    public int incomingLeft;
    public int enemyMissilesDestroyed;
    public double incomingPace;
    private double incomingBoost;
    private int citiesDestroyedInLevel;
    public boolean[] cityOk;
    public boolean[] baseOk;
    public int[] missilesLeft;

    public Text scoreCounter;
    public Text levelIndicator;
    public Text incomingCounter;
    public Text[] baseMissileCounter;

    public boolean highScoresAvailable;
    private StatisticDao statDao;

    /**
     * Constructor for the GameStatus class. It initializes its' attributes and
     * calls the reset() -method that sets all the values for a new start. It
     * also loads the high score file from the local hard drive.
     *
     * @see mizzilekommand.logics.GameStatus#reset()
     *
     */
    public GameStatus() {
        scoreCounter = new Text();
        levelIndicator = new Text();
        incomingCounter = new Text();
        baseMissileCounter = new Text[]{new Text(), new Text(), new Text()};
        highScoresAvailable = false;
        reset();
    }
    
    public void setStatisticDao(StatisticDao statDao) {
        this.statDao = statDao;
        if (this.statDao != null) {
            highScoresAvailable = true;
        }
    }
    
    public boolean isTopScore() {
        return statDao.isTopScore(score);
    }
    
    public void recordCurrentScore(String initials) {
        if (highScoresAvailable) {
            try {
                Statistic stat = new Statistic();
                stat.setInitials(initials);
                stat.setScore(score);
                stat.setLevel(level);
                stat.setMissilesDestroyed(enemyMissilesDestroyed);
                stat = statDao.add(stat);
            } catch (Exception e) {
                highScoresAvailable = false;
            }
            
        }

    }

    /**
     * This method updates all the attributes that have to be updated before a
     * new level is started. It returns the number of the new level.
     *
     * @return the new level as int
     */
    public int levelUp() {

        level++;
        setLevelIndicatorText();
        incomingTotal = 5 + ((level - 1) * 3);
        incomingLeft = incomingTotal;
        incomingPace += 0.002;
        incomingBoost += 0.25;
        citiesDestroyedInLevel = 0;
        baseOk = new boolean[]{true, true, true};
        missilesLeft = new int[]{10, 10, 10};
        baseMissileCounter[0].setText("" + missilesLeft[0]);
        baseMissileCounter[1].setText("" + missilesLeft[0]);
        baseMissileCounter[2].setText("" + missilesLeft[0]);
        return level;

    }

    /**
     * This method resets the game status to the beginning values. It is called
     * also at the end of the class constructor
     */
    public void reset() {

        score = 0;
        setScoreCounterText();
        level = 1;
        setLevelIndicatorText();
        incomingTotal = 5;
        incomingLeft = incomingTotal;
        setIncomingCounterText();
        enemyMissilesDestroyed = 0;
        incomingPace = 0.005;
        incomingBoost = 1.0;
        citiesDestroyedInLevel = 0;
        cityOk = new boolean[]{true, true, true, true, true, true};
        baseOk = new boolean[]{true, true, true};
        missilesLeft = new int[]{10, 10, 10};
        setBaseMissileCounterTexts();
    }

    /**
     * This method sets the score counter text on the screen. Specifically it
     * sets the score counter text it has a reference to in the current scene
     *
     * @see mizzilekommand.layout.SceneTemplate
     */
    public void setScoreCounterText() {
        scoreCounter.setText(SCORE + " " + score);
    }

    /**
     * This method sets the level indicator text on the screen. Specifically it
     * sets the level indicator text it has a reference to in the current scene
     *
     * @see mizzilekommand.layout.SceneTemplate
     */
    public void setLevelIndicatorText() {
        levelIndicator.setText(LEVEL + " " + level);
    }

    /**
     * This method sets the incoming counter text on the screen. Specifically it
     * sets the incoming counter text it has a reference to in the current scene
     *
     * @see mizzilekommand.layout.SceneTemplate
     */
    public void setIncomingCounterText() {
        incomingCounter.setText(INCOMING + " " + incomingLeft);
    }

    /**
     * This convenience method sets all the base missile counter texts
     *
     * @see mizzilekommand.logics.GameStatus#setBaseMissileCounterText(int)
     */
    public void setBaseMissileCounterTexts() {
        setBaseMissileCounterText(0);
        setBaseMissileCounterText(1);
        setBaseMissileCounterText(2);
    }

    /**
     * This method sets the base missile counter text of the corresponding base
     * on the screen. Specifically it sets the base missiel counter text it has
     * a reference to in the current scene
     *
     * @param base The id of the base (0-2)
     *
     * @see mizzilekommand.layout.SceneTemplate
     */
    public void setBaseMissileCounterText(int base) {
        baseMissileCounter[base].setText(" " + missilesLeft[base]);
    }

    /**
     * This method marks a city as destroyed. It also increments the number of
     * cities destroyed in the current level.
     *
     * @param id The id of the city (0-5)
     */
    public void destroyCity(int id) {
        if (id >= 0 && id < cityOk.length) {
            cityOk[id] = false;
            citiesDestroyedInLevel++;
        }
    }

    /**
     * This method tells whether enough cities were destroyed for the current
     * level.
     *
     * @return true if enough cities were destroyed for the current level. The
     * threshold is 3. False if less than 3 cities were destroyed.
     */
    public boolean citiesForLevelDestructed() {
        return (citiesDestroyedInLevel >= 3);
    }

    /**
     * This method returns true if the city with the id given as parameter is
     * not destroyed
     *
     * @param id Id of the city
     * @return true if city not destroyed, false otherwise
     */
    public boolean cityNotDestroyed(int id) {
        if (id >= 0 && id < cityOk.length) {
            return cityOk[id];
        }
        return false;
    }

    /**
     * This method tells the number of cities left.
     *
     * @return number of cities left
     */
    public int citiesLeft() {
        return (cityOk[0] ? 1 : 0) + (cityOk[1] ? 1 : 0) + (cityOk[2] ? 1 : 0)
                + (cityOk[3] ? 1 : 0) + (cityOk[4] ? 1 : 0) + (cityOk[5] ? 1 : 0);
    }

    /**
     * This method marks a base as destroyed.
     *
     * @param base The number of the base
     */
    public void destroyBase(int base) {
        if (base >= 0 && base < baseOk.length) {
            baseOk[base] = false;
            missilesLeft[base] = 0;
            baseMissileCounter[base].setText("");
        }
    }

    /**
     * This method returns true if the base with the id given as parameter is
     * not destroyed
     *
     * @param id Id of the base
     * @return true if base not destroyed, false otherwise
     */
    public boolean baseNotDestroyed(int id) {
        if (id >= 0 && id < baseOk.length) {
            return baseOk[id];
        }
        return false;
    }

    /**
     * This method tells the number of bases left.
     *
     * @return number of bases left
     */
    public int basesLeft() {
        return (baseOk[0] ? 1 : 0) + (baseOk[1] ? 1 : 0) + (baseOk[2] ? 1 : 0);
    }

    /**
     * This method tells whether there are any missiles left in the base
     * corresponding to the id given as parameter. Specifically it checks that
     * the base is not destroyed and that missiles are left.
     *
     * @param base
     * @return true if missiles left and base is not destroyed false otherwise
     */
    public boolean baseMissilesLeft(int base) {
        return (baseNotDestroyed(base) && missilesLeft[base] > 0);
    }

    /**
     * This method substracts a missile from the given base. It also calls the
     * method that updates the corresponding base missile counter
     *
     * @param base id of base (0-2)
     */
    public void substractMissileFromBase(int base) {
        if (missilesLeft[base] > 0) {
            missilesLeft[base]--;
            setBaseMissileCounterText(base);
        }
    }

    /**
     * This method returns true if any incoming missiles are left in the current
     * level
     *
     * @return true if any incoming missiles are left, false otherwise
     */
    public boolean incomingMissilesLeft() {
        return (incomingLeft > 0);
    }

    /**
     * This method subtracts 1 from the amount of incoming missiles left in the
     * current level
     */
    public void incomingMissilesDecrease() {
        incomingLeft--;
        setIncomingCounterText();
    }

    /**
     * This method increases the score counter when an enemy missile is
     * destroyed by the player
     */
    public void enemyMissileDestroyed() {
        enemyMissilesDestroyed++;
        score += 5;
        setScoreCounterText();
    }

    /**
     * Returns the number of incoming missiles left in the level.
     *
     * @return the number of incoming missiles
     */
    public int numberOfIncomingLeft() {
        return incomingLeft;
    }

    /**
     * Returns the current enemy missile speed factor.
     *
     * @return the incoming missiles' speed factor
     */
    public double getIncomingSpeedFactor() {
        return incomingBoost;
    }
    
    /**
     * Returns the total number of player missiles left at the moment.
     * @return number of player missiles left at the moment
     */
    public int playerMissilesLeft() {
        return missilesLeft[0] + missilesLeft[1] + missilesLeft[2];
    }
    
    /**
     * This method adds the bonus for left player missiles to the score
     */
    public void recordMissileBonus() {
        score += bonusForMissilesLeft();
    }

    /**
     * This method returns the bonus per missile left for the current level.
     * @return bonus per missile
     */
    public int bonusPerMissileLeft() {
        return (Math.min(level, MAX_BONUS_LEVEL_FACTOR) * MISSILE_BONUS);
    }
    
    /**
     * Returns the missile bonus rewarded from missiles left after level.
     * @return 
     */
    public int bonusForMissilesLeft() {
        return (playerMissilesLeft() * bonusPerMissileLeft());
    }

    /**
     * This method adds the bonus for left cities to the score
     */
    public void recordCityBonus() {
        score += bonusForCitiesLeft();
    }

    /**
     * This method returns the bonus per city left for the current level.
     *
     * @return bonus per city
     */
    public int bonusPerCityLeft() {
        return (Math.min(level, MAX_BONUS_LEVEL_FACTOR) * CITY_BONUS);
    }


    /**
     * Returns the city bonus rewarded from cities left after level.
     * @return 
     */
    public int bonusForCitiesLeft() {
        return (citiesLeft() * bonusPerCityLeft());
    }

}
