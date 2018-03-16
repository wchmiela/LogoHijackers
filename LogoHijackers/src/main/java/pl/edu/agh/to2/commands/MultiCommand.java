package pl.edu.agh.to2.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;

public class MultiCommand implements Command {
    private static final Logger log = LogManager.getLogger(MultiCommand.class);
    private ArrayDeque<Command> commands;

    public MultiCommand(ArrayDeque<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        log.debug("Executing multicommand");
        commands.forEach(Command::execute);
    }

    @Override
    public void revert() {
        log.debug("Reverting multicommand");
        commands.descendingIterator().forEachRemaining(Command::revert);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        commands.forEach(command -> builder
                .append(command)
                .append(command instanceof NopCommand ? "" : " "));
        return builder.toString().substring(0, builder.length() - 1);
    }
}
