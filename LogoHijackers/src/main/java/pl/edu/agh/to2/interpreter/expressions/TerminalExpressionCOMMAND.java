package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;

import java.util.Deque;

public class TerminalExpressionCOMMAND implements Expression {
    @Override
    public Command interpret(Deque<Expression> s) {
        return null;
    }

    @Override
    public String getRaw() {
        return null;
    }
}
