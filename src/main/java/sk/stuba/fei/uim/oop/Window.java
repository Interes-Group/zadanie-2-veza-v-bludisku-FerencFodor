package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

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

        cml = new CustomMouseListener(this);
        addMouseListener(cml);

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
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void repaint() {
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
        repaint();
    }

    private void initCanvas() {
        canvas = new MazePanel(maze, player);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        canvas.setFocusable(true);
        canvas.addKeyListener(new PlayerInput());
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
        JButton reset = new JButton("RESET");

        labelWin.setFocusable(false);
        buttonUp.setFocusable(false);
        buttonRight.setFocusable(false);
        buttonDown.setFocusable(false);
        buttonLeft.setFocusable(false);
        reset.setFocusable(false);

        reset.addActionListener(ae -> {
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
        controlPanel.add(reset, gc);

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


    public class PlayerInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'w') player.moveUp();
            if (e.getKeyChar() == 'a') player.moveLeft();
            if (e.getKeyChar() == 's') player.moveDown();
            if (e.getKeyChar() == 'd') player.moveRight();

            repaint();
        }
    }

    public class CustomMouseListener implements MouseMotionListener, MouseListener {

        public Point mousePointer;
        private int step;
        private List<Cell> grid;
        private Window window;

        public CustomMouseListener(Window window) {
            this.mousePointer = new Point();
            this.step = CANVAS_WIDTH / Cell.CELL_SIZE;
            grid = maze.getGrid();
            this.window = window;
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            var point = mouseEvent.getPoint();

            mousePointer.x = point.x % step == 0 ? point.x / step : (point.x - (point.x % step)) / step;
            mousePointer.y = point.y % step == 0 ? point.y / step : (point.y - (point.y % step)) / step;

            var xDif = Math.abs(mousePointer.x - player.getPX());
            var yDif = Math.abs(mousePointer.y - player.getPY());

            if (xDif >= yDif) {
                mousePointer.y = player.getPY();

                for (int i = 0; i < xDif; i++) {
                    var xPos = player.getPX() > mousePointer.x ? player.getPX() - i : player.getPX() + i;
                    var isWall = grid.get(getPos(xPos, player.getPY())).getWall(getDirection(mousePointer, xPos, mousePointer.y));

                    if (isWall) {
                        mousePointer.x = xPos;
                        break;
                    }
                }
            }

            if (xDif <= yDif) {
                mousePointer.x = player.getPX();

                for (int i = 0; i < yDif; i++) {
                    var yPos = player.getPY() > mousePointer.y ? player.getPY() - i : player.getPY() + i;
                    var isWall = grid.get(getPos(player.getPX(), yPos)).getWall(getDirection(mousePointer, mousePointer.x, yPos));

                    if (isWall) {
                        mousePointer.y = yPos;
                        break;
                    }
                }
            }

            canvas.setGuidePoint(mousePointer);
            canvas.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            player.setX(mousePointer.x);
            player.setY(mousePointer.y);
            player.checkFinish();
            window.repaint();
        }

        private int getPos(int x, int y) {
            return y * Cell.CELL_SIZE + x;
        }

        private int getPos() {
            return player.getPY() * Cell.CELL_SIZE + player.getPX();
        }

        private Direction getDirection(Point mouse, int x, int y) {
            if (mouse.y > y) return Direction.Down;
            if (mouse.y < y) return Direction.Up;
            if (mouse.x > x) return Direction.Right;
            if (mouse.x < x) return Direction.Left;
            return Direction.Up;
        }

        //<editor-fold desc="Unused">
        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
        //</editor-fold>
    }
}


