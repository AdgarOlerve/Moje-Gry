import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    private Socket clientSocket = null;
    private String name="";
    private int clientNumber=-1;

    public Client(Socket clientSocket) throws IOException
    {
        this.clientSocket=clientSocket;


    }

    public int getClientNumber()
    {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber)
    {
        this.clientNumber = clientNumber;
    }
    public Socket getClientSocket()
    {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
