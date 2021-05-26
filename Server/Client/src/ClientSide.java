import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientSide
{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;

    ////
    public static void main(String[] args) throws IOException
    {
        ClientSide t = new ClientSide();
    }

    ////
    public ClientSide() throws IOException
    {
       /*
        System.out.println("Wpisz IP serwera: ");
        Scanner sip = new Scanner(System.in);
        ip=sip.nextLine();
        */
        ip="192.168.43.85";
        startConnection(ip,6666);
        sendMessage();
    }

    ////
    public void startConnection(String ip, int port) throws IOException
    {
        try {
            clientSocket = new Socket(ip, port);
        } catch (ConnectException e) {
            System.out.println("Cannot connect to the server");
            System.exit(666);
        }
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println(in.readLine());//czyta wiadomosc powitalną z serwera
    }

    ////
    public void sendMessage() throws IOException
    {
        String input = "0";
        Scanner keyboard = new Scanner(System.in);
        new ClientHandler(in).start();
        while (!(input.equals("/q")))
        {
            input = keyboard.nextLine();
            out.println(input);
        }
    }

    ////
    public void stopConnection() throws IOException
    {
        in.close();
        out.close();
        clientSocket.close();
    }


}