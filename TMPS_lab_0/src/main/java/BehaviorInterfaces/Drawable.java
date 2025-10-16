package BehaviorInterfaces;

/**
 * Interface for objects that can be drawn/rendered.
 * Separates rendering concerns from geometric calculations.
 */
public interface Drawable {
    /**
     * Draws/renders the object.
     * In this implementation, prints a text description to console.
     */
    void draw();
}
