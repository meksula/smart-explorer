package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

public interface StatisticsProvider {
    List<Spot> findMostRated(int amount);

    List<Spot> findHighestRated(int amount);

    List<Spot> findMostVisited(int amount);
}
