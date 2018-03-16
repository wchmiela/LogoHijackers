package pl.edu.agh.to2.example.command

import javafx.scene.layout.AnchorPane
import pl.edu.agh.to2.controllers.OverviewController
import pl.edu.agh.to2.models.MarkerState
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.IntStream

class TurtleMarkerLowerCommandTest extends Specification {

    @Shared controller = new OverviewController()


    def "Should paint 1 line with lowered marker"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.pane = GroovySpy(AnchorPane.class)
        controller.pane.setPrefWidth(800)
        controller.pane.setPrefWidth(600)
        controller.getTurtle().getMarker().setState(MarkerState.RAISED)

        when:
        controller.executeCommand("opu")
        controller.executeCommand("np 1")

        then:
        controller.getPane().getChildren().size() == 1
    }

    def "Should paint 100 line with lowered marker"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.pane = GroovySpy(AnchorPane.class)
        controller.pane.setPrefWidth(800)
        controller.pane.setPrefWidth(600)
        controller.getTurtle().getMarker().setState(MarkerState.RAISED)
        when:
        controller.executeCommand("opu")
        IntStream.range(0, 100).each { i -> controller.executeCommand("np 1") }

        then:
        controller.getPane().getChildren().size() == 100
    }
}
