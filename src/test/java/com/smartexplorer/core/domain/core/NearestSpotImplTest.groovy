package com.smartexplorer.core.domain.core

import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import com.smartexplorer.core.domain.geolocation.BasicAddress
import com.smartexplorer.core.domain.geolocation.Geolocation
import com.smartexplorer.core.repository.SpotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 * */

@SpringBootTest
class NearestSpotImplTest extends Specification {

    /*
    * TODO muszę tutaj napisać kilka Spotów.
    * 3 spoty niech będą w tym mieście gdzie jesteś
    * 1 spot dodatkowo w powiecie
    *
    * Szukanie Spota na podstawie współrzędnych można zrobić tak, że ustalę fejkowo jakieś sztywne koordynaty
    *
    * */

    private GeocodingResult[] geocodingResult

    @Autowired
    private Geolocation geolocation

    @Autowired
    private NearestSpotImpl nearestSpot

    @Autowired
    private SpotRepository spotRepository

    void setup() {
        //geocodingResult = geolocation.geolocateByAddress(new BasicAddress("Warszawa", "Grzybowska", "4"))
        geocodingResult = geolocation.geolocateByAddress(new BasicAddress("Lubartów", "Słowackiego", "4"))
        //geocodingResult = geolocation.geocodeCoordinates(new LatLng(51.489769, 22.694968))

    }

    def 'geocoding to map deserialize test'() {
        when:
        nearestSpot.findNearestSpot(geocodingResult[0])

        then:
        println(geocodingResult[0].addressComponents.length)
        def map = nearestSpot.getSearchData()

    }


    def 'city search - should find one Spot and return it'() {

    }




    void cleanup() {
        spotRepository.deleteAll()
    }
}
