/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

/**
 *
 * @author jaakkovilenius
 */
public class GameStatus {

    public int level;
    public int incomingTotal;
    private int incomingLeft;
    private double incomingBoost;
    private int citiesDestroyedInLevel;
    private boolean[] cityOk;
    private boolean[] baseOk;
    public int[] missilesLeft;

    public GameStatus() {
        reset();
    }

    public int levelUp() {

        level++;
        incomingTotal = 5 + (level * 5);
        incomingLeft = incomingTotal;
        incomingBoost += 0.25;
        citiesDestroyedInLevel = 0;
        baseOk = new boolean[]{true, true, true};
        missilesLeft = new int[]{10, 10, 10};
        return level;

    }

    public void reset() {

        level = 1;
        incomingTotal = 5;
        incomingLeft = incomingTotal;
        incomingBoost = 1.0;
        citiesDestroyedInLevel = 0;
        cityOk = new boolean[]{true, true, true, true, true, true};
        baseOk = new boolean[]{true, true, true};
        missilesLeft = new int[]{10, 10, 10};

    }

    /**
     * This method marks a city as destroyed. It also increments the number of
     * cities destroyed in the current level.
     *
     * @param id The id of the city
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
     * This method marks a base as destroyed.
     *
     * @param id The id of the base
     */
    public void destroyBase(int id) {
        if (id >= 0 && id < baseOk.length) {
            baseOk[id] = false;
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

    public boolean baseMissilesLeft(int base) {
         return (baseNotDestroyed(base) && missilesLeft[base] > 0);
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
        //System.out.println("Incoming left " + incomingLeft);
    }

    /**
     * Returns the number of incoming missiles left in the level.
     *
     * @return the number of incoming missiles
     */
    public int numberOfIncomingLeft() {
        return incomingLeft;
    }
    
    public double getIncomingSpeedFactor() {
        return incomingBoost;
    }

}
