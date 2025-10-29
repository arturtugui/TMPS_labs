import BehaviorInterfaces.Drawable;
import Graphics.DrawableCircle;
import Graphics.DrawableRectangle;
import Graphics.DrawableTriangle;
import Management.ShapeManager;
import Point.Point;
import Shapes.Circle;
import Shapes.Rectangle;
import Shapes.Triangle;

public class Demo {
    public static void main(String[] args) {
        Circle circle = new Circle(new Point(5, 5), 3);
        Rectangle rect = new Rectangle(new Point(0, 4), new Point(6, 0));
        rect.scale(5);
        Triangle triangle = new Triangle(new Point(0, 0), new Point(4, 0), new Point(2, 3));
        triangle.rotate(Math.PI);

        Drawable dc = new DrawableCircle(circle);
        Drawable dr = new DrawableRectangle(rect);
        Drawable dt = new DrawableTriangle(triangle);

        ShapeManager manager = new ShapeManager();
        manager.addShape(dc);
        manager.addShape(dr);
        manager.addShape(dt);
        manager.drawAll();
    }
}
