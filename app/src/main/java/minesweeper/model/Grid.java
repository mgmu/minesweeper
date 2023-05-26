package minesweeper.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import java.awt.Dimension;

import minesweeper.view.Observer;

/**
 * A Grid encapsulates cells placed in order to form a grid of certain width and
 * height.
 */
public class Grid implements Observable {

    // The width of this Grid.
    private final int width;

    // The height of this Grid.
    private final int height;

    // The cells of this Grid.
    private final List<Cell> cells;

    // The observers of this Grid.
    private final List<Observer> observers;

    /**
     * Class constructor that specifies the dimensions of the new Grid and the
     * number of mines on the Grid. The Grid is initialized with the given
     * number of mined cells.
     * @param width the width of the new  Grid
     * @param height the height of the new Grid
     * @param mines the number of mines on the new Grid
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
        this.cells = new ArrayList<>(nbCells);
        this.observers = new ArrayList<>();
        this.initializeCells();
        this.placeMines(mines);
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
    }

    /**
     * Generetes mines random positions and places a mine on each corresponding
     * cell. The other cells are left unmodified.
     * @param mines the number of mines to place
     */
    public void placeMines(int mines) {
        List<Position> positions = Position.randomPositions(mines, this.height,
                this.width);
        for (Position position: positions) {
            Cell cell = this.cellAt(position);
            cell.mine();
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
     * {@return the number of mines on this Grid}
     */
    public int mines() {
        int total = 0;
        for (Cell cell: this.cells) {
            if (cell.isMined())
                total++;
        }
        return total;
    }

    @Override
    public void add(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(new RevealOnlyGrid(this));
        }
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
        return cell.reveal();
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
}
