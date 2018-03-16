package pl.edu.agh.to2.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayDeque;

public class CallStack {
    private ArrayDeque<Call> history;
    private ArrayDeque<Call> future;
    private ObjectProperty<ObservableList<Call>> total = new SimpleObjectProperty<>();

    public CallStack() {
        history = new ArrayDeque<>();
        future = new ArrayDeque<>();
        total.setValue(FXCollections.observableArrayList());
    }

    public void addCall(Call call) {
        history.addAll(future);
        future.clear();
        history.add(call);
        total.get().add(call);
    }

    public Call getPrev() {
        Call c = history.pollLast();
        if (c != null) {
            future.addFirst(c);
        }
        return c;
    }

    public Call getNext() {
        Call c = future.pollFirst();
        if (c != null) {
            history.addLast(c);
        }
        return c;
    }

    public ObjectProperty<ObservableList<Call>> totalProperty() {
        return total;
    }
}
