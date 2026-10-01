package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleShapeTest {

    @Test
    public void testIntersectsUsesBoundingBoxes() {
        SimpleShape r1 = new Rectangle("r1", 0, 0, 4, 4);
        SimpleShape r2 = new Rectangle("r2", 2, 2, 4, 4);
        SimpleShape r3 = new Rectangle("r3", 10, 10, 1, 1);

        assertTrue(r1.intersects(r2));
        assertFalse(r1.intersects(r3));
    }
}