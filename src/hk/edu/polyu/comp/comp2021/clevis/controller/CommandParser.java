package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parses the command line input and validates the arguments.
 */
public class CommandParser {

    /**
     * Parses a command string into a list of tokens.
     * @param commandLine The full command line string.
     * @return A list of command tokens.
     */
    public static List<String> parse(String commandLine) {
        return Arrays.stream(commandLine.trim().split("\\s+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Validates and extracts arguments for a command.
     * @param tokens The command tokens.
     * @param expectedCount The expected number of arguments (excluding the command name).
     * @return A list of arguments.
     * @throws ClevisException if the argument count is incorrect.
     */
    private static List<String> validateAndExtract(List<String> tokens, int expectedCount) throws ClevisException {
        if (tokens.size() - 1 != expectedCount) {
            throw new ClevisException(String.format("Error: Command '%s' expects %d argument(s), but received %d.",
                    tokens.get(0), expectedCount, tokens.size() - 1));
        }
        return tokens.subList(1, tokens.size());
    }

    /**
     * Validates and converts a string argument to a double.
     * @param arg The string argument.
     * @param argName The name of the argument for error reporting.
     * @return The double value.
     * @throws ClevisException if the argument is not a valid double.
     */
    private static double parseDouble(String arg, String argName) throws ClevisException {
        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            throw new ClevisException(String.format("Error: Invalid value for %s. Expected a number, but got '%s'.", argName, arg));
        }
    }

    /**
     * Processes the tokens and executes the corresponding action on the ClevisController.
     * @param tokens The command tokens.
     * @param controller The controller instance to execute the command on.
     * @return True if the command was 'quit', false otherwise.
     * @throws ClevisException for command-specific errors.
     */
    public static boolean executeCommand(List<String> tokens, ClevisController controller) throws ClevisException {
        if (tokens.isEmpty()) {
            return false;
        }

        String command = tokens.get(0).toLowerCase();
        List<String> args;

        try {
            switch (command) {
                case "rectangle":
                    args = validateAndExtract(tokens, 5);
                    String rectName = args.get(0);
                    double rectX = parseDouble(args.get(1), "x");
                    double rectY = parseDouble(args.get(2), "y");
                    double rectW = parseDouble(args.get(3), "width");
                    double rectH = parseDouble(args.get(4), "height");
                    controller.createRectangle(rectName, rectX, rectY, rectW, rectH);
                    break;

                case "line":
                    args = validateAndExtract(tokens, 5);
                    String lineName = args.get(0);
                    double lineX1 = parseDouble(args.get(1), "x1");
                    double lineY1 = parseDouble(args.get(2), "y1");
                    double lineX2 = parseDouble(args.get(3), "x2");
                    double lineY2 = parseDouble(args.get(4), "y2");
                    controller.createLine(lineName, lineX1, lineY1, lineX2, lineY2);
                    break;

                case "circle":
                    args = validateAndExtract(tokens, 4);
                    String circleName = args.get(0);
                    double circleX = parseDouble(args.get(1), "x");
                    double circleY = parseDouble(args.get(2), "y");
                    double circleR = parseDouble(args.get(3), "radius");
                    controller.createCircle(circleName, circleX, circleY, circleR);
                    break;

                case "square":
                    args = validateAndExtract(tokens, 4);
                    String squareName = args.get(0);
                    double squareX = parseDouble(args.get(1), "x");
                    double squareY = parseDouble(args.get(2), "y");
                    double squareL = parseDouble(args.get(3), "side length");
                    controller.createSquare(squareName, squareX, squareY, squareL);
                    break;

                case "group":
                    if (tokens.size() < 3) {
                        throw new ClevisException("Error: Command 'group' expects at least 2 arguments (groupName and at least one componentName).");
                    }
                    String groupName = tokens.get(1);
                    List<String> componentNames = tokens.subList(2, tokens.size());
                    controller.groupShapes(groupName, componentNames);
                    break;

                case "ungroup":
                    args = validateAndExtract(tokens, 1);
                    controller.ungroupShape(args.get(0));
                    break;

                case "delete":
                    args = validateAndExtract(tokens, 1);
                    controller.deleteShape(args.get(0));
                    break;

                case "boundingbox":
                    args = validateAndExtract(tokens, 1);
                    controller.getBoundingBox(args.get(0));
                    break;

                case "move":
                    args = validateAndExtract(tokens, 3);
                    String moveName = args.get(0);
                    double moveDx = parseDouble(args.get(1), "dx");
                    double moveDy = parseDouble(args.get(2), "dy");
                    controller.moveShape(moveName, moveDx, moveDy);
                    break;

                case "shapeat":
                    args = validateAndExtract(tokens, 2);
                    double shapeAtX = parseDouble(args.get(0), "x");
                    double shapeAtY = parseDouble(args.get(1), "y");
                    controller.shapeAt(shapeAtX, shapeAtY);
                    break;

                case "intersect":
                    args = validateAndExtract(tokens, 2);
                    controller.checkIntersection(args.get(0), args.get(1));
                    break;

                case "list":
                    args = validateAndExtract(tokens, 1);
                    controller.listShape(args.get(0));
                    break;

                case "listall":
                    args = validateAndExtract(tokens, 0);
                    controller.listAllShapes();
                    break;

                case "quit":
                    args = validateAndExtract(tokens, 0);
                    return true;

                default:
                    throw new ClevisException("Error: Unknown command '" + command + "'.");
            }
        } catch (ClevisException e) {
            throw e;
        } catch (Exception e) {
            throw new ClevisException("An unexpected error occurred during command execution: " + e.getMessage(), e);
        }

        return false;
    }
}
