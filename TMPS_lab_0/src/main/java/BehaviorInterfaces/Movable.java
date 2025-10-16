package BehaviorInterfaces;

/**
 * Interface for objects that can be moved/translated.
 */
public interface Movable {
    /**
     * Moves the object by the specified delta values.
     * 
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     */
    void move(double dx, double dy);
}
