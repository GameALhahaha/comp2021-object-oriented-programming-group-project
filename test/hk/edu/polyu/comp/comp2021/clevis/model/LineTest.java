package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    @Test
    public void testBoundingBox() {
        Line line = new Line("l1", 1, 2, 5, 6);
        BoundingBox bb = line.getBoundingBox();

        assertEquals(1.0, bb.getX(), 1e-9);
        assertEquals(2.0, bb.getY(), 1e-9);
        assertEquals(4.0, bb.getWidth(), 1e-9);
        assertEquals(4.0, bb.getHeight(), 1e-9);
    }

    @Test
    public void testMove() {
        Line line = new Line("l1", 1, 2, 5, 6);
        Shape moved = line.move(1, -2);

        assertTrue(moved instanceof Line);
        BoundingBox bb = moved.getBoundingBox();

        assertEquals(2.0, bb.getX(), 1e-9);
        assertEquals(0.0, bb.getY(), 1e-9);
        assertEquals(4.0, bb.getWidth(), 1e-9);
        assertEquals(4.0, bb.getHeight(), 1e-9);
    }

    @Test
    public void testContainsPointNearLine() {
        Line line = new Line("l1", 0, 0, 10, 0);
        Point p = new Point(5, 0.01); // near segment

        assertTrue(line.containsPoint(p));
    }

    @Test
    public void testDoesNotContainFarPoint() {
        Line line = new Line("l1", 0, 0, 10, 0);
        Point p = new Point(5, 1.0); // distance 1 > 0.05

        assertFalse(line.containsPoint(p));
    }

    @Test
    public void testListInfo() {
        Line line = new Line("l1", 0, 0, 10, 10);
        String info = line.listInfo();

        assertTrue(info.contains("Line l1"));
        assertTrue(info.contains("P1"));
        assertTrue(info.contains("P2"));
    }

    @Test
    public void testDrawDoesNotThrow() {
        Line line = new Line("l1", 0, 0, 10, 10);
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        assertDoesNotThrow(() -> line.draw(g2d));

        g2d.dispose();
    }
}