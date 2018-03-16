package pl.edu.agh.to2.example

import pl.edu.agh.to2.commands.Move
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

class MoveTest extends Specification {

    def eps = 1e-6

    def "Should return to start position after performing execute and revert operation"() {
        given:
        def controller = new OverviewController()
        controller.initializeTurtle(5, 5, false, 0)

        def move = new Move(null, controller.getTurtle(), null, 100, 0)

        when:
        move.execute()
        move.revert()

        then:
        new Double(controller.getTurtle().getX()) closeTo(5, eps)
        new Double(controller.getTurtle().getY()) closeTo(5, eps)
    }

    def "Should not move after performing only revert operation"() {
        given:
        def controller = new OverviewController()
        controller.initializeTurtle(5, 5, false, 0)

        def move = new Move(null, controller.getTurtle(), null, 100, 0)

        when:
        move.revert()

        then:
        new Double(controller.getTurtle().getX()) closeTo(5, eps)
        new Double(controller.getTurtle().getY()) closeTo(5, eps)
    }
}
