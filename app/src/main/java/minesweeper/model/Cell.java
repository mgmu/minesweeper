package minesweeper.model;

/**
 * A Cell represents an element of a grid. Located at a certain position, it can
 * be mined, holds the number of mines in the Cells around it and has a certain
 * visibility. 
 */
public class Cell {

    // Minimum number of mines around a Cell
    private static final int MIN_MINES_AROUND = 0;

    // Maximum number of mines around a Cell
    private static final int MAX_MINES_AROUND = 8;

    /**
     * The visibility of this Cell.
     */
    private Visibility visibility;

    /**
     * The number of mines around this Cell.
     */
    private int minesAround;

    /**
     * Indicates if this Cell is mined or not.
     */
    private boolean isMined;

    /**
     * The position of this Cell.
     */
    private Position position;

    /**
     * Class constructor that specifies visibility, number of mines around, a
     * flag that indicates if this Cell is mined and the Position of this Cell
     * @param visibility the visibility of this Cell
     * @param minesAround the number of mines around this Cell
     * @param isMined true if this Cell is mined, false otherwise
     * @param position the position of this Cell
     */
    public Cell(Visibility visibility, int minesAround, boolean isMined,
            Position position) {
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
}
