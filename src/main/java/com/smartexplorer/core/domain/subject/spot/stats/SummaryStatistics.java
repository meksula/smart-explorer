package com.smartexplorer.core.domain.subject.spot.stats;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;

import java.time.DayOfWeek;
import java.util.Map;

/**
 * @Author
 * Karol Meksuła
 * 30-06-2018
 * */

@Getter
@Setter
public class SummaryStatistics {
    private SpotStatistics spotStatistics;
    private String summaryDate;
    private Map<Integer, Long> visitsByHours;
    private Map<DayOfWeek, Long> visitInDayOfWeek;
    private long visitInLastMonth;
    private long visitInLastWeek;
    private double averageVisitsPerDays;
    private double averageVisitsPerWeeks;
    private double averageVisitsPerMonths;
    private long visitsPlanned;

    public SummaryStatistics(SpotStatistics spotStatistics) {
        this.spotStatistics = spotStatistics;
        this.summaryDate = LocalDate.now().toString();
    }

}
