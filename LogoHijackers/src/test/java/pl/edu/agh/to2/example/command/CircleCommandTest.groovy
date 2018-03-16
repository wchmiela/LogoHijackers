package pl.edu.agh.to2.example.command

import javafx.scene.layout.AnchorPane
import pl.edu.agh.to2.controllers.OverviewController
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.IntStream

class CircleCommandTest extends Specification {

    @Shared
            controller = new OverviewController()

    def "should paint circle"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.pane = GroovySpy(AnchorPane.class)
        controller.pane.setPrefWidth(800)
        controller.pane.setPrefWidth(600)

        when:
        controller.executeCommand("okrąg 100")

        then:
        controller.pane.getChildren().size() == 1
    }

    def "should paint 100 circles"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.pane = GroovySpy(AnchorPane.class)
        controller.pane.setPrefWidth(800)
        controller.pane.setPrefWidth(600)

        when:
        IntStream.range(0, 100).each { i -> controller.executeCommand("okrąg " + (100 - i)) }

        then:
        controller.pane.getChildren().size() == 100
    }
}
