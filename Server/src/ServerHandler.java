import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

////
public class ServerHandler extends Thread
{
    static private ArrayList<Client> clients = new ArrayList<>(1);
    private int clientNumber=-1;
    ////
    public ServerHandler(ArrayList<Client> clients,int clientNumber)
    {
        this.clients=clients;
        this.clientNumber=clientNumber;
    }

    ////
    @Override
    public void run()
    {
        PrintWriter out;
        BufferedReader in;
        try {
            out = new PrintWriter(clients.get(clientNumber).getClientSocket().getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clients.get(clientNumber).getClientSocket().getInputStream()));
        } catch (IOException e) {
            return;
        }
        try {
            login(in,out);
            System.out.println("New client conected...");
            System.out.println("NAME: "+ clients.get(clientNumber).getName()+"; NUMBER: "+clientNumber+"; SOCKET: "+clients.get(clientNumber).getClientSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line="";
        while(!line.equals("/q"))
        {
            line="";
            try
            {
                line=in.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if (!(line.equals("/q")))
            {
                writer(line,clientNumber,clients);
            }
        }
        try {
            disconnect(in,out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void writer(String line,int clientNumber,ArrayList<Client> clients)
    {
        PrintWriter out=null;
        for (int i = 0;i<clients.size();i++)
        {
            if (!(clients.get(i).equals(clients.get(clientNumber))))
            {
                System.out.println(clients.get(clientNumber).getName() + ": "+line);

                try
                {
                    out=new PrintWriter(clients.get(i).getClientSocket().getOutputStream(),true);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                out.println(clients.get(clientNumber).getName() + ": "+line);
            }
        }
    }
    private void disconnect(BufferedReader in,PrintWriter out) throws IOException
    {
        out.println("/q");
        in.close();
        out.close();
        System.out.println(clients);
        for (int i = 0;i<clients.size();i++)
        {
            if(clients.get(i).getName().equals(clients.get(clientNumber).getName()))
            {
                writer("//was disconected from the server//",clientNumber,clients);
                clients.remove(i);
                System.out.println(clients);
            }
        }
    }
    ////
    public void login(BufferedReader in,PrintWriter out) throws IOException
    {
        out.println("Your nick: ");
        String name = in.readLine();
        clients.get(clientNumber).setName(name);
        out.println("Nick saved...");
    }
}
