package sk.stuba.fei.uim.oop;

import java.util.*;

public class Board {

    private int width;
    private int height;
    private boolean isMazeGenerated;

    private Random random;

    private List<Cell> grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.isMazeGenerated = false;
        this.random = new Random();

        populateGrid();
    }

    private void populateGrid(){
        for (var i = 0; i < height; i++)
            for (var j = 0; j < width; j++)
                grid.add(new Cell(j, i));
    }

    public boolean generateMaze() {
        Stack<Cell> cellStack = new Stack<>();

        var x = random.nextInt(width);
        var y = random.nextInt(height);

        var current = grid.get(y*width + x);
        current.setVisited(true);

         do{
             var next = getRandomUnvisitedCell(current.getX(), current.getY());
             if(next != null){
                 next.setVisited(true);
                 removeWall(current,next);
                 cellStack.push(current);
                 current = next;
             } else {
                 current = cellStack.pop();
             }
         }while (!cellStack.empty());

         return true;
    }

    private Cell getRandomUnvisitedCell(int x, int y) {
        var neighbours = new ArrayList<Cell>();

        neighbours.add(grid.get(getIndex(x, y - 1)));
        neighbours.add(grid.get(getIndex(x + 1, y )));
        neighbours.add(grid.get(getIndex(x, y + 1)));
        neighbours.add(grid.get(getIndex(x - 1, y)));

        neighbours.removeIf(side -> side == null || side.isVisited());

        if(neighbours.size() > 0){
            return neighbours.get(random.nextInt(neighbours.size()));
        } else {
            return null;
        }

    }

    private int getIndex(int x, int y) {
        if (x < 0 || x > width ||
            y < 0 || y > height)
            return -1;

        return y*width + x;
    }

    private void removeWall(Cell current, Cell next) {
        var x = current.getX() - next.getX();
        var y = current.getY() - next.getY();

        if(x == 1){
            current.setWall(Direction.Left, false);
            next.setWall(Direction.Right, false);
        } else if(x == -1) {
            current.setWall(Direction.Right, false);
            current.setWall(Direction.Left, false);
        }

        if(y == 1) {
            current.setWall(Direction.Up, false);
            next.setWall(Direction.Down, false);
        } else if(y == -1){
            current.setWall(Direction.Down, false);
            next.setWall(Direction.Up, false);
        }
    }

}
