package pl.edu.agh.to2.commands;

public class NopCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void revert() {

    }

    @Override
    public String toString() {
        return "\\" + System.lineSeparator();
    }
}
