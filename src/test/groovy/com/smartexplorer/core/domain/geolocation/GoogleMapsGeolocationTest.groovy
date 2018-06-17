package com.smartexplorer.core.domain.geolocation

import com.google.maps.model.LatLng
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

@SpringBootTest
class GoogleMapsGeolocationTest extends Specification {
    private BasicAddress basicAddress = new BasicAddress("Warszawa", "Marszałkowska", "15")

    @Autowired
    private Geolocation geolocation

    private final double LAT = 51.4582780197085
    private final double LNG = 22.4879750197085

    def 'geolocateByAddress test'() {
        when:
        def results = geolocation.geolocateByAddress(basicAddress)

        then:
        results.length == 1

    }

    def 'geolocateByCoordinates test'() {
        setup:
        LatLng coords = new LatLng()
        coords.lat = LAT
        coords.lng = LNG

        when:
        def results = geolocation.geocodeCoordinates(coords)

        then:
        results.length == 7

    }

}