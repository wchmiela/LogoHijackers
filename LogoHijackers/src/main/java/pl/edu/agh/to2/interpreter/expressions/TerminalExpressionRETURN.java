package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.MoveToStart;
import pl.edu.agh.to2.commands.UnknownCommand;
import pl.edu.agh.to2.controllers.OverviewController;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionRETURN extends TerminalExpressionCOMMAND {

    private OverviewController overviewController;

    public TerminalExpressionRETURN(OverviewController overviewController) {
        this.overviewController  = overviewController;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        return parameters.isEmpty() ? new MoveToStart(overviewController) :
                new UnknownCommand(this, parameters);
    }

    @Override
    public String getRaw() {
        return "wróć";
    }
}
