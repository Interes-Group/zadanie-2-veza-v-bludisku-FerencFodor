package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class Window extends JFrame{
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;

    private MazePanel canvas;
    private Maze maze;
    private Player player;
    private JPanel controlPanel;

    public Window() {
        super();

        maze = new Maze();
        maze.generateMaze();

        player = new Player(0,0, maze);

        canvas = new MazePanel(maze, player);

        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        controlPanel = new JPanel();

        JButton reset = new JButton("RESET");
        reset.setFocusable(false);
        reset.addActionListener(ae -> {
            dispose();
            new Window();
        });

        GridBagConstraints gc = new GridBagConstraints();

        canvas.setFocusable(true);
        canvas.addKeyListener(new PlayerInput());


        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        //Canvas
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 2;
        cp.add(canvas, gc);
        //Control
        gc.gridx = 2;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;

        cp.add(MovementButtons(reset), gc);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }


    public JPanel MovementButtons(JButton reset) {
        JButton buttonUp = new JButton("UP");
        JButton buttonRight = new JButton("RIGHT");
        JButton buttonDown = new JButton("DOWN");
        JButton buttonLeft = new JButton("LEFT");

        buttonUp.addActionListener(ae -> {
            player.moveUp();
            repaint();
        });

        buttonRight.addActionListener(ae -> {
            player.moveRight();
            repaint();
        });

        buttonDown.addActionListener(ae -> {
            player.moveDown();
            repaint();
        });

        buttonLeft.addActionListener(ae -> {
            player.moveLeft();
            repaint();
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(buttonUp ,gc);

        gc.gridx = 0;
        gc.gridy = 2;
        panel.add(buttonLeft,gc);

        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(buttonDown,gc);

        gc.gridx = 2;
        gc.gridy = 2;
        panel.add(buttonRight,gc);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 3;
        panel.add(reset,gc);
        return panel;
    }

    public class PlayerInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar() == 'w') player.moveUp();
            if(e.getKeyChar() == 'a') player.moveLeft();
            if(e.getKeyChar() == 's') player.moveDown();
            if(e.getKeyChar() == 'd') player.moveRight();

            repaint();
        }
    }

}


