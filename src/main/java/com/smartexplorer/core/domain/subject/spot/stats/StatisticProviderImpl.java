package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.exception.StatisticsCounterException;
import com.smartexplorer.core.repository.SpotRepository;
import com.smartexplorer.core.repository.SpotStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 */

@Service
public class StatisticProviderImpl implements StatisticsProvider {
    private SpotStatisticsRepository spotStatisticsRepository;
    private SpotRepository spotRepository;
    private StatisticsExpander statisticsExpander;
    private final String ERROR_MSG = "Cannot summarize statistics because spot ot found.\n[spotId] ";

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Autowired
    public void setStatisticsExpander(StatisticsExpander statisticsExpander) {
        this.statisticsExpander = statisticsExpander;
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

    @Override
    public OverallStatistics serviceSummary() {
        return statisticsExpander.overallStatistics();
    }

    @Override
    public SummaryStatistics spotSummary(String spotId) {
        SpotStatistics spotStatistics = spotStatisticsRepository.findBySpotId(spotId)
                .orElseThrow(() -> new StatisticsCounterException
                        (ERROR_MSG + spotId));
        return statisticsExpander.expandStatistics(spotStatistics);
    }

    @Override
    public SpotStatistics findSpotStatistics(String spotId) {
        return spotStatisticsRepository.findBySpotId(spotId).orElseThrow(() -> new StatisticsCounterException
                (ERROR_MSG + spotId));
    }

}
