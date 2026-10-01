package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.model.*;
import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;
import hk.edu.polyu.comp.comp2021.clevis.view.ClevisView;

import javax.swing.SwingUtilities;
import hk.edu.polyu.comp.comp2021.clevis.view.Logger;

import java.util.List;

/**
 * Controller for the Clevis application.
 *
 */
public class ClevisController {
    private final ClevisModel model;
    private final Logger logger;
    private ClevisView view;

    /**
     * Create a new controller instance.
     *
     * @param model  the Clevis model instance used to manage shapes
     * @param logger the logger used to record textual output
     */
    public ClevisController(ClevisModel model, Logger logger) {
        this.model = model;
        this.logger = logger;
    }

    /**
     * Attach a view to this controller.
     *
     * @param view the UI view to attach
     */
    public void setView(ClevisView view) {
        this.view = view;
    }

    private void updateView() {
        if (view != null) {
            SwingUtilities.invokeLater(view::updateView);
        }
    }

    /**
     * Create and add a rectangle to the model.
     *
     * @param name the name of the rectangle
     * @param x    the x-coordinate of the rectangle's
     * @param y    the y-coordinate of the rectangle's
     * @param w    the width of the rectangle
     * @param h    the height of the rectangle
     * 
     */
    public void createRectangle(String name, double x, double y, double w, double h) throws ClevisException {
        if (w <= 0 || h <= 0) throw new ClevisException("Error: Width and height must be positive.");
        model.addShape(new Rectangle(name, x, y, w, h));
        updateView();
    }

    /**
     * Create and add a line to the model.
     *
     * @param name the name of the line
     * @param x1   x-coordinate of the first endpoint
     * @param y1   y-coordinate of the first endpoint
     * @param x2   x-coordinate of the second endpoint
     * @param y2   y-coordinate of the second endpoint
     * 
     */
    public void createLine(String name, double x1, double y1, double x2, double y2) throws ClevisException {
        model.addShape(new Line(name, x1, y1, x2, y2));
        updateView();
    }

    /**
     * Create and add a circle to the model.
     *
     * @param name the name of the circle
     * @param x    the x-coordinate of the circle's 
     * @param y    the y-coordinate of the circle's
     * @param r    the radius of the circle
     * 
     */
    public void createCircle(String name, double x, double y, double r) throws ClevisException {
        if (r <= 0) throw new ClevisException("Error: Radius must be positive.");
        model.addShape(new Circle(name, x, y, r));
        updateView();
    }

    /**
     * Create and add a square to the model.
     *
     * @param name the name of the square
     * @param x    the x-coordinate of the square's 
     * @param y    the y-coordinate of the square's 
     * @param l    the side length
     * 
     */
    public void createSquare(String name, double x, double y, double l) throws ClevisException {
        if (l <= 0) throw new ClevisException("Error: Side length must be positive.");
        model.addShape(new Square(name, x, y, l));
        updateView();
    }

    /**
     * Group multiple existing shapes into a new composite shape.
     *
     * @param groupName      the name for the new group
     * @param componentNames list of existing shape names to include
     * 
     */
    public void groupShapes(String groupName, List<String> componentNames) throws ClevisException {
        model.groupShapes(groupName, componentNames);
        updateView();
    }

    /**
     * Ungroup a previously created group shape, restoring its components.
     *
     * @param groupName the name of the group to ungroup
     * 
     */
    public void ungroupShape(String groupName) throws ClevisException {
        model.ungroupShape(groupName);
        updateView();
    }

    /**
     * Delete a shape by name
     *
     * @param name the name of the shape to remove
     * 
     */
    public void deleteShape(String name) throws ClevisException {
        model.deleteShape(name);
        updateView();
    }

    /**
     * Move a shape 
     *
     * @param name the name of the shape to move
     * @param dx   horizontal 
     * @param dy   vertical
     * 
     */
    public void moveShape(String name, double dx, double dy) throws ClevisException {
        model.moveShape(name, dx, dy);
        updateView();
    }

    /**
     * Retrieve and log the bounding box of a named shape.
     *
     * @param name the name of the shape
     * 
     */
    public void getBoundingBox(String name) throws ClevisException {
        String bb = model.boundingBox(name);
        logger.logOutput(bb);
        System.out.println(bb);
    }

    /**
     * Find and log the name of the shape at a given point.
     *
     * @param x x-coordinate of the query point
     * @param y y-coordinate of the query point
     */
    public void shapeAt(double x, double y) {
        String shapeName = model.shapeAt(x, y);
        String output;
        if (shapeName != null) {
            output = shapeName;
        } else output = "null";
        //String output = shapeName != null ? shapeName : "null";
        logger.logOutput(output);
        System.out.println(output);
    }

    /**
     * Check whether two named shapes intersect and log the result.
     *
     * @param n1 name of the first shape
     * @param n2 name of the second shape
     * 
     */
    public void checkIntersection(String n1, String n2) throws ClevisException {
        boolean intersects = model.intersects(n1, n2);
        String output = String.valueOf(intersects);
        logger.logOutput(output);
        System.out.println(output);
    }

    /**
     * List details for a named shape and send them to the logger
     *
     * @param name name of the shape to list
     * 
     */
    public void listShape(String name) throws ClevisException {
        String listInfo = model.listShape(name);
        logger.logOutput(listInfo);
        System.out.println(listInfo);
    }

    /**
     * List all shapes currently in the model and output their info.
     */
    public void listAllShapes() {
        List<Shape> allShapes = model.listAll();
        if (allShapes.isEmpty()) {
            System.out.println("No shapes drawn.");
            logger.logOutput("No shapes drawn.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Shape shape : allShapes) {
            sb.append(shape.listInfo()).append("\n");
        }
        String outputAll = sb.toString().trim();
        logger.logOutput(outputAll);
        System.out.println(outputAll);
    }
}
