package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomMouseListener implements MouseMotionListener, MouseListener {

    public boolean isFocused;
    private Point mousePointer;

    private final int step;
    private final List<Cell> grid;
    private final Player player;
    private final MazePanel canvas;
    private final Window window;
    private final List<Point> walkable;

    public CustomMouseListener(Window window) {
        this.mousePointer = new Point();
        this.step = Window.CANVAS_WIDTH / Cell.CELL_COUNT;
        this.window = window;
        this.isFocused = false;
        this.walkable = new ArrayList<>();

        grid = this.window.getMaze().getGrid();
        player = this.window.getPlayer();
        canvas = this.window.getCanvas();
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
        if (mousePointer.x == player.getPX() && mousePointer.y == player.getPY()) {
            isFocused = !isFocused;
            walkable.clear();
        }
        if (isFocused) {
            player.setX(mousePointer.x);
            player.setY(mousePointer.y);
            player.checkFinish();
            if (player.isFinish()) {
                window.repaint();
                mousePointer.x = player.getPX();
                mousePointer.y = player.getPY();
            }
            update();
        }
        window.repaint();
    }

    //Support Methods
    private int getPos(int x, int y) {
        return y * Cell.CELL_COUNT + x;
    }

    private Direction getDirection(Point mouse, int x, int y) {
        if (mouse.y > y) return Direction.Down;
        if (mouse.y < y) return Direction.Up;
        if (mouse.x > x) return Direction.Right;
        if (mouse.x < x) return Direction.Left;
        return Direction.Up;
    }

    private boolean isPathBlocked(int x, int y, Direction direction) {
        if (player.getPX() != x || player.getPY() != y)
            walkable.add(new Point(x, y));

        return grid.get(getPos(x, y)).getWall(direction);
    }

    private void update() {
        var px = player.getPX();
        var py = player.getPY();

        walkable.clear();

        for (var left = px; left >= 0; left--)
            if (isPathBlocked(left, py, Direction.Left))
                break;

        for (var right = px; right < Cell.CELL_COUNT; right++)
            if (isPathBlocked(right, py, Direction.Right))
                break;

        for (var up = py; up >= 0; up--)
            if (isPathBlocked(px, up, Direction.Up))
                break;

        for (var down = py; down < Cell.CELL_COUNT; down++)
            if (isPathBlocked(px, down, Direction.Down))
                break;

    }

    //Getters & Setters
    public Point getMousePointer() {
        return mousePointer;
    }

    public void setMousePointer(Point mousePointer) {
        this.mousePointer = mousePointer;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public List<Point> getWalkable() {
        return walkable;
    }

    //Unused
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