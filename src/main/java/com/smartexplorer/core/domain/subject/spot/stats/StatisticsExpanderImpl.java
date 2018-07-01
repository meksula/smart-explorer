package com.smartexplorer.core.domain.subject.spot.stats;

import com.smartexplorer.core.domain.subject.explorers.Visit;
import com.smartexplorer.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        SummaryStatistics summaryStatistics = new SummaryStatistics(spotStatistics);
        summaryStatistics.setVisitsByHours(countVisitsByHours(spotStatistics));
        return summaryStatistics;
    }

    private Map<Integer, Long> countVisitsByHours(final SpotStatistics spotStatistics) {
        Map<Integer, Long> map = new LinkedHashMap<>();
        List<Visit> visitList = visitHistoryRepository.findAllBySpotId(spotStatistics.getSpotId());

        for (int i = 0; i < 24; i++) {
            map.put(i, 0L);
        }

        for(Visit visit : visitList) {
            int hour = LocalDateTime.parse(visit.getDate()).getHour();
            map.get(hour);
        }

        return map;
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
