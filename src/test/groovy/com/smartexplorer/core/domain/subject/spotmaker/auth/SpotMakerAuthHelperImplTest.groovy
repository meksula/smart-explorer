package com.smartexplorer.core.domain.subject.spotmaker.auth

import com.google.maps.model.GeocodingResult
import com.smartexplorer.core.domain.subject.spot.Spot
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker
import com.smartexplorer.core.exception.SpotLocalizeException
import com.smartexplorer.core.repository.SpotMakerRepository
import com.smartexplorer.core.repository.SpotRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

@SpringBootTest
class SpotMakerAuthHelperImplTest extends Specification {
    private SpotMaker spotMaker
    private Spot spot
    private final String USERNAME = "MikolajKopernik"
    private final String SPOT_MAKER_ID = "w2398nd23dn32d"
    private final String SPOT_ID = "23982d9823n02d023d"

    @Autowired
    private SpotMakerRepository spotMakerRepository

    @Autowired
    private SpotRepository spotRepository

    @Autowired
    private SpotMakerAuthHelper spotMakerAuthHelper

    void setup() {
        spotMaker = new SpotMaker.SpotMakerBuilder().spotMakerId(SPOT_MAKER_ID).build()
        spotMaker.setUsername(USERNAME)

        spot = new Spot(SPOT_MAKER_ID, LocalDate.now() as String, new GeocodingResult[2])
        spot.id = SPOT_ID

        spotMakerRepository.save(spotMaker)
        spotRepository.save(spot)
    }

    def 'auth should be true test'() {
        expect:
        spotMakerAuthHelper.hasSpotRights(SPOT_ID, USERNAME)
    }

    def 'spotMaker has no right test'() {
        setup:
        def spot2 = new Spot(SPOT_MAKER_ID, LocalDate.now() as String, new GeocodingResult[2])
        spot2.id = "32fdswf43f3ddd"

        when:
        spotMakerAuthHelper.hasSpotRights(spot2.getId(), USERNAME)

        then:
        thrown SpotLocalizeException
    }

    void cleanup() {
        spotMakerRepository.deleteAll()
        spotRepository.deleteAll()
    }
}
