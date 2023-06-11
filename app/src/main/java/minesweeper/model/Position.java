package minesweeper.model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Objects;

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
        List<Position> res = new ArrayList<>();
        if (this.equals(Position.ORIGIN)) {
            res.add(new Position(0, 1));
            res.add(new Position(1, 1));
            res.add(new Position(1, 0));
            return res;
        }
        if (this.line == 0 && this.column > 0) {
            res.add(new Position(this.line, this.column + 1));
            res.add(new Position(this.line + 1, this.column + 1));
            res.add(new Position(this.line + 1, this.column));
            res.add(new Position(this.line + 1, this.column - 1));
            res.add(new Position(this.line, this.column -1));
            return res;
        } else if (this.line > 0 && this.column == 0) {
            res.add(new Position(this.line - 1, this.column));
            res.add(new Position(this.line - 1, this.column + 1));
            res.add(new Position(this.line, this.column + 1));
            res.add(new Position(this.line + 1, this.column + 1));
            res.add(new Position(this.line + 1, this.column));
            return res;
        } else {
            res.add(new Position(this.line - 1, this.column - 1));
            res.add(new Position(this.line - 1, this.column));
            res.add(new Position(this.line - 1, this.column + 1));
            res.add(new Position(this.line, this.column + 1));
            res.add(new Position(this.line + 1, this.column + 1));
            res.add(new Position(this.line + 1, this.column));
            res.add(new Position(this.line + 1, this.column - 1));
            res.add(new Position(this.line, this.column - 1));
            return res;
        }
    }

    /**
     * {@return a list of n unique random positions inside [0; maxL] x
     * [0; maxC]}
     * @param n the number of random positions to generate
     * @param maxL the maximum line coordinate value
     * @param maxC the maximum column coordinate value
     * @param excluded a list of positions that can not be chosen
     * @throws IllegalArgumentException if n is strictly inferior to 0, if maxL
     *         or maxC are inferior to 0 or if n is strictly superior to
     *         maxL * maxC
     * @throws NullPointerException if excluded is null
     */
    public static List<Position> randomPositions(int n, int maxL, int maxC,
            List<Position> excluded) {
        Objects.requireNonNull(excluded);
        if (maxL < 1 || maxC < 1) {
            throw new IllegalArgumentException(
              String.format("Illegal maximum values: %d, %d", maxL, maxC));
        }
        if (n < 0 || n > maxL * maxC) {
            throw new IllegalArgumentException(
              String.format("Illegal number of random positions: %d", n));
        }
        List<Position> positions = new ArrayList<>(n);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            Position position;
            do {
                position = new Position(rand.nextInt(maxL), rand.nextInt(maxC));
            } while (positions.contains(position)
                    || excluded.contains(position));
            positions.add(position);
        }
        return positions;
    }

    /**
     * {@return true if this Position is inside the matrix of size width *
     * height}
     * @param width the width of the matrix
     * @param height the height of the matrix
     */
    public boolean inBounds(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException(
              String.format("Illegal bounds: %d, %d", width, height));
        }
        return this.line < height && this.column < width;
    }
}
