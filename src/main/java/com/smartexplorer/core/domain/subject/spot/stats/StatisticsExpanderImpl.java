package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.explorers.Visit;
import com.smartexplorer.core.exception.SpotLocalizeException;
import com.smartexplorer.core.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @Author
 * Karol Meksuła
 * 01-07-2018
 * */

@Service
public class StatisticsExpanderImpl implements StatisticsExpander {
    private VisitHistoryRepository visitHistoryRepository;
    private SpotStatisticsRepository spotStatisticsRepository;
    private SpotOpinionsRepository spotOpinionsRepository;
    private SpotMakerRepository spotMakerRepository;
    private ExplorerRepository explorerRepository;
    private SpotRepository spotRepository;

    @Autowired
    public void setVisitHistoryRepository(VisitHistoryRepository visitHistoryRepository) {
        this.visitHistoryRepository = visitHistoryRepository;
    }

    @Autowired
    public void setSpotStatisticsRepository(SpotStatisticsRepository spotStatisticsRepository) {
        this.spotStatisticsRepository = spotStatisticsRepository;
    }

    @Autowired
    public void setSpotOpinionsRepository(SpotOpinionsRepository spotOpinionsRepository) {
        this.spotOpinionsRepository = spotOpinionsRepository;
    }

    @Autowired
    public void setSpotMakerRepository(SpotMakerRepository spotMakerRepository) {
        this.spotMakerRepository = spotMakerRepository;
    }

    @Autowired
    public void setExplorerRepository(ExplorerRepository explorerRepository) {
        this.explorerRepository = explorerRepository;
    }

    @Autowired
    public void setSpotRepository(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    public SummaryStatistics expandStatistics(SpotStatistics spotStatistics) {
        List<Visit> visitList = visitHistoryRepository.findAllBySpotId(spotStatistics.getSpotId());

        SummaryStatistics summaryStatistics = new SummaryStatistics(spotStatistics);
        summaryStatistics.setVisitsByHours(countVisitsByHours(visitList));
        summaryStatistics.setVisitInDayOfWeek(countVisitByWeekDays(visitList));
        summaryStatistics.setVisitInLastMonth(countVisitInLastMonth(visitList));
        summaryStatistics.setVisitInLastWeek(countVisitILastWeek(visitList));

        long allVisits = countAllVisits(spotStatistics.getSpotId());
        long daysForCreation = daysFromCreation(spotStatistics.getSpotId());
        summaryStatistics.setAverageVisitsPerDays(avgVisitsPerDay(allVisits, daysForCreation));
        summaryStatistics.setAverageVisitsPerWeeks(avgVisitsPerWeek(allVisits, daysForCreation));
        summaryStatistics.setAverageVisitsPerMonths(avgVisitsPerMonth(allVisits, daysForCreation));
        return summaryStatistics;
    }

    private Map<Integer, Long> countVisitsByHours(final List<Visit> visitList) {
        Map<Integer, Long> map = new LinkedHashMap<>();

        for (int i = 0; i < 24; i++) {
            map.put(i, 0L);
        }

        for(Visit visit : visitList) {
            int hour = LocalDateTime.parse(visit.getDate()).getHour();
            long visitsOnHour = map.get(hour) + 1L;

            map.put(hour, visitsOnHour);
        }

        return map;
    }

    private Map<DayOfWeek,Long> countVisitByWeekDays(final List<Visit> visitList) {
        Map<DayOfWeek, Long> map = new LinkedHashMap<>();

        for (int i = 1; i < 8; i++) {
            map.put(DayOfWeek.of(i), 0L);
        }

        for (Visit visit : visitList) {
            DayOfWeek dayOfWeek = LocalDateTime.parse(visit.getDate()).getDayOfWeek();
            long visitsOnDay = map.get(dayOfWeek) + 1L;

            map.put(dayOfWeek, visitsOnDay);
        }

        return map;
    }

    private long countVisitInLastMonth(final List<Visit> visitList) {
        Month currentMonth = LocalDateTime.now().getMonth();

        List<Visit> visitsInCurrentMonth = new ArrayList<>();

        for (Visit visit : visitList) {
            Month visitMonth = LocalDateTime.parse(visit.getDate()).getMonth();

            if (visitMonth == currentMonth) {
                visitsInCurrentMonth.add(visit);
            }
        }

        return visitsInCurrentMonth.size();
    }

    private long countVisitILastWeek(final List<Visit> visitList) {
        List<Visit> visitInCurrentWeek = new ArrayList<>();

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime weekBegan = current.minusWeeks(1);

        for (Visit visit : visitList) {
            LocalDateTime visitDate = LocalDateTime.parse(visit.getDate());

            if (visitDate.isAfter(weekBegan)) {
                visitInCurrentWeek.add(visit);
            }
        }

        return visitInCurrentWeek.size();
    }

    private double avgVisitsPerDay(final long allVisits, final long days) {
        if (allVisits == 0 || days == 0) {
            return 0;
        }

        return allVisits / days;
    }

    private double avgVisitsPerWeek(final long allVisits, final long days) {
        if (allVisits == 0 || days == 0) {
            return 0;
        }

        return allVisits / (days / 7);
    }

    private double avgVisitsPerMonth(final long allVisits, final long days) {
        if (allVisits == 0 || days == 0) {
            return 0;
        }

        return allVisits / (days / 30);
    }

    private long countAllVisits(String spotId) {
        return visitHistoryRepository.findAllBySpotId(spotId).size();
    }

    private long daysFromCreation(String spotId) {
        String creationDateString = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotLocalizeException("Cannot find spot, check if spotId is correct."))
                .getCreationDate();

        LocalDate creationDate = LocalDate.parse(creationDateString);
        return ChronoUnit.DAYS.between(creationDate, LocalDate.now());
    }

    @Override
    public OverallStatistics overallStatistics() {
        long allVisits = visitHistoryRepository.count();
        long allOpinions = spotOpinionsRepository.count();
        long spotMakers = spotMakerRepository.count();
        long explorers = explorerRepository.count();
        long spots = spotRepository.count();

        return new OverallStatistics.OverallStatisticsBuilder()
                .allVisits(allVisits)
                .allOpinions(allOpinions)
                .spotMakers(spotMakers)
                .explorers(explorers)
                .spots(spots)
                .allSearches(countAllSearches())
                .build();
    }

    private long countAllSearches() {
        long allSearches = 0;
        for (SpotStatistics spotStatistics : spotStatisticsRepository.findAll()) {
            allSearches += spotStatistics.getSearches();
        }

        return allSearches;
    }

}
