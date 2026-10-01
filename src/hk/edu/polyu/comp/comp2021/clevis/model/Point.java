package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.Objects;

/**
 * Represents a point in the 2D coordinate system.
 * This class is immutable.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Constructs a Point object.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate.
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the Euclidean distance to another point.
     * @param other The other point.
     * @return The distance between this point and the other point.
     */
    public double distanceTo(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the minimum distance from this point to a line segment defined by two points.
     * This is crucial for the REQ11 'shapeAt' logic.
     * @param p1 The first endpoint of the line segment.
     * @param p2 The second endpoint of the line segment.
     * @return The minimum distance.
     */
    public double distanceToLineSegment(Point p1, Point p2) {
        double l2 = p1.distanceTo(p2) * p1.distanceTo(p2);
        if (l2 == 0.0) return distanceTo(p1);
        double t = ((this.x - p1.getX()) * (p2.getX() - p1.getX()) + (this.y - p1.getY()) * (p2.getY() - p1.getY())) / l2;
        if (t < 0.0) return distanceTo(p1);
        if (t > 1.0) return distanceTo(p2);
        Point projection = new Point(p1.getX() + t * (p2.getX() - p1.getX()), p1.getY() + t * (p2.getY() - p1.getY()));

        return distanceTo(projection);
    }

    /**
     * Returns a string representation of the point, rounded to 2 decimal places.
     * @return A string in the format "(x, y)".
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        final double EPSILON = 1e-9;
        return Math.abs(point.getX() - x) < EPSILON && Math.abs(point.getY() - y) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
