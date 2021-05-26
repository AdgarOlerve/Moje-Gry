import java.io.BufferedReader;
import java.io.IOException;

public class ClientHandler extends Thread
{
    private BufferedReader in;
    public ClientHandler(BufferedReader in)
    {
        this.in = in;
    }
    @Override
    public void run()
    {
        String line="";
        while(true)
        {
            try
            {
                line = in.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (line.equals("/q"))
            {
                System.exit(101);
            }
            System.out.println(line);
        }
    }
}
