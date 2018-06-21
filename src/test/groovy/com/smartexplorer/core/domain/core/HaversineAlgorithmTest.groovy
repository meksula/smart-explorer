package groovy.com.smartexplorer.core.domain.core

import com.google.maps.model.LatLng
import com.smartexplorer.core.domain.core.Haversine
import com.smartexplorer.core.domain.core.HaversineFormula
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

class HaversineAlgorithmTest extends Specification {

    /**
     * Too points. Real distance between them ~~ 24 km
     * */
    final def POINT_A = new LatLng(51.451995, 22.622653)
    final def POINT_B = new LatLng(51.251142, 22.574685)

    private Haversine haversine;

    def setup() {
        this.haversine = new HaversineFormula()
    }

    def 'haversine formula correctly compute test'() {
        when:
        double distance = haversine.distance(POINT_A, POINT_B)

        then:
        distance == 22.580894541712084
    }

}
