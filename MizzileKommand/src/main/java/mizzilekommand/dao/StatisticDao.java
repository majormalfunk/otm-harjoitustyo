/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.dao;

import java.util.List;
import mizzilekommand.logics.Statistic;

/**
 *
 * @author jaakkovilenius
 */
public interface StatisticDao {

    Statistic add(Statistic statistic) throws Exception;

    List<Statistic> getAll();

    boolean isTopScore(int score);

}
