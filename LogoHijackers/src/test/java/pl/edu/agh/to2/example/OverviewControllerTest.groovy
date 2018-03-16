package pl.edu.agh.to2.example

import pl.edu.agh.to2.controllers.OverviewController
import pl.edu.agh.to2.models.Turtle
import spock.lang.Specification

import static java.lang.Math.sqrt
import static spock.util.matcher.HamcrestMatchers.closeTo

class OverviewControllerTest extends Specification {

    def eps = 1e-6

    def distance(x1, y1, x2, y2) {
        return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
    }

    def "Check if controllers executes command properly"() {
        setup:
        def controller = new OverviewController()

        when:
        controller.initializeTurtle(0, 0, false, 0)
        controller.executeCommand("np 300")

        then:
        new Double(distance(controller.getTurtle().getX(), controller.getTurtle().getY(), 0, 0)) closeTo(300, eps)
    }
}
