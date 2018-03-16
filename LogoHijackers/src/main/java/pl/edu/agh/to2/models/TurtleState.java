package pl.edu.agh.to2.models;

public class TurtleState {
    private double x;
    private double y;
    private double angle;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public TurtleState(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
}
