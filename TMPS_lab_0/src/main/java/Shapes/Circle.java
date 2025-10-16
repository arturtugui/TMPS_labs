package Shapes;

import BaseConceptInterfaces.Shape;
import BehaviorInterfaces.Movable;
import BehaviorInterfaces.Scalable;
import Point.Point;

public class Circle implements Shape, Movable, Scalable {
    private Point center;
    private double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return (double) (2 * Math.PI * getRadius());
    }

    @Override
    public double getArea() {
        return (double) (Math.PI * Math.pow(getRadius(), 2));
    }

    @Override
    public void move(double dx, double dy) {
        setCenter(new Point(center.getX() + dx, center.getY() + dy));
    }

    @Override
    public void scale(double s) {
        // scaling from the center
        setRadius(radius * s);
    }
}
