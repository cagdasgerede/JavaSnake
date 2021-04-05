package pl.nogacz.snake.pawn;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public enum Pawn {
    FOOD,
    HARMFUL_ITEM,
    BRICK,
    SNAKE_HEAD,
    SNAKE_BODY;

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isHarmfulItem() {
        return this == HARMFUL_ITEM;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }
}
