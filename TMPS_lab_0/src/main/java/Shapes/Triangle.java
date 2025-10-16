package Shapes;

import BaseConceptInterfaces.Shape;
import BehaviorInterfaces.Movable;
import BehaviorInterfaces.Rotatable;
import BehaviorInterfaces.Scalable;
import Point.Point;

public class Triangle implements Shape, Movable, Scalable, Rotatable {
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
        return a.distanceTo(b) + b.distanceTo(c) + c.distanceTo(a);
    }

    @Override
    public double getArea() {
        double semiPerimeter = getPerimeter() / 2;
        double ab = a.distanceTo(b);
        double bc = b.distanceTo(c);
        double ac = a.distanceTo(c);
        return Math.sqrt(semiPerimeter * (semiPerimeter - ab) * (semiPerimeter - ac) * (semiPerimeter - bc));
    }

    @Override
    public void move(double x, double y) {
        this.a.move(x, y);
        this.b.move(x, y);
        this.c.move(x, y);
    }

    public Point getCentroid() {
        double centerX = (a.getX() + b.getX() + c.getX()) / 3;
        double centerY = (a.getY() + b.getY() + c.getY()) / 3;
        return new Point(centerX, centerY);
    }

    @Override
    public void scale(double scale) {
        Point centroid = getCentroid();

        double newAx = centroid.getX() + scale * (a.getX() - centroid.getX());
        double newAy = centroid.getY() + scale * (a.getY() - centroid.getY());
        this.a = new Point(newAx, newAy);

        double newBx = centroid.getX() + scale * (b.getX() - centroid.getX());
        double newBy = centroid.getY() + scale * (b.getY() - centroid.getY());
        this.b = new Point(newBx, newBy);

        double newCx = centroid.getX() + scale * (c.getX() - centroid.getX());
        double newCy = centroid.getY() + scale * (c.getY() - centroid.getY());
        this.c = new Point(newCx, newCy);
    }

    @Override
    public void rotate(double angleRadians) {
        Point centroid = getCentroid();

        // Rotate vertex A around centroid
        this.a = a.rotateAroundCenter(centroid, angleRadians);

        // Rotate vertex B around centroid
        this.b = b.rotateAroundCenter(centroid, angleRadians);

        // Rotate vertex C around centroid
        this.c = c.rotateAroundCenter(centroid, angleRadians);
    }

}
