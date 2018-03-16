package pl.edu.agh.to2.interpreter.expressions;

public enum Direction {
    FORWARD(1), BACKWARD(-1), RIGHT(-1), LEFT(1);

    private final int value;

    Direction(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
