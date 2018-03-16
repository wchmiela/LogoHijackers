package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.UnknownCommand;

import java.util.Deque;

public class TerminalExpressionLISTEND extends TerminalExpressionPAR{
    @Override
    public Command interpret(Deque<Expression> s) {
        return new UnknownCommand(this);
    }

    @Override
    public String getRaw() {
        return "]";
    }
}
