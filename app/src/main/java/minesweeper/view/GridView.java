package minesweeper.view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import minesweeper.model.RevealOnlyGrid;
import minesweeper.model.Position;
import minesweeper.model.Cell;
import minesweeper.model.Visibility;

/**
 * The graphical representation of the Grid
 */
public class GridView extends JPanel implements Observer {

    // The grid to represent.
    private RevealOnlyGrid model;

    // The previous grid dimension
    private Dimension previousGridDim;

    // The current grid dimension
    private Dimension currentGridDim;

    // The center points of the cells of the grid
    private Point[][] centerPoints;

    /**
     * Class constructor.
     */
    public GridView() {
        this.model = null;
        this.previousGridDim = null;
        this.currentGridDim = null;
        this.centerPoints = null;
        this.setPreferredSize(new Dimension(1000, 800));
    }

    @Override
    public void update(RevealOnlyGrid model) {
        this.model = model;
        this.currentGridDim = this.model.dimension();
        this.repaint();
    }

    /**
     * {@return the side length of a cell based on the dimensions of this panel
     * and the grid}
     */ 
    public int sideLength() {
        return Math.min(this.getWidth() / this.currentGridDim.width,
                this.getHeight() / this.currentGridDim.height);
    }

    // Computes the center points of every cell of the grid
    private void computeCellCenterPoints() {
        int gh = this.currentGridDim.height;
        int gw = this.currentGridDim.width;
        this.centerPoints = new Point[gh][gw];
        int l = this.sideLength();
        int offset = l / 2;
        for (int i = 0; i < gh; i++) {
            for (int j = 0; j < gw; j++) {
                this.centerPoints[i][j] =
                    new Point(offset + j * l, offset + i * l);
            }
        }
    }

    /**
     * {@return the center points of every cell of the grid}
     */
    public Point[][] centerPoints() {
        return this.centerPoints;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.model == null)
            return;

        // Update center points on grid dimension update
        if (!this.currentGridDim.equals(this.previousGridDim)) {
            this.previousGridDim = this.currentGridDim;
            this.computeCellCenterPoints();
        }

        int l = this.sideLength();
        int o = l / 2;

        Graphics2D g2d = (Graphics2D) graphics;

        // Draw cells
        BasicStroke stroke = new BasicStroke(5.0f);
        for (int i = 0; i < this.centerPoints.length; i++) {
            for (int j = 0; j < this.centerPoints[i].length; j++) {
                Rectangle2D.Double cellBorder =
                    new Rectangle2D.Double(j * l - o, i * l - o, l, l);
                Cell cell = this.model.cellAt(new Position(i, j));
                Visibility visibility = cell.visibility();
                if (visibility == Visibility.HIDDEN) {
                    g2d.setPaint(Color.YELLOW);
                    g2d.fillRect(j*l, i*l, l, l);
                } else if (visibility == Visibility.FLAGGED) {
                    g2d.setPaint(Color.RED);
                    g2d.fillRect(j*l, i*l, l, l);
                } else {
                    StringBuilder builder = new StringBuilder();
                    if (cell.isMined())
                        builder.append("X");
                    else
                        builder.append(cell.minesAround());
                    g2d.drawString(builder.toString(),
                            (int)(j * l + (0.75) * o),
                            (int)(i * l + (1.5) * o));
                }
                g2d.setPaint(Color.BLACK);
                g2d.draw(new Rectangle2D.Double(j * l, i * l, l, l));
            }
        }
    }
}
