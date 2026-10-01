package hk.edu.polyu.comp.comp2021.clevis.view;

import hk.edu.polyu.comp.comp2021.clevis.model.ClevisModel;
import hk.edu.polyu.comp.comp2021.clevis.model.Shape;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The ClevisView class extends JPanel to provide a graphical rendering of the shapes
 */
public class ClevisView extends JPanel {

    private final ClevisModel model;
    private static final int CANVAS_SIZE = 600;
    private static final int PADDING = 50;

    /**
     * Constructs a ClevisView panel.
     * @param model The ClevisModel instance to draw shapes from.
     */
    public ClevisView(ClevisModel model) {
        this.model = model;
        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
        setBackground(Color.WHITE);
    }

    /**
     * Overrides the paintComponent method to draw all shapes in the model.
     * Shapes are drawn in reverse Z-order (highest Z-index first, which is last in the list).
     * @param g The Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        List<Shape> shapesToDraw = model.getZOrder();
        for (Shape shape : shapesToDraw) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            shape.draw(g2d);
        }
    }

    /**
     * Notifies the view to repaint itself.
     */
    public void updateView() {
        repaint();
    }
}
