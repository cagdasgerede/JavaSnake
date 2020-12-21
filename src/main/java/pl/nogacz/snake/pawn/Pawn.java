package pl.nogacz.snake.pawn;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public enum Pawn {
    FOOD,
    BRICK,
    SNAKE_HEAD,
    SNAKE_BODY,
    SNAKE_BODY_SKIN_ORANGE,
    SNAKE_BODY_SKIN_GREEN;

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }
}
