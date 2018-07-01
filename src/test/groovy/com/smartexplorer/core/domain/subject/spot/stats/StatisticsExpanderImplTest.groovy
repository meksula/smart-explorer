package com.smartexplorer.core.domain.subject.spot.stats

import com.smartexplorer.core.domain.subject.explorers.Explorer
import com.smartexplorer.core.repository.ExplorerRepository
import com.smartexplorer.core.repository.SpotStatisticsRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @Author
 * Karol Meksuła
 * 01-07-2018
 * */

@SpringBootTest
class StatisticsExpanderImplTest extends Specification {

    @Autowired
    private StatisticsExpander statisticsExpander

    @Autowired
    private ExplorerRepository explorerRepository

    @Autowired
    private SpotStatisticsRepository spotStatisticsRepository

    private SpotStatistics spotStatistics

    private final SPOT_ID = "23d2md23md23dsc43g4"

    void setup() {
        explorerRepository.save(new Explorer())
        def statA = new SpotStatistics()
        statA.setSearches(50)
        def statB = new SpotStatistics()
        statB.setSearches(150)
        spotStatisticsRepository.saveAll([statA, statB])

        spotStatistics = new SpotStatistics(SPOT_ID)
        spotStatistics.setId(SPOT_ID)
        spotStatistics.setSearches(7535)
        spotStatistics.setRatesAvg(4.5)
        spotStatistics.setRatesNumber(100)
        spotStatistics.setRatesTotal(450)
        spotStatistics.setVisitNumber(2145)
    }

    def 'injection test'() {
        expect:
        statisticsExpander != null
    }

    def 'overallStats test'() {
        when:
        def overallStats = statisticsExpander.overallStatistics()

        then:
        overallStats.getExplorers() == 1
        overallStats.getAllSearches() == 200
    }

    def 'expand statistics test'() {
        when:
        def expanded = statisticsExpander.expandStatistics(spotStatistics)

        then:
        expanded.getSpotStatistics().getId() == SPOT_ID
        expanded.getSummaryDate() != null
        expanded.getVisitsByHours() != null
    }

    def 'joda date parse test'() {
        given:
        def dateString = "2018-06-30T22:30:04.444"

        when:
        def date = LocalDateTime.parse(dateString)

        then:
        date.getHour() == 22
    }

    void cleanup() {
        explorerRepository.deleteAll()
        spotStatisticsRepository.deleteAll()
    }

}
