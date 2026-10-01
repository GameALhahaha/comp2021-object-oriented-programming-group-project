package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.model.ClevisModel;
import hk.edu.polyu.comp.comp2021.clevis.model.Shape;
import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;
import hk.edu.polyu.comp.comp2021.clevis.view.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClevisControllerTest {

    @Test
    public void testCreateRectangleAndListShape() throws Exception {
        ClevisModel model = new ClevisModel();
        InMemoryLogger logger = new InMemoryLogger();
        ClevisController controller = new ClevisController(model, logger);

        controller.createRectangle("r1", 0, 0, 10, 20);
        controller.listShape("r1");

        assertFalse(logger.outputs.isEmpty());
        String output = logger.outputs.get(0);
        assertTrue(output.contains("Rectangle r1"));
    }

    @Test
    public void testCreateCircleInvalidRadius() throws Exception {
        ClevisModel model = new ClevisModel();
        InMemoryLogger logger = new InMemoryLogger();
        ClevisController controller = new ClevisController(model, logger);

        assertThrows(ClevisException.class, () ->
                controller.createCircle("c1", 0, 0, -1));
    }

    @Test
    public void testMoveAndBoundingBox() throws Exception {
        ClevisModel model = new ClevisModel();
        InMemoryLogger logger = new InMemoryLogger();
        ClevisController controller = new ClevisController(model, logger);

        controller.createRectangle("r1", 0, 0, 1, 1);
        controller.moveShape("r1", 2, 3);
        controller.getBoundingBox("r1");

        assertEquals(1, logger.outputs.size());
        assertEquals("2.00 3.00 1.00 1.00", logger.outputs.get(0));
    }

    @Test
    public void testShapeAtAndListAll() throws Exception {
        ClevisModel model = new ClevisModel();
        InMemoryLogger logger = new InMemoryLogger();
        ClevisController controller = new ClevisController(model, logger);

        controller.createRectangle("r1", 0, 0, 10, 10);
        controller.shapeAt(5, 0.01);
        controller.listAllShapes();

        assertEquals("r1", logger.outputs.get(0));
        assertTrue(logger.outputs.get(1).contains("Rectangle r1"));
    }

    @Test
    public void testCheckIntersection() throws Exception {
        ClevisModel model = new ClevisModel();
        InMemoryLogger logger = new InMemoryLogger();
        ClevisController controller = new ClevisController(model, logger);

        controller.createRectangle("r1", 0, 0, 4, 4);
        controller.createRectangle("r2", 2, 2, 4, 4);

        controller.checkIntersection("r1", "r2");
        assertEquals("true", logger.outputs.get(0));
    }

    private static class InMemoryLogger extends Logger {

        final List<String> commands = new ArrayList<String>();
        final List<String> outputs = new ArrayList<String>();

        InMemoryLogger() throws IOException {
            super(createTempPath(), createTempPath());
        }

        private static String createTempPath() throws IOException {
            java.io.File f = java.io.File.createTempFile("clevis_test", ".log");
            f.deleteOnExit();
            return f.getAbsolutePath();
        }

        @Override
        public void logCommand(String command) {
            commands.add(command);
        }

        @Override
        public void logOutput(String output) {
            outputs.add(output);
        }

        @Override
        public void closeHtmlLog() {
        }
    }
}