package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;

import java.util.ArrayDeque;
import java.util.Deque;

public interface Expression {
    Command interpret(Deque<Expression> s);
    String getRaw();

    static ArrayDeque<Expression> getParameters(Deque<Expression> s){
        ArrayDeque<Expression> parameters = new ArrayDeque<>();
        ArrayDeque<Expression> nops = new ArrayDeque<>();
        while (!s.isEmpty() && !(s.peekFirst() instanceof TerminalExpressionCOMMAND)) {
            if (s.peekFirst() instanceof  TerminalExpressionLIST) {
                Expression list = s.pollFirst();
                list.interpret(s);
                parameters.addLast(list);
            } else if (s.peekFirst() instanceof TerminalExpressionLINEBREAKER) {
                nops.add(s.pollFirst());
            } else
                parameters.addLast(s.pollFirst());
        }
        nops.forEach(s::addFirst);
        return parameters;
    }
}
