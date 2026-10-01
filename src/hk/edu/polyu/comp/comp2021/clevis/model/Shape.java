package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.List;
import java.util.Collections;
import java.awt.Graphics2D;

/**
 * Abstract base class for all shapes in Clevis.
 * Implements the common interface for all geometric objects.
 */
public abstract class Shape {
    private final String name;

    /**
     * Constructs a Shape with a unique name.
     * @param name The unique name of the shape.
     */
    public Shape(String name) {
        this.name = name;
    }

    /**
     * Gets the unique name of the shape.
     * @return The shape's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates the minimum bounding box of the shape.
     * @return The BoundingBox object.
     */
    public abstract BoundingBox getBoundingBox();

    /**
     * Moves the shape horizontally by dx and vertically by dy.
     * @param dx The horizontal displacement.
     * @param dy The vertical displacement.
     * @return A new Shape object representing the moved shape.
     */
    public abstract Shape move(double dx, double dy);

    /**
     * Checks if the shape's outline covers a given point.
     * A shape covers a point if the minimum distance from the point to the outline is smaller than 0.05.
     * @param p The point to check.
     * @return True if the shape covers the point, false otherwise.
     */
    public abstract boolean containsPoint(Point p);

    /**
     * Lists the basic information about the shape.
     * @return A formatted string containing the shape's information.
     */
    public abstract String listInfo();

    /**
     * Draws the shape on a given Graphics2D context.
     * @param g The Graphics2D context to draw on.
     */
    public abstract void draw(Graphics2D g);

    /**
     * Returns a list of component shapes. For simple shapes, this is a list containing only itself.
     * For a Group, this is its members. This is part of the Composite Pattern.
     * @return A list of component shapes.
     */
    public List<Shape> getComponents() {
        return Collections.singletonList(this);
    }

    /**
     * Checks if the shape is a Group.
     * @return True if the shape is a Group, false otherwise.
     */
    public boolean isGroup() {
        return false;
    }
}
