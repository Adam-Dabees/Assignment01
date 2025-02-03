package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeWalker {
    private char[][] maze;
    private int startRow = -1;
    private int endRow = -1;
    private final Solver solver;

    public MazeWalker(String filePath, Solver solver) {
        this.solver = solver;
        readMaze(filePath);
        findStartAndEndPoints();
    }

    public void readMaze(String filePath) {
        List<String> mazeRows = new ArrayList<>();
        int maxCols = 0;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
    
            // Read all lines, including blanks
            while ((line = reader.readLine()) != null) {
                mazeRows.add(line); 
                maxCols = Math.max(maxCols, line.length()); 
            }
    
            if (mazeRows.isEmpty()) {
                throw new IllegalStateException("Maze file is empty.");
            }
    
            int rows = mazeRows.size();
            maze = new char[rows][maxCols];
    
            for (int i = 0; i < rows; i++) {
                String row = mazeRows.get(i);
                for (int j = 0; j < maxCols; j++) {
                    if (j < row.length()) {
                        maze[i][j] = row.charAt(j); 
                    } else {
                        maze[i][j] = ' '; // Fill missing spaces with ' '
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading maze file: " + e.getMessage(), e);
        }
    }
    
    private void findStartAndEndPoints() {
        for (int i = 0; i < maze.length; i++) {
            if (maze[i].length > 0 && maze[i][0] == ' ') { // Check the first column for a gap
                startRow = i;
            }
            if (maze[i].length > 0 && maze[i][maze[i].length - 1] == ' ') { // Check the last column for a gap
                endRow = i;
            }
        }
    
        if (startRow == -1 || endRow == -1) {
            throw new IllegalStateException("No valid start or end point found in the maze.");
        }
    }

    public String findCorrectPath() {
        List<String> path = solver.solve(maze, startRow, endRow);
        return factorizePath(String.join("", path));
    }

    private String factorizePath(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }

        StringBuilder compressedPath = new StringBuilder();
        int count = 1;
        char prevChar = path.charAt(0);

        for (int i = 1; i < path.length(); i++) {
            char currentChar = path.charAt(i);
            if (currentChar == prevChar) {
                count++;
            } else {
                if (count > 1) {
                    compressedPath.append(count);
                }
                compressedPath.append(prevChar);
                count = 1;
            }
            prevChar = currentChar;
        }

        // Append the last character or its count
        if (count > 1) {
            compressedPath.append(count);
        }
        compressedPath.append(prevChar);

        return compressedPath.toString();
    }

    public boolean validatePath(String inputtedPath) {
        Position currentPosition = new Position(startRow, 0);
        Position finalPosition = new Position(endRow, maze[0].length - 1);
        int direction = 0;
        int i = 0;

        while (i < inputtedPath.length()) {
            char currentChar = inputtedPath.charAt(i);
            int repeat = 1;

            // Check if the current character is a number (e.g., "3F" means move forward 3 times)
            if (Character.isDigit(currentChar)) {
                int start = i;
                while (i < inputtedPath.length() && Character.isDigit(inputtedPath.charAt(i))) {
                    i++;
                }
                repeat = Integer.parseInt(inputtedPath.substring(start, i));
                if (i >= inputtedPath.length()) break;
                currentChar = inputtedPath.charAt(i);
            }

            switch (currentChar) {
                case 'F':
                    for (int j = 0; j < repeat; j++) {
                        moveBasedOnDirection(currentPosition, direction);
                        if (!isValid(currentPosition)) return false;
                    }
                    break;
                case 'R':
                    direction = (direction + repeat) % 4;
                    break;
                case 'L':
                    direction = (direction - repeat + 4) % 4;
                    break;
            }
            i++;
        }
        return currentPosition.equalsEachOther(finalPosition);
    }

    private boolean isValid(Position pos) {
        if (pos.row < 0 || pos.row >= maze.length || pos.col < 0 || pos.col >= maze[0].length) {
            return false;
        }
        return maze[pos.row][pos.col] != '#';
    }

    private void moveBasedOnDirection(Position pos, int direction) {
        switch (direction % 4) {
            case 0 -> pos.moveForward();
            case 1 -> pos.moveRight();
            case 2 -> pos.moveBack();
            case 3 -> pos.moveLeft();
        }
    }
}