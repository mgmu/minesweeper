package minesweeper.view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;

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
}
