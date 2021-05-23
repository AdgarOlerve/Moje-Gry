public class SnakeBody
{
    private int posx=0;
    private int posy=0;
    public SnakeBody(int x,int y)
    {
        this.posx=x;
        this.posy=y;
    }
    public int getX()
    {
        return posx;
    }
    public int getY()
    {
        return posy;
    }
    public void setX(int x)
    {
        this.posx=x;
    }
    public void setY(int y)
    {
        this.posy=y;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o==this)
        {
            return true;

        }
        if(o==null || o.getClass() != this.getClass())
        {
            return false;

        }
        SnakeBody checked=(SnakeBody) o;
        if(posx==checked.posx && posy==checked.posy)
        {
            return true;
        }
        return false;
    }
}