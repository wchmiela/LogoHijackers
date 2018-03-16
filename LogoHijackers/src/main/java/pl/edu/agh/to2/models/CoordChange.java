package pl.edu.agh.to2.models;

public class CoordChange {
    private double x;
    private double y;
    private double oldX;
    private double oldY;

    public CoordChange(double x, double y, double oldX, double oldY) {
        this.x = x;
        this.y = y;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

}
