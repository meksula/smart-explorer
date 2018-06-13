package com.smartexplorer.repository

import com.smartexplorer.core.repository.id.DefaultUniqueDatabaseIdCreator
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 11-06-2018
 * */

class DefaultUniqueDatabaseIdCreatorTest extends Specification {

    def 'unique ID should has 25 char size'() {
        given:
        def idCreator = new DefaultUniqueDatabaseIdCreator()

        when:
        def result = idCreator.assignUniqueId()

        then:
        println result
        result.length() == 25
    }

}
