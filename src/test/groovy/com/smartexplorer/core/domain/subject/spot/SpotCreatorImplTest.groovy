package com.smartexplorer.core.domain.subject.spot

import com.fasterxml.jackson.databind.ObjectMapper
import com.smartexplorer.core.repository.SpotRepository
import com.smartexplorer.core.repository.SpotStatisticsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

@SpringBootTest
class SpotCreatorImplTest extends Specification {
    private final String NAME = "Muzeum Powstania Warszawskiego"
    private final String DESCRIPTION = "Placówka muzeala powstała w celu upamiętnienia heroicznych walk polskich powstańców."
    private final boolean SEARCH_ENABLE = true
    private final String SPOT_MAKER_ID = "33fdj4i3fdcj43dmj439d"

    private final String CITY = "Warszawa"
    private final String STREET = "Grzybowska"
    private final String BUILDING_NUMBER = "79"

    private SpotCreationForm form

    @Autowired
    private SpotRepository spotRepository

    @Autowired
    private SpotStatisticsRepository spotStatisticsRepository

    @Autowired
    private SpotCreator spotCreator

    void setup() {
        form = new SpotCreationForm(NAME, DESCRIPTION, SEARCH_ENABLE, CITY, STREET, BUILDING_NUMBER)
    }

    def 'instantiate test'() {
        expect:
        spotRepository != null
        spotCreator != null
    }

    def 'spot creation test'() {
        when:
        def spot = spotCreator.createSpot(form)

        then:
        spot != null
        spot.getCreationDate() != null
        spot.getName() == NAME
        spot.getDescription() == DESCRIPTION
        spot.getGeocodingResult().length != 0
        spot.isSearchEnable()

        spot.longitude != 0
        spot.latitude != 0
        !spot.country.isEmpty()
        !spot.zipCode.isEmpty()
        !spot.district.isEmpty()
        !spot.city.isEmpty()
        !spot.street.isEmpty()
        !spot.buildingNumber.isEmpty()

        spotStatisticsRepository.findBySpotId(spot.id).isPresent()

        println(new ObjectMapper().writeValueAsString(spot))
    }

    void cleanup() {
        spotRepository.deleteAll()
        spotStatisticsRepository.deleteAll()
    }
}
