package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RightHandSolver implements Solver {
    private char[][] maze;
    private Position current;
    private Position end;
    private int direction;
    private List<String> path;

    @Override
    public List<String> solve(char[][] maze, int startRow, int endRow) {
        this.maze = maze;
        this.current = new Position(startRow, 0);
        this.end = new Position(endRow, maze[0].length - 1);
        this.direction = 0; // Initial direction: right
        this.path = new ArrayList<>();

        Set<String> visited = new HashSet<>();

        while (!current.equalsEachOther(end)) {
            String state = current.row + "," + current.col + "," + direction;
            if (visited.contains(state)) break; // Prevent infinite loops
            visited.add(state);

            Position rightPos = getRightPosition();
            Position frontPos = getFrontPosition();
            Position leftPos = getLeftPosition();

            if (isValid(rightPos)) {
                turnRight();
                moveForward();
            } else if (isValid(frontPos)) {
                moveForward();
            } else if (isValid(leftPos)) {
                turnLeft();
                moveForward();
            } else {
                turnAround();
            }
        }
        return path;
    }

    private Position getRightPosition() {
        Position p = current.copy();
        moveBasedOnDirection(p, (direction + 1) % 4);
        return p;
    }

    private Position getFrontPosition() {
        Position p = current.copy();
        moveBasedOnDirection(p, direction);
        return p;
    }

    private Position getLeftPosition() {
        Position p = current.copy();
        moveBasedOnDirection(p, (direction + 3) % 4);
        return p;
    }

    private void moveBasedOnDirection(Position pos, int dir) {
        switch (dir % 4) {
            case 0 -> pos.moveForward();
            case 1 -> pos.moveRight();
            case 2 -> pos.moveBack();
            case 3 -> pos.moveLeft();
        }
    }

    private void moveForward() {
        path.add("F");
        moveBasedOnDirection(current, direction);
    }

    private void turnRight() {
        direction = (direction + 1) % 4;
        path.add("R");
    }

    private void turnLeft() {
        direction = (direction + 3) % 4;
        path.add("L");
    }

    private void turnAround() {
        direction = (direction + 2) % 4;
        path.add("RR");
    }

    private boolean isValid(Position pos) {
        return pos.row >= 0 && pos.row < maze.length &&
               pos.col >= 0 && pos.col < maze[0].length &&
               maze[pos.row][pos.col] != '#';
    }
}