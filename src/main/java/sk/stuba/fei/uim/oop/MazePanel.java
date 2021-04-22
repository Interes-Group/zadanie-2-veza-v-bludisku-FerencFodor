package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {
    Maze maze;
    Player player;
    List<Cell> grid;
    private Point guidePoint;
    private int step;

    public MazePanel(Maze maze, Player player) {
        super();
        this.maze = maze;
        this.player = player;
        this.step = Window.CANVAS_WIDTH / Cell.CELL_SIZE;

        this.guidePoint = new Point();
        this.grid = maze.getGrid();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        for (var cell : grid) {
            cell.show(g);
        }

        var fx = maze.getFinishPos()[0] * step;
        var fy = maze.getFinishPos()[1] * step;

        g.setColor(Color.blue);
        g.fillRect(fx + 3, fy + 3, step - 6, step - 6);

        var px = player.getPX() * step;
        var py = player.getPY() * step;

        g.setColor(Color.green);
        g.fillOval(px + 3, py + 3, step - 6, step - 6);
        g.setColor(Color.black);

        if (guidePoint.x != player.getPX() || guidePoint.y != player.getPY()) {
            g.setColor(Color.gray);
            g.fillOval(guidePoint.x * step + 3, guidePoint.y * step + 3, step - 6, step - 6);
            g.setColor(Color.black);
        }
    }

    public Point getGuidePoint() {
        return guidePoint;
    }

    public void setGuidePoint(Point guidePoint) {
        this.guidePoint = guidePoint;

    }
}