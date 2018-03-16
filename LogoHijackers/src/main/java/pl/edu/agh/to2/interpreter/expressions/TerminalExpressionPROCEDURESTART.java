package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.CreateProcedure;
import pl.edu.agh.to2.controllers.OverviewController;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class TerminalExpressionPROCEDURESTART extends TerminalExpressionCOMMAND {

    private OverviewController overviewController;

    public TerminalExpressionPROCEDURESTART(OverviewController overviewController, String raw) {
        this.overviewController = overviewController;
    }

    public TerminalExpressionPROCEDURESTART(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        String procedureName = parameters.peekFirst().getRaw().replaceAll(">", "").replaceAll("<", "");

        ArrayDeque<Expression> arg = new ArrayDeque<>(
                parameters
                .stream()
                .filter(x -> x instanceof TerminalExpressionPROCEDUREVARIABLE)
                .collect(Collectors.toList()));

        return new CreateProcedure(overviewController, procedureName, arg);
    }
}
