package pl.edu.agh.to2.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.controllers.OverviewController;
import pl.edu.agh.to2.models.Turtle;
import pl.edu.agh.to2.models.TurtleState;

import java.util.ArrayDeque;

public class MoveToStart implements Command{
    private final static Logger log = LogManager.getLogger(MoveToStart.class);
    private OverviewController overviewController;
    private ArrayDeque<TurtleState> turtleStates = new ArrayDeque<>();

    public MoveToStart(OverviewController overviewController) {
        this.overviewController = overviewController;
        Turtle turtle = overviewController.getTurtle();
        turtleStates.addFirst(new TurtleState(turtle.getX(), turtle.getY(), turtle.getAngle()));
    }

    @Override
    public void execute() {
        log.debug("Executing command move to start");
        this.overviewController.setDefaultPosition();
    }

    @Override
    public void revert() {
        log.debug("Reverting command move to start");
        if (!turtleStates.isEmpty()){
            overviewController.getTurtle().setPosition(turtleStates.pollFirst());
        } else {
            log.warn("Trying to revert unexecuted command move to start");
        }
    }

    @Override
    public String toString() {
        return "wróć";
    }
}
