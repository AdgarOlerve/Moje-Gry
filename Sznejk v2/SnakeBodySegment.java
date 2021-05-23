
import java.awt.*;
import java.util.Random;

public class SnakeBodySegment
{
    private int snakeX = 0;
    private int snakeY = 0;
    private Random rand = new Random();

    public SnakeBodySegment(int x, int y)
    {
        this.snakeX = x;
        this.snakeY = y;
    }

    public SnakeBodySegment()
    {
        this.snakeX = 0;
        this.snakeY = 0;
    }

    public void GenRandPos(int maxX,int maxY)
    {
        this.snakeX=rand.nextInt(maxX);
        this.snakeY= rand.nextInt(maxY);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnakeBodySegment that = (SnakeBodySegment) o;
        return snakeX == that.snakeX && snakeY == that.snakeY;
    }

    public int getSnakeX()
    {
        return snakeX;
    }

    public void setSnakeX(int snakeX)
    {
        this.snakeX = snakeX;
    }

    public int getSnakeY()
    {
        return snakeY;
    }

    public void setSnakeY(int snakeY)
    {
        this.snakeY = snakeY;
    }
}
