package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeWalker {
    private char[][] newMaze; 
    private int startRow = -1;
    private int endRow = -1;

    public MazeWalker(String filePath) {
        readMaze(filePath);
        findStartAndEndPoints();
    }

    public void readMaze(String filePath) {
        final Logger logger = LogManager.getLogger();

        List<String> mazeRows = new ArrayList<>();
        int maxCols = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read all lines, including blanks
            while ((line = reader.readLine()) != null) {
                mazeRows.add(line); // Add line as is (blank lines included)
                maxCols = Math.max(maxCols, line.length()); // Track the maximum row length
            }

            // Validate maze
            if (mazeRows.isEmpty()) {
                throw new IllegalStateException("Maze file is empty.");
            }

            // Initialize the 2D char array
            int rows = mazeRows.size();
            newMaze = new char[rows][maxCols];

            // Populate the 2D array, filling empty spaces for short rows
            for (int i = 0; i < rows; i++) {
                String row = mazeRows.get(i);
                for (int j = 0; j < maxCols; j++) {
                    if (j < row.length()) {
                        newMaze[i][j] = row.charAt(j); // Copy character
                    } else {
                        newMaze[i][j] = ' '; // Fill remaining spaces with ' '
                    }
                }
            }

        } catch (IOException e) {
            logger.error("/!\\ An error has occurred while reading the maze /!\\", e);
        }
    }

    private void findStartAndEndPoints() {
        for (int i = 0; i < newMaze.length; i++) {
            if (newMaze[i][0] == ' ') { // Check the first column for a gap
                startRow = i;
            }
            if (newMaze[i][newMaze[i].length - 1] == ' ') { // Check the last column for a gap
                endRow = i;
            }
        }

        if (startRow == -1 || endRow == -1) {
            throw new IllegalStateException("No valid start or end point found in the maze.");
        }
    }

    boolean validatePath(String inputtedPath) {
        Position currentPosition = new Position(startRow, 0); // Start at the first column
        Position finalPosition = new Position(endRow, newMaze[0].length - 1); // End at the last column
        int direction = 0;
        for (int i = 0; i < inputtedPath.length(); i++) {
            switch (inputtedPath.charAt(i)) {
                case 'F':
                    if (direction < 0 && Math.abs(direction)%4==1 || direction > 0 && direction%4==3) {
                        currentPosition.moveLeft();
                    }
                    else if (direction > 0 && direction%4==1 || direction < 0 && Math.abs(direction)%4==3) {
                        currentPosition.moveRight();
                    }
                    else if (direction%4==2) {
                        currentPosition.moveBack();
                    }
                    else {
                        currentPosition.moveForward();
                    }   if (!isMoveValid(currentPosition)) {
                        return false;
                    }   break;
                case 'R':
                    direction +=1;
                    break;
                case 'L':
                    direction -= 1;
                    break;
                default:
                    break;
            }
        }

        return currentPosition.equalsEachOther(finalPosition);
    }

    private boolean isMoveValid(Position position) {
        // Check if the position is out of bounds
        if (position.getRow() < 0 || position.getRow() >= newMaze.length ||
            position.getColumn() < 0 || position.getColumn() >= newMaze[0].length) {
            return false; // Out of bounds
        }
    
        // Check if the position hits a wall
        if (newMaze[position.getRow()][position.getColumn()] == '#') {
            return false; // Hit a wall
        }
    
        return true; // Move is valid
    }
    
    static class Position {
        int row;
        int col;

        Position(int r, int c) {
            this.row = r;
            this.col = c;
        }

        private void moveForward() {
            col += 1;
        }

        private void moveRight() {
            row += 1;
        }

        private void moveLeft() {
            row -= 1;
        }

        private void moveBack() {
            col -= 1;
        }

        public int getColumn() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public boolean equalsEachOther(Position otherPosition) {
            if (this.getColumn()==otherPosition.getColumn() && this.getRow() == otherPosition.getRow()) {
                return true;
            }
            else {
                return false;
            }
        }     
    }
}