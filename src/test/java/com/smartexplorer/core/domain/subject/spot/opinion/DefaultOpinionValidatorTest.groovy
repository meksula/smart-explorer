package com.smartexplorer.core.domain.subject.spot.opinion

import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 18-06-2018
 * */

class DefaultOpinionValidatorTest extends Specification {
    private Opinion opinion
    private OpinionValidator validator = new DefaultOpinionValidator()
    private final String text = "Ta opinie to tylko test. Ma sprawdzić ile znaków wystarczy do wystawienia odpowiedniej opinii." +
            "Trzeba zabezpieczyć endpoint przed przesłaniem masy bezsensownego tekstu." +
            "Myślę, że tyle znaków wystarczy."
    private double validRate = 5

    void setup() {
        opinion = new Opinion("354364237", "ftg453g3eg3", validRate, text)
    }

    def 'validator allow to save'() {
        expect:
        validator.validateOpinion(opinion)
    }

    def 'validator NOT allow to save'() {
        setup:
        opinion.rate = 6

        expect:
        !validator.validateOpinion(opinion)
    }

}
