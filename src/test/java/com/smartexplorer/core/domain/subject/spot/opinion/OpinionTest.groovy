package com.smartexplorer.core.domain.subject.spot.opinion

import com.fasterxml.jackson.databind.ObjectMapper
import com.smartexplorer.core.repository.SpotOpinionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

@SpringBootTest
class OpinionTest extends Specification {
    private Opinion opinion
    private String explorerId = "3423894238490723359"
    private String spotId = "2fnjn82dndij392d3092d"
    private double rate = 5
    private String content = "Very nice place!"

    @Autowired
    private SpotOpinionsRepository spotOpinionsRepository

    void setup() {
        opinion = new Opinion(explorerId, spotId, rate, content)
        opinion.initDate()
    }

    def 'opinion JSON serialize test'() {
        when:
        def json = new ObjectMapper().writeValueAsString(opinion)

        then:
        println(json)
    }

    def 'should save correctly and append id'() {
        when:
        def updated = spotOpinionsRepository.save(opinion)

        then:
        updated.getId() != null

        spotOpinionsRepository.findById(updated.getId()).isPresent()
    }

    def cleanup() {
        spotOpinionsRepository.deleteAll()
    }

}
