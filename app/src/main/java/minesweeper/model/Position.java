package minesweeper.model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * A Position represents the line and column coordinates of an element inside a
 * matrix.
 */
public record Position(int line, int column) {

    /**
     * The position (0, 0).
     */
    public static final Position ORIGIN = new Position(0, 0); 

    /**
     * Class constructor that specifies line and column coordinates.
     * @param line the line coordinate
     * @param column the column coordinate
     * @throws IllegalArgumentException if line or column are inferior to 0
     */
    public Position {
        if (line < 0 || column < 0) {
            throw new IllegalArgumentException(
              String.format("Invalid position: %d, %d", line, column));
        }
    }

    /**
     * {@return a list of the neighbor Positions of this Position}
     */
    public List<Position> neighbors() {
        if (this.equals(Position.ORIGIN))
            return Arrays.asList(new Position(0, 1), new Position(1, 1),
                    new Position(1, 0));
        if (this.line == 0 && this.column > 0)
            return Arrays.asList(new Position(this.line, this.column + 1),
                    new Position(this.line + 1, this.column + 1),
                    new Position(this.line + 1, this.column),
                    new Position(this.line + 1, this.column - 1),
                    new Position(this.line, this.column -1));
        else if (this.line > 0 && this.column == 0)
            return Arrays.asList(new Position(this.line - 1, this.column),
                    new Position(this.line - 1, this.column + 1),
                    new Position(this.line, this.column + 1),
                    new Position(this.line + 1, this.column + 1),
                    new Position(this.line + 1, this.column));
        else
            return Arrays.asList(new Position(this.line - 1, this.column - 1),
                    new Position(this.line - 1, this.column),
                    new Position(this.line - 1, this.column + 1),
                    new Position(this.line, this.column + 1),
                    new Position(this.line + 1, this.column + 1),
                    new Position(this.line + 1, this.column),
                    new Position(this.line + 1, this.column - 1),
                    new Position(this.line, this.column - 1));
    }
}
