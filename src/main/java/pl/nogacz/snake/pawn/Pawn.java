package pl.nogacz.snake.pawn;

public enum Pawn {
    FOOD,
    BRICK,
    SNAKE_HEAD,
    SNAKE_BODY,
    FOOD2,
    SNAKE_HEAD2,
    SNAKE_BODY2;

    public boolean isFood2() {
        return this == FOOD2;
    }

    public boolean isHead2() {
        return this == SNAKE_HEAD2;
    }

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }
}
