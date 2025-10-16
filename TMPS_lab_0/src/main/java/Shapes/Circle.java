package Shapes;

import BaseConceptInterfaces.Shape;

import java.awt.*;

public class Circle implements Shape {
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
}
