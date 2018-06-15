package com.smartexplorer.core.domain.geolocation

import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

class BasicAddressTest extends Specification {
    private final String CITY = "Lublin"
    private final String STREET = "Grodzka"
    private final String BUILDING_NUMBER = "46"

    private final String EXPECT = "Lublin, Grodzka 46"

    def 'basic address toString method test'() {
        setup:
        def address = new BasicAddress(CITY, STREET, BUILDING_NUMBER)

        when:
        String fullAddress = address.toString()

        then:
        fullAddress == EXPECT

    }

}
