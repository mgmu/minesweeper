package minesweeper.model;

import minesweeper.view.Observer;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a game of minesweeper.
 */
public class Game implements Observable {

    /**
     * Default height of the grid of a Game.
     */
    public static final int DEFAULT_HEIGHT = 16;

    /**
     * Minimum height of the grid of a Game.
     */
    public static final int MIN_HEIGHT = 1;

    /**
     * Maximum height of the grid of a Game.
     */
    public static final int MAX_HEIGHT = 256;

    /**
     * Default width of the grid of a Game.
     */
    public static final int DEFAULT_WIDTH = 30;

    /**
     * Minimum width of the grid of a Game.
     */
    public static final int MIN_WIDTH = 1;

    /**
     * Maximum width of the grid of a Game.
     */
    public static final int MAX_WIDTH = 256;

    /**
     * Default number of mines on the grid of a Game.
     */
    public static final int DEFAULT_MINES = 99;

    /**
     * Minimum number of mines on the grid of a Game.
     */
    public static final int MIN_MINES = 0;

    /**
     * Maximum number of mines on the grid of a Game.
     */
    public static final int MAX_MINES = MAX_HEIGHT * MAX_WIDTH;

    // The grid of this Game
    private final Grid grid;

    // The observers of this Game.
    private final List<Observer> observers;

    /**
     * The number of unflagged mines.
     */
    private int unflaggedMines;

    /**
     * Class constructor that initializes the grid to its default dimensions and
     * number of mines.
     */
    public Game() {
        this.grid = new Grid(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINES);
        this.observers = new ArrayList<>();
        this.unflaggedMines = DEFAULT_MINES;
    }

    /**
     * Class constructor specifying grid.
     * @param grid the grid of the new Game
     */
    public Game(Grid grid) {
        this.grid = grid;
        this.observers = new ArrayList<>();
        this.unflaggedMines = this.grid.mines();
    }

    /**
     * {@return the grid of this Game}
     */
    public Grid grid() {
        return this.grid;
    }

    @Override
    public void add(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    /**
     * {@return true if at least one of the revealed cells of the grid of this
     * Game is mined}
     */
    public boolean hasMineRevealed() {
        return this.grid.hasMineRevealed();
    }

    /**
     * {@return true if the cell at the given position was indeed revealed}
     * @param position the position of the cell to reveal
     * @throws IllegalArgumentException if position is not inside the grid of
     *                                  this Game
     * @throws NullPointerException if position is null
     */
    public boolean revealCellAt(Position position) {
        return this.grid.revealCellAt(position);
    }

    /**
     * {@return true if the cell at position was flagged and became hidden or
     * was hidden and then became flagged}
     * @param position the position of the cell to flag or unflag
     */
    public boolean flagCellAt(Position position) {
        boolean res = this.grid.flagCellAt(position);
        if (res) {
            Cell cell = this.grid.cellAt(position);
            if (cell.isFlagged())
                this.unflaggedMines--;
            else
                this.unflaggedMines++;
        }
        return res;
    }

    /**
     * {@return true if the grid of this Game is mined}
     */
    public boolean isMined() {
        return this.grid.isMined();
    }

    /**
     * Places the number of mines to place on the grid of this Game at random
     * positions, excluding the positions provided.
     * @param excluded a list of positions that won't be selected
     */
    public void placeMines(List<Position> excluded) {
        this.grid.placeMines(excluded);
    }

    /**
     * {@return the number of unflagged mines on the grid of this Game}
     */
    public int unflaggedMines() {
        return this.unflaggedMines;
    }
}
