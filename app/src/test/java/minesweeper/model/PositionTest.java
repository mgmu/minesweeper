package minesweeper.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class PositionTest {

    @Test void positionWithNegativeLineIndexThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(-1, 0));
    }

    @Test void positionWithNegativeColumnIndexThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(0, -8));
    }

    @Test void createdPositionWith5AsLineAndValidColumnHas5AsLineIndex() {
        Position sut = new Position(5, 4);
        assertEquals(5, sut.line());
    }

    @Test void createdPositionWith3AsColumnAndValidLineHas3AsColumnIndex() {
        Position sut = new Position(0, 3);
        assertEquals(3, sut.column());
    }

    @Test void positionsAreNotEqualIfOneIsNull() {
        Position sut = new Position(0, 0);
        assertNotEquals(sut, null);
    }

    @Test void positionIsNotEqualToIntArray() {
        Position sut = new Position(0, 1);
        assertNotEquals(sut, new int[0]);
    }

    @Test void positionsWithDifferentLineCoordinatesAreNotEqual() {
        Position p1 = new Position(0, 2);
        Position p2 = new Position(1, 4);
        assertNotEquals(p1, p2);
    }

    @Test void positionsWithDifferentColumnCoordinatesAreNotEqual() {
        Position p1 = new Position(4, 5);
        Position p2 = new Position(3, 54);
        assertNotEquals(p1, p2);
    }

    @Test void positionsWithSameCoordinatesAreEqual() {
        Position p1 = new Position(42, 32);
        Position p2 = new Position(42, 32);
        assertEquals(p1, p2);
    }

    @Test void originHas3NeighborsAndAreCorrect() {
        Position origin = new Position(0, 0);
        List<Position> expected = Arrays.asList(new Position(0, 1),
                new Position(1, 1), new Position(1, 0));
        assertEquals(expected, origin.neighbors());
    }

    @Test void positionAtFirstLineAndNotAtFirstColHas5NeighborsAndAreCorrect() {
        Position sut = new Position(0, 42);
        List<Position> expected = Arrays.asList(new Position(0, 43),
                new Position(1, 43), new Position(1, 42), new Position(1, 41),
                new Position(0, 41));
        assertEquals(expected, sut.neighbors());
    }

    @Test void positionAtFirstColAndNotAfFirstLineHas5NeighborsAndAreCorrect() {
        Position sut = new Position(42, 0);
        List<Position> expected = Arrays.asList(new Position(41, 0),
                new Position(41, 1), new Position(42, 1), new Position(43, 1),
                new Position(43, 0));
        assertEquals(expected, sut.neighbors());
    }

    @Test void positionWithCoordinatesDiffThan0Has8NeighborsAndAreCorrect() {
        Position sut = new Position(42, 42);
        List<Position> expected = Arrays.asList(new Position(41, 41),
                new Position(41, 42), new Position(41, 43),
                new Position(42, 43), new Position(43, 43),
                new Position(43, 42), new Position(43, 41),
                new Position(42, 41));
        assertEquals(expected, sut.neighbors());
    }
}
