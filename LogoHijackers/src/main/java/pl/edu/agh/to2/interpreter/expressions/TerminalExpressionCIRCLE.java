package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.DrawCircle;
import pl.edu.agh.to2.commands.UnknownCommand;
import pl.edu.agh.to2.controllers.OverviewController;

import java.util.ArrayDeque;
import java.util.Deque;


public class TerminalExpressionCIRCLE extends TerminalExpressionCOMMAND{

    private OverviewController overviewController;

    public TerminalExpressionCIRCLE(OverviewController overviewController) {
        this.overviewController  = overviewController;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        if (parameters.size() != 1 || !(parameters.peekFirst() instanceof TerminalExpressionNUMBER))
            return new UnknownCommand(this, parameters);
        else
            return new DrawCircle(overviewController.getPane(), overviewController.getTurtle(),
                    ((TerminalExpressionNUMBER) parameters.pop()).getNumber().doubleValue());
    }

    @Override
    public String getRaw() {
        return "okrąg";
    }
}
