package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.CloseProcedure;
import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.CreateProcedure;
import pl.edu.agh.to2.controllers.OverviewController;

import java.util.Deque;

public class TerminalExpressionPROCEDURESTOP extends TerminalExpressionCOMMAND {

    private OverviewController overviewController;
    private String raw;

    public TerminalExpressionPROCEDURESTOP(OverviewController overviewController, String raw) {
        this.overviewController = overviewController;
        this.raw = raw;
    }

    public TerminalExpressionPROCEDURESTOP(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        return new CloseProcedure(overviewController);
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
