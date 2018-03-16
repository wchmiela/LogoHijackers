package pl.edu.agh.to2.interpreter.expressions;

import javafx.scene.layout.AnchorPane;
import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.Move;
import pl.edu.agh.to2.commands.UnknownCommand;
import pl.edu.agh.to2.controllers.OverviewController;
import pl.edu.agh.to2.models.Turtle;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionMOVE extends TerminalExpressionCOMMAND {
    private OverviewController overviewController;
    private String raw = null;
    private MoveType type;
    private Direction direction;

    public TerminalExpressionMOVE(OverviewController overviewController, MoveType type, Direction direction) {
        this.overviewController = overviewController;
        this.type = type;
        this.direction = direction;
        switch (direction) {
            case LEFT:
                raw = "lw";
                break;
            case RIGHT:
                raw = "pw";
                break;
            case FORWARD:
                raw = "np";
                break;
            case BACKWARD:
                raw = "ws";
                break;
        }
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        if (parameters.size() != 1 || !(parameters.peekFirst() instanceof TerminalExpressionNUMBER))
            return new UnknownCommand(this, parameters);

        Command command;
        double val = direction.getValue() * ((TerminalExpressionNUMBER) parameters.pop()).getNumber().doubleValue();
        switch (type){
            case DISTANCE:
                command = new Move(overviewController.getPane(), overviewController.getTurtle(),
                    overviewController.getConsole(),
                    val, 0, String.format("%s %f", raw, Math.abs(val)));
                break;
            case ANGLE:
                command = new Move(overviewController.getPane(), overviewController.getTurtle(),
                        overviewController.getConsole(),
                        0, val, String.format("%s %f", raw, Math.abs(val)));
                break;
            default:
                command = new UnknownCommand(this, parameters);
        }
        return command;
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
