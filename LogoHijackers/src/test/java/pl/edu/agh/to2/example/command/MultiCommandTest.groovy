package pl.edu.agh.to2.example.command

import javafx.scene.layout.AnchorPane
import pl.edu.agh.to2.commands.Move
import pl.edu.agh.to2.commands.MultiCommand
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

class MultiCommandTest extends Specification {

    def eps = 1e-6

    def "Should return proper position after execute all given operations"() {
        given:
        def controller = new OverviewController()
        controller.initializeTurtle(0, 0, false, 0)

        def commands = new ArrayDeque()
        commands << new Move(null, controller.getTurtle(), null,  100, 0)
        commands << new Move(null, controller.getTurtle(), null,  50, 0)
        commands << new Move(null, controller.getTurtle(), null,  25, 0)
        commands << new Move(null, controller.getTurtle(), null,  25, 0)

        def multiCommand = new MultiCommand(commands)

        when:
        multiCommand.execute()

        then:
        new Double(controller.getTurtle().getX()) closeTo(200, eps)
        new Double(controller.getTurtle().getY()) closeTo(0, eps)
    }

    def "Should return to start postion after execute and revert operations"() {
        given:
        def controller = new OverviewController()
        controller.initializeTurtle(5, 5, false, 0)

        def commands = new ArrayDeque()
        commands << new Move(null, controller.getTurtle(), null, 100, 0)
        commands << new Move(null, controller.getTurtle(), null, 50, 0)
        commands << new Move(null, controller.getTurtle(), null, 25, 0)
        commands << new Move(null, controller.getTurtle(), null, 25, 0)

        def multiCommand = new MultiCommand(commands)

        when:
        multiCommand.execute()
        multiCommand.revert()

        then:
        new Double(controller.getTurtle().getX()) closeTo(5, eps)
        new Double(controller.getTurtle().getY()) closeTo(5, eps)
    }
}
