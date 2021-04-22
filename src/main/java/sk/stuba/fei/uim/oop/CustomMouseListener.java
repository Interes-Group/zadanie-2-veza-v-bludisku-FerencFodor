package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class CustomMouseListener implements MouseMotionListener, MouseListener {

    private Point mousePointer;
    private int step;
    private List<Cell> grid;
    private Player player;
    private MazePanel canvas;
    private Window window;

    public CustomMouseListener(Window window) {
        this.mousePointer = new Point();
        this.step = Window.CANVAS_WIDTH / Cell.CELL_SIZE;
        this.window = window;

        grid = this.window.getMaze().getGrid();
        player = this.window.getPlayer();
        canvas = this.window.getCanvas();
    }

    public Point getMousePointer() {
        return mousePointer;
    }

    public void setMousePointer(Point mousePointer) {
        this.mousePointer = mousePointer;
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
        } else {
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
        if(player.isFinish()){
            window.repaint();
            mousePointer.x = player.getPX();
            mousePointer.y = player.getPY();
        }
        window.repaint();
    }

    private int getPos(int x, int y) {
        return y * Cell.CELL_SIZE + x;
    }

    private Direction getDirection(Point mouse, int x, int y) {
        if (mouse.y > y) return Direction.Down;
        if (mouse.y < y) return Direction.Up;
        if (mouse.x > x) return Direction.Right;
        if (mouse.x < x) return Direction.Left;
        return Direction.Up;
    }

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
}