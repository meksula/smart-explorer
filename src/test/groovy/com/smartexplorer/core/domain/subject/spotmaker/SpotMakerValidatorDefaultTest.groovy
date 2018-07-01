package com.smartexplorer.core.domain.subject.spotmaker

import spock.lang.Shared
import spock.lang.Specification

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

class SpotMakerValidatorDefaultTest extends Specification {

    @Shared private SpotMakerValidator validator
    @Shared private SpotMakerForm validForm, invalidForm

    private static String username = "Clerifly"
    private static String password = "445rfcgv"
    private static String name = "Cler"
    private static String surname = "Abrevee"
    private static String email = "cl.abrevee@gmail.com"
    private static int age = 34

    private static String invalidEmail = "someemail.com"

    def setupSpec() {
        validator = new SpotMakerValidatorDefault()

        validForm = new SpotMakerForm(username, password, name, surname, email, age)
        invalidForm = new SpotMakerForm(username, password, name, surname, invalidEmail, age)
    }

    def 'validator should return true -> correct validation'() {
        when:
        def result = validator.validateSpotMaker(validForm)

        then:
        result
    }

    def 'validator should return false -> validation failure'() {
        setup:

        when:
        def result = validator.validateSpotMaker(invalidForm)

        then:
        !result
    }

    def 'pattern test should allow'() {
        given:
        def USERNAME_PATTERN = "[0-9a-zA-Z]{6,25}"

        when:
        Pattern pattern = Pattern.compile(USERNAME_PATTERN)
        Matcher matcher = pattern.matcher("Karol2018")
        def decission = matcher.matches()

        then:
        decission
    }

    def 'pattern test should fail'() {
        given:
        def USERNAME_PATTERN = "[0-9a-zA-Z]{6,25}"

        when:
        Pattern pattern = Pattern.compile(USERNAME_PATTERN)
        Matcher matcher = pattern.matcher("Karol_2018")
        def decission = matcher.matches()

        then:
        !decission
    }

    def 'email pattern test'() {
        given:
        def MAIL_PATTERN = ".+@{1}.+\\.[a-z]{2,}"

        when:
        Pattern pattern = Pattern.compile(MAIL_PATTERN)
        Matcher matcher = pattern.matcher("karolmeksula@onet.pl")
        def decission = matcher.matches()

        then:
        decission
    }

}
