package pl.edu.agh.to2.example.command

import javafx.embed.swing.JFXPanel
import javafx.scene.control.TextArea
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

class NPCommandTest extends Specification {

    @Shared controller = new OverviewController()
    @Shared fxPanel = new JFXPanel()

    def eps = 1e-6

    def "Should return proper turtle's position after applying command to move 100 forward"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("np 100")

        then:
        new Double(controller.getTurtle().getX()) closeTo(100, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying command not to move"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("np 0")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying command to move 100 backwards"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("np -100")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying command without value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("np")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying more than one command name in row"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("np np 100")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying command with more than one value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("np 100 100")

        then:
        new Double(controller.getTurtle().getX()) closeTo(0, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should change turtle's position twice"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("np 100 np 100")

        then:
        new Double(controller.getTurtle().getX()) closeTo(200, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }
}
