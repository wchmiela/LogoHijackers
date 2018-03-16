package pl.edu.agh.to2.example.command

import javafx.embed.swing.JFXPanel
import javafx.scene.control.TextArea
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Shared
import spock.lang.Specification

class WSCommandTest extends Specification {

    @Shared controller = new OverviewController()
    @Shared fxPanel = new JFXPanel()

    def eps = 1e-6

    def "Should return proper turtle's position after applying command to move 100 backward"() {
        given:
        controller.initializeTurtle(100, 0, false, 0)

        when:
        controller.executeCommand("ws 100")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }

    def "Should not change turtle's position after applying command not to move"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("ws 0")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }

    def "Should not change turtle's position after applying command to move 100 forward"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("ws -100")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }

    def "Should not change turtle's position after applying command without value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("ws")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }

    def "Should not change turtle's position after applying more than one command name in row"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("ws ws 100")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }

    def "Should not change turtle's position after applying command with more than one value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("ws 100 100")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }

    def "Should change turtle's position twice"() {
        given:
        controller.initializeTurtle(200, 0, false, 0)

        when:
        controller.executeCommand("ws 100 ws 100")

        then:
        controller.getTurtle().getX() == 0
        controller.getTurtle().getY() == 0
    }
}

