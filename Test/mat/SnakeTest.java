package mat;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mateusz on 21.05.2017.
 */
public class SnakeTest {

    @DataProvider
    public Object[][] validDataProvider() {
        return new Object[][]{
                {Arrays.asList(new Point(1, 1), new Point(1, 2), new Point(1, 3)), Direction.DOWN},
                {Arrays.asList(new Point(1, 1), new Point(2, 1), new Point(3, 1)), Direction.LEFT},
                {Arrays.asList(new Point(3, 1), new Point(2, 1), new Point(1, 1)), Direction.RIGHT},
                {Arrays.asList(new Point(1, 3), new Point(1, 2), new Point(1, 1)), Direction.UP},
        };
    }

    @DataProvider
    public Object[][] invalidDataProvider() {
        return new Object[][]{
                {Arrays.asList(), Direction.DOWN}, //pusta lista
                {Arrays.asList(new Point(1, 3), new Point(1, 6), new Point(1, 7)), Direction.DOWN}, //Rozdzielony waz
                {Arrays.asList(new Point(1, 1), new Point(6, 1), new Point(7, 1)), Direction.LEFT}, //Rozdzielony waz
                {Arrays.asList(new Point(1, 1), new Point(2, 1), new Point(3, 1)), Direction.RIGHT}, //zly kierunek
                {Arrays.asList(new Point(1, 1), new Point(1, 1), new Point(1, 1)), Direction.LEFT}, //powtarzajace sie punkty
                {Arrays.asList(new Point(1, 2), new Point(1, 2), new Point(1, 7)), Direction.LEFT}, //powtarzajace
                // sie punkty
                {Arrays.asList(new Point(1, 1), new Point(2, 2), new Point(3, 3)), Direction.LEFT}, //"ukosne" cialo
        };
    }

    @DataProvider // Dane używane do testowania metody changeDirection
    public Object[][] directionDataProvider() {
        return new Object[][]{

                {Arrays.asList(new Point(1, 1)), Direction.DOWN, Direction.DOWN, Direction.DOWN},
                {Arrays.asList(new Point(1, 1)), Direction.LEFT, Direction.LEFT, Direction.LEFT},
                {Arrays.asList(new Point(1, 1)), Direction.RIGHT, Direction.RIGHT, Direction.RIGHT},
                {Arrays.asList(new Point(1, 1)), Direction.UP, Direction.UP, Direction.UP},

                {Arrays.asList(new Point(1, 1)), Direction.DOWN, Direction.UP, Direction.DOWN},
                {Arrays.asList(new Point(1, 1)), Direction.LEFT, Direction.RIGHT, Direction.LEFT},
                {Arrays.asList(new Point(1, 1)), Direction.RIGHT, Direction.LEFT, Direction.RIGHT},
                {Arrays.asList(new Point(1, 1)), Direction.UP, Direction.DOWN, Direction.UP},

                {Arrays.asList(new Point(1, 1)), Direction.DOWN, Direction.LEFT, Direction.LEFT},
                {Arrays.asList(new Point(1, 1)), Direction.LEFT, Direction.UP, Direction.UP},
                {Arrays.asList(new Point(1, 1)), Direction.RIGHT, Direction.DOWN, Direction.DOWN},
                {Arrays.asList(new Point(1, 1)), Direction.UP, Direction.RIGHT, Direction.RIGHT},

                {Arrays.asList(new Point(1, 1)), Direction.DOWN, Direction.RIGHT, Direction.RIGHT},
                {Arrays.asList(new Point(1, 1)), Direction.LEFT, Direction.DOWN, Direction.DOWN},
                {Arrays.asList(new Point(1, 1)), Direction.RIGHT, Direction.UP, Direction.UP},
                {Arrays.asList(new Point(1, 1)), Direction.UP, Direction.LEFT, Direction.LEFT},
        };
    }

    @DataProvider
    public Object[][] updateDataProvider() {
        return new Object[][]{

                {Arrays.asList(new Point(1, 1)), Direction.DOWN},
        };
    }


    @Test(dataProvider = "validDataProvider")
    public void testSucceedCreation(List<Point> initialBody, Direction initialHeadDirection) {
        new Snake(initialBody, initialHeadDirection);
    }

    @Test(dataProvider = "invalidDataProvider", expectedExceptions = SnakeCreationException.class)
    public void testFailedCreation(List<Point> initialBody, Direction initialHeadDirection) {
        new Snake(initialBody, initialHeadDirection);
    }


    @Test(dataProvider = "validDataProvider")
    public void testGetState(List<Point> initialBody, Direction initialHeadDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        SnakeState testedSnakeState = testedSnake.getState();
        Assert.assertNotNull(testedSnakeState);
        Assert.assertEquals(initialHeadDirection, testedSnakeState.getHeadDirection());
        Assert.assertEquals(initialBody, testedSnakeState.getBody());
    }

    @Test(dataProvider = "directionDataProvider")
    public void testChangeDirection(List<Point> initialBody, Direction initialHeadDirection, Direction newHeadDirection, Direction resultDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        testedSnake.changeDirection(newHeadDirection);
        Direction headDirection = testedSnake.getState().getHeadDirection();
        Assert.assertEquals(headDirection, resultDirection);
    }

    @Test(dataProvider = "validDataProvider")
    public void testStateIsImmutable(List<Point> initialBody, Direction initialHeadDirection) {
        Snake snake = new Snake(initialBody, initialHeadDirection);
        SnakeState state = snake.getState();
        List<Point> copyOfBody = new LinkedList<>(state.getBody());
        state.getBody().add(new Point(0, 0)); // zmiana listy ze stanu
        Assert.assertEquals(snake.getState().getBody(), copyOfBody); //zmiana listy state nie zmienia nam samego stanu węża.
    }

    @Test(dataProvider = "updateDataProvider")
    public void testUpdate(List<Point> initialBody, Direction initialHeadDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        testedSnake.changeDirection(Direction.DOWN);
        testedSnake.update();
        testedSnake.changeDirection(Direction.LEFT);
        testedSnake.update();
        testedSnake.changeDirection(Direction.DOWN);
        testedSnake.update();
        testedSnake.changeDirection(Direction.RIGHT);
        testedSnake.update();
        testedSnake.changeDirection(Direction.LEFT);
        testedSnake.update();
        SnakeState state = testedSnake.getState();

        //Tworzenie oczekiwanej listy punktów ciała
        LinkedList<Point> expectedBody = new LinkedList<>();
        expectedBody.addFirst(new Point(1, 1)); //Początek
        expectedBody.addFirst(new Point(1, 0)); //W dol
        expectedBody.addFirst(new Point(0, 0)); //W lewo
        expectedBody.addFirst(new Point(0, -1)); //W dol
        expectedBody.addFirst(new Point(1, -1)); //W prawo
        expectedBody.addFirst(new Point(2, -1)); //W lewo, nie zmienia się kierunek, przesuw nadal w prawo
// Końcowy kierunek głowy wężą - RIGHT
        for (int i = 0; i < state.getBody().size() - 1; i++) {
            Assert.assertEquals(state.getBody().get(i).getX(), expectedBody.get(i).getX());
            Assert.assertEquals(state.getBody().get(i).getY(), expectedBody.get(i).getY());
        }
        Assert.assertEquals(state.getBody().size(), 1); // sprawdzenie czy listy są jednakowej długości.
        Assert.assertEquals(state.getHeadDirection(), Direction.RIGHT);   // Czy głowa osatecznie jest skierowana w tą samą stronę.

    }
}