package com.smartexplorer.core.domain.subject.spot.opinion

import com.smartexplorer.core.SmarteplorerSpotmakerApplication
import com.smartexplorer.core.repository.SpotOpinionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

@SpringBootTest(classes = SmarteplorerSpotmakerApplication)
class OpinionProviderTest extends Specification {
    private final String SPOT_ID = "qnvinwxm34m24d4dm2"
    private final String EXPLORER_ID = "i3n29ds23m9dx23d"

    private Opinion opinionA = new Opinion(EXPLORER_ID, SPOT_ID, 1, "worst")
    private Opinion opinionB = new Opinion(EXPLORER_ID, SPOT_ID, 2, "worst")
    private Opinion opinionC = new Opinion(EXPLORER_ID, SPOT_ID, 3, "content...")
    private Opinion opinionD = new Opinion(EXPLORER_ID, SPOT_ID, 4, "best")
    private Opinion opinionE = new Opinion("", SPOT_ID, 5, "best")
    private Opinion opinionF = new Opinion("", SPOT_ID, 3.5, "content...")

    @Autowired
    private SpotOpinionsRepository spotOpinionsRepository

    @Autowired
    private OpinionsProvider opinionsProvider

    def setup() {
        opinionA.initDate()
        opinionB.initDate()
        opinionC.initDate()
        opinionD.initDate()
        opinionE.initDate()
        opinionF.initDate()

        spotOpinionsRepository.saveAll([opinionA,opinionB,opinionC,opinionD,opinionE,opinionF])
    }

    def 'opinion should be saved'() {
        expect:
        spotOpinionsRepository.findAll().size() == 6
    }

    def 'last added get test'() {
        when:
        def list = opinionsProvider.getLastAdded(SPOT_ID, 1)

        then:
        list.size() == 1
        list.get(0).getDate() == opinionF.getDate()
    }

    def 'WORST opinions get test'() {
        when:
        def list = opinionsProvider.getWorstOpinions(SPOT_ID, 2)

        then:
        list.size() == 2
        list.get(0).getContent() == "worst"
        list.get(1).getContent() == "worst"
    }

    def 'BEST opinions get test'() {
        when:
        def list = opinionsProvider.getBestOpinions(SPOT_ID, 2)

        then:
        list.size() == 2
        list.get(0).getContent() == "best"
        list.get(1).getContent() == "best"
    }

    def 'getExplorersOpinions test'() {
        expect:
        opinionsProvider.getExplorersOpinions(EXPLORER_ID).size() == 4
    }

    def 'getLastAdded test'() {
        expect:
        opinionsProvider.getAllOpinions(SPOT_ID).size() == 6
    }

    def cleanup() {
        spotOpinionsRepository.deleteAll()
    }

}
