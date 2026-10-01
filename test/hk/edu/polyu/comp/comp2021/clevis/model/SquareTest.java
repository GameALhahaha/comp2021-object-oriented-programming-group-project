package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    @Test
    public void testMoveProducesSquareWithSameSide() {
        Square sq = new Square("s1", 1, 1, 5);
        Shape moved = sq.move(2, 3);

        assertTrue(moved instanceof Square);
        BoundingBox bb = moved.getBoundingBox();

        assertEquals(3.0, bb.getX(), 1e-9);
        assertEquals(4.0, bb.getY(), 1e-9);
        assertEquals(5.0, bb.getWidth(), 1e-9);
        assertEquals(5.0, bb.getHeight(), 1e-9);
    }

    @Test
    public void testListInfoContainsSquare() {
        Square sq = new Square("s1", 1, 2, 5);
        String info = sq.listInfo();

        assertTrue(info.contains("Square s1"));
        assertTrue(info.contains("Side Length 5.00"));
    }

    @Test
    public void testDrawDoesNotThrow() {
        Square sq = new Square("s1", 0, 0, 10);
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        assertDoesNotThrow(() -> sq.draw(g2d));

        g2d.dispose();
    }
}