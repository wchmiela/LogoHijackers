package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.UnknownCommand;

import java.util.Deque;

public class TerminalExpressionNUMBER implements Expression {
    private Number number;
    private String raw;

    public TerminalExpressionNUMBER(Number number, String raw) {
        this.number = number;
        this.raw = raw;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        return new UnknownCommand(this);
    }

    public Number getNumber() {
        return number;
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
