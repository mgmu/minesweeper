package minesweeper.model;

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
}
