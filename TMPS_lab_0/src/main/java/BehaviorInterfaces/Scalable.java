package BehaviorInterfaces;

/**
 * Interface for shapes that can be scaled (resized).
 */
public interface Scalable {
    /**
     * Scales the shape by the specified factor from its center.
     * 
     * @param factor the scaling factor (2.0 = double size, 0.5 = half size)
     */
    void scale(double factor);
}
