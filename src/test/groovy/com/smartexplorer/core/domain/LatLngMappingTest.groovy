package com.smartexplorer.core.domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.maps.model.LatLng
import spock.lang.Specification

class LatLngMappingTest extends Specification{

    def 'latLng json mapping test'() {
        given:
        LatLng latLng = new LatLng(34, 34)

        expect:
        println(new ObjectMapper().writeValueAsString(latLng))
    }
}
