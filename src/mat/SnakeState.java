package mat;

import java.util.LinkedList;

/**
 * Created by Mateusz on 23.05.2017.
 */
public class SnakeState {
    private final LinkedList<Point> body;
    private final Direction headDirection;

    public LinkedList<Point> getBody() {
        return body;
    }

    public Direction getHeadDirection() {
        return headDirection;
    }

    public SnakeState(LinkedList<Point> body, Direction headDirection) {
        this.body = new LinkedList<>(body);
        this.headDirection = headDirection;
    }
}