package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.TurtleMarkerLower;
import pl.edu.agh.to2.commands.UnknownCommand;
import pl.edu.agh.to2.controllers.OverviewController;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionLOWER extends TerminalExpressionCOMMAND {

    private OverviewController overviewController;

    public TerminalExpressionLOWER(OverviewController overviewController) {
        this.overviewController  = overviewController;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        return parameters.isEmpty() ? new TurtleMarkerLower(overviewController.getTurtle().getMarker()) :
                new UnknownCommand(this, parameters);
    }

    @Override
    public String getRaw() {
        return "opu";
    }
}
