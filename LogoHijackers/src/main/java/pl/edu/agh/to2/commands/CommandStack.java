package pl.edu.agh.to2.commands;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;

public class CommandStack {
    private final static Logger log = LogManager.getLogger(CommandStack.class);
    private ArrayDeque<Command> history;
    private ArrayDeque<Command> future;

    private BooleanProperty historyEmpty = new SimpleBooleanProperty(true);
    private BooleanProperty futureEmpty = new SimpleBooleanProperty(true);

    public CommandStack() {
        history = new ArrayDeque<>();
        future = new ArrayDeque<>();
    }

    public BooleanProperty historyEmptyProperty() {
        return historyEmpty;
    }

    public BooleanProperty futureEmptyProperty() {
        return futureEmpty;
    }

    public void execute(Command command) {
        future.clear();
        if (!(command instanceof UnknownCommand) && !(command instanceof NopCommand)) {
            log.debug(String.format("Adding command \"%s\" to history", command));
            history.add(command);
        }
        setProperties();
        command.execute();
    }

    public void undo() {
        log.debug("Undoing last command");
        if (!history.isEmpty()) {
            Command command = history.pollLast();
            future.addFirst(command);
            setProperties();
            command.revert();
        } else {
            log.warn("Trying to undo command, but history is empty");
        }
    }

    public void redo() {
        log.debug("Redoing command");
        if (!future.isEmpty()) {
            Command command = future.pollFirst();
            history.add(command);
            setProperties();
            command.execute();
        } else {
            log.warn("Trying to redo command, but future is empty");
        }
    }

    private void setProperties() {
        this.historyEmpty.setValue(history.isEmpty());
        this.futureEmpty.setValue(future.isEmpty());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        history.forEach(command ->
                builder
                        .append(command)
                        .append(System.lineSeparator()));
        return builder.toString();
    }
}
