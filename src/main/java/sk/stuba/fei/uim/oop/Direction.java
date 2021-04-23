package sk.stuba.fei.uim.oop;

public enum Direction {
    Up(0),
    Right(1),
    Down(2),
    Left(3);

    public final int value;

    Direction(int i) {
        this.value = i;
    }
}
