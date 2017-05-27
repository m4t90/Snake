package mat;

/**
 * Created by Mateusz on 21.05.2017.
 */

enum Direction {
    UP {
        @Override
        Direction opposite() {
            return DOWN;
        }
    },
    DOWN {
        @Override
        Direction opposite() {
            return UP;
        }
    },
    LEFT {
        @Override
        Direction opposite() {
            return RIGHT;
        }
    },
    RIGHT {
        @Override
        Direction opposite() {
            return LEFT;
        }
    };


    abstract Direction opposite();

}