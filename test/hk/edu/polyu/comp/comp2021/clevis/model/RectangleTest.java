package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    public void testBoundingBox() {
        Rectangle rect = new Rectangle("r1", 1, 2, 3, 4);
        BoundingBox bb = rect.getBoundingBox();

        assertEquals(1.0, bb.getX(), 1e-9);
        assertEquals(2.0, bb.getY(), 1e-9);
        assertEquals(3.0, bb.getWidth(), 1e-9);
        assertEquals(4.0, bb.getHeight(), 1e-9);
    }

    @Test
    public void testMoveReturnsNewRectangle() {
        Rectangle rect = new Rectangle("r1", 1, 2, 3, 4);
        Shape moved = rect.move(2, 3);

        assertTrue(moved instanceof Rectangle);
        BoundingBox bb = moved.getBoundingBox();

        assertEquals(3.0, bb.getX(), 1e-9); // 1 + 2
        assertEquals(5.0, bb.getY(), 1e-9); // 2 + 3
        assertEquals(3.0, bb.getWidth(), 1e-9);
        assertEquals(4.0, bb.getHeight(), 1e-9);
        assertEquals("r1", moved.getName());
    }

    @Test
    public void testContainsPointOnEdge() {
        Rectangle rect = new Rectangle("r1", 0, 0, 10, 10);
        Point p = new Point(5, 0.01);

        assertTrue(rect.containsPoint(p));
    }

    @Test
    public void testDoesNotContainFarPoint() {
        Rectangle rect = new Rectangle("r1", 0, 0, 10, 10);
        Point p = new Point(5, 5);

        assertFalse(rect.containsPoint(p));
    }

    @Test
    public void testListInfo() {
        Rectangle rect = new Rectangle("r1", 1, 2, 3, 4);
        String info = rect.listInfo();

        assertTrue(info.contains("Rectangle r1"));
        assertTrue(info.contains("Top-Left"));
        assertTrue(info.contains("Width 3.00"));
        assertTrue(info.contains("Height 4.00"));
    }

    @Test
    public void testDrawDoesNotThrow() {
        Rectangle rect = new Rectangle("r1", 0, 0, 10, 20);
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        assertDoesNotThrow(() -> rect.draw(g2d));

        g2d.dispose();
    }
}
