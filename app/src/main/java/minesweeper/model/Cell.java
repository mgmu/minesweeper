package minesweeper.model;

import java.util.Objects;
import java.util.List;

/**
 * A Cell represents an element of a grid. Located at a certain position, it can
 * be mined, holds the number of mines in the Cells around it and has a certain
 * visibility. 
 */
public class Cell {

    // Minimum number of mines around a Cell
    public static final int MIN_MINES_AROUND = 0;

    // Maximum number of mines around a Cell
    public static final int MAX_MINES_AROUND = 8;

    // The visibility of this Cell.
    private Visibility visibility;

    // The number of mines around this Cell.
    private int minesAround;

    // Indicates if this Cell is mined or not.
    private boolean isMined;

    // The position of this Cell.
    private Position position;

    /**
     * Class constructor that specifies visibility, number of mines around, a
     * flag that indicates if the new Cell is mined and the Position of thew new
     * Cell.
     * @param visibility the visibility of the new Cell
     * @param minesAround the number of mines around the new Cell
     * @param isMined true if the new Cell is mined, false otherwise
     * @param position the position of the new Cell
     * @throws IllegalArgumentException if minesAround is strictly inferior to
     *                                  MIN_MINES_AROUND or strictly superior to
     *                                  MAX_MINES_AROUND
     */
    public Cell(Visibility visibility, int minesAround, boolean isMined,
            Position position) {
        Objects.requireNonNull(visibility);
        Objects.requireNonNull(position);
        if (minesAround < Cell.MIN_MINES_AROUND
                || minesAround > Cell.MAX_MINES_AROUND) {
            throw new IllegalArgumentException(
              String.format("Illegal number of mines around: %d", minesAround));
        }
        this.visibility = visibility;
        this.minesAround = minesAround;
        this.isMined = isMined;
        this.position = position;
    }

    /**
     * {@return the visibility of this Cell}
     */
    public Visibility visibility() {
        return this.visibility;
    }

    /**
     * {@return the number of mines around this Cell}
     */
    public int minesAround() {
        return this.minesAround;
    }

    /**
     * {@return true if this Cell is mined, false otherwise}
     */
    public boolean isMined() {
        return this.isMined;
    }

    /**
     * {@return the position of this Cell}
     */
    public Position position() {
        return this.position;
    }

    /**
     * Places a mine on this Cell.
     */
    public void mine() {
        this.isMined = true;
    }

    /**
     * Reveals this Cell if possible. A Cell can be revealed if its Visibility
     * is HIDDEN, otherwise, it can not be revealed.
     * @return true if this Cell was indeed revealed, false otherwise.
     */
    public boolean reveal() {
        if (this.visibility == Visibility.HIDDEN) {
            this.visibility = Visibility.REVEALED;
            return true;
        }
        return false;
    }

    /**
     * Increments the number of mines around this Cell. If the number of mines
     * around this Cell is superior or equal the maximum number of mines around
     * a Cell, does nothing.
     */
    void incrementMinesAround() {
        if (this.minesAround < MAX_MINES_AROUND)
            this.minesAround++;
    }

    /**
     * {@return true if the visibility of this Cell is Visibility.HIDDEN}
     */
    public boolean isHidden() {
        return this.visibility == Visibility.HIDDEN;
    }

    /**
     * {@return the list of neighbor positions of this Cell position}
     */
    public List<Position> neighborsPositions() {
        return this.position.neighbors();
    }

    /**
     * {@return true if this Cell was hidden and became flagged or was flagged
     * and became hidden, false otherwise}
     */
    public boolean flag() {
        if (this.visibility == Visibility.FLAGGED) {
            this.visibility = Visibility.HIDDEN;
            return true;
        } else if (this.visibility == Visibility.HIDDEN) {
            this.visibility = Visibility.FLAGGED;
            return true;
        }
        return false;
    }
}
