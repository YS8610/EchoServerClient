import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient 
{
    public static void main(String[] args) 
    {
        
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        
        try 
        {
            socket = new Socket("127.0.0.1",3000); //Connect to server 
            
            inputStreamReader = new InputStreamReader(socket.getInputStream()); //read from server
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream()); //send stuff to server

            bufferedReader = new BufferedReader(inputStreamReader); // handle the data more efficient
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            
            Scanner scanner = new Scanner(System.in);
            
            while (true)
            {
                String msg = scanner.nextLine();

                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Server: " + bufferedReader.readLine());

                if (msg.equalsIgnoreCase("end"))
                {
                    scanner.close();
                    break;
                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (socket!=null) socket.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (outputStreamWriter != null) outputStreamWriter.close();
                if (bufferedReader != null) bufferedReader.close();
                if (bufferedWriter != null) bufferedWriter.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
