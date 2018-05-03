/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import mizzilekommand.logics.Statistic;

/**
 * This Data Access Object (DAO) is used for storing high scores in a file on
 * the local hard drive.
 *
 * @author jaakkovilenius
 */
public class FileStatisticDao implements StatisticDao {

    private FilePropertiesHandler propHandler;
    public List<Statistic> statistics;
    private String file;
    public final int MAX_HIGH_SCORES = 10;

    /**
     * This is the constructor. It populates the statistics list from a local
     * file of which the name is given as a parameter.
     *
     * @param file The name of the high score file
     * @throws Exception
     *
     * @see mizzilekommand.logics.Statistic
     */
    public FileStatisticDao(String file) throws Exception {

        propHandler = new FilePropertiesHandler();

        statistics = new ArrayList<>();
        this.file = file;

        Properties scores = propHandler.loadOrCreateProperties(file, null);

        if (scores != null) {
            for (int i = 0; i < scores.size(); i++) {
                String[] parts = (scores.getProperty("" + (i + 1))).split(";");
                String initials = parts[0];
                int score = Integer.parseInt(parts[1]);
                int level = Integer.parseInt(parts[2]);
                int missilesDestroyed = Integer.parseInt(parts[3]);
                Statistic statistic = new Statistic(i + 1, initials, score, level, missilesDestroyed);
                statistics.add(statistic);
            }
            Collections.sort(statistics);
        }

    }

    /**
     * This method saves the highscore statistics to the local hard drive.
     *
     * @throws Exception
     */
    private void save() {
        Properties props = new Properties();
        for (Statistic statistic : statistics) {
            props.setProperty("" + statistic.getRank(),
                    statistic.getInitials() + ";"
                    + statistic.getScore() + ";"
                    + statistic.getLevel() + ";"
                    + statistic.getMissilesDestroyed());
        }
        propHandler.storeProperties(file, props);
    }

    /**
     * This method returns the high score statistics list.
     *
     * @return The list of Statistics objects.
     */
    @Override
    public List<Statistic> getAll() {
        return statistics;
    }

    /**
     * This method adds a new high score to the statistics list. Only 10 top
     * high scores are kept on the disk to more closely reflect the old arcade
     * game feeling.
     *
     * @param statistic the new Score
     * @return returns the new statistics updated with rank
     * @throws Exception
     *
     * @see mizzilekommand.logics.Statistic
     */
    @Override
    public Statistic add(Statistic statistic) {

        statistics.add(statistic);
        Collections.sort(statistics);
        if (statistics.size() > MAX_HIGH_SCORES) {
            statistics.remove(MAX_HIGH_SCORES);
        }
        updateRanks();
        save();
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

}
