package pl.edu.agh.to2.commands;

import pl.edu.agh.to2.controllers.OverviewController;
import pl.edu.agh.to2.interpreter.expressions.Expression;
import pl.edu.agh.to2.models.Procedure;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.List;

public class CreateProcedure implements Command {

    private OverviewController overviewController;
    private String procedureName;
    private ArrayDeque<Expression> args;

    public CreateProcedure(OverviewController overviewController, String procedureName, ArrayDeque<Expression> args) {
        this.overviewController = overviewController;
        this.procedureName = procedureName;
        this.args = args;
    }

    @Override
    public void execute() {
        Procedure procedure = new Procedure(procedureName, args, false);

        overviewController.getProceduresStack().addProcedure(procedure);
    }

    @Override
    public void revert() {
        Procedure procedure = overviewController.getProceduresStack().getProcedures().get(procedureName);

        overviewController.getProceduresStack().getProcedures().remove(procedure);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Oto ").append(procedureName);
        args.forEach(command -> builder.append(" ").append(command.getRaw()));

        return builder.toString();
    }
}
