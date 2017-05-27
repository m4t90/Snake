package mat;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Mateusz on 21.05.2017.
 */
public class Snake {
    private Direction headDirection;
    private LinkedList<Point> body;


    public Snake(List<Point> startState, Direction startDirection) {

        if (startState.isEmpty())
            throw new SnakeCreationException("lista pusta");

        for (int i = (startState.size() - 1); i > 0; i--) {

            int stepY, stepX;
            stepY = startState.get(i).getY() - startState.get(i - 1).getY();
            stepX = startState.get(i).getX() - startState.get(i - 1).getX();

            if (abs(stepX) > 1 || abs(stepY) > 1)
                throw new SnakeCreationException("waz rozdzielony");

            if (abs(stepX) == 1 && abs(stepY) == 1)
                throw new SnakeCreationException("cialo po ukosie ");

            if (stepX == 1 && startDirection == Direction.RIGHT || stepX == -1
                    && startDirection == Direction.LEFT || stepY == 1
                    && startDirection == Direction.UP || stepY == -1
                    && startDirection == Direction.DOWN)
                throw new SnakeCreationException("zly kierunek ");

            for (int j = 0; j < (startState.size()); j++) {
                if ((startState.get(i).getX() == startState.get(j).getX()) && (startState.get(i).getY() == startState
                        .get(j).getY()) && i != j) {
                    throw new SnakeCreationException("powtarzający sie punkt");
                }
            }
        }
        headDirection = startDirection;
        body = new LinkedList<>(startState);
    }

    public SnakeState getState() {
        SnakeState snakestate = new SnakeState(body, headDirection);
        return snakestate;
    }


    public void changeDirection(Direction newHeadDirection) {
        if (newHeadDirection != headDirection.opposite()) {
            headDirection = newHeadDirection;
        }
    }


    public void update() {
        Point head = new Point(body.getFirst().getX(), body.getFirst().getY());
        int dx = 0, dy = 0;

        if (headDirection == Direction.DOWN) {
            dy = -1;
        }
        if (headDirection == Direction.UP) {
            dy = 1;
        }
        if (headDirection == Direction.LEFT) {
            dx = -1;
        }
        if (headDirection == Direction.RIGHT) {
            dx = 1;
        }
        body.addFirst(new Point(head.getX() + dx, head.getY() + dy));
        body.removeLast();


    }
}