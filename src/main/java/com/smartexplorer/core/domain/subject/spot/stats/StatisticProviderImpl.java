package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

//TODO

@Service
public class StatisticProviderImpl implements StatisticsProvider {
    @Override
    public List<Spot> findMostRated(int amount) {
        return null;
    }

    @Override
    public List<Spot> findHighestRated(int amount) {
        return null;
    }

    @Override
    public List<Spot> findMostVisited(int amount) {
        return null;
    }
}
