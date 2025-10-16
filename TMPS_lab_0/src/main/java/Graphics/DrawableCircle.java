package Graphics;

import BehaviorInterfaces.Drawable;
import Shapes.Circle;

public class DrawableCircle implements Drawable {
    private Circle circle;

    public DrawableCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Circle:");
        System.out.println("  Center: (" + String.format("%.2f", circle.getCenter().getX()) +
                ", " + String.format("%.2f", circle.getCenter().getY()) + ")");
        System.out.println("  Radius: " + String.format("%.2f", circle.getRadius()));
    }

    public Circle getCircle() {
        return circle;
    }
}
