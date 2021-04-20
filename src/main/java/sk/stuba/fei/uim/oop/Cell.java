package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Direction {
    Up(0),
    Right(1),
    Down(2),
    Left(3);

    public final int value;

    Direction(int i) {
        this.value = i;
    }
}

public class Cell {
    public static final int CELL_SIZE = 15;

    private int x;
    private int y;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public List<Boolean> getWalls() {
        return walls;
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

    @Deprecated
    public void show(Graphics g) {
        var size = Window.CANVAS_WIDTH / CELL_SIZE;
        var i = this.x * size;
        var j = this.y * size;

        g.setColor(Color.white);
        g.fillRect(i, j, size, size);
        g.setColor(Color.black);
        if (walls.get(0)) g.drawLine(i, j, i + size, j);
        if (walls.get(1)) g.drawLine(i + size, j, i + size, j + size);
        if (walls.get(2)) g.drawLine(i + size, j + size, i, j + size);
        if (walls.get(3)) g.drawLine(i, j + size, i, j);
    }
}
