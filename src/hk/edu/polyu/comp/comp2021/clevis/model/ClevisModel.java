package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.util.ClevisException;

import java.util.*;

/**
 * The core Model class for the Clevis application.
 * Manages all shapes and their Z-order.
 */
public class ClevisModel {
    private final Map<String, Shape> shapes;
    private final List<Shape> zOrder;

    /**
     * Returns the list of top-level shapes in Z-order.
     * @return An unmodifiable list of shapes.
     */
    public List<Shape> getZOrder() {
        return Collections.unmodifiableList(zOrder);
    }

    /**
     * Initializes an empty HashMap for shapes and an empty ArrayList for z-order.
     *
     */
    public ClevisModel() {
        this.shapes = new HashMap<>();
        this.zOrder = new ArrayList<>();
    }

    /**
     * Adds a new shape to the model.
     * @param shape The shape to add.
     * @throws ClevisException if the shape name is not defined.
     */
    public void addShape(Shape shape) throws ClevisException {
        if (shapes.containsKey(shape.getName())) {
            throw new ClevisException("Error: Shape name '" + shape.getName() + "' already exists.");
        }
        shapes.put(shape.getName(), shape);
        zOrder.add(shape);
    }

    /**
     * Gets a shape by its name.
     * @param name The name of the shape.
     * @return The Shape object.
     * @throws ClevisException if the shape name is not defined.
     */
    public Shape getShape(String name) throws ClevisException {
        Shape shape = shapes.get(name);
        if (shape == null) {
            throw new ClevisException("Error: Shape name '" + name + "' is not defined.");
        }
        return shape;
    }

    /**
     * Deletes a shape from the model.
     * @param name The name of the shape to delete.
     * @throws ClevisException if the shape name is not defined.
     */
    public void deleteShape(String name) throws ClevisException {
        Shape shape = getShape(name);
        shapes.remove(name);
        zOrder.remove(shape);
    }

    /**
     * Moves a shape.
     * @param name The name of the shape to move.
     * @param dx The horizontal displacement.
     * @param dy The vertical displacement.
     * @throws ClevisException if the shape name is not defined.
     */
    public void moveShape(String name, double dx, double dy) throws ClevisException {
        Shape oldShape = getShape(name);
        Shape newShape = oldShape.move(dx, dy);
        shapes.put(name, newShape);
        int index = zOrder.indexOf(oldShape);
        if (index != -1) {
            zOrder.set(index, newShape);
        }
    }

    /**
     * Finds the topmost shape that covers a point.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return The name of the topmost shape, or null if none covers the point.
     */
    public String shapeAt(double x, double y) {
        Point p = new Point(x, y);
        for (int i = zOrder.size() - 1; i >= 0; i--) {
            Shape shape = zOrder.get(i);
            if (shape.containsPoint(p)) {
                return shape.getName();
            }
        }
        return null;
    }

    /**
     * Reports whether two shapes intersect.
     * @param n1 Name of the first shape.
     * @param n2 Name of the second shape.
     * @return True if they intersect, false otherwise.
     * @throws ClevisException if either shape name is not defined.
     */
    public boolean intersects(String n1, String n2) throws ClevisException {
        Shape s1 = getShape(n1);
        Shape s2 = getShape(n2);
        return s1.getBoundingBox().intersects(s2.getBoundingBox());
    }

    /**
     * Groups a list of existing shapes into a new group shape.
     * @param groupName The name of the new group.
     * @param componentNames The names of the shapes to be grouped.
     * @throws ClevisException if the group name is already used, or any component name is not defined,
     * or if a component is already a group (as per the requirement: "shapes n1, n2, ... cannot be used directly").
     */
    public void groupShapes(String groupName, List<String> componentNames) throws ClevisException {
        if (shapes.containsKey(groupName)) {
            throw new ClevisException("Error: Group name '" + groupName + "' already exists.");
        }
        if (componentNames.isEmpty()) {
            throw new ClevisException("Error: Cannot create a group with an empty list of shapes.");
        }

        List<Shape> components = new ArrayList<>();
        for (String name : componentNames) {
            Shape component = getShape(name);
            components.add(component);
        }


        Group newGroup = new Group(groupName, components);
        for (Shape component : components) {
            shapes.remove(component.getName());
            zOrder.remove(component);
        }
        addShape(newGroup);
    }

    /**
     * Ungroups a shape that was created by grouping shapes.
     * @param groupName The name of the group to ungroup.
     * @return The list of component shapes that were ungrouped.
     * @throws ClevisException if the shape is not a group or the name is not defined.
     */
    public List<Shape> ungroupShape(String groupName) throws ClevisException {
        Shape shape = getShape(groupName);

        if (!shape.isGroup()) {
            throw new ClevisException("Error: Shape '" + groupName + "' is not a group and cannot be ungrouped.");
        }
        Group group = (Group) shape;
        List<Shape> components = group.getComponents();
        int groupIndex = zOrder.indexOf(group);
        shapes.remove(groupName);
        zOrder.remove(group);
        for (int i = 0; i < components.size(); i++) {
            Shape component = components.get(i);
            shapes.put(component.getName(), component);
            zOrder.add(groupIndex + i, component);
        }

        return components;
    }

    /**
     * Lists all shapes in decreasing Z-order.
     * @return A list of all top-level shapes in reverse Z-order.
     */
    public List<Shape> listAll() {
        List<Shape> reversedZOrder = new ArrayList<>(zOrder);
        Collections.reverse(reversedZOrder);
        return reversedZOrder;
    }

    /**
     * Lists the basic information about a shape.
     * @param name The name of the shape.
     * @return The formatted information string.
     * @throws ClevisException if the shape name is not defined.
     */
    public String listShape(String name) throws ClevisException {
        Shape shape = getShape(name);
        return shape.listInfo();
    }

    /**
     * Calculates the minimum bounding box of a shape.
     * @param name The name of the shape.
     * @return The formatted bounding box string.
     * @throws ClevisException if the shape name is not defined.
     */
    public String boundingBox(String name) throws ClevisException {
        Shape shape = getShape(name);
        return shape.getBoundingBox().toString();
    }
}
