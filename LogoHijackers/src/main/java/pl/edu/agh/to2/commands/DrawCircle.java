package pl.edu.agh.to2.commands;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.models.MarkerState;
import pl.edu.agh.to2.models.Turtle;

import java.util.ArrayDeque;

public class DrawCircle implements Command {
    private final static Logger log = LogManager.getLogger(DrawCircle.class);
    private AnchorPane pane;
    private Turtle turtle;
    private ArrayDeque<Circle> circles = new ArrayDeque<>();
    private double radius;

    public DrawCircle(AnchorPane pane, Turtle turtle, double radius) {
        this.pane = pane;
        this.turtle = turtle;
        this.radius = radius;
    }

    @Override
    public void execute() {
        log.debug("Executing draw circle");
        Circle circle;
        circle = new Circle(turtle.getX(), turtle.getY(), radius, Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        if (turtle.getMarker().getState() == MarkerState.LOWERED){
            pane.getChildren().add(circle);
        }
        circles.addFirst(circle);
    }

    @Override
    public void revert() {
        log.debug("Reverting draw circle");
        if(!circles.isEmpty()){
            pane.getChildren().remove(circles.pollFirst());
        } else {
            log.warn("Trying to revert unexecuted command draw circle");
        }
    }

    @Override
    public String toString() {
        return String.format("okrąg %f", radius);
    }
}
