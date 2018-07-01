package com.smartexplorer.core.domain.subject.spot.stats;

/**
 * @Author
 * Karol Meksuła
 * 01-07-2018
 * */

public interface StatisticsExpander {
    SummaryStatistics expandStatistics(SpotStatistics spotStatistics);

    OverallStatistics overallStatistics();
}
