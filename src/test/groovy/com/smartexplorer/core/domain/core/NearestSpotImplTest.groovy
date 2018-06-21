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
    private GeocodingResult fakeGeocodingResult

    @Autowired
    private Geolocation geolocation

    @Autowired
    private NearestSpotImpl nearestSpot

    @Autowired
    private SpotRepository spotRepository

    private Spot spotA
    private Spot spotB
    private Spot spotC
    private Spot spotD

    private final MY_CURRENT_LATITUDE = 52.213459
    private final MY_CURRENT_LONGITUDE = 21.026894

    void setup() {
        //geocodingResult = geolocation.geolocateByAddress(new BasicAddress("Lubartów", "Słowackiego", "4"))
        //geocodingResult = geolocation.geocodeCoordinates(new LatLng(51.926267, 22.376516))
        geocodingResult = geolocation.geocodeCoordinates(new LatLng(51.447168, 22.613559))

        /*
        * Fake GeocodingResult to test, latitude & longitude in connection with fake created Spot
        * Some Coorditanes in Warszawa
        * */
        fakeGeocodingResult = geolocation.geocodeCoordinates(new LatLng(MY_CURRENT_LATITUDE, MY_CURRENT_LONGITUDE))[0]

        /*
        * Fake Spot creation
        * */
        spotA = new Spot("", "", new GeocodingResult[4])
        spotB = new Spot("", "", new GeocodingResult[4])
        spotC = new Spot("", "", new GeocodingResult[4])
        spotD = new Spot("", "", new GeocodingResult[4])

        /*
        * Set values required to pass tests
        * */
        spotA.id = "v43mdf34dme"
        spotB.id = "vavavrgvacc"
        spotC.id = "evtbtrbtvrv"
        spotD.id = "bbrbvtrvree"

        spotA.city = "Warszawa"
        spotB.city = "Warszawa"
        spotC.city = "Warszawa"
        spotD.city = "Piastów"  //nearby Warszawa but not exactly

        spotA.district = "Warszawa"
        spotB.district = "Warszawa"
        spotC.district = "Warszawa"
        spotD.district = "Warszawa"

        spotA.latitude = 52.227947
        spotA.longitude = 21.042118
        spotB.latitude = 52.233969
        spotB.longitude = 21.052510
        spotC.latitude = 52.236282
        spotC.longitude = 21.057435
        spotD.latitude = 52.239120
        spotD.longitude = 21.063443

        spotRepository.saveAll([spotA, spotB, spotC, spotD])
    }

    def 'repository has 4 spots'() {
        expect:
        spotRepository.findAll().size() == 4
    }

    def 'geocoding to map deserialize test'() {
        when:
        nearestSpot.findNearestSpot(fakeGeocodingResult)

        then:
        def map = nearestSpot.getSearchData()
        map.get("administrative_area_level_2") == "Warszawa"
        map.get("locality") == "Warszawa"
        map.size() == 8

    }

    /**
     * Distance between MY_LAT & MY_LONG and spots
     * 1. closest: spotA
     * 2. spotB
     * 3. spotC
     * 4. farthest: spotD
     * */

    def 'city search - should find one Spot and return it'() {
        when: 'search in Warszawa'
        def founded = nearestSpot.findNearestSpot(fakeGeocodingResult)

        then: 'spotA should be founded'
        founded.getId() == spotA.getId()
    }

    def 'district search - should find one Spot and return it'() {
        when: 'search in Warszawa'
        def founded = nearestSpot.findNearestSpot(geolocation.geocodeCoordinates(new LatLng(52.189444, 20.849737))[0])

        then: 'spotD should be founded'
        founded.getId() == spotD.getId()
    }

    void cleanup() {
        spotRepository.deleteAll()
    }
}
