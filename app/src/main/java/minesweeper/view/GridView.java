package minesweeper.view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import minesweeper.model.RevealOnlyGrid;

/**
 * The graphical representation of the Grid
 */
public class GridView extends JPanel implements Observer {

    // The grid to represent.
    private RevealOnlyGrid model;

    /**
     * Class constructor.
     */
    public GridView() {
        this.model = null;
        this.setPreferredSize(new Dimension(1000, 800));
        this.setBackground(Color.RED);
    }

    @Override
    public void update(RevealOnlyGrid model) {
        this.model = model;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.model == null)
            return;

        int l = Math.min(this.getWidth() / this.model.width(),
                this.getHeight() / this.model.height());

        Graphics2D g2d = (Graphics2D) graphics;

        // Draw cells
        BasicStroke stroke = new BasicStroke(5.0f);
        for (int i = 0; i < this.model.height(); i++) {
            for (int j = 0; j < this.model.width(); j++) {
                Rectangle2D.Double r =
                    new Rectangle2D.Double(j * l, i * l, l, l);
                g2d.setPaint(Color.YELLOW);
                g2d.fillRect(j*l, i*l, l, l);
                g2d.setPaint(Color.BLACK);
                g2d.draw(new Rectangle2D.Double(j * l, i * l, l, l));
            }
        }
    }
}
