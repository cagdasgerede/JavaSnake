package pl.nogacz.snake.pawn;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public enum Pawn {
    FOOD,
    BRICK,
    SNAKE_HEAD,
    SNAKE_BODY,
    ROTTEN_APPLE_BLACK,
    ROTTEN_APPLE_GREY,
    ROTTEN_APPLE_ORANGE;

    public boolean isFood() {
        return this == FOOD;
    }

    public boolean isRottenAppleBlack(){
        return this == ROTTEN_APPLE_BLACK;
    }

    public boolean isRottenAppleGrey(){
        return this == ROTTEN_APPLE_GREY;
    }

    public boolean isRottenAppleOrange(){
        return this == ROTTEN_APPLE_ORANGE;
    }

    public boolean isHead() {
        return this == SNAKE_HEAD;
    }
}
