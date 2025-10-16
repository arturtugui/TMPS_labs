package Graphics;

import BehaviorInterfaces.Drawable;
import Shapes.Triangle;

public class DrawableTriangle implements Drawable {
    private Triangle triangle;

    public DrawableTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Triangle:");
        System.out.println("  Point A: (" + String.format("%.2f", triangle.getA().getX()) +
                ", " + String.format("%.2f", triangle.getA().getY()) + ")");
        System.out.println("  Point B: (" + String.format("%.2f", triangle.getB().getX()) +
                ", " + String.format("%.2f", triangle.getB().getY()) + ")");
        System.out.println("  Point C: (" + String.format("%.2f", triangle.getC().getX()) +
                ", " + String.format("%.2f", triangle.getC().getY()) + ")");
    }

    public Triangle getTriangle() {
        return triangle;
    }
}
