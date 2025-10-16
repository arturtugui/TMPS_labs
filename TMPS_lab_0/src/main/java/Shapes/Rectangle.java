package Shapes;

import BaseConceptInterfaces.Shape;
import BehaviorInterfaces.Movable;
import BehaviorInterfaces.Rotatable;
import BehaviorInterfaces.Scalable;
import Point.Point;

public class Rectangle implements Shape, Movable, Scalable, Rotatable {
    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public double getLength() {
        return Math.abs(bottomRight.getX() - topLeft.getX());
    }

    public double getWidth() {
        return Math.abs(bottomRight.getY() - topLeft.getY());
    }

    @Override
    public double getPerimeter() {
        return 2 * this.getLength() + 2 * this.getWidth();
    }

    @Override
    public double getArea() {
        return this.getLength() * this.getWidth();
    }

    @Override
    public void move(double x, double y) {
        this.topLeft.move(x, y);
        this.bottomRight.move(x, y);
    }

    public Point getCenter() {
        double x = topLeft.getX() + getLength() / 2;
        double y = topLeft.getY() - getWidth() / 2;
        return new Point(x, y);
    }

    @Override
    public void scale(double scale) {
        // scaling
        Point center = this.getCenter();
        double lengthBy2 = this.getLength() / 2;
        double widthBy2 = this.getWidth() / 2;
        setTopLeft(new Point(center.getX() - scale * lengthBy2, center.getY() + scale * widthBy2));
        setBottomRight(new Point(center.getX() + scale * lengthBy2, center.getY() - scale * widthBy2));
    }

    @Override
    public void rotate(double angleRadians) {
        Point center = getCenter();

        // Rotate topLeft corner around center
        this.topLeft = topLeft.rotateAroundCenter(center, angleRadians);

        // Rotate bottomRight corner around center
        this.bottomRight = bottomRight.rotateAroundCenter(center, angleRadians);
    }
}
