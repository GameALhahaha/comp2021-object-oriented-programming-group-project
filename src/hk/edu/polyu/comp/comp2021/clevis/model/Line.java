package hk.edu.polyu.comp.comp2021.clevis.model;

import java.awt.Graphics2D;

/**
 * Represents a line segment.
 */
public class Line extends SimpleShape {
    private final Point p1;
    private final Point p2;

    /**
     * Constructs a Line object.
     * @param name The unique name of the line.
     * @param x1 The x-coordinate of the first endpoint.
     * @param y1 The y-coordinate of the first endpoint.
     * @param x2 The x-coordinate of the second endpoint.
     * @param y2 The y-coordinate of the second endpoint.
     */
    public Line(String name, double x1, double y1, double x2, double y2) {
        super(name);
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
    }

    private Line(String name, Point p1, Point p2) {
        super(name);
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public BoundingBox getBoundingBox() {
        double minX = Math.min(p1.getX(), p2.getX());
        double minY = Math.min(p1.getY(), p2.getY());
        double maxX = Math.max(p1.getX(), p2.getX());
        double maxY = Math.max(p1.getY(), p2.getY());
        double width = maxX - minX;
        double height = maxY - minY;
        return new BoundingBox(minX, minY, width, height);
    }

    @Override
    public Shape move(double dx, double dy) {
        Point newP1 = new Point(p1.getX() + dx, p1.getY() + dy);
        Point newP2 = new Point(p2.getX() + dx, p2.getY() + dy);
        return new Line(getName(), newP1, newP2);
    }

    @Override
    public boolean containsPoint(Point p) {
        final double THRESHOLD = 0.05;
        return p.distanceToLineSegment(p1, p2) < THRESHOLD;
    }

    @Override

    public void draw(Graphics2D g) {
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    }

    @Override
    public String listInfo() {
        return String.format("Line %s: P1%s, P2%s", getName(), p1.toString(), p2.toString());
    }
}
