package com.smartexplorer.core.domain.subject.spot.information

import com.fasterxml.jackson.databind.ObjectMapper
import com.smartexplorer.core.repository.SpotInformationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.DayOfWeek

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

@SpringBootTest
class SpotInformationCreatorImplTest extends Specification {

    @Autowired
    private SpotInformationManager spotInformationManager

    @Autowired
    private SpotInformationRepository spotInformationRepository

    private final String SPOT_ID = "12424256tfqd13ds13d2"
    private final String MESSAGE = "Open 9 - 15. Tickets for children for free!"

    def 'empty SpotInformation create test'() {
        when:
        spotInformationManager.createEmptyInfo(SPOT_ID)

        then:
        SpotInformation spotInformation = spotInformationRepository.findBySpotId(SPOT_ID).orElse(null)
        spotInformation != null
        println(new ObjectMapper().writeValueAsString(spotInformation))
    }

    def 'modify spotInformation test'() {
        setup:
        def updated = buildFakeSpotInfo()

        when:
        spotInformationManager.modifySpotInformation(updated)

        then:
        println(new ObjectMapper().writeValueAsString(updated))

        def spotInfo = spotInformationRepository.findBySpotId(SPOT_ID).get()
        spotInfo.message == MESSAGE
        spotInfo.getVisitDaysInWeek().size() == 7
        spotInfo.getVisitDaysInWeek().get(DayOfWeek.MONDAY).open
        !spotInfo.getVisitDaysInWeek().get(DayOfWeek.SATURDAY).open
    }

    def 'is visitable now? test'() {
        setup:
        spotInformationManager.modifySpotInformation(buildFakeSpotInfo())

        expect:
        spotInformationManager.isSpotVisitableNow(SPOT_ID)

    }

    private SpotInformation buildFakeSpotInfo() {
        SpotInformation updated = new SpotInformation(SPOT_ID)
        updated.setMessage(MESSAGE)

        Map<DayOfWeek, VisitDay> map = new LinkedHashMap<>()

        for (int i = 1; i < 8; i++) {
            VisitDay visitDay = new VisitDay([1,2,3,4,5,6,7,8,9, 10, 11, 12, 13, 14, 15,16,17,18,19,20,21,22,23,24] as int[])
            if (i < 6)
                map.put(DayOfWeek.of(i), visitDay)
            else
                map.put(DayOfWeek.of(i), new VisitDay())
        }

        updated.setVisitDaysInWeek(map)
        return updated
    }

    void cleanup() {
        spotInformationRepository.deleteAll()
    }

}
