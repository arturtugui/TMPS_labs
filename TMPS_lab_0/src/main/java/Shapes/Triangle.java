package Shapes;

import BaseConceptInterfaces.Shape;

import java.awt.*;

public class Triangle implements Shape {
    private Point a;
    private Point b;
    private Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getPerimeter() {
        return a.distance(b) + b.distance(c) + c.distance(a);
    }

    @Override
    public double getArea() {
        double semiPerimeter = getPerimeter() / 2;
        double ab = a.distance(b);
        double bc = b.distance(c);
        double ac = a.distance(c);
        return Math.sqrt(semiPerimeter * (semiPerimeter - ab) * (semiPerimeter - ac) * (semiPerimeter - bc));
    }
}
