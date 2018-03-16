package pl.edu.agh.to2.example.command

import javafx.embed.swing.JFXPanel
import javafx.scene.control.TextArea
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

class LoopCommandTest extends Specification {

    @Shared controller = new OverviewController()
    @Shared fxPanel = new JFXPanel()

    def eps = 1e-6

    def "Should perform one move"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("powtórz 1 [np 100]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(100, eps)
    }

    def "Should perform two moves"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("powtórz 2 [np 100]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(200, eps)
    }

    def "Should perform 999 moves"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("powtórz 999 [np 1]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(999, eps)
    }

    def "Should not change the location when perform negative amount of loop executions"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("powtórz -1 [np 100]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
    }

    def "Should peform 9 moves because of two nested loops"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("powtórz 3 [ powtórz 3 [ np 5 ]]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(45, eps)
    }

    def "Should peform 2 independent loops"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("powtórz 2 [ np 10 ] powtórz 2 [ ws 10 ]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
    }

    def "Should peform 2 independent loops with one independent operation between them"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("powtórz 2 [ np 10 ] np 77 powtórz 2 [ ws 10 ]")

        then:
        new Double(controller.getTurtle().getX()) closeTo(77, eps)
    }
}
