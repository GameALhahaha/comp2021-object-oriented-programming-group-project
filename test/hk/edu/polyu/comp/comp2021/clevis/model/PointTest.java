package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    public void testDistanceTo() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);

        assertEquals(5.0, p1.distanceTo(p2), 1e-9);
    }

    @Test
    public void testDistanceToLineSegmentOnSegment() {
        Point p = new Point(1, 1);
        Point a = new Point(0, 0);
        Point b = new Point(2, 2);

        double d = p.distanceToLineSegment(a, b);
        assertEquals(0.0, d, 1e-9);
    }

    @Test
    public void testDistanceToLineSegmentOutsideSegment() {
        Point p = new Point(5, 0);
        Point a = new Point(0, 0);
        Point b = new Point(2, 0);

        double d = p.distanceToLineSegment(a, b);
        assertEquals(3.0, d, 1e-9);
    }

    @Test
    public void testToStringFormatting() {
        Point p = new Point(1.2345, 2.3456);
        assertEquals("(1.23, 2.35)", p.toString());
    }

}