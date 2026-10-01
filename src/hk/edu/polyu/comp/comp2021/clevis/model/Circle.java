package hk.edu.polyu.comp.comp2021.clevis.model;

import java.awt.Graphics2D;

/**
 * Represents a circle.
 */
public class Circle extends SimpleShape {
    private final Point center;
    private final double radius;

    /**
     * Constructs a Circle object.
     * @param name The unique name of the circle.
     * @param x The x-coordinate of the center.
     * @param y The y-coordinate of the center.
     * @param r The radius.
     */
    public Circle(String name, double x, double y, double r) {
        super(name);
        this.center = new Point(x, y);
        this.radius = r;
    }

    private Circle(String name, Point center, double radius) {
        super(name);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public BoundingBox getBoundingBox() {
        double x = center.getX() - radius;
        double y = center.getY() - radius;
        double diameter = 2 * radius;
        return new BoundingBox(x, y, diameter, diameter);
    }

    @Override
    public Shape move(double dx, double dy) {
        Point newCenter = new Point(center.getX() + dx, center.getY() + dy);
        return new Circle(getName(), newCenter, radius);
    }

    @Override
    public boolean containsPoint(Point p) {
        final double THRESHOLD = 0.05;
        double distanceToCenter = p.distanceTo(center);
        return Math.abs(distanceToCenter - radius) < THRESHOLD;
    }

    @Override
    public void draw(Graphics2D g) {
        int x = (int) (center.getX() - radius);
        int y = (int) (center.getY() - radius);
        int diameter = (int) (2 * radius);
        g.drawOval(x, y, diameter, diameter);
    }

    @Override
    public String listInfo() {
        return String.format("Circle %s: Center%s, Radius %.2f", getName(), center.toString(), radius);
    }
}
