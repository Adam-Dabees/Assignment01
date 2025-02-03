package ca.mcmaster.se2aa4.mazerunner;

public class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void moveForward() {
        this.col += 1; // Move right
    }

    public void moveRight() {
        this.row += 1; // Move down
    }

    public void moveLeft() {
        this.row -= 1; // Move up
    }

    public void moveBack() {
        this.col -= 1; // Move left
    }

    public boolean equalsEachOther(Position other) {
        return this.row == other.row && this.col == other.col;
    }

    public Position copy() {
        return new Position(this.row, this.col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}