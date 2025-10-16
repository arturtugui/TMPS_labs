package Graphics;

import BehaviorInterfaces.Drawable;
import Shapes.Rectangle;

public class DrawableRectangle implements Drawable {
    private Rectangle rectangle;

    public DrawableRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle:");
        System.out.println("  Top-Left: (" + String.format("%.2f", rectangle.getTopLeft().getX()) +
                ", " + String.format("%.2f", rectangle.getTopLeft().getY()) + ")");
        System.out.println("  Bottom-Right: (" + String.format("%.2f", rectangle.getBottomRight().getX()) +
                ", " + String.format("%.2f", rectangle.getBottomRight().getY()) + ")");
    }

    // Expose the geometric shape if needed
    public Rectangle getRectangle() {
        return rectangle;
    }
}
