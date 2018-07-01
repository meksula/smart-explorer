package com.smartexplorer.core.domain.subject.spot.stats

import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 30-06-2018
 * */

class OverallStatisticsTest extends Specification {
    private OverallStatistics overallStatistics

    private final long VALUE = 949232

    def 'builder test'() {
        setup:
        this.overallStatistics = new OverallStatistics.OverallStatisticsBuilder()
                .allOpinions(VALUE)
                .allSearches(VALUE)
                .allVisits(VALUE)
                .explorers(VALUE)
                .spots(VALUE)
                .spotMakers(VALUE)
                .build()

        expect:
        overallStatistics.getAllOpinions() == VALUE
        overallStatistics.getAllSearches() == VALUE
        overallStatistics.getAllVisits() == VALUE
        overallStatistics.getExplorers() == VALUE
        overallStatistics.getSpotMakers() == VALUE
        overallStatistics.getSpotMakers() == VALUE
    }

}
