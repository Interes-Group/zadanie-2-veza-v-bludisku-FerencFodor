package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;


    private MazePanel canvas;
    private Maze maze;
    private JPanel controlPanel;

    public Window() {
        super();

        maze = new Maze();
        maze.generateMaze();

        canvas = new MazePanel(maze);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(100, CANVAS_HEIGHT));

        JButton reset = new JButton("RESET");
        reset.setFocusable(false);
        controlPanel.add(reset, BorderLayout.SOUTH);
        reset.addActionListener(ae -> {
            dispose();
            new Window();
        });

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(controlPanel, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
