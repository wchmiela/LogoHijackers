package pl.edu.agh.to2.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.controllers.OverviewController;
import pl.edu.agh.to2.models.Turtle;

import java.util.ArrayDeque;

public class TurtleHide implements Command {
    private static final Logger log = LogManager.getLogger(TurtleHide.class);
    private Turtle turtle;
    private ArrayDeque<Boolean> prevStates = new ArrayDeque<>();

    public TurtleHide(Turtle turtle) {
        this.turtle = turtle;
    }

    @Override
    public void execute() {
        log.debug("Executing command turtle hide");
        this.prevStates.addFirst(turtle.isVisible());
        this.turtle.setVisible(false);
    }

    @Override
    public void revert() {
        log.debug("Reverting command turtle hide");
        if (!prevStates.isEmpty()){
            this.turtle.setVisible(prevStates.pollFirst());
        } else {
            log.warn("Trying to revert unexecuted command turtle hide");
        }
    }

    @Override
    public String toString() {
        return "sż";
    }
}
