package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public interface Solver {
    List<String> solve(char[][] maze, int startRow, int endRow);
}