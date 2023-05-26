package minesweeper.controller;

import javax.swing.event.MouseInputAdapter;
import javax.swing.SwingUtilities;

import java.awt.Point;
import java.awt.event.MouseEvent;

import minesweeper.model.Grid;
import minesweeper.model.Position;
import minesweeper.view.GridView;

/**
 * Controls the user input on the View. The actions provided by the
 * View are executed on the encapsulated model.
 */
public class GameController extends MouseInputAdapter {

    // The model to act on
    private final Grid model;

    /**
     * Class constructor that specifies the grid to act on.
     * @param model the grid to act on
     */
    public GameController(Grid model) {
        this.model = model;
        this.model.notifyObservers();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        Object src = event.getSource();
        if (!(src instanceof GridView))
            return;

        GridView gridView = (GridView) src;
        Point[][] centerPoints = gridView.centerPoints();
        Point click = event.getPoint();

        if (centerPoints == null)
            return;

        // Compute position of clicked cell with euclidian distance from click
        // to cell center
        int minDist = Integer.MAX_VALUE;
        int l = Integer.MAX_VALUE;
        int c = Integer.MAX_VALUE;
        for (int i = 0; i < centerPoints.length; i++) {
            for (int j = 0; j < centerPoints[i].length; j++) {
                Point center = centerPoints[i][j];
                int dist = (int) center.distance(click);
                if (dist < minDist) {
                    minDist = dist;
                    l = i;
                    c = j;
                }
            }
        }

        if (minDist > gridView.sideLength())
            return;

        Position pos = new Position(l, c);

        if (SwingUtilities.isLeftMouseButton(event)) {
            if (this.model.revealCellAt(pos))
                this.model.notifyObservers();
        } else if (SwingUtilities.isRightMouseButton(event)) {
            if (this.model.flagCellAt(pos))
                this.model.notifyObservers();
        }
    }
}
