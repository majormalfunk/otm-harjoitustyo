/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mizzilekommand.dao.StatisticDao;
import mizzilekommand.logics.Statistic;

/**
 *
 * @author jaakkovilenius
 */
public class FakeDao implements StatisticDao {

    public List<Statistic> statistics;
    private String file;
    public final int MAX_HIGH_SCORES = 10;

    public FakeDao() {
        statistics = new ArrayList();
    }
    
    @Override
    public Statistic add(Statistic statistic) {

        statistics.add(statistic);
        Collections.sort(statistics);
        if (statistics.size() > MAX_HIGH_SCORES) {
            statistics.remove(MAX_HIGH_SCORES);
        }
        updateRanks();
        return statistic;
    }

    /**
     * This method updates the ranks in the statistics list according to the
     * order of them in the statistics list
     */
    private void updateRanks() {
        for (int i = 0; i < statistics.size(); i++) {
            statistics.get(i).setRank(i + 1);
        }
    }

    /**
     * This method checks if the given score would qualify as a top score.
     *
     * @param score
     * @return true if score is a top score false otherwise
     */
    @Override
    public boolean isTopScore(int score) {
        if (statistics == null || statistics.isEmpty() || statistics.size() < MAX_HIGH_SCORES) {
            return true;
        }
        return score > statistics.get(statistics.size() - 1).getScore();
    }

    @Override
    public List<Statistic> getAll() {
        return statistics;
    }
    
}
