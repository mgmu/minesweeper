package minesweeper.model;

import java.awt.Dimension;

/**
 * A RevealOnlyGrid represents a Grid that supports the unique operation of
 * revealing its cells.
 */
public record RevealOnlyGrid(Grid grid) {

    /**
     * {@return true if the cell at the given position was indeed revealed}
     * @param position the position of the Cell to reveal
     */
    public boolean revealCellAt(Position position) {
        return this.grid.revealCellAt(position);
    }

    /**
     * {@return the height of this Grid}
     */
    public int height() {
        return this.grid.height();
    }

    /**
     * {@return the width of this Grid}
     */
    public int width() {
        return this.grid.width();
    }

    /**
     * {@return the dimensino of this Grid}
     */
    public Dimension dimension() {
        return this.grid.dimension();
    }
}
