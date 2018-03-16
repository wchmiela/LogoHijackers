package pl.edu.agh.to2.example.models

import pl.edu.agh.to2.models.TurtleState
import spock.lang.Specification
import static spock.util.matcher.HamcrestMatchers.closeTo

class TurtleStateTest extends Specification {

    def eps = 1e-6

    def "Should return proper TurtleState #1"() {
        given:
        def state

        when:
        state = new TurtleState(1, 1, 0)

        then:
        new Double(state.getX()) closeTo(1, eps)
        new Double(state.getY()) closeTo(1, eps)
        new Double(state.getAngle()) closeTo(0, eps)
    }

    def "Should return proper TurtleState #2"() {
        given:
        def state

        when:
        state = new TurtleState(-1, -1, 0)

        then:
        new Double(state.getX()) closeTo(-1, eps)
        new Double(state.getY()) closeTo(-1, eps)
        new Double(state.getAngle()) closeTo(0, eps)
    }

    def "Should return proper TurtleState angle"() {
        given:
        def state

        when:
        state = new TurtleState(0, 0, 361)

        then:
        new Double(state.getAngle()) closeTo(361, eps)
    }
}
