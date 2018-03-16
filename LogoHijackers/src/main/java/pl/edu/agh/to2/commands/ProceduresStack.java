package pl.edu.agh.to2.commands;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.models.Call;
import pl.edu.agh.to2.models.Procedure;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProceduresStack {

    private ObjectProperty<ObservableList<Call>> total = new SimpleObjectProperty<>();

    private Map<String, Procedure> procedures = new LinkedHashMap<>();

    public ProceduresStack() {
        total.setValue(FXCollections.observableArrayList());
    }

    public void addProcedure(Procedure procedure) {
        procedures.put(procedure.getName(), procedure);
    }

    public void removeProcedure(Procedure procedure) {
        procedures.remove(procedure.getName());
    }

    public Map<String, Procedure> getProcedures() {
        return procedures;
    }

    public Procedure getProcedure(String key) {
        return procedures.get(key);
    }

    public Procedure getActiveProcedure() {
        return procedures.values().stream().filter(x -> !x.isReady()).findFirst().orElse(null);
    }

    public ObjectProperty<ObservableList<Call>> totalProperty() {
        return total;
    }
}
