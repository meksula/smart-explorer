package com.smartexplorer.domain.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.smartexplorer.core.domain.core.CustomPreferences
import com.smartexplorer.core.domain.core.RequestObject
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 10-06-2018
 * */

class RequestObjectTest extends Specification {

    final double accurancy = 3434
    final double latitude = 23.4442222
    final double longitude = 24.322122
    private final customPreferences = new CustomPreferences()

    def 'class instance creation and JSON mapping test'() {
        setup:
        def requestObject = new RequestObject(accurancy, latitude, longitude, customPreferences)

        when:
        def json = new ObjectMapper().writeValueAsString(requestObject)

        then:
        json.equals("{\"accurancy\":3434.0,\"latitude\":23.4442222,\"longitude\":24.322122,\"customPreferences\":{}}")
    }

}
