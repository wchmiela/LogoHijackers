package pl.edu.agh.to2.example.command

import javafx.embed.swing.JFXPanel
import javafx.scene.control.TextArea
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

class PWCommandTest extends Specification {

    @Shared controller = new OverviewController()
    @Shared fxPanel = new JFXPanel()

    def eps = 1e-6

    def "Should return -1 degree angle after applying command to rotate clockwise 1 degree"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("pw 1")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(-1, eps)
    }

    def "Should return 0 degree angle after applying command to rotate clockwise 360 degree"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("pw 360")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(0, eps)
    }

    def "Should return -1 degree angle after applying command to rotate clockwise 360 degree"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("pw 361")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(-1, eps)
    }

    def "Should not change turtle's angle after applying command with negative angle value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("pw -1")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(0, eps)
    }

    def "Should not change turtle's angle after applying pw command without value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("pw")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying more than one command name in row"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("pw pw 100")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(0, eps)
    }

    def "Should not change turtle's position after applying command with more than one value"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.console = GroovySpy(TextArea.class)

        when:
        controller.executeCommand("pw 100 100")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(0, eps)
    }

    def "Should change turtle's angle twice"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)

        when:
        controller.executeCommand("pw 90 pw 90")

        then:
        new Double(controller.getTurtle().getAngle()) closeTo(-180, eps)
    }
}
