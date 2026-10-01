package hk.edu.polyu.comp.comp2021.clevis.model;

/**
 * Represents a square.
 * Implemented by extending Rectangle, demonstrating inheritance.
 */
public class Square extends Rectangle {
    private final double sideLength;

    /**
     * Constructs a Square object.
     * @param name The unique name of the square.
     * @param x The x-coordinate of the top-left corner.
     * @param y The y-coordinate of the top-left corner.
     * @param l The side length.
     */
    public Square(String name, double x, double y, double l) {
        super(name, x, y, l, l);
        this.sideLength = l;
    }

    private Square(String name, Point topLeft, double sideLength) {
        super(name, topLeft.getX(), topLeft.getY(), sideLength, sideLength);
        this.sideLength = sideLength;
    }

    @Override
    public Shape move(double dx, double dy) {
        Rectangle movedRect = (Rectangle) super.move(dx, dy);
        BoundingBox bb = movedRect.getBoundingBox();
        Point newTopLeft = new Point(bb.getX(), bb.getY());
        return new Square(getName(), newTopLeft, sideLength);
    }

    @Override
    public String listInfo() {
        BoundingBox bb = getBoundingBox();
        Point topLeft = new Point(bb.getX(), bb.getY());
        return String.format("Square %s: Top-Left%s, Side Length %.2f",
                getName(), topLeft.toString(), sideLength);
    }
}
