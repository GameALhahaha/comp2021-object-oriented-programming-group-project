package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoundingBoxTest {

    @Test
    public void testCombineNonOverlapping() {
        BoundingBox b1 = new BoundingBox(0, 0, 2, 2);
        BoundingBox b2 = new BoundingBox(5, 5, 2, 2);

        BoundingBox combined = b1.combine(b2);

        assertEquals(0.0, combined.getX(), 1e-9);
        assertEquals(0.0, combined.getY(), 1e-9);
        assertEquals(7.0, combined.getWidth(), 1e-9);   // from 0 to 7
        assertEquals(7.0, combined.getHeight(), 1e-9);  // from 0 to 7
    }

    @Test
    public void testCombineOverlapping() {
        BoundingBox b1 = new BoundingBox(0, 0, 4, 4);
        BoundingBox b2 = new BoundingBox(2, 2, 4, 4);

        BoundingBox combined = b1.combine(b2);

        assertEquals(0.0, combined.getX(), 1e-9);
        assertEquals(0.0, combined.getY(), 1e-9);
        assertEquals(6.0, combined.getWidth(), 1e-9);   // from 0 to 6
        assertEquals(6.0, combined.getHeight(), 1e-9);  // from 0 to 6
    }

    @Test
    public void testIntersectsTrue() {
        BoundingBox b1 = new BoundingBox(0, 0, 4, 4);
        BoundingBox b2 = new BoundingBox(2, 2, 4, 4);

        assertTrue(b1.intersects(b2));
        assertTrue(b2.intersects(b1));
    }

    @Test
    public void testIntersectsTouchingEdgesIsFalse() {
        BoundingBox b1 = new BoundingBox(0, 0, 4, 4);
        BoundingBox b2 = new BoundingBox(4, 0, 4, 4);

        assertFalse(b1.intersects(b2));
        assertFalse(b2.intersects(b1));
    }

    @Test
    public void testToStringFormatting() {
        BoundingBox b = new BoundingBox(1.2345, 2.3456, 3.4567, 4.5678);
        assertEquals("1.23 2.35 3.46 4.57", b.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        BoundingBox b1 = new BoundingBox(1, 2, 3, 4);
        BoundingBox b2 = new BoundingBox(1, 2, 3, 4);

        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
    }
}