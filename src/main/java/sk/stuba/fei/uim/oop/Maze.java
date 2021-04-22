package sk.stuba.fei.uim.oop;

import java.util.*;

public class Maze {

    private int size;

    private Random random;
    private List<Cell> grid;

    private int[] finishPos;
    private int[] playerPos;

    public Maze() {
        this.size = Cell.CELL_SIZE;
        this.random = new Random();
        this.grid = new ArrayList<>();
        this.finishPos = new int[]{Cell.CELL_SIZE - 1, Cell.CELL_SIZE - 1};
        this.playerPos = new int[]{0, 0};

        initGrid();
        setFinish();
        setPlayer();
    }

    public void initGrid() {
        for (var i = 0; i < size; i++)
            for (var j = 0; j < size; j++)
                grid.add(new Cell(j, i));
    }

    public void setFinish() {
        finishPos[1] = random.nextInt(Cell.CELL_SIZE);
    }

    public void setPlayer() {

        playerPos[1] = random.nextInt(Cell.CELL_SIZE);
    }

    public void reset() {
        grid.forEach(cell -> {
            cell.setWalls(Arrays.asList(true, true, true, true));
            cell.setVisited(false);
        });

        setFinish();
        setPlayer();
    }

    public void generateMaze() {
        Stack<Cell> cellStack = new Stack<>();

        var x = random.nextInt(size);
        var y = random.nextInt(size);

        var current = grid.get(y * size + x);
        current.setVisited(true);

        do {
            var next = getRandomUnvisitedCell(current.getX(), current.getY());
            if (next != null) {
                next.setVisited(true);
                removeWall(current, next);
                cellStack.push(current);
                current = next;
            } else {
                current = cellStack.pop();
            }
        } while (!cellStack.empty());
    }

    private Cell getRandomUnvisitedCell(int x, int y) {
        var neighbours = new ArrayList<Cell>();

        if (getIndex(x, y - 1) != -1) {
            neighbours.add(grid.get(getIndex(x, y - 1)));
        }
        if (getIndex(x + 1, y) != -1) {
            neighbours.add(grid.get(getIndex(x + 1, y)));
        }
        if (getIndex(x, y + 1) != -1) {
            neighbours.add(grid.get(getIndex(x, y + 1)));
        }
        if (getIndex(x - 1, y) != -1) {
            neighbours.add(grid.get(getIndex(x - 1, y)));
        }

        neighbours.removeIf(Cell::isVisited);

        if (neighbours.size() > 0) {
            return neighbours.get(random.nextInt(neighbours.size()));
        }
        return null;
    }

    private int getIndex(int x, int y) {
        if (x < 0 || x > size - 1 ||
                y < 0 || y > size - 1)
            return -1;

        return y * size + x;
    }

    private void removeWall(Cell current, Cell next) {
        var x = current.getX() - next.getX();
        var y = current.getY() - next.getY();

        if (x == 1) {
            current.setWall(Direction.Left, false);
            next.setWall(Direction.Right, false);
        } else if (x == -1) {
            current.setWall(Direction.Right, false);
            next.setWall(Direction.Left, false);
        }

        if (y == 1) {
            current.setWall(Direction.Up, false);
            next.setWall(Direction.Down, false);
        } else if (y == -1) {
            current.setWall(Direction.Down, false);
            next.setWall(Direction.Up, false);
        }
    }

    public List<Cell> getGrid() {
        return grid;
    }

    public void setGrid(List<Cell> grid) {
        this.grid = grid;
    }

    public int[] getFinishPos() {
        return finishPos;
    }

    public void setFinishPos(int[] finishPos) {
        this.finishPos = finishPos;
    }

    public int[] getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(int[] playerPos) {
        this.playerPos = playerPos;
    }
}
