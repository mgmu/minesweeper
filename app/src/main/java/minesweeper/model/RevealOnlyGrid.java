package minesweeper.model;

import java.awt.Dimension;

/**
 * A RevealOnlyGrid represents a Grid that supports the unique operation of
 * revealing its cells.
 */
public record RevealOnlyGrid(Grid grid) {

    /**
     * {@return true if the cell at the given position was indeed revealed}
     * @param position the position of the cell to reveal
     * @throws IllegalArgumentException if position is not inside this Grid
     * @throws NullPointerException if position is null
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
     * Computes the dimension of this Grid. Causes the heap allocation of a new
     * Dimension object. Use {@code height()} and {@code width()} for direct
     * acces.
     * @return the dimension of this Grid
     */
    public Dimension dimension() {
        return this.grid.dimension();
    }

    /**
     * {@return the cell at the given position}
     * @param position the position of the cell to get
     * @throws IllegalArgumentException if position is outside this Grid
     * @throws IllegalStateException if position is valid and no cell was found
     *                               at that position
     * @throws NullPointerException if position is null
     */
    public Cell cellAt(Position position) {
        return this.grid.cellAt(position);
    }
}
