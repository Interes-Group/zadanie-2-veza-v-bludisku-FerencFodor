package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;

    private MazePanel canvas;
    private Maze maze;
    private Player player;

    private String winCounterLabel;
    private int winCounter;
    private JLabel labelWin;

    private JPanel controlPanel;
    private CustomMouseListener cml;


    public Window() {
        super();

        maze = new Maze();
        player = new Player(maze);

        winCounter = 0;
        winCounterLabel = "Completed: ";

        maze.generateMaze();

        initCanvas();
        initControlPanel();

        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

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

        cp.add(controlPanel, gc);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void repaint()
    {
        fix();
        super.repaint();
        if (player.isFinish()) {
            player.setFinish(false);
            winCounter++;
            labelWin.setText(winCounterLabel + winCounter);
            reset();
        }
    }

    public void reset() {
        maze.reset();
        maze.generateMaze();
        player.setX(maze.getPlayerPos()[0]);
        player.setY(maze.getPlayerPos()[1]);
        fix();
        repaint();
    }

    private void initCanvas() {
        canvas = new MazePanel(maze, player);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        canvas.setFocusable(true);
        canvas.addKeyListener(new PlayerInput(this));

        cml = new CustomMouseListener(this);

        canvas.addMouseMotionListener(cml);
        canvas.addMouseListener(cml);
    }

    public void initControlPanel() {
        controlPanel = new JPanel(new GridBagLayout());

        labelWin = new JLabel(winCounterLabel + winCounter);

        JButton buttonUp = new JButton("UP");
        JButton buttonRight = new JButton("RIGHT");
        JButton buttonDown = new JButton("DOWN");
        JButton buttonLeft = new JButton("LEFT");
        JButton buttonReset = new JButton("RESET");

        labelWin.setFocusable(false);

        buttonUp.setFocusable(false);
        buttonRight.setFocusable(false);
        buttonDown.setFocusable(false);
        buttonLeft.setFocusable(false);
        buttonReset.setFocusable(false);

        buttonReset.addActionListener(ae -> {
            winCounter = 0;
            labelWin.setText(winCounterLabel + winCounter);
            reset();
        });

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

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        controlPanel.add(labelWin, gc);

        gc.gridwidth = 1;

        gc.gridx = 2;
        gc.gridy = 0;
        controlPanel.add(buttonReset, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        controlPanel.add(buttonUp, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        controlPanel.add(buttonLeft, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        controlPanel.add(buttonDown, gc);

        gc.gridx = 2;
        gc.gridy = 2;
        controlPanel.add(buttonRight, gc);
    }

    public void fix() {
        cml.setMousePointer(new Point(player.getPX(), player.getPY()));
        canvas.setGuidePoint(cml.getMousePointer());
    }

    public Player getPlayer() {
        return player;
    }

    public Maze getMaze() {
        return maze;
    }

    public MazePanel getCanvas() {
        return canvas;
    }

    public CustomMouseListener getCml() {
        return cml;
    }

    public void setCml(CustomMouseListener cml) {
        this.cml = cml;
    }
}


