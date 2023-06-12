package minesweeper.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class CellTest {

    @Test
    void cellThrowsIllegalArgumentExceptionIfNegativeNumberOfMinesAround() {
        Visibility vis = Visibility.FLAGGED;
        Position pos = Position.ORIGIN;
        assertThrows(IllegalArgumentException.class,
                () -> new Cell(vis, -1, false, pos));
    }

    @Test
    void cellThrowsIllegalArgumentExceptionIfMoreThanMaxMinesAround() {
        Visibility vis = Visibility.REVEALED;
        assertThrows(IllegalArgumentException.class,
                () -> new Cell(vis, 9, true, Position.ORIGIN));
    }

    @Test
    void isMinedCellReturnsTrueAfterMiningCell() {
        Cell sut = new Cell(Visibility.HIDDEN, 0, false, Position.ORIGIN);
        sut.mine();
        assertTrue(sut.isMined());
    }

    @Test
    void revealedCellCanNotBeRevealed() {
        Cell sut = new Cell(Visibility.REVEALED, 0, false, Position.ORIGIN);
        assertFalse(sut.reveal());
    }

    @Test
    void hiddenCellCanBeRevealed() {
        Cell sut = new Cell(Visibility.HIDDEN, 0, false, Position.ORIGIN);
        assertTrue(sut.reveal());
    }

    @Test
    void flaggedCellCanNotBeRevealed() {
        Cell sut = new Cell(Visibility.FLAGGED, 0, false, Position.ORIGIN);
        assertFalse(sut.reveal());
    }

    @Test
    void cellIncrementMinesAroundIsLimitedToMax() {
        Cell sut = new Cell(Visibility.HIDDEN, 8, false, Position.ORIGIN);
        sut.incrementMinesAround();
        assertEquals(Cell.MAX_MINES_AROUND, sut.minesAround());
    }

    @Test
    void cellNeighborPositionsEqualCellPositionNeighbors() {
        Position position = new Position(4, 5);
        Cell sut = new Cell(Visibility.FLAGGED, 5, false, position);
        List<Position> expected = position.neighbors();
        assertEquals(expected, sut.neighborsPositions());
    }

    @Test
    void isHiddenCellWhenRevealedReturnsFalse() {
        Cell sut = new Cell(Visibility.REVEALED, 4, false, Position.ORIGIN);
        assertFalse(sut.isHidden());
    }

    @Test
    void isHiddenCellWhenFlaggedReturnsFalse() {
        Cell sut = new Cell(Visibility.FLAGGED, 0, true, Position.ORIGIN);
        assertFalse(sut.isHidden());
    }

    @Test
    void isHiddenCellWhenHiddenReturnsTrue() {
        Cell sut = new Cell(Visibility.HIDDEN, 3, true, Position.ORIGIN);
        assertTrue(sut.isHidden());
    }

    @Test
    void isRevealedCellWhenHiddenReturnsFalse() {
        Cell sut = new Cell(Visibility.HIDDEN, 5, false, new Position(4, 4));
        assertFalse(sut.isRevealed());
    }

    @Test
    void isRevealedCellWhenFlaggedReturnsFalse() {
        Cell sut = new Cell(Visibility.FLAGGED, 0, true, new Position(2, 7));
        assertFalse(sut.isRevealed());
    }

    @Test
    void isRevealedCellWhenRevealedReturnsTrue() {
        Cell sut = new Cell(Visibility.REVEALED, 4, true, new Position(8, 8));
        assertTrue(sut.isRevealed());
    }

    @Test
    void isFlaggedCellWhenHiddenReturnsFalse() {
        Cell sut = new Cell(Visibility.HIDDEN, 0, false, new Position(4, 4));
        assertFalse(sut.isFlagged());
    }

    @Test
    void isFlaggedCellWhenFlaggedReturnsTrue() {
        Cell sut = new Cell(Visibility.FLAGGED, 1, true, new Position(4, 3));
        assertTrue(sut.isFlagged());
    }

    @Test
    void isFlaggedCellWhenRevealedReturnsFalse() {
        Cell sut = new Cell(Visibility.REVEALED, 0, false, new Position(3, 4));
        assertFalse(sut.isFlagged());
    }
}
