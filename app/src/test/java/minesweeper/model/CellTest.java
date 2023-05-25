package minesweeper.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
