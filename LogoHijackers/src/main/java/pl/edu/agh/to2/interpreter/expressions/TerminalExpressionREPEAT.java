package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.Loop;
import pl.edu.agh.to2.commands.MultiCommand;
import pl.edu.agh.to2.commands.UnknownCommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionREPEAT extends TerminalExpressionCOMMAND {
    private ArrayDeque<Command> commands = new ArrayDeque<>();

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        if (parameters.size() != 2
                || !(parameters.peekFirst() instanceof TerminalExpressionINTEGER)
                || !(parameters.peekLast() instanceof TerminalExpressionLIST))
            return new UnknownCommand(this, parameters);

        if (!((TerminalExpressionLIST)parameters.peekLast()).isValid())
            return new UnknownCommand(this, parameters);

        TerminalExpressionINTEGER count = (TerminalExpressionINTEGER) parameters.pop();
        TerminalExpressionLIST list = (TerminalExpressionLIST) parameters.pop();

        ArrayDeque<Expression> expressions = list.getExpressions();
        while (!expressions.isEmpty()) {
            commands.add(expressions.pollFirst().interpret(expressions));
        }

        return new Loop(new MultiCommand(commands), count.getNumber());
    }

    @Override
    public String getRaw() {
        return "powtórz";
    }
}
