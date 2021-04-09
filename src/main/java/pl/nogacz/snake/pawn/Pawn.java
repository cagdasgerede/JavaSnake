package pl.nogacz.snake.pawn;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public enum Pawn {
    FOOD,
    BRICK,
    SNAKE_HEAD,
    SECOND_SNAKE_HEAD,
    SNAKE_BODY;

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }

    public boolean isHead2() {
        return this == SECOND_SNAKE_HEAD;
    }
}
