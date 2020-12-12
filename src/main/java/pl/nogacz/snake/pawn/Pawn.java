package pl.nogacz.snake.pawn;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public enum Pawn {
    FOOD,
    BRICK,
    ITEM,
    SNAKE_HEAD,
    SNAKE_BODY;

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isItem() {
        return this == ITEM;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }

    public boolean isBrick() {
        return this == BRICK;
    }
    
}
