package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.opinion.Opinion;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public interface StatisticsCounter {
    void visitNumberIncrement(Spot spot);

    void searchesIncrement(Spot spot);

    void searchesIncrement(List<Spot> spotList);

    void opinionStatsUpdate(Opinion opinion);

}
