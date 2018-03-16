package pl.edu.agh.to2.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public class Turtle {
    private Marker marker;
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty angle = new SimpleDoubleProperty();
    private BooleanProperty visible = new SimpleBooleanProperty();

    public Turtle(double x, double y, boolean visible, double angle) {
        this.setX(x);
        this.setY(y);
        this.setAngle(angle);
        this.setVisible(visible);
        this.marker = new Marker();
        marker.setState(MarkerState.LOWERED);
    }

    public Turtle(double x, double y, boolean visible, double angle, ImageView image) {
        this.x.setValue(x);
        this.y.setValue(y);
        this.visible.setValue(visible);
        this.angle.setValue(angle);
        if (image != null) {
            image.xProperty().bind(this.x.subtract(image.fitWidthProperty().divide(2)));
            image.yProperty().bind(this.y.subtract(image.fitHeightProperty().divide(2)));
            image.rotateProperty().bind(this.angle.multiply(-1));
            image.visibleProperty().bind(this.visible);
        }
        this.marker = new Marker();
        marker.setState(MarkerState.LOWERED);
    }

    public CoordChange move(double distance, double direction) {
        double newX = this.getX() + Math.cos((this.getAngle()) * Math.PI / 180) * distance;
        double newY = this.getY() - Math.sin((this.getAngle()) * Math.PI / 180) * distance;

        this.setAngle(angle.getValue() + direction);
        CoordChange coordChange = new CoordChange(newX, newY, this.getX(), this.getY());
        this.setX(newX);
        this.setY(newY);
        return coordChange;
    }

    public double getX() {
        return x.getValue();
    }

    private void setX(double x) {
        this.x.setValue(x);
    }

    public double getY() {
        return y.getValue();
    }

    private void setY(double y) {
        this.y.setValue(y);
    }

    public void setPosition(double x, double y, double angle) {
        this.setY(y);
        this.setX(x);
        this.setAngle(angle);
    }

    public void setPosition(TurtleState state) {
        this.setY(state.getY());
        this.setX(state.getX());
        this.setAngle(state.getAngle());
    }

    public void setPosition(double x, double y) {
        this.setY(y);
        this.setX(x);
    }

    public double getAngle() {
        return angle.getValue();
    }

    private void setAngle(double angle) {
        this.angle.setValue(angle - 360 * ((int) angle / 360));
    }

    public boolean isVisible() {
        return visible.getValue();
    }

    public void setVisible(boolean visible) {
        this.visible.setValue(visible);
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
