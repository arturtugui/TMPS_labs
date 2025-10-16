package BaseConceptInterfaces;

/**
 * Interface representing a geometric shape.
 * Defines the basic properties all shapes must have.
 */
public interface Shape {
    /**
     * Calculates the area of the shape.
     * @return the area
     */
    double getArea();
    
    /**
     * Calculates the perimeter of the shape.
     * @return the perimeter
     */
    double getPerimeter();
}
