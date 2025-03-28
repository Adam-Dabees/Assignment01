package ca.mcmaster.se2aa4.mazerunnertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.MazeWalker;
import ca.mcmaster.se2aa4.mazerunner.RightHandSolver;
import ca.mcmaster.se2aa4.mazerunner.Solver;
import ca.mcmaster.se2aa4.mazerunner.TremauxSolver;

class MazeWalkerTest {
    private MazeWalkerHelper mazeWalker;
    private MazeWalkerHelper TremauxMazeWalker;
    private Solver RightHandSolver;
    private Solver TremauxSolver;

    @BeforeEach
    void setUp() {
        RightHandSolver = new RightHandSolver();
        TremauxSolver = new TremauxSolver();// or any other solver
        mazeWalker = new MazeWalkerHelper("examples/small.maz.txt", RightHandSolver);
        TremauxMazeWalker = new MazeWalkerHelper("examples/small.maz.txt", TremauxSolver);
    }

    @Test
    void testReadMaze_ValidFile() {
        // Assuming the maze file is valid
        assertNotNull(mazeWalker);
    }

    @Test
    void testReadMaze_EmptyFile() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new MazeWalker("examples/empty.maz.txt", RightHandSolver);
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
        String factorized = mazeWalker.factorizePath(path);
        assertEquals("3F2R2F", factorized);
    }

    @Test
    void testFactorizePath_EmptyPath() {
        String factorized = mazeWalker.factorizePath("");
        assertEquals("", factorized);
    }
}