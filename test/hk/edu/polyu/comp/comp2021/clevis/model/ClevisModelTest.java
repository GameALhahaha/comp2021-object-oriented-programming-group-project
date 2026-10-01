package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClevisModelTest {

    @Test
    public void testAddAndGetShape() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 1, 1));

        Shape s = model.getShape("r1");
        assertNotNull(s);
        assertEquals("r1", s.getName());
    }

    @Test
    public void testAddShapeNameConflict() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Circle("c1", 0, 0, 1));

        assertThrows(ClevisException.class, () ->
                model.addShape(new Rectangle("c1", 0, 0, 1, 1)));
    }

    @Test
    public void testDeleteShape() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 1, 1));

        model.deleteShape("r1");
        assertThrows(ClevisException.class, () -> model.getShape("r1"));
    }

    @Test
    public void testMoveShape() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 1, 1));

        model.moveShape("r1", 2, 3);
        Shape moved = model.getShape("r1");
        BoundingBox bb = moved.getBoundingBox();

        assertEquals(2.0, bb.getX(), 1e-9);
        assertEquals(3.0, bb.getY(), 1e-9);
    }

    @Test
    public void testShapeAtReturnsTopmost() throws Exception {
        ClevisModel model = new ClevisModel();
        Rectangle bottom = new Rectangle("bottom", 0, 0, 10, 10);
        Rectangle top = new Rectangle("top", 0, 0, 10, 10);

        model.addShape(bottom);
        model.addShape(top);

        String found = model.shapeAt(5, 0.01);
        assertEquals("top", found);
    }

    @Test
    public void testShapeAtReturnsNullWhenNone() {
        ClevisModel model = new ClevisModel();
        String found = model.shapeAt(1, 1);
        assertNull(found);
    }

    @Test
    public void testIntersectsDelegatesToBoundingBoxes() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 4, 4));
        model.addShape(new Rectangle("r2", 2, 2, 4, 4));
        model.addShape(new Rectangle("r3", 10, 10, 1, 1));

        assertTrue(model.intersects("r1", "r2"));
        assertFalse(model.intersects("r1", "r3"));
    }

    @Test
    public void testGroupAndUngroupShapes() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 1, 1));
        model.addShape(new Circle("c1", 5, 5, 1));

        List<String> names = new ArrayList<String>();
        names.add("r1");
        names.add("c1");

        model.groupShapes("g1", names);

        assertThrows(ClevisException.class, () -> model.getShape("r1"));
        assertThrows(ClevisException.class, () -> model.getShape("c1"));

        Shape g = model.getShape("g1");
        assertTrue(g.isGroup());

        List<Shape> components = model.ungroupShape("g1");

        assertEquals(2, components.size());
        assertEquals("r1", model.getShape("r1").getName());
        assertEquals("c1", model.getShape("c1").getName());
        assertThrows(ClevisException.class, () -> model.getShape("g1"));
    }

    @Test
    public void testListAllReverseZOrder() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 1, 1));
        model.addShape(new Circle("c1", 5, 5, 1));

        List<Shape> list = model.listAll();
        assertEquals("c1", list.get(0).getName());
        assertEquals("r1", list.get(1).getName());
    }

    @Test
    public void testListShapeUsesListInfo() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 1, 1));

        String info = model.listShape("r1");
        assertTrue(info.contains("Rectangle r1"));
    }

    @Test
    public void testBoundingBoxCommandReturnsString() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 1, 2, 3, 4));
        String bb = model.boundingBox("r1");

        assertEquals("1.00 2.00 3.00 4.00", bb);
    }
}