import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerSide
{
    static private final int PORT = 6666;
    static private ArrayList<Client> clients = new ArrayList<>(1);
    static ServerSocket serverSocket = null;
    static Socket clientSocket = null;

    ////
    public static void main(String[] args) throws IOException
    {
        ServerSide server = new ServerSide();
    }

    ////
    public ServerSide() throws IOException
    {
        getMyIp();//podaje IP komputera na którym uruchomiony jest serwer
        try
        {
            serverSocket = new ServerSocket(PORT);//ustawia port serwera
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Server is on...");
        while (true)
        {
            try
            {
                clientSocket = serverSocket.accept();//akceptuje nowego clienta
            }
            catch (IOException e)
            {
                System.out.println("I/O error: " + e);
            }
            clients.add(new Client(clientSocket));
            clients.get(clients.size()-1).setClientNumber(clients.size()-1);
            new ServerHandler(clients,clients.size()-1).start();
        }
    }

    ////
    public static void getMyIp()
    {
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
