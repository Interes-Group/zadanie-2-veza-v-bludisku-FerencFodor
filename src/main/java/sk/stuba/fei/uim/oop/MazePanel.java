package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;


public class MazePanel extends JPanel {
    Maze maze;

    public MazePanel(Maze maze) {
        super();
        this.maze = maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        setBackground(Color.black);

        for (var cell : maze.getGrid()) {
            cell.show(g);
        }
    }
}
