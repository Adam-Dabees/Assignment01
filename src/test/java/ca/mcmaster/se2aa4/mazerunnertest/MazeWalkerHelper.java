package ca.mcmaster.se2aa4.mazerunnertest;

import ca.mcmaster.se2aa4.mazerunner.MazeWalker;
import ca.mcmaster.se2aa4.mazerunner.PathFormatter;
import ca.mcmaster.se2aa4.mazerunner.Solver;
public class MazeWalkerHelper extends MazeWalker {

    public MazeWalkerHelper(String filePath, Solver solver, PathFormatter pathFormatter) {
        super(filePath, solver, pathFormatter);
    }

    @Override
    public boolean validatePath(String inputtedPath) {
        return super.validatePath(inputtedPath); // Accessing protected method
    }
    
    @Override
    public void findStartAndEndPoints() {
        super.findStartAndEndPoints();
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }
}