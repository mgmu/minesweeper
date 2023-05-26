package minesweeper.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

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
        assertEquals(5, sut.mines());
    }

    @Test
    void gridWithoutMinesHas0Mines() {
        Grid sut = new Grid(40, 40, 0);
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
}
