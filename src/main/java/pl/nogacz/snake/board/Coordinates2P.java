package pl.nogacz.snake.board;

import java.util.Objects;

public class Coordinates2P extends Coordinates {

    public Coordinates2P(int x, int y) {
        super(x, y);
    }


    public boolean isValid() {
        return getX() <= 44 && getX() >= 0 && getY() <= 44 && getY() >= 0;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Coordinates that = (Coordinates) o;
//        return x == that.x &&
//                y == that.y;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(x, y);
//    }
}
