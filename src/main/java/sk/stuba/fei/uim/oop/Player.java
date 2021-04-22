package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class Player extends JPanel implements ActionListener {

    private int x;
    private int y;
    private Maze maze;
    private boolean isFinish;

    public Player(Maze maze) {
        this.x = 0;
        this.y = 0;
        this.maze = maze;
        this.isFinish = false;
    }

    public int getPX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getPY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public void moveUp() {
        move(Direction.Up, 0, -1);
    }

    public void moveRight() {
        move(Direction.Right, 1, 0);
    }

    public void moveDown() {
        move(Direction.Down, 0, 1);
    }

    public void moveLeft() {
        move(Direction.Left, -1, 0);
    }

    private void move(Direction direction, int x, int y) {
        var grid = maze.getGrid();
        var cell = grid.get(this.y * Cell.CELL_SIZE + this.x);

        if (!cell.getWall(direction)) {
            this.x = this.x + x;
            this.y = this.y + y;
        }

        checkFinish();
    }

    public void checkFinish() {
        var pos = new int[]{this.x, this.y};
        if (Arrays.equals(maze.getFinishPos(), pos)) {
            isFinish = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }
}