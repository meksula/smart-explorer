package com.smartexplorer.core.domain.subject

import com.fasterxml.jackson.databind.ObjectMapper
import com.smartexplorer.core.domain.subject.spotmaker.SpotMakerForm
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

class SpotMakerFormTest extends Specification {
    private String username = "Clerifly"
    private String password = "445rfcgv"
    private String name = "Cler"
    private String surname = "Abrevee"
    private String email = "cl.abrevee@gmail.com"
    private int age = 34

    def 'dto creation and JSON serialization test'() {
        setup:
        def spotMakerForm = new SpotMakerForm(username, password, name, surname, email, age)

        when:
        def json = new ObjectMapper().writeValueAsString(spotMakerForm)

        then:
        json == "{\"username\":\"Clerifly\",\"password\":\"445rfcgv\",\"name\":\"Cler\"," +
                "\"surname\":\"Abrevee\",\"email\":\"cl.abrevee@gmail.com\",\"age\":34}"
    }
}
