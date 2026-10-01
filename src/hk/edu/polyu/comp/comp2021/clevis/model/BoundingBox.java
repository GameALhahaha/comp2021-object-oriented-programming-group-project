package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Objects;

/**
 * Represents the minimum bounding box of a shape.
 * This class is immutable.
 */
public class BoundingBox {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    /**
     * Constructs a BoundingBox object.
     * @param x The x-coordinate of the top-left corner.
     * @param y The y-coordinate of the top-left corner.
     * @param width The width of the box.
     * @param height The height of the box.
     */
    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @return The x-coordinate value
     **/
    public double getX() { return x; }
    /**
     * @return The y-coordinate value
     **/
    public double getY() { return y; }
    /**
     * @return The Width value
     **/
    public double getWidth() { return width; }
    /**
     * @return The Height value
     **/
    public double getHeight() { return height; }

    /**
     * Calculates the union of this bounding box and another one.
     * This is used for calculating the bounding box of a Group.
     * @param other The other bounding box.
     * @return A new BoundingBox that encloses both.
     */
    public BoundingBox combine(BoundingBox other) {
        double minX = Math.min(this.x, other.getX());
        double minY = Math.min(this.y, other.getY());
        double maxX = Math.max(this.x + this.width, other.getX() + other.getWidth());
        double maxY = Math.max(this.y + this.height, other.getY() + other.getHeight());
        double newWidth = maxX - minX;
        double newHeight = maxY - minY;
        return new BoundingBox(minX, minY, newWidth, newHeight);
    }

    /**
     * Reports whether two bounding boxes intersect with each other,
     * i.e., whether they share any internal points.
     * @param other The other bounding box.
     * @return True if they intersect, false otherwise.
     */
    public boolean intersects(BoundingBox other) {
        if (this.x + this.width <= other.getX() || other.getX() + other.getWidth() <= this.x) {
            return false;
        }
        if (this.y + this.height <= other.getY() || other.getY() + other.getHeight() <= this.y) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the bounding box.
     * Format: "x y w h", rounded to 2 decimal places.
     * @return The formatted string.
     */
    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f %.2f", x, y, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBox that = (BoundingBox) o;
        final double EPSILON = 1e-9;
        return Math.abs(that.getX() - x) < EPSILON &&
               Math.abs(that.getY() - y) < EPSILON &&
               Math.abs(that.getWidth() - width) < EPSILON &&
               Math.abs(that.getHeight() - height) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }
}
