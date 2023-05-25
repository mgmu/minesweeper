package minesweeper.view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Color;

/**
 * The graphical representation of the Grid
 */
public class GridView extends JPanel {

    public GridView() {
        this.setPreferredSize(new Dimension(1000, 800));
        this.setBackground(Color.RED);
    }
}
