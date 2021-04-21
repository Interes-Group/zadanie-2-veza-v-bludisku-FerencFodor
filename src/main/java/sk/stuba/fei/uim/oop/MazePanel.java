package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;


public class MazePanel extends JPanel {
    Maze maze;
    Player player;

    private int step = Window.CANVAS_WIDTH / Cell.CELL_SIZE;

    public MazePanel(Maze maze, Player player) {
        super();
        this.maze = maze;
        this.player = player;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        for (var cell : maze.getGrid()) {
            cell.show(g);
        }

        var px = player.getPX()*step;
        var py = player.getPY()*step;

        g.setColor(Color.green);
        g.fillOval(px +3, py + 3, step - 6, step - 6);
        g.setColor(Color.black);
    }
}
