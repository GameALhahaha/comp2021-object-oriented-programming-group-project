package hk.edu.polyu.comp.comp2021.clevis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Graphics2D;

/**
 * Represents a group of shapes.
 * Implements the Composite Pattern by holding a list of Shape objects.
 */
public class Group extends Shape {
    private final List<Shape> components;

    /**
     * Constructs a Group object.
     * @param name The unique name of the group.
     * @param components The list of shapes to be grouped.
     */
    public Group(String name, List<Shape> components) {
        super(name);
        this.components = new ArrayList<>(components);
    }

    @Override
    public BoundingBox getBoundingBox() {
        if (components.isEmpty()) {
            return new BoundingBox(0, 0, 0, 0);
        }

        BoundingBox combinedBox = components.get(0).getBoundingBox();
        for (int i = 1; i < components.size(); i++) {
            combinedBox = combinedBox.combine(components.get(i).getBoundingBox());
        }

        return combinedBox;
    }

    @Override
    public Shape move(double dx, double dy) {
        List<Shape> movedComponents = components.stream()
                .map(shape -> shape.move(dx, dy))
                .collect(Collectors.toList());

        return new Group(getName(), movedComponents);
    }

    @Override
    public boolean containsPoint(Point p) {
        for (Shape component : components) {
            if (component.containsPoint(p)) {
                return true;
            }
        }
        return false;
    }

    @Override

    public void draw(Graphics2D g) {
        for (Shape component : components) {
            component.draw(g);
        }
    }

    @Override
    public String listInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Group %s: Components [", getName()));
        String componentNames = components.stream()
                .map(Shape::getName)
                .collect(Collectors.joining(", "));
        sb.append(componentNames);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public List<Shape> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public boolean isGroup() {
        return true;
    }
}
