package com.smartexplorer.core.repository

import com.google.maps.model.GeocodingResult
import com.smartexplorer.core.domain.subject.spot.Spot
import com.smartexplorer.core.domain.subject.spot.stats.SpotStatistics
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

@SpringBootTest
class SpotRepositoryTest extends Specification {
    private final String spotMakerId = "dk223423mrd923dm912d2d3"
    private final String creationDate = LocalDateTime.now()
    private final String name = "Muzeum lubelskie"
    private final String description = "Description of place..."
    private final SpotStatistics spotStatistics = new SpotStatistics()
    private final GeocodingResult geocodingResult = new GeocodingResult()
    private Spot spot

    @Autowired
    private SpotRepository spotRepository

    def 'instantiate test'() {
        expect:
        spotRepository != null
    }

    void setup() {
        spot = new Spot(spotMakerId, creationDate, geocodingResult)
        spot.setName(name)
        spot.setDescription(description)
        spot.setSpotStatistics(spotStatistics)
        spot.setSearchEnable(true)
    }

    def 'should be able to save and found Spot - serialize test'() {
        when:
        spotRepository.save(spot)

        then:
        Optional<Spot> opt = spotRepository.findByName(name)
        opt.isPresent()
        opt.get().id != null
    }

    void cleanup() {
        spotRepository.deleteAll()
    }
}
