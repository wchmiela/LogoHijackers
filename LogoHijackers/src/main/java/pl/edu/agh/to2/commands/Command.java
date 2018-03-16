package pl.edu.agh.to2.commands;

public interface Command {
    void execute();

    void revert();
}
