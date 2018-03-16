package pl.edu.agh.to2.example.command

import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Shared
import spock.lang.Specification

class TurtleHideCommandTest extends Specification {

    @Shared controller = new OverviewController()

    def "Should change turtle's visibility"() {
        given:
        controller.initializeTurtle(0, 0, true, 0)

        when:
        controller.executeCommand("sż")

        then:
        controller.getTurtle().isVisible() == false
    }

    def "Should not change turtle's visibility"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("sż")

        then:
        controller.getTurtle().isVisible() == false
    }
}
