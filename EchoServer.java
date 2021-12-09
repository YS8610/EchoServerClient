import java.io.*;
import java.net.*;

public class EchoServer
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(3000); //listening to port 3000
        System.out.println("Server started. Waiting for connection...");
        
        while (true)
        {
            try 
            {
                socket = serverSocket.accept(); //waiting for client to connect
                System.out.println("Client connected..");
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true)
                {
                    String msgfromclient = bufferedReader.readLine(); //receive msg from client
                    System.out.println("Client: " + msgfromclient);
                    
                    bufferedWriter.write("echo " + msgfromclient);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if (msgfromclient.equalsIgnoreCase("end"))
                    {
                        break;
                    }
                }

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();
                serverSocket.close();
                break;

            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
