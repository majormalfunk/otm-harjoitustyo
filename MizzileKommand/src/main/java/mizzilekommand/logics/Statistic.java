/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

/**
 * This class represents the statistics of a played game
 * @author jaakkovilenius
 */
public class Statistic implements Comparable<Statistic> {
    
    private int rank;
    private String initials;
    private int score;
    private int level;
    private int missilesDestroyed;
    
    /**
     * Constructor for a default statistic object.
     */
    public Statistic() {
        rank = 9999;
        initials = "";
        score = 0;
        level = 1;
        missilesDestroyed = 0;
    }
    
    /**
     * Constructor for a statistic object with values.
     * 
     * @param score The score reached
     * @param level The level reached
     * @param missilesDestroyed The number of enemy missiles destroyed in game
     * 
     * @see mizzilekommand.dao.StatisticDao
     */
    public Statistic(int rank, String initials, int score, int level, int missilesDestroyed) {
        this.rank = rank;
        this.initials = initials;
        this.score = score;
        this.level = level;
        this.missilesDestroyed = missilesDestroyed;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank() {
        return this.rank;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
    public String getInitials() {
        return this.initials;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return this.score;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return this.level;
    }

    public void setMissilesDestroyed(int missilesDestroyed) {
        this.missilesDestroyed = missilesDestroyed;
    }
    public int getMissilesDestroyed() {
        return this.missilesDestroyed;
    }

    @Override
    public int compareTo(Statistic other) {
        return other.getScore() - this.getScore();
    }

    
    
}
