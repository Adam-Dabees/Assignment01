package ca.mcmaster.se2aa4.mazerunnertest;

import ca.mcmaster.se2aa4.mazerunner.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void testMoveForward() {
        Position pos = new Position(0, 0);
        pos.moveForward();
        assertEquals(0, pos.row);
        assertEquals(1, pos.col);
    }

    @Test
    void testMoveRight() {
        Position pos = new Position(0, 0);
        pos.moveRight();
        assertEquals(1, pos.row);
        assertEquals(0, pos.col);
    }

    @Test
    void testMoveLeft() {
        Position pos = new Position(1, 0);
        pos.moveLeft();
        assertEquals(0, pos.row);
        assertEquals(0, pos.col);
    }

    @Test
    void testMoveBack() {
        Position pos = new Position(0, 1);
        pos.moveBack();
        assertEquals(0, pos.row);
        assertEquals(0, pos.col);
    }

    @Test
    void testEqualsEachOther() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 1);
        assertTrue(pos1.equalsEachOther(pos2));
    }

    @Test
    void testNotEqualsEachOther() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 2);
        assertFalse(pos1.equalsEachOther(pos2));
    }

    @Test
    void testCopy() {
        Position pos = new Position(1, 1);
        Position copy = pos.copy();
        assertEquals(pos.row, copy.row);
        assertEquals(pos.col, copy.col);
    }
}
