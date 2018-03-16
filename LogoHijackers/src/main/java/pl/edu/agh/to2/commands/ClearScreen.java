package pl.edu.agh.to2.commands;

import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.controllers.OverviewController;

import java.util.ArrayDeque;

public class ClearScreen implements Command {
    private static final Logger log = LogManager.getLogger(ClearScreen.class);

    private OverviewController overviewController;
    private ArrayDeque<ArrayDeque<Node>> childrenNodes = new ArrayDeque<>();

    public ClearScreen(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

    @Override
    public void execute() {
        log.debug("Executing clear screen command");
        ArrayDeque<Node> children = overviewController.clearCanvas();
        childrenNodes.addFirst(children);
    }

    @Override
    public void revert() {
        log.debug("Reverting command clear screen");
        if (!childrenNodes.isEmpty()){
            this.overviewController.getPane().getChildren().addAll(childrenNodes.pollFirst());
        } else {
            log.warn("Reverting not executed command clear screen");
        }
    }

    @Override
    public String toString() {
        return "czyść";
    }
}
