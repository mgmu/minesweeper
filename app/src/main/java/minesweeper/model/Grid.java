package minesweeper.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Deque;
import java.util.ArrayDeque;

import java.awt.Dimension;

/**
 * A Grid encapsulates cells placed in order to form a grid of certain width and
 * height.
 */
public class Grid {

    // The width of this Grid.
    private final int width;

    // The height of this Grid.
    private final int height;

    // The cells of this Grid.
    private final List<Cell> cells;

    // The number of mines to place on this Grid.
    private final int mines;

    // Indicates if mines have been placed on this Grid
    private boolean isMined;

    /**
     * Class constructor that specifies the dimensions of the new Grid and the
     * number of mines on the Grid. The Grid is not initialized with the given
     * number of mined cells, call placeMines() in order to place them.
     * @param width the width of the new  Grid
     * @param height the height of the new Grid
     * @param mines the number of mines to place on the new Grid
     * @throws IllegalArgumentException if width, height or mines are strictly
     *         inferior to 0 or if mines is strictly superior to width * height
     */
    public Grid(int width, int height, int mines) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException(
              String.format("Illegal dimensions: %d, %d", width, height));
        }
        final int nbCells = width * height;
        if (mines < 0 || mines > nbCells) {
            throw new IllegalArgumentException(
              String.format("Illegal number of mines: %d", mines));
        }
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.isMined = false;
        this.cells = new ArrayList<>(nbCells);
        this.initializeCells();
    }

    /**
     * Class constructor that specifies the dimensions of the new Grid. The
     * number of mines is initialized to 0.
     * @param width the width of the new Grid
     * @param height the height of the new Grid
     */
    public Grid(int width, int height) {
        this(width, height, 0);
    }

    /**
     * Clears the cells of this Grid and adds width * height new hidden safe
     * cells.
     */
    public void initializeCells() {
        this.cells.clear();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Position pos = new Position(i, j);
                this.cells.add(new Cell(Visibility.HIDDEN, 0, false, pos));
            }
        }
        this.isMined = false;
    }

    /**
     * Generates mines random positions and places a mine at each generated
     * position.
     * @param excluded a list of positions that won't be selected
     */
    public void placeMines(List<Position> excluded) {
        this.isMined = true;
        List<Position> positions = Position.randomPositions(this.mines,
                this.height, this.width, excluded);
        for (Position position: positions) {
            Cell cell = this.cellAt(position);
            cell.mine();
            List<Position> neighbors = position.neighbors();
            for (Position pos: neighbors) {
                if (pos.inBounds(this.width, this.height)) {
                    Cell neighbor = this.cellAt(pos);
                    neighbor.incrementMinesAround();
                }
            }
        }
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
        Objects.requireNonNull(position);
        if (position.line() >= this.height || position.column() >= this.width)
            throw new IllegalArgumentException("Illegal position: " + position);
        for (Cell cell: this.cells) {
            if (cell.position().equals(position))
                return cell;
        }
        throw new IllegalStateException("No Cell found at " + position);
    }

    /**
     * {@return the number of mines to place on this Grid}
     */
    public int mines() {
        return this.mines;
    }

    /**
     * {@return the number of mines placed on this Grid}
     * Note that this number might be different than the number of mines
     * provided at construction.
     */
    public int minesPlaced() {
        int total = 0;
        for (Cell cell: this.cells) {
            if (cell.isMined())
                total++;
        }
        return total;
    }

    /**
     * {@return true if the cell at the given position was indeed revealed}
     * @param position the position of the cell to reveal
     * @throws IllegalArgumentException if position is not inside this Grid
     * @throws NullPointerException if position is null
     */
    public boolean revealCellAt(Position position) {
        Objects.requireNonNull(position);
        Cell cell = this.cellAt(position);
        if (cell.reveal()) {
            this.flood(cell);
            return true;
        }
        return false;
    }

    // Reveals surroundings of src if they are hidden, not mined and
    // without surrounding mines and propagates to its surroundings
    private void flood(Cell src) {
        if (src.minesAround() != 0 || src.isMined())
            return;

        Deque<Cell> toExplore = new ArrayDeque<>();
        toExplore.push(src);
        while (!toExplore.isEmpty()) {
            Cell cur = toExplore.pop();
            cur.reveal();
            if (cur.minesAround() != 0)
                continue;
            List<Position> neighbors = cur.neighborsPositions();
            for (Position position: neighbors) {
                if (!position.inBounds(this.width, this.height))
                    continue;
                Cell neigh = this.cellAt(position);
                if (neigh.isHidden() && !toExplore.contains(neigh)
                        && !neigh.isMined())
                    toExplore.add(neigh);
            }
        }
    }

    /**
     * {@return the height of this Grid}
     */
    public int height() {
        return this.height;
    }

    /**
     * {@return the width of this Grid}
     */
    public int width() {
        return this.width;
    }

    /**
     * Computes the dimension of this Grid. Causes the heap allocation of a new
     * Dimension object. Use {@code height()} and {@code width()} for direct
     * acces.
     * @return the dimension of this Grid
     */
    public Dimension dimension() {
        return new Dimension(this.width, this.height);
    }

    /**
     * {@return true if the cell at position was flagged and became hidden or
     * was hidden and then became flagged}
     * @param position the position of the cell to flag or unflag
     */
    public boolean flagCellAt(Position position) {
        Objects.requireNonNull(position);
        Cell cell = this.cellAt(position);
        return cell.flag();
    }

    /**
     * {@return true if this Grid is mined}
     */
    public boolean isMined() {
        return this.isMined;
    }

    /**
     * {@return true if at least one of the revealed cells of this Grid is
     * mined}
     */
    public boolean hasMineRevealed() {
        for (Cell cell: this.cells) {
            if (cell.isRevealed() && cell.isMined())
                return true;
        }
        return false;
    }
}
