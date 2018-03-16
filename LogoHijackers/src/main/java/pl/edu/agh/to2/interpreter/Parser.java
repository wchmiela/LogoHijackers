package pl.edu.agh.to2.interpreter;

import pl.edu.agh.to2.commands.Command;

import java.util.Collection;

public interface Parser {
    void parse(String command);
    Command evaluate(boolean force);
}
