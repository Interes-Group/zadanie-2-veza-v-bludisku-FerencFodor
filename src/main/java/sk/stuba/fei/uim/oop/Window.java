package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 600;

    private final Maze maze;
    private final Player player;

    private MazePanel canvas;
    private JPanel controlPanel;
    private JPanel guidePanel;

    private JLabel winCounterLabel;
    private final String winCounterString;
    private int winCounterNumber;

    private CustomMouseListener cml;

    public Window() {
        super();

        maze = new Maze();
        player = new Player(maze);

        winCounterNumber = 0;
        winCounterString = "Completed: ";

        maze.generateMaze();

        initCanvas();
        initControlPanel();
        initGuide();
        initWindow();
    }

    //Misc
    @Override
    public void repaint() {
        fixGuideGhosting();
        if (!canvas.getWalkable().equals(cml.getWalkable()))
            canvas.setWalkable(cml.getWalkable());

        super.repaint();
        if (player.isFinish()) {
            player.setFinish(false);
            cml.setFocused(false);
            setLabel(++winCounterNumber);
            reset();
        }
    }

    private void setLabel(int value) {
        winCounterNumber = value;
        winCounterLabel.setText(winCounterString + winCounterNumber);
    }

    public void reset() {
        maze.reset();
        maze.generateMaze();
        player.setX(maze.getPlayerPos()[0]);
        player.setY(maze.getPlayerPos()[1]);
        repaint();
    }

    private void fixGuideGhosting() {
        cml.setMousePointer(new Point(player.getPX(), player.getPY()));
        canvas.setGuidePoint(cml.getMousePointer());
    }

    //initializers
    private void initWindow() {
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

        //Guide
        gc.gridx = 2;
        gc.gridy = 1;

        gc.anchor = GridBagConstraints.PAGE_END;
        cp.add(guidePanel, gc);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private void initGuide() {
        guidePanel = new JPanel(new FlowLayout());
        guidePanel.setBorder(BorderFactory.createTitledBorder("How to play"));

        JLabel textField = new JLabel();
        textField.setText(
                "<html>Movement:<br>" +
                        "- Keyboard W/A/S/D or Arrow Keys,<br>" +
                        "- Use buttons on side,<br>" +
                        "- Click on the player to use mouse.<br>" +
                        "<br>" +
                        "Press Reset to start again.</html>"
        );

        guidePanel.add(textField);
    }

    private void initCanvas() {
        canvas = new MazePanel(this);

        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        canvas.setFocusable(true);

        cml = new CustomMouseListener(this);

        canvas.addKeyListener(new PlayerInput(this));
        canvas.addMouseMotionListener(cml);
        canvas.addMouseListener(cml);
    }

    private void initControlPanel() {
        controlPanel = new JPanel(new GridBagLayout());

        winCounterLabel = new JLabel(winCounterString + winCounterNumber);

        JButton buttonUp = new JButton("UP");
        JButton buttonRight = new JButton("RIGHT");
        JButton buttonDown = new JButton("DOWN");
        JButton buttonLeft = new JButton("LEFT");
        JButton buttonReset = new JButton("RESET");

        winCounterLabel.setFocusable(false);
        buttonUp.setFocusable(false);
        buttonRight.setFocusable(false);
        buttonDown.setFocusable(false);
        buttonLeft.setFocusable(false);
        buttonReset.setFocusable(false);

        buttonReset.addActionListener(ae -> {
            setLabel(0);
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
        controlPanel.add(winCounterLabel, gc);

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

    //Getters
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
}


