package hk.edu.polyu.comp.comp2021.clevis.model;

import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    @Test
    public void testBoundingBoxCombinesComponents() throws Exception {
        Rectangle r = new Rectangle("r1", 0, 0, 2, 2);
        Circle c = new Circle("c1", 5, 5, 1); // bb: (4,4,2,2)

        List<Shape> components = new ArrayList<Shape>();
        components.add(r);
        components.add(c);

        Group g = new Group("g1", components);
        BoundingBox bb = g.getBoundingBox();

        assertEquals(0.0, bb.getX(), 1e-9);
        assertEquals(0.0, bb.getY(), 1e-9);
        assertEquals(6.0, bb.getWidth(), 1e-9);  // from x=0 to x=6
        assertEquals(6.0, bb.getHeight(), 1e-9); // from y=0 to y=6
    }

    @Test
    public void testMoveMovesAllComponents() {
        Rectangle r = new Rectangle("r1", 0, 0, 2, 2);
        Circle c = new Circle("c1", 5, 5, 1);
        List<Shape> components = new ArrayList<Shape>();
        components.add(r);
        components.add(c);

        Group g = new Group("g1", components);
        Shape moved = g.move(1, 2);

        assertTrue(moved instanceof Group);
        Group gMoved = (Group) moved;

        BoundingBox bb = gMoved.getBoundingBox();
        assertEquals(1.0, bb.getX(), 1e-9);
        assertEquals(2.0, bb.getY(), 1e-9);
    }

    @Test
    public void testContainsPointDelegatesToComponents() {
        Rectangle r = new Rectangle("r1", 0, 0, 10, 10);
        Circle c = new Circle("c1", 100, 100, 5);
        List<Shape> comps = new ArrayList<Shape>();
        comps.add(r);
        comps.add(c);

        Group g = new Group("g1", comps);
        Point nearRectEdge = new Point(5, 0.01);

        assertTrue(g.containsPoint(nearRectEdge));
        Point farPoint = new Point(50, 50);
        assertFalse(g.containsPoint(farPoint));
    }

    @Test
    public void testListInfoContainsComponentNames() {
        Rectangle r = new Rectangle("r1", 0, 0, 2, 2);
        Circle c = new Circle("c1", 5, 5, 1);
        List<Shape> comps = new ArrayList<Shape>();
        comps.add(r);
        comps.add(c);

        Group g = new Group("g1", comps);
        String info = g.listInfo();

        assertTrue(info.contains("Group g1"));
        assertTrue(info.contains("r1"));
        assertTrue(info.contains("c1"));
    }

    @Test
    public void testGetComponentsReturnsCopy() {
        Rectangle r = new Rectangle("r1", 0, 0, 2, 2);
        List<Shape> comps = new ArrayList<Shape>();
        comps.add(r);

        Group g = new Group("g1", comps);
        List<Shape> returned = g.getComponents();

        assertEquals(1, returned.size());
        assertNotSame(comps, returned);
        assertEquals("r1", returned.get(0).getName());
    }

    @Test
    public void testIsGroup() {
        Rectangle r = new Rectangle("r1", 0, 0, 1, 1);
        List<Shape> comps = new ArrayList<Shape>();
        comps.add(r);

        Group g = new Group("g1", comps);

        assertTrue(g.isGroup());
        assertFalse(r.isGroup());
    }

    @Test
    public void testDrawDoesNotThrow() {
        Rectangle r = new Rectangle("r1", 0, 0, 2, 2);
        Circle c = new Circle("c1", 5, 5, 1);
        List<Shape> comps = new ArrayList<Shape>();
        comps.add(r);
        comps.add(c);

        Group g = new Group("g1", comps);

        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        assertDoesNotThrow(() -> g.draw(g2d));

        g2d.dispose();
    }
}