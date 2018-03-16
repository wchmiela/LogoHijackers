package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.UnknownCommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionDEFAULT implements Expression {
    private String raw;

    public TerminalExpressionDEFAULT(String raw) {
        this.raw = raw;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        return new UnknownCommand(this, parameters);
    }

    @Override
    public String getRaw() {
        return String.format(">>>%s<<<", raw);
    }
}
