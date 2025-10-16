package Management;

import BehaviorInterfaces.Drawable;
import java.util.ArrayList;
import java.util.List;

public class ShapeManager {
    private List<Drawable> shapes;

    public ShapeManager() {
        this.shapes = new ArrayList<>(); // composition
    }

    public void addShape(Drawable shape) {
        shapes.add(shape);
        System.out.println("Shape added. Total shapes: " + shapes.size());
    }

    public boolean removeShape(Drawable shape) {
        boolean removed = shapes.remove(shape);
        if (removed) {
            System.out.println("Shape removed. Total shapes: " + shapes.size());
        } else {
            System.out.println("Shape not found.");
        }
        return removed;
    }

    public void drawAll() {
        if (shapes.isEmpty()) {
            System.out.println("No shapes to draw.");
            return;
        }

        System.out.println("\n=== Drawing All Shapes (" + shapes.size() + " total) ===\n");
        for (int i = 0; i < shapes.size(); i++) {
            System.out.println("Shape " + (i + 1) + ":");
            shapes.get(i).draw();
            System.out.println();
        }
    }

    public int getShapeCount() {
        return shapes.size();
    }

    public void clear() {
        shapes.clear();
        System.out.println("All shapes cleared.");
    }

    public Drawable getShape(int index) {
        return shapes.get(index);
    }
}
