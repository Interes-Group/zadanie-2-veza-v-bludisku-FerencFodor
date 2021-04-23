package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazePanel extends JPanel {
    private final int step;
    private final Window window;
    Maze maze;
    Player player;
    List<Cell> grid;
    private Point guidePoint;
    private List<Point> walkable;

    public MazePanel(Window window) {
        super();
        this.window = window;
        this.maze = this.window.getMaze();
        this.player = this.window.getPlayer();
        this.step = Window.CANVAS_WIDTH / Cell.CELL_COUNT;

        this.guidePoint = new Point();
        this.walkable = new ArrayList<>();
        this.grid = maze.getGrid();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.white);
        g.fillRect(0,0,Window.CANVAS_WIDTH, Window.CANVAS_HEIGHT);
        g.setColor(Color.black);

        paintWalkable(g);
        paintMaze(g);

        if ((guidePoint.x != player.getPX() || guidePoint.y != player.getPY()) && window.getCml().isFocused()) {
            paintGuide(g);
        }

        painFinish(g);
        paintPlayer(g);
    }

    //Painters

    private void paintGuide(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(guidePoint.x * step + 1, guidePoint.y * step + 1, step - 2, step - 2);
    }

    private void paintWalkable(Graphics g) {
        if(walkable.isEmpty())
            return;

        g.setColor(Color.pink);
        for (var cell : walkable) {
            if (cell.x != player.getPX() || cell.y != player.getPY())
                g.fillRect(cell.x * step, cell.y * step, step, step);
        }
    }

    private void paintPlayer(Graphics g) {
        var px = player.getPX() * step;
        var py = player.getPY() * step;
        var poly = new Polygon();

        poly.addPoint(px + step / 2, py + step / 3);
        poly.addPoint(px + step / 4, py + step);
        poly.addPoint(px + 3 * step / 4, py + step);

        g.setColor(Color.green);
        g.fillOval(px + step / 4, py, step / 2, step / 2);
        g.fillPolygon(poly);
    }

    private void painFinish(Graphics g) {
        var fx = maze.getFinishPos()[0] * step;
        var fy = maze.getFinishPos()[1] * step;
        var div = 4;
        var offset = 4;
        var divStep = (step - 2 * offset) / div;


        for (var i = 0; i < div; i++) {
            for (var j = 0; j < div; j++) {
                g.setColor((i + j) % 2 == 0 ? Color.black : Color.white);
                g.fillRect(fx + i * divStep + offset, fy + j * divStep + offset,
                        divStep, divStep);
            }
        }

        g.setColor(Color.black);
        g.drawRect(fx + offset, fy + offset, step - 2 * offset, step - 2 * offset);
    }

    private void paintMaze(Graphics g) {
        for (var cell : grid) {
            paintCell(cell, g);
        }
    }

    private void paintCell(Cell cell, Graphics g) {
        var i = cell.getX() * step;
        var j = cell.getY() * step;

        g.setColor(Color.black);
        if (cell.getWall(Direction.Up)) g.drawLine(i, j, i + step, j);
        if (cell.getWall(Direction.Right)) g.drawLine(i + step, j, i + step, j + step);
        if (cell.getWall(Direction.Down)) g.drawLine(i + step, j + step, i, j + step);
        if (cell.getWall(Direction.Left)) g.drawLine(i, j + step, i, j);
    }

    //Getters & Setters
    public void setGuidePoint(Point guidePoint) {
        this.guidePoint = guidePoint;
    }

    public List<Point> getWalkable() {
        return walkable;
    }

    public void setWalkable(List<Point> walkable) {
        this.walkable.clear();
        this.walkable = walkable;
    }
}