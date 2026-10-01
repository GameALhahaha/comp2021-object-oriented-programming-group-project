package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class CircleTest {

    @Test
    public void testBoundingBox() {
        Circle c = new Circle("c1", 5, 5, 2);
        BoundingBox bb = c.getBoundingBox();

        assertEquals(3.0, bb.getX(), 1e-9);
        assertEquals(3.0, bb.getY(), 1e-9);
        assertEquals(4.0, bb.getWidth(), 1e-9);
        assertEquals(4.0, bb.getHeight(), 1e-9);
    }

    @Test
    public void testMove() {
        Circle c = new Circle("c1", 5, 5, 2);
        Shape moved = c.move(3, -1);

        assertTrue(moved instanceof Circle);
        BoundingBox bb = moved.getBoundingBox();

        assertEquals(6.0, bb.getX(), 1e-9); // center (8,4) -> x=8-2
        assertEquals(2.0, bb.getY(), 1e-9); // y=4-2
    }

    @Test
    public void testContainsPointOnOutline() {
        Circle c = new Circle("c1", 0, 0, 5);
        Point p = new Point(5, 0); // exactly on outline

        assertTrue(c.containsPoint(p));
    }

    @Test
    public void testDoesNotContainFarPoint() {
        Circle c = new Circle("c1", 0, 0, 5);
        Point p = new Point(0, 0); // distance 5 from outline

        assertFalse(c.containsPoint(p));
    }

    @Test
    public void testListInfo() {
        Circle c = new Circle("c1", 1, 2, 3);
        String info = c.listInfo();

        assertTrue(info.contains("Circle c1"));
        assertTrue(info.contains("Center"));
        assertTrue(info.contains("Radius 3.00"));
    }

    @Test
    public void testDrawDoesNotThrow() {
        Circle c = new Circle("c1", 10, 10, 5);
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        assertDoesNotThrow(() -> c.draw(g2d));

        g2d.dispose();
    }
}
