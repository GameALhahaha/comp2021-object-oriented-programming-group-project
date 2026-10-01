package hk.edu.polyu.comp.comp2021.clevis.model;

import java.awt.Graphics2D;

/**
 * Represents a rectangle.
 */
public class Rectangle extends SimpleShape {
    private final Point topLeft;
    private final double width;
    private final double height;

    /**
     * Constructs a Rectangle object.
     * @param name The unique name of the rectangle.
     * @param x The x-coordinate of the top-left corner.
     * @param y The y-coordinate of the top-left corner.
     * @param w The width.
     * @param h The height.
     */
    public Rectangle(String name, double x, double y, double w, double h) {
        super(name);
        this.topLeft = new Point(x, y);
        this.width = w;
        this.height = h;
    }

    private Rectangle(String name, Point topLeft, double width, double height) {
        super(name);
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(topLeft.getX(), topLeft.getY(), width, height);
    }

    @Override
    public Shape move(double dx, double dy) {
        Point newTopLeft = new Point(topLeft.getX() + dx, topLeft.getY() + dy);
        return new Rectangle(getName(), newTopLeft, width, height);
    }

    @Override
    public boolean containsPoint(Point p) {
        final double THRESHOLD = 0.05;
        double x = topLeft.getX();
        double y = topLeft.getY();
        double x2 = x + width;
        double y2 = y + height;
        Point p1 = topLeft;
        Point p2 = new Point(x2, y);
        Point p3 = new Point(x2, y2);
        Point p4 = new Point(x, y2);
        if (p.distanceToLineSegment(p1, p2) < THRESHOLD) return true;
        if (p.distanceToLineSegment(p2, p3) < THRESHOLD) return true;
        if (p.distanceToLineSegment(p3, p4) < THRESHOLD) return true;
        if (p.distanceToLineSegment(p4, p1) < THRESHOLD) return true;

        return false;
    }

    @Override

    public void draw(Graphics2D g) {
        g.drawRect((int) topLeft.getX(), (int) topLeft.getY(), (int) width, (int) height);
    }

    @Override
    public String listInfo() {
        return String.format("Rectangle %s: Top-Left%s, Width %.2f, Height %.2f",
                getName(), topLeft.toString(), width, height);
    }
}
