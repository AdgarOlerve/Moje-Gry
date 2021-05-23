import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameFrame extends JFrame implements Runnable, KeyListener
{
    JFrame frame;
    JPanel gamePanel;
    int gridRows=10;
    int gridCols=gridRows;
    int snakeSpeed = 350;
    int frameWidth=900;
    int frameHeight=900;
    char snakeDirection='w';
    boolean gameOver = false;
    boolean appleEaten = false;
    Color appleColor = new Color(140,0,0);
    Color snakeColor = new Color(0,140,0);
    Color backgroundColor = new Color(42, 50, 65);

    JPanel[][] panelGrid = new JPanel[gridRows][gridCols];//mapa gry
    ArrayList<SnakeBodySegment>Snake = new ArrayList<SnakeBodySegment>(2);//wąż
    SnakeBodySegment apple = new SnakeBodySegment();//jabłko
    public GameFrame()
    {
        frame = new JFrame("Sznejk v2");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth,frameHeight);
        this.setLocationRelativeTo(null);
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.GRAY);
        gamePanel.setPreferredSize(new Dimension(frameWidth,frameHeight));

        for(int i=0;i<gridCols;i++)
        {
            for(int j=0;j<gridRows;j++)
            {
                panelGrid[i][j]=new JPanel();
                gamePanel.add(panelGrid[i][j]);
            }
        }
        gamePanel.setLayout(new GridLayout(gridRows,gridCols,5,5));
        this.add(gamePanel);
        this.pack();
        this.setVisible(true);

        Thread t1 = new Thread(this);
        t1.start();
        gameplay();
    }

    private void gameplay()
    {
        while(true) {
            addKeyListener(this);
            intro();
            snakeDirection='w';
            apple.GenRandPos(gridCols, gridRows);
            Snake.clear();
            Snake.add(new SnakeBodySegment(gridRows/2,gridCols/2));
            Snake.add(new SnakeBodySegment(gridRows/2+1,gridCols/2));
            while (!gameOver)
            {
                mapPainter();
                move();
            }
            gameOver=false;
        }
    }
    public void move()
    {
        SnakeBodySegment test = new SnakeBodySegment(0,0);
        int x = Snake.get(0).getSnakeX();
        int y = Snake.get(0).getSnakeY();
        int nextX=0;
        int nextY=0;
        if(snakeDirection == 'w')
        {
            nextX = x-1;
            nextY = y;
        }
        else if(snakeDirection == 's')
        {
            nextX = x+1;
            nextY = y;
        }
        else if(snakeDirection == 'a')
        {
            nextX = x;
            nextY = y-1;
        }
        else if(snakeDirection == 'd')
        {
            nextX = x;
            nextY = y+1;
        }
        else if(snakeDirection == 'r')
        {
            snakeDirection='w';
            gameOver=true;
            return;
        }

        if(nextX<0) //sprawdzenie czy wąż wychodzi za mapę i przerzucenie go na drugą stronę
        {
            nextX=panelGrid.length-1;
        }
        if(nextY<0)
        {
            nextY=panelGrid.length-1;
        }
        if(nextX>panelGrid.length-1)
        {
            nextX=0;
        }
        if(nextY>panelGrid.length-1)
        {
            nextY=0;
        }
        test.setSnakeX(nextX);
        test.setSnakeY(nextY);
        if(isColiding(test))
        {
            gameOver=true;
        }
        if(test.equals(apple))
        {
            appleEaten=true;
            test.GenRandPos(panelGrid.length,panelGrid.length);
            while(isColiding(test))
            {
                test.GenRandPos(panelGrid.length,panelGrid.length);
            }
            apple.setSnakeX(test.getSnakeX());
            apple.setSnakeY(test.getSnakeY());
        }
        bodyFallow();
        Snake.get(0).setSnakeX(nextX);
        Snake.get(0).setSnakeY(nextY);
    }
    private boolean isColiding(SnakeBodySegment test)
    {
        for(int i=0;i<Snake.size();i++)//sprawdzenie czy podany obiekt jest w tym samym miejscu co któryś z segmentów węża
        {
            if(Snake.get(i).equals(test))
            {
                return true;
            }
        }
        return false;
    }
    private void bodyFallow()
    {
        SnakeBodySegment test = new SnakeBodySegment(Snake.get(Snake.size()-1).getSnakeX(), Snake.get(Snake.size()-1).getSnakeY());
        for(int i=Snake.size()-1;i>0;i--)
        {
           Snake.get(i).setSnakeX(Snake.get(i-1).getSnakeX());
           Snake.get(i).setSnakeY(Snake.get(i-1).getSnakeY());
        }
        if(appleEaten)
        {
            Snake.add(new SnakeBodySegment(test.getSnakeX(), test.getSnakeY()));
            appleEaten=false;
        }
    }

    private void mapPainter()
    {
        colorWholeMap(backgroundColor,0);

        int x = apple.getSnakeX();
        int y = apple.getSnakeY();
        panelGrid[x][y].setBackground(appleColor);
        panelGrid[x][y].revalidate();

        for(int i = 0;i<Snake.size();i++)
        {
                x = Snake.get(i).getSnakeX();
                y = Snake.get(i).getSnakeY();
                panelGrid[x][y].setBackground(snakeColor);
                panelGrid[x][y].revalidate();
        }
        wait(snakeSpeed);
    }
    public void intro()
    {
        System.out.println("intro");
        Random rand = new Random();
        int r=0,g=0,b=0;
        for(int i=0;i<gridCols;i++)
        {
            for(int j=0;j<gridRows;j++)
            {
                r=rand.nextInt(255);
                g=rand.nextInt(255);
                b=rand.nextInt(255);
                panelGrid[i][j].setBackground(new Color(r,g,b));
                panelGrid[i][j].revalidate();
                wait(10);
            }
        }
        colorWholeMap(backgroundColor,10);
    }
    public void colorWholeMap(Color color,int delay)
    {
        for(int i=0;i<gridCols;i++)
        {
            for(int j=0;j<gridRows;j++)
            {
                panelGrid[i][j].setBackground(color);
                panelGrid[i][j].revalidate();
                wait(delay);
            }
        }
    }
    private void wait(int time)
    {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run()
    {
        int h=5,w=5;
        while(true) {
            h = this.getHeight();
            w = this.getWidth();
            gamePanel.setPreferredSize(new Dimension(w-20, h-45));
            wait(200);
            gamePanel.revalidate();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
            System.out.println("keyTyped");
            char x = e.getKeyChar();
            if (x == 'w' || x == 's' || x == 'a' || x == 'd' || x == 'r')
            {
                snakeDirection = x;
            }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==16)
            snakeSpeed = 75;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        snakeSpeed = 350;
    }
}
