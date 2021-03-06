package com.smartexplorer.core.domain.core

import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import com.smartexplorer.core.domain.geolocation.Geolocation
import com.smartexplorer.core.domain.subject.spot.Spot
import com.smartexplorer.core.repository.SpotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 17-06-2018
 * */

@SpringBootTest
class SpotExplorationImplTest extends Specification {

    @Autowired
    private SpotExploration spotExploration

    @Autowired
    private SpotRepository spotRepository

    @Autowired
    private Geolocation geolocation

    /*
    * Some coordinates of some radom place in Warszawa
    * */
    private final double LAT = 52.221756
    private final double LNG = 21.001276

    private LatLng latLng

    private Spot spot

    def setup() {
        latLng = new LatLng(LAT, LNG)

        spot = new Spot("", "", new GeocodingResult[6])
        spot.city = "Warszawa"
        spot.district = "Warszawa"
        spotRepository.save(spot)
    }

    def 'findSpotInCity test'() {
        when:
        def result = spotExploration.findSpotsInCity(latLng)

        then:
        result.size() == 1
    }

    def 'findSpotBySpotId test'() {
        when:
        def result = spotExploration.findBySpotId(spot.getId())

        then:
        result != null
    }

    def 'findSpotInDistrict test'() {
        setup: 'save extra spot'
        def spot2 = new Spot("", "", new GeocodingResult[6])
        spot2.district = "Warszawa"
        spotRepository.save(spot2)

        expect:
        spotExploration.findSpotsInDistrict(latLng).size() == 2
    }

    def 'findTopSpotsInCountry'() {

    }



    def cleanup() {
        spotRepository.deleteAll()
    }

}
