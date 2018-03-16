package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.NopCommand;

import java.util.Deque;

public class TerminalExpressionLINEBREAKER implements Expression {
    @Override
    public Command interpret(Deque<Expression> s) {
        return new NopCommand();
    }

    @Override
    public String getRaw() {
        return "\\";
    }
}
