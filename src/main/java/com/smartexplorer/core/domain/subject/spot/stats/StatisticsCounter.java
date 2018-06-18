package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

public interface StatisticsCounter {
    void visitNumberIncrement(Spot spot);

    void ratesNumberIncrement(Spot spot);

    void searchesIncrement(Spot spot);

    void ratesTotalSum(Spot spot);

    void ratesNumber(Spot spot);

    void ratesAvgCompute(Spot spot);

}
