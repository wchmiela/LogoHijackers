package pl.edu.agh.to2.example.models

import pl.edu.agh.to2.models.Marker
import pl.edu.agh.to2.models.MarkerState
import spock.lang.Specification

class MarkerTest extends Specification {

    def "Should get proper marker state #1"() {
        given:
        def marker = new Marker()

        when:
        marker.setState(MarkerState.RAISED)

        then:
        marker.getState() == MarkerState.RAISED
    }

    def "Should get proper marker state #2"() {
        given:
        def marker = new Marker()

        when:
        marker.setState(MarkerState.LOWERED)

        then:
        marker.getState() == MarkerState.LOWERED
    }
}
