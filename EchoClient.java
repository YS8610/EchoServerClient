import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient 
{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public EchoClient(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeMsg(BufferedWriter bufferedWriter, String msg)
    {
        try 
        {
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeEverything(Socket socket)
    {
        try
        {
            if (this.bufferedReader!=null) this.bufferedReader.close();
            if (this.bufferedWriter!=null) this.bufferedWriter.close();
            if (socket!=null) socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        try 
        {
            Socket socket = new Socket("127.0.0.1",3000); //Connect to server 
            System.out.println("Server connected..");
            EchoClient echoClient = new EchoClient(socket);
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                String msg = scanner.nextLine();
                echoClient.writeMsg(echoClient.bufferedWriter, msg);

                System.out.println("Server: " + echoClient.bufferedReader.readLine());

                if (msg.equalsIgnoreCase("end"))
                {
                    scanner.close();
                    break;
                }
            }
            echoClient.closeEverything(socket);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
