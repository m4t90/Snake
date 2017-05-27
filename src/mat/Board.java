package mat;


import static java.lang.StrictMath.abs;

/**
 * Created by Mateusz on 21.05.2017.
 */
public class Board {
    private final int BOX_SIZE = 500;
    SnakeState snakestate;

    public Board(SnakeState snakestate) {
        this.snakestate = snakestate;
    }


    public boolean isSnakeDead() {
        int x = snakestate.getBody().getFirst().getX();
        int y = snakestate.getBody().getFirst().getY();

        if (abs(x) > (BOX_SIZE / 2) || abs(y) > (BOX_SIZE / 2)) {
            return true;
        } else
            return false;

    }

    public void update() {
        if (isSnakeDead() == false) {
            Board currentBoard = new Board(snakestate);
        } else {
            snakestate.getBody().remove();
        }
    }
}