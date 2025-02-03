package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TremauxSolver implements Solver {
    private char[][] maze;
    private Position current;
    private Position end;
    private int direction;
    private List<String> path;
    private Map<String, Integer> visitedPaths; // Tracks path markings

    @Override
    public List<String> solve(char[][] maze, int startRow, int endRow) {
        this.maze = maze;
        this.current = new Position(startRow, 0);
        this.end = new Position(endRow, maze[0].length - 1);
        this.direction = 0; // Start facing right
        this.path = new ArrayList<>();
        this.visitedPaths = new HashMap<>();

        while (!current.equalsEachOther(end)) {
            String posKey = current.row + "," + current.col;

            // Mark the current passage (increment visit count)
            visitedPaths.put(posKey, visitedPaths.getOrDefault(posKey, 0) + 1);

            // Get available directions
            List<Integer> possibleMoves = getAvailableMoves();

            if (!possibleMoves.isEmpty()) {
                // Prefer unvisited or least visited paths
                possibleMoves.sort(Comparator.comparingInt(move -> getVisitCount(getNextPosition(move))));

                int bestMove = possibleMoves.get(0);
                move(bestMove);
            } else {
                // Dead-end: Backtrack to last junction
                backtrack();
            }
        }
        return path;
    }

    private List<Integer> getAvailableMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Position nextPos = getNextPosition(i);
            if (isValid(nextPos)) {
                moves.add(i);
            }
        }
        return moves;
    }

    private Position getNextPosition(int dir) {
        Position next = current.copy();
        switch (dir) {
            case 0 -> next.moveForward();
            case 1 -> next.moveRight();
            case 2 -> next.moveBack();
            case 3 -> next.moveLeft();
        }
        return next;
    }

    private int getVisitCount(Position pos) {
        return visitedPaths.getOrDefault(pos.row + "," + pos.col, 0);
    }

    private void move(int newDirection) {
        if (newDirection != direction) {
            if ((newDirection - direction + 4) % 4 == 1) {
                path.add("R");
            } else if ((newDirection - direction + 4) % 4 == 3) {
                path.add("L");
            } else {
                path.add("RR");
            }
            direction = newDirection;
        }
        path.add("F");
        moveBasedOnDirection(current, direction);
    }

    private void backtrack() {
        while (!visitedPaths.isEmpty()) {
            String posKey = current.row + "," + current.col;
            if (visitedPaths.getOrDefault(posKey, 0) < 2) break; // Stop at a junction

            Position prevPos = getNextPosition((direction + 2) % 4);
            if (isValid(prevPos)) {
                move((direction + 2) % 4);
                return;
            }
        }
    }

    private boolean isValid(Position pos) {
        return pos.row >= 0 && pos.row < maze.length &&
               pos.col >= 0 && pos.col < maze[0].length &&
               maze[pos.row][pos.col] != '#';
    }

    private void moveBasedOnDirection(Position pos, int dir) {
        switch (dir % 4) {
            case 0 -> pos.moveForward();
            case 1 -> pos.moveRight();
            case 2 -> pos.moveBack();
            case 3 -> pos.moveLeft();
        }
    }
}