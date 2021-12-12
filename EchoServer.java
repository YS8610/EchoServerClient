import java.io.*;
import java.net.*;

public class EchoServer
{
    Socket socket;
    ServerSocket serverSocket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public EchoServer(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void startServer() throws IOException
    {
        System.out.println("Server started. Waiting for connection...");
        while(!serverSocket.isClosed())
        {
            try
            {
                socket = serverSocket.accept();
                System.out.println("Client connected..");

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream()));

                while(true)
                {
                    String msgfromclient = bufferedReader.readLine(); //receive msg from client
                    System.out.println("Client: " + msgfromclient);
                    sendMsg(bufferedWriter,"echo " + msgfromclient);

                    if (msgfromclient.equalsIgnoreCase("end"))
                    {
                        break;
                    }
                }
                closeEverything(socket, serverSocket, bufferedReader, bufferedWriter);
            }
            catch (IOException e) 
            {
                e.printStackTrace();
                closeEverything(socket, serverSocket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void sendMsg(BufferedWriter bufferedWriter, String msg)
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

    public void closeEverything(Socket socket, ServerSocket serverSocket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        try
        {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        int portno = 3000;
        try
        {
            ServerSocket serverSocket = new ServerSocket(portno);
            EchoServer server = new EchoServer(serverSocket);
            server.startServer();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
