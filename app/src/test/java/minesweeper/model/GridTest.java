package minesweeper.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import java.util.ArrayList;

class GridTest {

    @Test
    void gridWithNegativeWidthThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(-1, 0, 4));
    }

    @Test
    void gridWithNegativeHeightThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(3, -12, 4));
    }

    @Test
    void gridWithNegativeNumberOfMinesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(4, 3, -1));
    }

    @Test
    void gridWithNumberOfMinesSuperiorToSurfaceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(5, 5, 26));
    }

    @Test
    void cellAtPositionWithInvalidLineCoordinateThrowsException() {
        Grid sut = new Grid(5, 5, 4);
        Position pos = new Position(5, 5);
        assertThrows(IllegalArgumentException.class, () -> sut.cellAt(pos));
    }

    @Test
    void cellAtPositionWithInvalidColumnCoordinateThrowsException() {
        Grid sut = new Grid(10, 5, 2);
        Position pos = new Position(4, 25);
        assertThrows(IllegalArgumentException.class, () -> sut.cellAt(pos));
    }

    @Test
    void cellAtOriginReturnsCellAtOrigin() {
        Grid sut = new Grid(10, 5, 2);
        Cell cell = sut.cellAt(Position.ORIGIN);
        assertEquals(Position.ORIGIN, cell.position());
    }

    @Test
    void gridWith5MinesHas5Mines() {
        Grid sut = new Grid(10, 4, 5);
        sut.placeMines(new ArrayList<>());
        assertEquals(5, sut.mines());
    }

    @Test
    void gridWithoutMinesHas0Mines() {
        Grid sut = new Grid(40, 40, 0);
        sut.placeMines(new ArrayList<>());
        assertEquals(0, sut.mines());
    }

    @Test
    void revealHiddenCellMakesCellRevealed() {
        Grid sut = new Grid(5, 5, 0);
        sut.revealCellAt(Position.ORIGIN);
        Cell cell = sut.cellAt(Position.ORIGIN);
        assertEquals(Visibility.REVEALED, cell.visibility());
    }

    @Test
    void gridDimensionCorrespondsToWidthAndHeight() {
        Grid sut = new Grid(42, 42);
        Dimension expected = new Dimension(sut.width(), sut.height());
        assertEquals(expected, sut.dimension());
    }

    @Test
    void flagCellAtNullPositionThrowsNPE() {
        Grid sut = new Grid(3, 3);
        assertThrows(NullPointerException.class, () -> sut.flagCellAt(null));
    }

    @Test
    void flagRevealedCellReturnsFalse() {
        Grid sut = new Grid(3, 3);
        Position position = new Position(2, 2);
        sut.revealCellAt(position);
        assertFalse(sut.flagCellAt(position));
    }

    @Test
    void flagHiddenCellReturnsTrue() {
        Grid sut = new Grid(2, 5);
        Position position = new Position(1, 0);
        sut.flagCellAt(position);
        assertTrue(sut.flagCellAt(position));
    }

    @Test
    void gridWithoutRevealedCellsReturnFalse() {
        Grid sut = new Grid(4, 4);
        assertFalse(sut.hasMineRevealed());
    }

    @Test
    void gridWithoudMinesAndRevealedCellsReturnsFalse() {
        Grid sut = new Grid(2, 2);
        sut.revealCellAt(Position.ORIGIN);
        sut.revealCellAt(new Position(0, 1));
        sut.revealCellAt(new Position(1, 0));
        sut.revealCellAt(new Position(1, 1));
        assertFalse(sut.hasMineRevealed());
    }

    @Test
    void gridFullyMinedReturnsTrueWhenMineRevealedFromTheFirstReveal() {
        Grid sut = new Grid(4, 4, 16);
        sut.placeMines(new ArrayList<>());
        sut.revealCellAt(Position.ORIGIN);
        assertTrue(sut.hasMineRevealed());
    }

    @Test
    void newGridIsNotCompleted() {
        Grid sut = new Grid(4, 4, 5);
        sut.placeMines(new ArrayList<>());
        assertFalse(sut.isCompleted());
    }

    @Test
    void gridWithoutMinesAndFullyRevealedIsCompleted() {
        Grid sut = new Grid(2, 2, 0);
        sut.placeMines(new ArrayList<>());
        sut.revealCellAt(Position.ORIGIN);
        sut.revealCellAt(new Position(0, 1));
        sut.revealCellAt(new Position(1, 0));
        sut.revealCellAt(new Position(1, 1));
        assertTrue(sut.isCompleted());
    }
}
