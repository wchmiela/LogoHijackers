package pl.edu.agh.to2.example.command

import javafx.scene.layout.AnchorPane
import pl.edu.agh.to2.controllers.OverviewController
import pl.edu.agh.to2.models.MarkerState
import spock.lang.Shared
import spock.lang.Specification

class TurtleMarkerRaiseCommandTest extends Specification {

    @Shared controller = new OverviewController()

    def "Should not paint line with lowered marker"() {
        given:
        controller.initializeTurtle(0, 0, false, 0)
        controller.pane = GroovySpy(AnchorPane.class)
        controller.pane.setPrefWidth(800)
        controller.pane.setPrefWidth(600)
        controller.getTurtle().getMarker().setState(MarkerState.LOWERED)

        when:
        controller.executeCommand("pod")
        controller.executeCommand("np 100")

        then:
        controller.getPane().getChildren().size() == 0
    }
}
