package hk.edu.polyu.comp.comp2021.clevis.view;

import hk.edu.polyu.comp.comp2021.clevis.model.ClevisModel;
import hk.edu.polyu.comp.comp2021.clevis.model.Rectangle;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ClevisViewTest {

    @Test
    public void testPaintComponentDoesNotThrow() throws Exception {
        ClevisModel model = new ClevisModel();
        model.addShape(new Rectangle("r1", 0, 0, 10, 10));

        ClevisView view = new ClevisView(model);

        BufferedImage img = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        assertDoesNotThrow(() -> view.paintComponent(g));

        g.dispose();
    }

    @Test
    public void testUpdateViewDoesNotThrow() {
        ClevisModel model = new ClevisModel();
        ClevisView view = new ClevisView(model);

        assertDoesNotThrow(view::updateView);
    }
}
