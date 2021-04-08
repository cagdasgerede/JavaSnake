package pl.nogacz.snake.board;

import java.util.Objects;

/**
 * @author Dawid Nogacz on 19.05.2019
 */
public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isValid() {
        return x <= 21 && x >= 0 && y <= 21 && y >= 0;
    }

    public boolean isValidForSecond() {
        return x <= 43 && x >= 22 && y <= 21 && y >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
