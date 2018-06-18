package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.spot.Spot;
import com.smartexplorer.core.domain.subject.spot.opinion.Opinion;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.exception.StatisticsCounterException;
import com.smartexplorer.core.repository.SpotStatisticsRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@Aspect
@Component
public class StatisticsCounterImpl implements StatisticsCounter {
    private SpotStatisticsRepository spotStatisticsRepository;

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Override
    @AfterReturning(value = "execution(* com.smartexplorer.core.controller.SpotExplorationController.markAsVisited(..))",
            returning = "spot")
    public void visitNumberIncrement(Spot spot) {
        SpotStatistics spotStatistics = findSpotStatistics(spot);

        long visits = spotStatistics.getVisitNumber();
        spotStatistics.setVisitNumber(visits + 1);
        spotStatisticsRepository.save(spotStatistics);
    }

    @Override
    @AfterReturning(value = "@annotation(SpotSearch)", returning = "spot")
    public void searchesIncrement(Spot spot) {
        SpotStatistics spotStatistics = findSpotStatistics(spot);

        long searches = spotStatistics.getSearches();
        spotStatistics.setSearches(searches + 1);
        spotStatisticsRepository.save(spotStatistics);
    }

    @Override
    @AfterReturning(value = "@annotation(SpotSearch)", returning = "spotList")
    public void searchesIncrement(List<Spot> spotList) {
        for (Spot spot : spotList) {
            SpotStatistics spotStatistics = findSpotStatistics(spot);

            long searches = spotStatistics.getSearches();
            spotStatistics.setSearches(searches + 1);
            spotStatisticsRepository.save(spotStatistics);
        }

    }

    @Override
    @AfterReturning(value = "@annotation(OpinionStat)", returning = "opinion")
    public void opinionStatsUpdate(Opinion opinion) {
        String spotId = opinion.getSpotId();
        SpotStatistics spotStatistics = spotStatisticsRepository.findBySpotId(spotId)
                .orElseThrow(() -> new StatisticsCounterException("Cannot count stats."));

        int ratesNumber = spotStatistics.getRatesNumber();
        double ratesTotal = spotStatistics.getRatesTotal();

        spotStatistics.setRatesNumber(ratesNumber + 1);
        spotStatistics.setRatesTotal(ratesTotal + opinion.getRate());
        spotStatistics.setRatesAvg(ratesTotal / ratesNumber);

        spotStatisticsRepository.save(spotStatistics);
    }

    private SpotStatistics findSpotStatistics(Spot spot) {
        String spotId = spot.getId();
        return spotStatisticsRepository.findBySpotId(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Cannot load spot statistics with id: " + spotId));
    }

}
