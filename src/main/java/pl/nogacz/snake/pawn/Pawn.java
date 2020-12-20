package pl.nogacz.snake.pawn;

public enum Pawn {
    FOOD,
    BRICK,
    SNAKE_HEAD,
    SNAKE_BODY,
    FOOD_SECOND_PLAYER,
    SNAKE_HEAD_SECOND_PLAYER,
    SNAKE_BODY_SECOND_PLAYER;

    public boolean isFoodOfSecondPlayer() {
        return this == FOOD_SECOND_PLAYER;
    }

    public boolean isHeadOfSecondPlayer() {
        return this == SNAKE_HEAD_SECOND_PLAYER;
    }

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }
}
