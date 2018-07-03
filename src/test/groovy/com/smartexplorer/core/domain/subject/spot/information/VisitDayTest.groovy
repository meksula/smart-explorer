package com.smartexplorer.core.domain.subject.spot.information

import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 03-07-2018
 * */

class VisitDayTest extends Specification {
    private VisitDay visitDay

    void setup() {
        visitDay = new VisitDay([1, 2, 3, 4, 5] as int[])
        visitDay.setOpen(true)
    }

    def 'toString test'() {
        expect:
        visitDay.toString() == "Open at 1 - 5"
    }

    def 'when is closed test'() {
        setup:
        visitDay.setOpen(false)

        expect:
        visitDay.toString() == "Closed today."
    }

}
