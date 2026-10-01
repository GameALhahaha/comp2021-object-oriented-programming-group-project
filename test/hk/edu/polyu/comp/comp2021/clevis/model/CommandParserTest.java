package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    @Test
    public void testParseSplitsTokens() {
        List<String> tokens = CommandParser.parse("  rectangle   r1 0 0 10 20  ");
        assertEquals(6, tokens.size());
        assertEquals("rectangle", tokens.get(0));
        assertEquals("r1", tokens.get(1));
    }

    @Test
    public void testExecuteUnknownCommand() {
        ClevisControllerStub controller = new ClevisControllerStub();
        List<String> tokens = CommandParser.parse("unknowncmd a b");

        assertThrows(ClevisException.class, () ->
                CommandParser.executeCommand(tokens, controller));
    }

    @Test
    public void testExecuteRectangleSuccess() throws Exception {
        ClevisControllerStub controller = new ClevisControllerStub();
        List<String> tokens = CommandParser.parse("rectangle r1 1 2 3 4");

        boolean quit = CommandParser.executeCommand(tokens, controller);
        assertFalse(quit);
        assertEquals("rectangle", controller.lastCommand);
        assertEquals("r1", controller.lastArgs[0]);
    }

    @Test
    public void testExecuteQuit() throws Exception {
        ClevisControllerStub controller = new ClevisControllerStub();
        List<String> tokens = CommandParser.parse("quit");

        boolean quit = CommandParser.executeCommand(tokens, controller);
        assertTrue(quit);
    }

    @Test
    public void testExecuteRectangleWrongArgCount() {
        ClevisControllerStub controller = new ClevisControllerStub();
        List<String> tokens = CommandParser.parse("rectangle r1 1 2 3");

        assertThrows(ClevisException.class, () ->
                CommandParser.executeCommand(tokens, controller));
    }

    @Test
    public void testExecuteMoveInvalidDouble() {
        ClevisControllerStub controller = new ClevisControllerStub();
        List<String> tokens = CommandParser.parse("move r1 dx 5");

        assertThrows(ClevisException.class, () ->
                CommandParser.executeCommand(tokens, controller));
    }

    // Simple stub controller that records which method was called
    private static class ClevisControllerStub extends ClevisController {

        String lastCommand;
        String[] lastArgs;

        ClevisControllerStub() {
            super(null, null);
        }

        @Override
        public void createRectangle(String name, double x, double y, double w, double h) {
            lastCommand = "rectangle";
            lastArgs = new String[]{name, String.valueOf(x), String.valueOf(y),
                    String.valueOf(w), String.valueOf(h)};
        }

        @Override
        public void createLine(String name, double x1, double y1, double x2, double y2) {
            lastCommand = "line";
            lastArgs = new String[]{name};
        }

        @Override
        public void createCircle(String name, double x, double y, double r) {
            lastCommand = "circle";
            lastArgs = new String[]{name};
        }

        @Override
        public void createSquare(String name, double x, double y, double l) {
            lastCommand = "square";
            lastArgs = new String[]{name};
        }

        @Override
        public void groupShapes(String groupName, java.util.List<String> componentNames) {
            lastCommand = "group";
            lastArgs = new String[]{groupName};
        }

        @Override
        public void ungroupShape(String groupName) {
            lastCommand = "ungroup";
            lastArgs = new String[]{groupName};
        }

        @Override
        public void deleteShape(String name) {
            lastCommand = "delete";
            lastArgs = new String[]{name};
        }

        @Override
        public void moveShape(String name, double dx, double dy) {
            lastCommand = "move";
            lastArgs = new String[]{name, String.valueOf(dx), String.valueOf(dy)};
        }

        @Override
        public void getBoundingBox(String name) {
            lastCommand = "boundingbox";
            lastArgs = new String[]{name};
        }

        @Override
        public void shapeAt(double x, double y) {
            lastCommand = "shapeat";
            lastArgs = new String[]{String.valueOf(x), String.valueOf(y)};
        }

        @Override
        public void checkIntersection(String n1, String n2) {
            lastCommand = "intersect";
            lastArgs = new String[]{n1, n2};
        }

        @Override
        public void listShape(String name) {
            lastCommand = "list";
            lastArgs = new String[]{name};
        }

        @Override
        public void listAllShapes() {
            lastCommand = "listall";
            lastArgs = new String[0];
        }
    }
}