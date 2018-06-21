package com.smartexplorer.core.domain.core

import com.google.maps.model.LatLng
import com.smartexplorer.core.domain.geolocation.BasicAddress
import com.smartexplorer.core.domain.geolocation.Geolocation
import com.smartexplorer.core.domain.geolocation.GoogleMapsGeolocation
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 20-06-2018
 * */

class AddressDeserializerTest extends Specification {
    private BasicAddress village = new BasicAddress("Serniki", "Serniki", "15")
    private BasicAddress town = new BasicAddress("Lublin", "Krakowskie przedmieście", "34")
    private BasicAddress city = new BasicAddress("Warszawa", "Grzybowska", "7")

    private Geolocation geolocation

    void setup() {
        geolocation = new GoogleMapsGeolocation()
    }


    def 'village deserialize test'() {
        setup:
        def result = geolocation.geolocateByAddress(village)[0]

        when:
        def map = AddressDeserializer.VILLAGE.deserialize(result)

        then:
        map.size() == 6
    }

    def 'town deserialize test'() {
        setup:
        def result = geolocation.geolocateByAddress(town)[0]

        when:
        def map = AddressDeserializer.TOWN.deserialize(result)

        then:
        map.size() == 7
    }

    def 'city deserialize test'() {
        setup:
        def result = geolocation.geolocateByAddress(city)[0]

        when:
        def map = AddressDeserializer.CITY.deserialize(result)

        then:
        map.size() == 8
    }

    def 'random VILLAGE coordinates deserialize test'() {
        setup:
        def result = geolocation.geocodeCoordinates(new LatLng(51.492155, 22.537002))[0]

        when:
        def map = AddressDeserializer.VILLAGE.deserialize(result)

        then:
        map.size() == 6
    }

    def 'random TOWN coordinates deserialize test' () {
        setup:
        def result = geolocation.geocodeCoordinates(new LatLng(51.241502, 22.563128))[0]

        when:
        def map = AddressDeserializer.TOWN.deserialize(result)

        then:
        map.size() == 7
    }

    def 'random CITY coordinates deserialize test'() {
        setup:
        def result = geolocation.geocodeCoordinates(new LatLng(50.067495, 19.945166))[0]

        when:
        def map = AddressDeserializer.CITY.deserialize(result)

        then:
        map.size() == 8
    }

}
