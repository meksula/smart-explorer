package com.smartexplorer.core.domain.subject.spot.stats

import com.smartexplorer.core.domain.subject.explorers.Visit
import spock.lang.Specification

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month

/**
 * @Author
 * Karol Meksuła
 * 01-07-2018
 * */

class MapTest extends Specification {

    def 'check if map can storage values and manipulate them'() {
        setup:
        Map<Integer, Long> map = new LinkedHashMap<>()

        when:
        map.put(5, 35L)
        map.put(5, (35L + 15L))

        then:
        map.get(5) == 50
    }

    def 'week day test'() {
        setup:
        Map<DayOfWeek, Long> map = new LinkedHashMap<>()

        for (int i = 1; i < 8; i++) {
            map.put(DayOfWeek.of(i), 0L)
        }

        expect:
        map.size() == 7

    }

}
