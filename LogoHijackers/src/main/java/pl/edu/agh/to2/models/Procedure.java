package pl.edu.agh.to2.models;

import pl.edu.agh.to2.interpreter.expressions.Expression;

import java.util.ArrayDeque;
import java.util.Collection;

public class Procedure {

    private String name;

    private boolean ready;

    private ArrayDeque<Expression> expressions = new ArrayDeque<>();

    private ArrayDeque<Expression> arguments = new ArrayDeque<>();

    public Procedure(String name, ArrayDeque<Expression> args, boolean isReady) {
        this.name = name;
        this.arguments = args;
        this.ready = isReady;
    }

    public Procedure(Procedure procedure) {
        this.name = procedure.name;
        this.arguments = new ArrayDeque<>(procedure.arguments);
        this.ready = procedure.ready;
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }

    public void addExpressions(Collection<Expression> expression) {
        expressions.addAll(expression);
    }

    public void addProcedureArgument(Expression expression) {
        arguments.add(expression);
    }

    public ArrayDeque<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayDeque<Expression> arguments) {
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public ArrayDeque<Expression> getExpressions() {
        return expressions;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setExpressions(ArrayDeque<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override

    public String toString() {
        return name;
    }
}
