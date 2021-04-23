package sk.stuba.fei.uim.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
    public static final int CELL_COUNT = 15;

    private final int x;
    private final int y;

    private boolean isVisited;
    private List<Boolean> walls;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

        this.isVisited = false;
        this.walls = new ArrayList<>(Arrays.asList(true, true, true, true));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setWalls(List<Boolean> walls) {
        this.walls = walls;
    }

    public boolean getWall(Direction direction) {
        return this.walls.get(direction.value);
    }

    public void setWall(Direction direction, boolean value) {
        this.walls.set(direction.value, value);
    }

}
