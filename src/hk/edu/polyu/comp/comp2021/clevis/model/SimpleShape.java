package hk.edu.polyu.comp.comp2021.clevis.model;

/**
 * Abstract base class for simple, non-group shapes.
 * It provides a common structure for geometric shapes.
 */
public abstract class SimpleShape extends Shape {

    /**
     * Constructs a SimpleShape with a unique name.
     * @param name The unique name of the shape.
     */
    public SimpleShape(String name) {
        super(name);
    }

    /**
     * Reports whether this shape intersects with another shape.
     * Intersection is defined as their minimum bounding boxes sharing any internal points.
     * @param other The other shape.
     * @return True if they intersect, false otherwise.
     */
    public boolean intersects(Shape other) {
        return this.getBoundingBox().intersects(other.getBoundingBox());
    }
}
