package mat;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Mateusz on 23.05.2017.
 */
public class BoardTest {

    @DataProvider
    public Object[][] snakeIsDeadDataProvider() {
        return new Object[][]{
                {Arrays.asList(new Point(-251, 1)), Direction.DOWN},
                {Arrays.asList(new Point(2, 251)), Direction.DOWN},
        };
    }

    @DataProvider
    public Object[][] snakeIsAliveDataProvider() {
        return new Object[][]{
                {Arrays.asList(new Point(-125, 1)), Direction.DOWN},
                {Arrays.asList(new Point(23, -240)), Direction.DOWN},
        };
    }

    @DataProvider
    public Object[][] updateDataProvider() {
        return new Object[][]{

                {Arrays.asList(new Point(1, 1)), Direction.DOWN},

        };
    }

    @DataProvider
    public Object[][] invalidUpdateDataProvider() {
        return new Object[][]{

                {Arrays.asList(new Point(251, 1)), Direction.DOWN}
        };
    }


    @Test(dataProvider = "snakeIsDeadDataProvider")
    public void snakeIsDead(List<Point> initialBody, Direction initialHeadDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        testedSnake.getState();
        Board testBoard = new Board(testedSnake.getState());
        testBoard.isSnakeDead();
        Assert.assertEquals(testBoard.isSnakeDead(), true);
    }

    @Test(dataProvider = "snakeIsAliveDataProvider")
    public void snakeIsAlive(List<Point> initialBody, Direction initialHeadDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        testedSnake.getState();
        Board testBoard = new Board(testedSnake.getState());
        testBoard.isSnakeDead();
        Assert.assertEquals(testBoard.isSnakeDead(), false);
    }

    @Test(dataProvider = "updateDataProvider")
    public void update(List<Point> initialBody, Direction initialHeadDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        testedSnake.getState();
        Board testBoard = new Board(testedSnake.getState());
        testBoard.isSnakeDead();
        testBoard.update();
        Assert.assertEquals(testBoard.snakestate.getBody(), testedSnake.getState().getBody());
    }

    @Test(dataProvider = "invalidUpdateDataProvider")
    public void updateSnakeDead(List<Point> initialBody, Direction initialHeadDirection) {
        Snake testedSnake = new Snake(initialBody, initialHeadDirection);
        testedSnake.getState();
        Board testBoard = new Board(testedSnake.getState());
        testBoard.isSnakeDead();
        testBoard.update();
        Assert.assertEquals(testBoard.snakestate.getBody().size(), 0);
    }

}