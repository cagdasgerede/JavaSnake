package pl.nogacz.snake.board;

public class Coordinates2P extends Coordinates {

    public Coordinates2P(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean isValid() {
        return getX() <= 44 && getX() >= 0 && getY() <= 22 && getY() >= 0;
    }
}
