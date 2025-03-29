package ca.mcmaster.se2aa4.mazerunnertest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.FactorizedFormatter;
import ca.mcmaster.se2aa4.mazerunner.MazeWalker;
import ca.mcmaster.se2aa4.mazerunner.PathFormatter;
import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.RightHandSolver;
import ca.mcmaster.se2aa4.mazerunner.Solver;
import ca.mcmaster.se2aa4.mazerunner.TremauxSolver;

class MazeWalkerTest {
    private MazeWalkerHelper mazeWalker;
    private MazeWalkerHelper TremauxMazeWalker;
    private Solver RightHandSolver;
    private Solver TremauxSolver;
    private PathFormatter factorizedFormatter;
    private Position pos1;
    private Position pos2;

    @BeforeEach
    void setUp() {
        factorizedFormatter = new FactorizedFormatter();
        RightHandSolver = new RightHandSolver();
        TremauxSolver = new TremauxSolver();// or any other solver
        mazeWalker = new MazeWalkerHelper("examples/small.maz.txt", RightHandSolver, factorizedFormatter);
        TremauxMazeWalker = new MazeWalkerHelper("examples/small.maz.txt", TremauxSolver, factorizedFormatter);
        pos1 = new Position(1, 1);
        pos2 = new Position(1, 2);
    }

    @Test
    void testReadMaze_ValidFile() {
        // Assuming the maze file is valid
        assertNotNull(mazeWalker);
    }

    @Test
    void testReadMaze_EmptyFile() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new MazeWalker("examples/empty.maz.txt", RightHandSolver, factorizedFormatter);
        });
        assertEquals("Maze file is empty.", exception.getMessage());
    }

    @Test
    void testFindStartAndEndPoints_ValidMaze() {
        mazeWalker.readMaze("examples/small.maz.txt");
        mazeWalker.findStartAndEndPoints();
        assertNotEquals(-1, mazeWalker.getStartRow());
        assertNotEquals(-1, mazeWalker.getEndRow());
    }

    @Test
    void testFindCorrectPath() {
        mazeWalker.readMaze("examples/small.maz.txt");
        mazeWalker.findStartAndEndPoints();
        String path = mazeWalker.findCorrectPath();
        assertNotNull(path);
        assertFalse(path.isEmpty());
    }

    @Test
    void testValidatePath_ValidPathTremaux() {
        TremauxMazeWalker.readMaze("examples/small.maz.txt");
        String validPath = TremauxMazeWalker.findCorrectPath(); // Example path
        assertTrue(TremauxMazeWalker.validatePath(validPath));
    }

    @Test
    void testValidatePath_ValidPathRightHand() {
        mazeWalker.readMaze("examples/small.maz.txt");
        String validPath = mazeWalker.findCorrectPath(); // Example path
        assertTrue(mazeWalker.validatePath(validPath));
    }

    @Test
    void testValidatePath_InvalidPath() {
        String invalidPath = "FFLFF"; // Example invalid path
        assertFalse(mazeWalker.validatePath(invalidPath));
    }

    @Test
    void testFactorizePath() {
        String path = "FFFRRFF";
        List<String> pathList = Arrays.asList(path.split(""));
        String factorized = factorizedFormatter.format(pathList);
        assertEquals("3F2R2F", factorized);
    }

    @Test
    void testEqualsEachOther() {
        Position pos1 = new Position(1, 1);
        Position pos2 = new Position(1, 1);
        assertTrue(pos1.equalsEachOther(pos2));
    }

    @Test
    void testCopy() {
        Position pos = new Position(1, 1);
        Position copy = pos.copy();
        assertEquals(pos.row, copy.row);
        assertEquals(pos.col, copy.col);
    }
}