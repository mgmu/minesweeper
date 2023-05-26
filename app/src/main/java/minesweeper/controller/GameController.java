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

    // Acts on model depending on button activated
    private void actionOnMouseButton(MouseEvent event, Position position) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            if (this.model.revealCellAt(position))
                this.model.notifyObservers();
        } else if (SwingUtilities.isRightMouseButton(event)) {
            if (this.model.flagCellAt(position))
                this.model.notifyObservers();
        }
    }

    // Returns the position of the activated cell, null if there was no cell
    // activated
    private Position positionOfClick(GridView gridView, MouseEvent event) {
        Point[][] centerPoints = gridView.centerPoints();
        Point click = event.getPoint();

        if (centerPoints == null)
            return null;

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
            return null;
        return new Position(l, c);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        Object src = event.getSource();
        if (!(src instanceof GridView))
            return;

        GridView gridView = (GridView) src;
        Position pos = this.positionOfClick(gridView, event);
        if (pos != null)
            this.actionOnMouseButton(event, pos);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        Object src = event.getSource();
        if (!(src instanceof GridView))
            return;

        GridView gridView = (GridView) src;
        Position pos = this.positionOfClick(gridView, event);
        if (pos != null)
        this.actionOnMouseButton(event, pos);        
    }
}
