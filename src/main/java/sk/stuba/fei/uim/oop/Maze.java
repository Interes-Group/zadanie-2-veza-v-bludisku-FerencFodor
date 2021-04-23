package sk.stuba.fei.uim.oop;

import java.util.*;

public class Maze {

    private final int size;

    private final Random random;
    private final List<Cell> grid;

    private final int[] finishPos;
    private final int[] playerPos;

    public Maze() {
        this.size = Cell.CELL_COUNT;
        this.random = new Random();
        this.grid = new ArrayList<>();
        this.finishPos = new int[]{Cell.CELL_COUNT - 1, Cell.CELL_COUNT - 1};
        this.playerPos = new int[]{0, 0};

        initGrid();
        generateRandomFinishPosition();
        generateRandomPlayerStartPosition();
    }

    public void generateRandomFinishPosition() {
        finishPos[1] = random.nextInt(Cell.CELL_COUNT);
    }

    public void generateRandomPlayerStartPosition() {
        playerPos[1] = random.nextInt(Cell.CELL_COUNT);
    }

    //Maze Generation
    public void initGrid() {
        for (var i = 0; i < size; i++)
            for (var j = 0; j < size; j++)
                grid.add(new Cell(j, i));
    }

    public void reset() {
        grid.forEach(cell -> {
            cell.setWalls(Arrays.asList(true, true, true, true));
            cell.setVisited(false);
        });

        generateRandomFinishPosition();
        generateRandomPlayerStartPosition();
    }

    public void generateMaze() {
        Stack<Cell> cellStack = new Stack<>();

        var x = random.nextInt(size);
        var y = random.nextInt(size);

        var current = grid.get(y * size + x);
        current.setVisited(true);

        do {
            var next = getRandomNeighbourCell(current.getX(), current.getY());
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

    private Cell getRandomNeighbourCell(int x, int y) {
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

    //Getters
    public List<Cell> getGrid() {
        return grid;
    }

    public int[] getFinishPos() {
        return finishPos;
    }

    public int[] getPlayerPos() {
        return playerPos;
    }

}
