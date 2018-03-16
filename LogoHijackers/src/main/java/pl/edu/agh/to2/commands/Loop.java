package pl.edu.agh.to2.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.IntStream;

public class Loop implements Command {
    private static final Logger log = LogManager.getLogger(Loop.class);
    private Command command;
    private int count;

    public Loop(Command command, int count) {
        this.command = command;
        this.count = count;
    }

    @Override
    public void execute() {
        log.debug("Executing loop command");
        IntStream.range(0, count).forEach(i -> command.execute());
    }

    @Override
    public void revert() {
        log.debug("Reverting loop command");
        IntStream.range(0, count).forEach(i -> command.revert());
    }

    @Override
    public String toString() {
        return String.format("powtórz %d [%s]", count, command);
    }
}
