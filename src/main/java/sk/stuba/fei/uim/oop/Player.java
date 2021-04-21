package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.*;

public class Player extends JPanel implements ActionListener {

    private int x;
    private int y;
    private Maze maze;

    public Player(int x, int y, Maze maze) {
        this.x = x;
        this.y = y;
        this.maze = maze;
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

    public void moveUp(){
        move(Direction.Up, 0, -1);
    }

    public void moveRight() {
        move(Direction.Right,1, 0);
    }

    public void moveDown(){
        move(Direction.Down, 0,1);
    }

    public void moveLeft(){
        move(Direction.Left, -1, 0);
    }

    private void move(Direction direction, int x, int y) {
        var grid = maze.getGrid();
        var cell = grid.get(this.y * Cell.CELL_SIZE + this.x);

        if(!cell.getWall(direction)){
            this.x = this.x + x;
            this.y = this.y +y;
        }

        System.out.println(this.x + " " + this.y);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }
}