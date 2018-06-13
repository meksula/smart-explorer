package com.smartexplorer.domain.subject

import com.fasterxml.jackson.databind.ObjectMapper
import com.smartexplorer.core.domain.subject.spotmaker.SpotMaker
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

class SpotMakerTest extends Specification {
    private String spotMakerId = "dg454ec63f22"
    private String principalNumber = "9928494849848"
    private String username = "Clerifly"
    private String password = "445rfcgv"
    private String name = "Cler"
    private String surname = "Abrevee"
    private String email = "cl.abrevee@gmail.com"
    private int age = 34
    private String[] auth = ["USER", "SPOTMAKER"]

    def 'dto creation and JSON serialization test'() {
        setup:
        def spotMaker = new SpotMaker()
        spotMaker.setSpotMakerId(spotMakerId)
        spotMaker.setPrincipalNumber(principalNumber)
        spotMaker.setUsername(username)
        spotMaker.setPassword(password)
        spotMaker.setName(name)
        spotMaker.setSurname(surname)
        spotMaker.setEmail(email)
        spotMaker.setAge(age)
        spotMaker.setAuthorities(auth)

        when:
        def json = new ObjectMapper().writeValueAsString(spotMaker)

        then:
        println json
        json == "{\"spotMakerId\":\"dg454ec63f22\",\"confirmed\":false,\"principalNumber\":\"9928494849848\",\"username\":\"Clerifly\",\"password\":\"445rfcgv\",\"name\":\"Cler\",\"surname\":\"Abrevee\",\"email\":\"cl.abrevee@gmail.com\",\"age\":34,\"authorities\":[{\"authority\":\"USER\"},{\"authority\":\"SPOTMAKER\"}],\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}"
    }

    def 'spotmaker builder pattern test'() {
        setup:
        def spotMaker = new SpotMaker.SpotMakerBuilder()
                .spotMakerId()
                .username(username)
                .password(password)
                .name(name)
                .surname(surname)
                .email(email)
                .age(age)
                .authorities(auth)
                .build()

        expect:
        this.username == spotMaker.getUsername()
        this.password == spotMaker.getPassword()
        this.name == spotMaker.getName()
        this.surname == spotMaker.getSurname()
        this.email == spotMaker.getEmail()
        this.age == spotMaker.getAge()
        spotMaker.getAuthorities().size() == 2
    }

}
