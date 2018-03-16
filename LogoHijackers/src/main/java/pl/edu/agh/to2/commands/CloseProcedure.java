package pl.edu.agh.to2.commands;

import pl.edu.agh.to2.controllers.OverviewController;

public class CloseProcedure implements Command {

    private OverviewController overviewController;

    public CloseProcedure(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

    @Override
    public void execute() {
        this.overviewController
                .getProceduresStack().getProcedures()
                .values()
                .stream()
                .filter(x -> !x.isReady())
                .forEach(x -> x.setReady(true));
    }

    @Override
    public void revert() {

    }

    @Override
    public String toString() {
        return "Już";
    }
}
