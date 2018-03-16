package pl.edu.agh.to2.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.interpreter.expressions.Expression;

import java.util.Collection;

public class UnknownCommand implements Command {
    private static final Logger log = LogManager.getLogger(UnknownCommand.class);
    private String logMessage;

    public UnknownCommand(Expression rawHigh) {
        logMessage = "Unknown command " + rawHigh.getRaw();
    }

    public UnknownCommand(Expression rawHigh, Collection<Expression> rawLow) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Unknown command '%s' with%s parameters ",
                rawHigh.getRaw(), rawLow.isEmpty() ? " no": ""));
        for (Expression raw : rawLow) {
            builder.append(String.format("'%s'", raw.getRaw())).append(" ");
        }
        logMessage = builder.toString();
    }

    public UnknownCommand(Iterable<Command> commands){
        StringBuilder builder = new StringBuilder()
                .append("Cannot execute command").append("\n");
        commands.forEach(command -> {
            if (command instanceof UnknownCommand) {
                builder.append("\n")
                       .append(command)
                       .append("\n");
            } else {
                builder.append(command).append(" ");
            }
        });
        logMessage = builder.toString();
    }

    @Override
    public void execute() {
        log.warn(logMessage);
    }

    @Override
    public void revert() {

    }

    @Override
    public String toString() {
        return String.format("# %s", logMessage);
    }
}
