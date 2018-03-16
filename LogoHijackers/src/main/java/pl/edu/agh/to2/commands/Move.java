package pl.edu.agh.to2.commands;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.controllers.OverviewController;
import pl.edu.agh.to2.models.CoordChange;
import pl.edu.agh.to2.models.MarkerState;
import pl.edu.agh.to2.models.Turtle;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Move implements Command {
    private static final Logger log = LogManager.getLogger(Move.class);
    private Turtle turtle;
    private AnchorPane pane;
    private TextArea console;
    private double steps;
    private double direction;
    private ArrayDeque<Line> lines = new ArrayDeque<>();
    private String raw;

    public Move(AnchorPane pane, Turtle turtle, TextArea console, double steps, double direction) {
        this(pane, turtle, console, steps, direction, "MoveCommand");
    }

    public Move(AnchorPane pane, Turtle turtle, TextArea console, double steps, double direction, String raw) {
        if (pane == null) {
            pane = new AnchorPane();
            pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        this.turtle = turtle;
        this.console = console;
        this.pane = pane;
        this.steps = steps;
        this.direction = direction;
        this.raw = raw;
    }

    @Override
    public void execute() {
        log.debug("Executing command move");
        Line line;
        CoordChange change = turtle.move(steps, direction);
        line = new Line(
                Math.max(Math.min(change.getOldX(), pane.getPrefWidth()), 0),
                Math.max(Math.min(change.getOldY(), pane.getPrefHeight()), 0),
                Math.max(Math.min(change.getX(), pane.getPrefWidth()), 0),
                Math.max(Math.min(change.getY(), pane.getPrefHeight()), 0));
        lines.addFirst(line);
        if (line.getEndX() != change.getX() || line.getEndY() != change.getY()) {
            log.debug("Reverting changes because of crossing the borders");
            turtle.setPosition(line.getStartX(), line.getStartY());
            if (console != null) {
                console.appendText("\n# Warning: You have passed the borders, \n# turtle's position will remain the same");
            }
        } else if (turtle.getMarker().getState() == MarkerState.LOWERED) {
            pane.getChildren().add(line);
        }
    }

    @Override
    public void revert() {
        log.debug("Reverting command move");
        if (!lines.isEmpty()) {
            Line line = lines.pollFirst();
            pane.getChildren().remove(line);
            turtle.setPosition(line.getStartX(), line.getStartY());
            turtle.move(0, -direction);
        } else {
            log.warn("Trying to revert unexecuted command move");
        }
    }

    @Override
    public String toString() {
        return raw;
    }
}
