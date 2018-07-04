package com.smartexplorer.core.domain.subject.spot

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.maps.model.GeocodingResult
import com.smartexplorer.core.domain.subject.spot.stats.SpotStatistics
import org.joda.time.LocalDateTime
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

class SpotTest extends Specification {
    private final String spotMakerId = "dk223423mrd923dm912d2d3"
    private final String creationDate = LocalDateTime.now()
    private final String name = "Muzeum lubelskie"
    private final String description = "Description of place..."
    private final SpotStatistics spotStatistics = new SpotStatistics()
    private final GeocodingResult[] geocodingResult = [new GeocodingResult(), new GeocodingResult()]

    def 'spot correct creation test'() {
        setup:
        def spot = new Spot(spotMakerId, creationDate, geocodingResult)
        spot.setName(name)
        spot.setDescription(description)
        spot.setSearchEnable(true)

        and:
        println(new ObjectMapper().writeValueAsString(spot))

        expect:
        !creationDate.isEmpty()
        spotStatistics != null
        geocodingResult != null
        spotMakerId == spot.getSpotMakerId()
        name == spot.getName()
        description == spot.getDescription()
        spot.searchEnable
    }

}
