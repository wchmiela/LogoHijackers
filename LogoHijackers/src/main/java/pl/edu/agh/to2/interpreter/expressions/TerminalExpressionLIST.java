package pl.edu.agh.to2.interpreter.expressions;

import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.UnknownCommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionLIST extends TerminalExpressionPAR {
    private ArrayDeque<Expression> expressions = null;
    private boolean validParentheses = false;
    private boolean validCommand = true;

    @Override
    public Command interpret(Deque<Expression> s) {
        if (expressions == null ||  expressions.isEmpty()){
            setExpressions(s);
        }
        return new UnknownCommand(this);
    }

    @Override
    public String getRaw() {
        return String.format("[%s%s", expressions
                .stream()
                .map(Expression::getRaw)
                .reduce("", (a, b) -> a + " " + b), validParentheses ? "]" : "");
    }

    private void setExpressions(Deque<Expression> s){
        ArrayDeque<Expression> elements = new ArrayDeque<>();
        while (!s.isEmpty() && !validParentheses) {
            while (!s.isEmpty() && !(s.peekFirst() instanceof TerminalExpressionPAR)) {
                elements.addLast(s.pollFirst());
            }
            if (!s.isEmpty()) {
                Expression par = s.pollFirst();
                if (par instanceof TerminalExpressionLIST) {
                    par.interpret(s);
                    elements.addLast(par);
                    validCommand = validCommand && ((TerminalExpressionLIST) par).isValid();
                } else {
                    validParentheses = true;
                    validCommand = validCommand && elements
                            .stream()
                            .filter(expression -> expression instanceof TerminalExpressionDEFAULT)
                            .count() == 0;
                }
            }
        }
        expressions = elements;
    }

    public boolean isValid(){
        return validParentheses && validCommand;
    }

    public ArrayDeque<Expression> getExpressions() {
        return expressions.clone();
    }

    public void emptify(){
        if (expressions != null){
            expressions.clear();
        }
        validParentheses = false;
        validCommand = true;
    }
}
