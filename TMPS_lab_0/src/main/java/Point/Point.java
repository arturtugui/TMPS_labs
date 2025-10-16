package Point;

import BehaviorInterfaces.Movable;

public class Point implements Movable {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
    }

    public Point rotateAroundCenter(Point center, double angleRadians) {
        // Translate point to origin (relative to center)
        double dx = this.x - center.getX();
        double dy = this.y - center.getY();

        // Apply rotation matrix
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        double newX = dx * cos - dy * sin;
        double newY = dx * sin + dy * cos;

        // Translate back to original position and return new point
        return new Point(center.getX() + newX, center.getY() + newY);
    }
}
