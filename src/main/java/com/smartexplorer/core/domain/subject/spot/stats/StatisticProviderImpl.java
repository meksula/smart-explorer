package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.SpotRepository;
import com.smartexplorer.core.repository.SpotStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author Karol Meksuła
 * 17-06-2018
 */

@Service
public class StatisticProviderImpl implements StatisticsProvider {
    private SpotStatisticsRepository spotStatisticsRepository;
    private SpotRepository spotRepository;

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    public List<Spot> findMostRated(int amount) {
        List<SpotStatistics> spotList = spotStatisticsRepository.findAll();
        spotList.sort(Comparator.comparingInt(SpotStatistics::getRatesNumber).reversed());

        return loadSpotList(spotList, amount);
    }

    private List<Spot> loadSpotList(List<SpotStatistics> spotStatisticsList, int amount) {
        ArrayList<SpotStatistics> spotStatistics;

        try {
            spotStatistics = new ArrayList<>(spotStatisticsList.subList(0, amount));
        } catch (IndexOutOfBoundsException e) {
            spotStatistics = new ArrayList<>(spotStatisticsList.subList(0, spotStatisticsList.size() - 1));
        }

        ArrayList<Spot> results = new ArrayList<>();
        spotStatistics.forEach(item -> {
            String spotId = item.getSpotId();
            results.add(spotRepository.findById(spotId)
                    .orElseThrow(() -> new SpotLocalizeException("Cannot find spot with id: " + spotId)));
        });

        return results;
    }

    @Override
    public List<Spot> findHighestRated(int amount) {
        List<SpotStatistics> spotList = spotStatisticsRepository.findAll();
        spotList.sort(Comparator.comparingDouble(SpotStatistics::getRatesAvg).reversed());

        return loadSpotList(spotList, amount);
    }

    @Override
    public List<Spot> findMostVisited(int amount) {
        List<SpotStatistics> spotList = spotStatisticsRepository.findAll();
        spotList.sort(Comparator.comparingDouble(SpotStatistics::getVisitNumber).reversed());

        return loadSpotList(spotList, amount);
    }

}
