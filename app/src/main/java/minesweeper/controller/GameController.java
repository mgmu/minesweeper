package minesweeper.controller;

import javax.swing.event.MouseInputAdapter;
import javax.swing.SwingUtilities;

import java.awt.Point;
import java.awt.event.MouseEvent;

import java.util.List;

import minesweeper.model.Game;
import minesweeper.model.Position;
import minesweeper.view.GridView;
import minesweeper.App;

/**
 * Controls the user input on the View. The actions provided by the
 * View are executed on the encapsulated model.
 */
public class GameController extends MouseInputAdapter {
    private Game model;

    // The app.
    private App app;

    // Indicates if this GameController is enabled.
    private boolean enabled;

    /**
     * Class constructor that specifies the grid to act on.
     * @param model the grid to act on
     * @param app the main application
     */
    public GameController(Game model, App app) {
        this.model = model;
        this.app = app;
        this.model.notifyObservers();
        this.setEnabled();
    }

    // Reveals the cell at position, and if it was indeed revealed, notifies
    // observers
    private void revealCellAndUpdate(Position position) {
        if (this.model.revealCellAt(position))
            this.model.notifyObservers();
    }

    // Flags the cell at position, and if it changed, notifies observers
    private void flagCellAndUpdate(Position position) {
        if (this.model.flagCellAt(position))
            this.model.notifyObservers();
    }

    // Acts on model depending on button activated
    private void actionOnMouseButton(MouseEvent event, Position position) {
        if (SwingUtilities.isLeftMouseButton(event))
            this.revealCellAndUpdate(position);
        else if (SwingUtilities.isRightMouseButton(event))
            this.flagCellAndUpdate(position);
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

        if (!this.enabled)
            return;

        GridView gridView = (GridView) src;
        Position pos = this.positionOfClick(gridView, event);
        if (pos != null) {
            if (!this.model.isMined()) {
                List<Position> excluded = pos.neighbors();
                excluded.add(pos);
                this.model.placeMines(excluded);
            }
            this.actionOnMouseButton(event, pos);
        }
        if (this.model.hasMineRevealed())
            this.enabled = false;
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        Object src = event.getSource();
        if (!(src instanceof GridView))
            return;

        if (!this.enabled)
            return;

        GridView gridView = (GridView) src;
        Position pos = this.positionOfClick(gridView, event);
        if (pos != null && SwingUtilities.isLeftMouseButton(event))
            this.revealCellAndUpdate(pos);
        if (this.model.hasMineRevealed())
            this.enabled = false;
    }

    /**
     * Sets the new model of this GameController.
     * @param model the new model
     */
    public void setModel(Game model) {
        this.model = model;
        this.model.notifyObservers();
        this.setEnabled();
    }

    // Checks the state of the model and enables this GameController according
    // to the result
    private void setEnabled() {
        if (this.model.hasMineRevealed())
            this.enabled = false;
        else
            this.enabled = true;
    }
}
